package com.example.mbd2Android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mbd2Android.Fragments.OverviewFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * onCreate methodes worden iedere keer opnieuw aangeroepen wanneer er een activity geinstanteerd wordt.
     * In deze methode wordt de te gebruiken layout aangeroepen.
     *
     * Bij de mHasOnePane wordt er gekeken of het apparaat in landscape- of portrait-modes is.
     * Wanneer het apparaat in portrait-modes is wordt het dynamische fragment vervangen met een OverviewFragment.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.checkPermission();

        setContentView(R.layout.activity_main);

        boolean mHasOnePane = !getResources().getBoolean(R.bool.dual_pane);
        if (mHasOnePane) {
            OverviewFragment mOverviewFragment = new OverviewFragment();
            replaceDynamicFragment(mOverviewFragment);
        }

    }

    /**
     * checkPermission controlleert of de benodigde bevoegdheden al zijn geaccepteerd.
     * Wanneer dit niet het geval is wordt er om de bevoegdheden gevraagd.
     */
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0x1);
        }
    }

    /**
     * onCreateOptionMenu wordt aangeroepen wanneer het optie menu wordt geinstantieerd.
     * Deze methode creeert een nieuwe 'inflater', waarna hij het menu instantieerd.
     * De inflater is verantwoordelijk voor het aanroepen van een component.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preferences_menu, menu);
        return true;
    }


    /**
     * onOptionsItemSelected start de PreferencesActivity wanneer er op het optie Menu geklikt wordt.
     * Dit resulteert in een navigatie naar de preferences pagina.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
        return true;
    }

    /**
     * replaceDynamicFragment vervangt de huidige overViewFragment met een detailFragment.
     *
     * @param dFragment
     */
    protected void replaceDynamicFragment(Fragment dFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, dFragment).commit();
    }
}

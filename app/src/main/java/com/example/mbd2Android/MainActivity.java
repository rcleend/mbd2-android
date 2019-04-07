package com.example.mbd2Android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mbd2Android.Fragments.OverviewFragment;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ARE_YOU_AWESOME = 0;

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

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            // permission already granted
            Log.d("FEEEST", "FEESt");
            return;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_ARE_YOU_AWESOME);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preferences_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
        return true;
    }

    protected void replaceDynamicFragment(Fragment dFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, dFragment).commit();
    }
}

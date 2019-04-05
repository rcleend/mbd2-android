package com.example.dynamicfragmentproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dynamicfragmentproject.Fragments.OverviewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean mHasOnePane = !getResources().getBoolean(R.bool.dual_pane);
        if (mHasOnePane){
            OverviewFragment mOverviewFragment = new OverviewFragment();
            replaceDynamicFragment(mOverviewFragment);
        }
    }

    protected void replaceDynamicFragment(Fragment dFragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, dFragment).commit();
    }
}

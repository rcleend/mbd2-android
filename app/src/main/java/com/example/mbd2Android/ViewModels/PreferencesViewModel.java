package com.example.mbd2Android.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.SharedPreferences;

import com.example.mbd2Android.R;

public class PreferencesViewModel extends AndroidViewModel {
    private int pageLimit;
    private SharedPreferences sharedPreferences;


    public PreferencesViewModel(Application application) {
        super(application);
        this.sharedPreferences = application.getSharedPreferences(application.getResources().getString(R.string.myPreferences), 0);
        this.pageLimit = sharedPreferences.getInt("cardLimit", 12);
    }

    public void saveCardLimit(int newValue) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("cardLimit", newValue);
        editor.commit();
    }

    public int getPageLimit() {
        return this.pageLimit;
    }
}

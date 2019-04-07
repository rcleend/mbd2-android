package com.example.mbd2Android.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.SharedPreferences;

import com.example.mbd2Android.R;

public class PreferencesViewModel extends AndroidViewModel {
    private int pageLimit;
    private SharedPreferences sharedPreferences;


    /**
     * De PreferenceViewModel is verantwoordelijk voor de logica achter de PreferenceActivity
     *
     * @param application
     */
    public PreferencesViewModel(Application application) {
        super(application);
        this.sharedPreferences = application.getSharedPreferences(application.getResources().getString(R.string.myPreferences), 0);
        this.pageLimit = sharedPreferences.getInt("cardLimit", 12);
    }

    /**
     * saveCardLimit slaat de nieuwe cardLimit op in de sharedPreferences
     * Om de sharedPreferences te wijzigen wordt er een editor geinstanteerd.
     * De reden hiervoor is omdat het wijzigen van preferences resource intensief is en
     * daarom het liefst zo min mogelijk gebruikt moet worden.
     *
     * @param newValue
     */
    public void saveCardLimit(int newValue) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("cardLimit", newValue);
        editor.commit();
    }
}

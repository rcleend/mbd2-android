package com.example.mbd2Android;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mbd2Android.ViewModels.PreferencesViewModel;


public class PreferencesActivity extends AppCompatActivity {
    private PreferencesViewModel viewModel;

    /**
     * In onCreate wordt het viewModel en de content gezet.
     * De viewModel biedt een extra abstractielaag tussen die Views en de Repositories.
     *
     * Tenslotte worden de EditText-input, toolbar en saveButton geconfigureerd.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this).get(PreferencesViewModel.class);
        setContentView(R.layout.activity_preferences);

        this.setupEditText();
        this.setupToolbar();
        this.setupSaveButton();
    }

    /**
     * setupEditText is verantwoordelijk voor het instantieren van de EditText.
     * De sharedPreferences vullen de inhoud van de EditText met de preference-waarde.
     */
    private void setupEditText() {
        EditText editText = findViewById(R.id.cardLimitInput);
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.myPreferences), 0);
        int cardLimit = sharedPreferences.getInt("cardLimit", 12);
        editText.setText("" + cardLimit);
    }

    /**
     * setupToolbar instantieerd de toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * setupToolbar instantieerd de saveButton
     */
    private void setupSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(createSaveButtonListener());
    }

    /**
     * createSaveButtonListener creeerd een OnClickListener voor de saveButton.
     *
     * @return
     */
    private View.OnClickListener createSaveButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCardLimit();
            }
        };
    }

    /**
     * saveCardLimit haalt de waarde uit de EditText en zet het om naar een Integer.
     * Wanneer de waarde van cardLimitValue hoger is dan 100 wordt het teruggezet naar 100.
     *
     * De cardLimitValue wordt doorgegeven naar de viewModel de deze waarde verder verwerkt.
     *
     * finish() zorgt ervoor dat de activity zich afsluit en dat er wordt terug genavigeerd naar de MainActivity.
     */
    private void saveCardLimit() {
        EditText cardLimitText = findViewById(R.id.cardLimitInput);
        String cardLimitStringValue = cardLimitText.getText().toString();
        int cardLimitValue = Integer.parseInt(cardLimitStringValue);

        if (cardLimitValue > 100) {
            cardLimitValue = 100;
        }
        viewModel.saveCardLimit(cardLimitValue);

        finish();
    }
}

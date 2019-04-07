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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this).get(PreferencesViewModel.class);
        setContentView(R.layout.activity_preferences);

        this.setupEditText();
        this.setupToolbar();
        this.setupSaveButton();
    }

    private void setupEditText() {
        EditText editText = findViewById(R.id.cardLimitInput);
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.myPreferences), 0);
        int cardLimit = sharedPreferences.getInt("cardLimit", 12);
        editText.setText("" + cardLimit);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(createSaveButtonListener());
    }

    private View.OnClickListener createSaveButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCardLimit();
            }
        };
    }

    private void saveCardLimit() {
        EditText cardLimitText = findViewById(R.id.cardLimitInput);
        String cardLimitStringValue = cardLimitText.getText().toString();
        int cardLimitValue = Integer.parseInt(cardLimitStringValue);
        viewModel.saveCardLimit(cardLimitValue);
        finish();
    }
}

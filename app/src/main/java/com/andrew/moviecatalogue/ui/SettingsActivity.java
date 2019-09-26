package com.andrew.moviecatalogue.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.notification.DailyReminderReceiver;
import com.andrew.moviecatalogue.notification.ReleaseReminderReceiver;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private DailyReminderReceiver dailyReminder = new DailyReminderReceiver();
    private ReleaseReminderReceiver releaseReminder = new ReleaseReminderReceiver();
    private TextView tvLanguageSettings;
    private SwitchCompat scDailyReminder;
    private SwitchCompat scReleaseReminder;
    private Boolean stateDailyReminder = false;
    private Boolean stateReleaseReminder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        scDailyReminder = findViewById(R.id.sc_daily_reminder);
        scReleaseReminder = findViewById(R.id.sc_release_reminder);

        tvLanguageSettings = findViewById(R.id.change_language_settings);
        tvLanguageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });

        sharedPreferences =  getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        stateDailyReminder = sharedPreferences.getBoolean("sc_daily_reminder", false);
        stateReleaseReminder = sharedPreferences.getBoolean("sc_release_reminder", false);

        scDailyReminder.setChecked(stateDailyReminder);
        scReleaseReminder.setChecked(stateReleaseReminder);

        scDailyReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateDailyReminder = !stateDailyReminder;
                sharedPreferences.edit().putBoolean("sc_daily_reminder", stateDailyReminder).apply();

                if (stateDailyReminder) {
                    dailyReminder.setAlarm(getApplicationContext());
                } else {
                    dailyReminder.cancelAlarm(getApplicationContext());
                }
            }
        });

        scReleaseReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateReleaseReminder = !stateReleaseReminder;
                sharedPreferences.edit().putBoolean("sc_release_reminder", stateReleaseReminder).apply();

                if (stateReleaseReminder) {
                    releaseReminder.setAlarm(getApplicationContext());
                } else {
                    releaseReminder.cancelAlarm(getApplicationContext());
                }
            }
        });



        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.settings);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

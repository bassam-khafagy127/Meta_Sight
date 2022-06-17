package com.example.metasight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;

import lib.folderpicker.FolderPicker;

public class SettingsActivity extends AppCompatActivity {

    private ToggleButton lang_state, theem_state;
    private CardView directory_chose, report_abug, send_feedback, about_us;
    private TextView version_numper;
    private ImageView settings_back;
    private String Audios_Location;
    private final int FOLDERPICKER_CODE = 1110;
    private SharedPreferences m_sharedPreferences;
    private final String SharedPrefFile = "com.example.metasight";
    private final String LANG_STATE_KEY = "lang_state";
    private final String MODE_STATE_KEY = "mode_state";
    private final String AUDIOS_LOCATION_KEY = "audios_location";
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        m_sharedPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        lang_state = findViewById(R.id.lang_state_toggle);
        theem_state = findViewById(R.id.dark_mood_stat_toggle);
        directory_chose = findViewById(R.id.directory_chose);
        report_abug = findViewById(R.id.report_abug);
        about_us = findViewById(R.id.about_us_page);
        send_feedback = findViewById(R.id.send_feedback);
        settings_back = findViewById(R.id.settigs_back);
        version_numper = findViewById(R.id.textView_version_n);
        //restore page state
        res_instance();
        //button back
        settings_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // about_us button
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
            }
        });

        //report bug button
        report_abug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "report a bug clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //send feedback button
        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "send feedback clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // language state
        lang_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lang_state.isChecked()) {

                    Toast.makeText(SettingsActivity.this, "Arabic Activated", Toast.LENGTH_SHORT).show();
                    speak("Arabic Activated");
                } else {
                    Toast.makeText(SettingsActivity.this, "English Activated", Toast.LENGTH_SHORT).show();
                    speak("English Activated");

                }
            }
        });
        theem_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theem_state.isChecked()) {

                    Toast.makeText(SettingsActivity.this, "light Activated", Toast.LENGTH_SHORT).show();
                    speak("light Activated");

                } else {
                    Toast.makeText(SettingsActivity.this, "dark Activated", Toast.LENGTH_SHORT).show();
                    speak("dark Activated");

                }
            }
        });

        //directory_chose
        directory_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "directory_chose clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), FolderPicker.class);
                intent.putExtra("title", "Select Directory to upload");

                //To begin from a selected folder instead of sd card's root folder. Example : DIRECTORY_DOWNLOADS directory
                intent.putExtra("location", Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageDirectory().getAbsolutePath()).getAbsolutePath());
                startActivityForResult(intent, FOLDERPICKER_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOLDERPICKER_CODE && resultCode == Activity.RESULT_OK) {
            Audios_Location = data.getExtras().getString("data");
            Log.i("folderLocation", Audios_Location);
            Toast.makeText(SettingsActivity.this, "directory_chose picked", Toast.LENGTH_SHORT).show();
        }
    }


    void res_instance() {
        Boolean Lang_State = m_sharedPreferences.getBoolean(LANG_STATE_KEY, true);
        Boolean Theme_State = m_sharedPreferences.getBoolean(MODE_STATE_KEY, true);
        lang_state.setChecked(Lang_State);
        theem_state.setChecked(Theme_State);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor pref_editor = m_sharedPreferences.edit();
        pref_editor.putBoolean(LANG_STATE_KEY, lang_state.isChecked());
        pref_editor.putBoolean(MODE_STATE_KEY, theem_state.isChecked());
        pref_editor.putString(AUDIOS_LOCATION_KEY, Audios_Location);
        pref_editor.apply();
    }

    public void speak(String text) {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
}
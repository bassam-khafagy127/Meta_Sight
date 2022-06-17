package com.example.metasight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.metasight.booklibrary.LibraryActivity;
import com.example.metasight.color_recognition.ClassifierActivityColor;
import com.example.metasight.ocr_tts.Converter_Activity;
import com.example.metasight.ocr_tts.OCR_Activity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageView Logo_img, action_menu;
    private CardView
            cardView_camera, cardView_converter, cardView_detector,
            cardView_color, cardView_money, cardView_library;
    private Toolbar toolbar;
    private SharedPreferences m_sharedPreferences;
    private final String Main_Tag = MainActivity.class.getSimpleName();
    private final String SharedPrefFile = "com.example.metasight";
    private final String LANG_STATE_KEY = "lang_state";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Customize Toolbar
        toolbar = findViewById(R.id.main_app_bar);
        //Set Toolbar
        setSupportActionBar(toolbar);
        action_menu = findViewById(R.id.action_icon);

        // go to about activity
        action_menu.setOnClickListener(v -> {
            Intent intentSwitcher = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intentSwitcher);
        });

        // shared pref
        m_sharedPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        //set language
        if (restore_instance()) {
            setLocale(this, "ar");
        }


        Logo_img = findViewById(R.id.logo_img);
        //Cards
        cardView_camera = findViewById(R.id.ocr_card);
        cardView_converter = findViewById(R.id.converter_card);
        cardView_detector = findViewById(R.id.detector_card);
        cardView_color = findViewById(R.id.color_card);
        cardView_money = findViewById(R.id.money_card);
        cardView_library = findViewById(R.id.library_card);

        //When Logo Clicked
        Logo_img.setOnClickListener(v -> {
            Log.d(Main_Tag, "Logo clicked");
        });

        cardView_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ocr = new Intent(getBaseContext(), OCR_Activity.class);
                startActivity(ocr);
            }
        });
        cardView_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_Convert = new Intent(getBaseContext(), Converter_Activity.class);
                startActivity(to_Convert);
            }
        });
        cardView_detector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detector_intent = new Intent(getBaseContext(), com.example.metasight.object_detection.DetectorActivity.class);
                startActivity(detector_intent);
            }
        });
        cardView_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent color_intent = new Intent(getBaseContext(), ClassifierActivityColor.class);
                startActivity(color_intent);
            }
        });
        cardView_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent money_Activity = new Intent(getBaseContext(), com.example.metasight.money_recognition.ClassifierActivity.class);
                startActivity(money_Activity);
            }
        });
        cardView_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent librarySwitcher = new Intent(getBaseContext(), LibraryActivity.class);
                startActivity(librarySwitcher);
            }
        });

    }

    Boolean restore_instance() {
        return m_sharedPreferences.getBoolean(LANG_STATE_KEY, true);
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (restore_instance() == true) {
            setLocale(this, "ar");
        } else
            setLocale(this, "en");
    }
}
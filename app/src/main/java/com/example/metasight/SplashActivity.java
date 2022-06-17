package com.example.metasight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    Animation logo_animation, text_animation;
    ImageView splash_logo;
    TextView welcome_message;
    private final int SplashActivityDelay = 3500;
    private final String LANG_STATE_KEY = "lang_state";

    private SharedPreferences m_sharedPreferences;
    private final String SharedPrefFile = "com.example.metasight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        m_sharedPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        if (restore_instance() == true) {
            setLocale(this, "ar");
        } else
            setLocale(this, "en");

        logo_animation = AnimationUtils.loadAnimation(this, R.anim.move_left);
        text_animation = AnimationUtils.loadAnimation(this, R.anim.move_right);
        splash_logo = findViewById(R.id.imageView_splash);
        welcome_message = findViewById(R.id.welcome_message_tv);
        splash_logo.startAnimation(logo_animation);
        welcome_message.startAnimation(text_animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SplashActivityDelay);
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    Boolean restore_instance() {
        return m_sharedPreferences.getBoolean(LANG_STATE_KEY, true);
    }


}
package com.example.metasight.ocr_tts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.metasight.R;

public class Converter_Activity extends AppCompatActivity {
    Toolbar actionbar;
    CardView sound, pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        actionbar = findViewById(R.id.converter_action_bar);
        actionbar.setTitle("Converter");
        actionbar.setTitleTextColor(getResources().getColor(R.color.text));
        actionbar.setTitleTextColor(getResources().getColor(R.color.text));
        actionbar.setPadding(20, 40, 0, 5);
        actionbar.setPadding(10, 5, 0, 5);
        actionbar.setNavigationIcon(R.drawable.arrow_back);
        //Set Toolbar
        setSupportActionBar(actionbar);
        actionbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sound = findViewById(R.id.sound_lis);
        pdf = findViewById(R.id.pdf_lis);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_tts = new Intent(getBaseContext(), Text_To_Sound.class);
                startActivity(to_tts);
            }
        });
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent to_ocr = new Intent(getBaseContext(), Pdf_to_Text_Activity.class);
                startActivity(to_ocr);

            }
        });
    }
}
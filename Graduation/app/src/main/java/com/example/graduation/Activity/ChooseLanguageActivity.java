package com.example.graduation.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.R;

import java.util.Locale;

import cn.bmob.v3.Bmob;

public class ChooseLanguageActivity extends AppCompatActivity {

    private Spinner choose_language;
    private Button confirm_language;
    private static int last_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        choose_language = this.findViewById(R.id.sp_choose_language);
        choose_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Resources resources = getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration config = resources.getConfiguration();
                switch (i){
                    case 0:
                        config.locale = Locale.SIMPLIFIED_CHINESE;
                        resources.updateConfiguration(config, dm);
                        if(i != last_position){
                            last_position = i;
                            recreate();
                        }
                        break;
                    case 1:
                        config.locale = Locale.TRADITIONAL_CHINESE;
                        resources.updateConfiguration(config, dm);
                        if(i != last_position){
                            last_position = i;
                            recreate();
                        }
                        break;
                    case 2:
                        config.locale = Locale.ENGLISH;
                        resources.updateConfiguration(config, dm);
                        if(i != last_position){
                            last_position = i;
                            recreate();
                        }
                        break;
                    case 3:
                        config.locale = Locale.GERMAN;
                        resources.updateConfiguration(config, dm);
                        if(i != last_position){
                            last_position = i;
                            recreate();
                        }
                        break;
                    case 4:
                        config.locale = Locale.JAPANESE;
                        resources.updateConfiguration(config, dm);
                        if(i != last_position){
                            last_position = i;
                            recreate();
                        }
                        break;
                    case 5:
                        config.locale = Locale.KOREAN;
                        resources.updateConfiguration(config, dm);
                        if(i != last_position){
                            last_position = i;
                            recreate();
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        choose_language.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        confirm_language = this.findViewById(R.id.bt_confirm_language);
        confirm_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLanguageActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}



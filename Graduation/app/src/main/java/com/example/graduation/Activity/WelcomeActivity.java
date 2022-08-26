package com.example.graduation.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class WelcomeActivity extends AppCompatActivity {

    private boolean isFirstUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        SharedPreferences preferences = getSharedPreferences("isFirstUse",MODE_PRIVATE);
        isFirstUse = preferences.getBoolean("isFirstUse", true);
        if (isFirstUse) {
            startActivity(new Intent(WelcomeActivity.this, ChooseLanguageActivity.class));
        } else {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        }
        finish();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstUse", false);
        editor.commit();
    }
}

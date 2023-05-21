package com.shubhamgupta16.simplewallpaper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.shubhamgupta16.simplewallpaper.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Within SPLASH_SCREEN_TIMEOUT, redirect to MainActivity if ad not shown
        handler.postDelayed(runnable, getResources().getInteger(R.integer.splash_screen_timeout));
    }

    private final Runnable runnable = this::startMainActivity;

    public void startMainActivity() {
        //Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.pref_sign_up), Context.MODE_PRIVATE);
        boolean isLogged = sharedPref.getBoolean("isLogged", false);
        Intent intent;
        if (isLogged) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

        finish();
    }

    public void notifyAdShown() {
        // don't redirect automatically
        handler.removeCallbacks(runnable);
    }

    public void notifyAdComplete() {
        startMainActivity();
    }
}
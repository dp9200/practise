package com.example.lunchver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.lunchver2.controller.Processor;

public class WelcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        SharedPreferences sharedPreferences = getSharedPreferences("pick_item_data", Context.MODE_PRIVATE);
        Processor.getMainProcessor().initSavedData(sharedPreferences);
        delayToMainActivity();
    }

    private void delayToMainActivity() {
        Runnable runnable = getChangeActivityRunnable();
        Handler handler = new Handler();
        handler.postDelayed(runnable,2000);
    }

    private Runnable getChangeActivityRunnable()
    {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }
}
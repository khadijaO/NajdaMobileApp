package com.example.najdaapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_activity extends AppCompatActivity {
    private static int TIME_OUT = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

//        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView textView=findViewById(R.id.textView);
        textView.animate().translationX(1000).setDuration(1000).setStartDelay(2000);
        TextView textView2=findViewById(R.id.textView2);
        textView2.animate().translationX(2000).setDuration(2000).setStartDelay(2000);
        final View myLayout = findViewById(R.id.startscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }


}

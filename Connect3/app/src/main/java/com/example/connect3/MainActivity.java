package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView heading=findViewById(R.id.heading);
        heading.animate().alpha(0.85f).setDuration(2000);
        new Handler().postDelayed(
                new Runnable(){
                    @Override
                    public void run(){
                        Intent home_intent=new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(home_intent);
                        finish();
                    }
                },SPLASH_TIME_OUT
        );
    }
}

package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    SeekBar time;
    MediaPlayer sound;
    boolean is_active=false;
    CountDownTimer countDownTimer;
    public String makestr(int seconds){
        String secondstring=Integer.toString(seconds);
        if(secondstring.length()==1){
            return "0"+seconds;
        }else{
            return Integer.toString(seconds);
        }
    }
    public void end(){
        ImageView img=findViewById(R.id.img2);
        img.animate().alpha(1f).setDuration(1000);
        sound.start();
        time.setEnabled(true);
        is_active=false;
        Button b1=findViewById(R.id.button);
        b1.setText("GO");
    }
    public void onclick(View v) {

        if(!is_active) {
            is_active=true;
            Button b1=findViewById(R.id.button);
            b1.setText("STOP");
            ImageView img2 = findViewById(R.id.img2);
            img2.setImageResource(R.drawable.egg_cooked);
            img2.animate().alpha(0f).setDuration(500);
            time.setEnabled(false);
            countDownTimer=new CountDownTimer(time.getProgress() * 1000, 1000) {
                public void onTick(long time_remaining) {
                    TextView output = findViewById(R.id.Output);
                    long minutes = time_remaining / (60 * 1000);
                    long seconds = (time_remaining - (minutes * 60 * 1000)) / 1000;
                    output.setText(minutes + " : " + makestr((int) seconds));
                }

                public void onFinish() {
                    end();
                }
            }.start();
        }else{
            countDownTimer.cancel();
            time.setProgress(0);
            time.setEnabled(true);
            TextView output = findViewById(R.id.Output);
            output.setText("0 : 00");
            Button b1=findViewById(R.id.button);
            b1.setText("GO");
            is_active=false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img2 = findViewById(R.id.img2);
        img2.setImageResource(R.drawable.egg_cooked);
        img2.setAlpha(0f);
        time=findViewById(R.id.time);
        time.setMax(600);
        sound=MediaPlayer.create(this,R.raw.airhorn);
        time.setProgress(0);
        TextView out=findViewById(R.id.Output);
        out.setText("0 : 00");
        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean User) {
                TextView output = findViewById(R.id.Output);
                int minutes=position/60;
                int seconds=(position-minutes*60);
                output.setText(minutes+" : "+makestr(seconds));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

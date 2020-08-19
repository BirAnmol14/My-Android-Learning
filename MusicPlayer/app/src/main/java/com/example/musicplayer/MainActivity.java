package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer song;
    SeekBar vol_cont;
    AudioManager audioManager;
    SeekBar time_cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        song = MediaPlayer.create(this, R.raw.tommy);
        vol_cont=findViewById(R.id.vol);
        time_cont=findViewById(R.id.move);
        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max_vol=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curr_vol=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        vol_cont.setMax(max_vol);
        vol_cont.setProgress(curr_vol);
        vol_cont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        int end=song.getDuration();
        time_cont.setMax(end);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time_cont.setProgress(song.getCurrentPosition());
            }
        }, 0, 1000);
        time_cont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
                if(fromuser){song.seekTo(progress);}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               song.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                song.start();
            }
        });

    }
    int state = 1;
    public void playsong(View v) {
        Button b1 = findViewById(R.id.play);
        if (state == 1) {
            song.start();
            b1.setText("Pause");
            state=0;
        }else{
            b1.setText("Play");
            song.pause();
            state=1;
        }
    }
}
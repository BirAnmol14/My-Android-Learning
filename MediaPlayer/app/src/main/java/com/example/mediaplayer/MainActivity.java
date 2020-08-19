package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView vid=findViewById(R.id.videoView);
        vid.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.s9_vid);
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(vid);
        vid.setMediaController(mediaController);
        vid.start();
    }
}

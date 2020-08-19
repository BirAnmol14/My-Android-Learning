package com.my.flappybird;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.my.flappybird.FlappyBird;
/*
Flappy Bird Code lies in 
FlappyBirdClone-libGDX\core\src\com\my\flappybird
*/
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FlappyBird(), config);
	}
}

package com.my.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import javax.xml.soap.Text;
import java.util.Random;
public class FlappyBird extends ApplicationAdapter {
	//ShapeRenderer shapeRenderer;
	Texture gameover;
	int score=0;
	int scoringtube=0;
	SpriteBatch batch;
	Texture background;
	Texture[]birds;
	Texture bottom_tube;
	Texture upper_tube;
	int flap_state=0;
	float velocity=0;
	float birdY=0;
	int game_state=0;
	float gravity=1.5f;
	float gap=430;
	float max_tube_offset;
	Random random;
	float tube_velocity;
	int tube_count=4;
	float[] tubeX;
	float dist_between_tubes;
	float [] arr;
	float[] tube_offsets=new float[tube_count];
	Circle bird_circle;
	Rectangle[] pipe_top;
	Rectangle[] pipe_bottom;
	BitmapFont font;
	@Override
	public void create () {
		//shapeRenderer=new ShapeRenderer();
		bird_circle=new Circle();
		pipe_top=new Rectangle[tube_count];
		pipe_bottom=new Rectangle[tube_count];
		batch = new SpriteBatch();
		background=new Texture("bg.png");
		birds=new Texture[2];
		birds[0]=new Texture("bird.png");
		birds[1]=new Texture("bird2.png");
		bottom_tube=new Texture("bottomtube.png");
		upper_tube=new Texture("toptube.png");
		max_tube_offset=Gdx.graphics.getHeight()-gap/2-100;
		random=new Random();
		tube_velocity=4;
		dist_between_tubes=Gdx.graphics.getWidth()*0.60f;
		arr=new float[tube_count];
		tubeX=new float[tube_count];
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().scale(10);
		gameover=new Texture("gameover.png");
		startgame();

	}//on app launch
	public void startgame(){
		birdY=Gdx.graphics.getHeight() / 2 - birds[flap_state].getHeight() / 2;
		for(int i=0;i<tube_count;i++){
			tube_offsets[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-200);//We want fractions btw -0.5 and 0.5 as 0 is the screen center and more over this will be within maxoffset limits'
			tubeX[i]=Gdx.graphics.getWidth() / 2 - upper_tube.getWidth() / 2+i*dist_between_tubes+Gdx.graphics.getWidth()*0.65f;
			pipe_top[i]=new Rectangle();
			pipe_bottom[i]=new Rectangle();
		}
	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Gravity method
		if(game_state==1) {
			if(tubeX[scoringtube]<Gdx.graphics.getWidth()/2){
				score++;
				System.out.println("Score: "+score);
				if(scoringtube<tube_count-1){
					scoringtube++;
				}else{
					scoringtube=0;
				}
			}
			if(Gdx.input.justTouched()){
				velocity=-25;
			}
			for(int i=0;i<tube_count;i++) {

				if(tubeX[i]<-bottom_tube.getWidth()){
					tubeX[i]+=tube_count*dist_between_tubes;
					tube_offsets[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-200);
				}else {
					tubeX[i] -= tube_velocity;
				}
				batch.draw(upper_tube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tube_offsets[i]);
				batch.draw(bottom_tube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottom_tube.getHeight() + tube_offsets[i]);
				pipe_top[i].set(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tube_offsets[i],upper_tube.getWidth(),upper_tube.getHeight());
				pipe_bottom[i].set(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottom_tube.getHeight() + tube_offsets[i],bottom_tube.getWidth(),bottom_tube.getHeight());

			}
			if(birdY>0) {
				velocity += gravity;
				birdY -= velocity;

			}else{
				game_state=2;
			}
		}else if(game_state==0){
			if(Gdx.input.justTouched()){
				game_state=1;
			}
		}else if(game_state==2){
			batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2-gameover.getHeight()/2);
			if(Gdx.input.justTouched()){
				game_state=1;
				startgame();
				score=0;
				scoringtube=0;
				velocity=0;
			}
		}
		if(flap_state==0) {

			flap_state=1;
		}else{

			flap_state=0;
		}

		batch.draw(birds[flap_state], Gdx.graphics.getWidth() / 2 - birds[flap_state].getWidth() / 2,birdY );

		font.draw(batch,String.valueOf(score),100,200);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);

		bird_circle.set(Gdx.graphics.getWidth()/2,birdY+birds[flap_state].getHeight()/2,birds[flap_state].getWidth()/2);

		//shapeRenderer.circle(bird_circle.x,bird_circle.y,bird_circle.radius);
		//shapeRenderer.setColor(Color.BLUE);
		for (int i=0;i<tube_count;i++){
			//shapeRenderer.rect(pipe_top[i].x,pipe_top[i].y,pipe_top[i].width,pipe_top[i].height);
			//shapeRenderer.rect(pipe_bottom[i].x,pipe_bottom[i].y,pipe_bottom[i].width,pipe_bottom[i].height);
			if(Intersector.overlaps(bird_circle,pipe_top[i])||Intersector.overlaps(bird_circle,pipe_bottom[i])){
				game_state=2;
			}
		}
		//shapeRenderer.end();
		batch.end();
	}//as animation occurs
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}

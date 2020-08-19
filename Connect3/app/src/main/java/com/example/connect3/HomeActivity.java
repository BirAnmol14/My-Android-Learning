package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    int active_player=0;
    int[] state={2,2,2,2,2,2,2,2,2};
    //0 is earth,1 is moon
    int[][]winning_positions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};//on the 2d grid
    boolean game_active=true;
    public void dropin(View v){
        TextView turn=findViewById(R.id.player);
        LinearLayout playagain=findViewById(R.id.playagainlayout);
        TextView output=findViewById(R.id.output);
        Button button=findViewById(R.id.button);
        ImageView counter=(ImageView)v;//the image view that was clicked on,no need for id
        System.out.println(counter.getTag().toString());
        int tag= Integer.parseInt(counter.getTag().toString());
        if (state[tag]==2&&game_active)
        {
            state[tag]=active_player;
            counter.setTranslationY(-2000f);
            if (active_player == 0) {
                counter.setImageResource(R.drawable.earth);
                active_player = 1;
                turn.setText("Turn Player Two");
            }
            else if(active_player==1){
                counter.setImageResource(R.drawable.moon);
                active_player = 0;
                turn.setText("Turn Player One");
            }
            counter.animate().translationYBy(2000f).setDuration(400);

            for(int[] arr:winning_positions){
                if((state[arr[0]] == state[arr[1]]) && (state[arr[1]] == state[arr[2]]) && (state[arr[0]] != 2)){
                    if(state[arr[0]]==0){
                        output.setText("Earth Won");
                        button.setText("Play Again");
                        playagain.setVisibility(View.VISIBLE);
                        playagain.animate().alpha(1).setDuration(1000);

                    }else {
                        output.setText("Moon Won");
                        button.setText("Play Again");
                        playagain.setVisibility(View.VISIBLE);
                        playagain.animate().alpha(1).setDuration(1000);

                    }
                    game_active=false;
                }else{
                    boolean game_over=true;
                    for(int i:state){
                        if(i==2){
                            game_over=false;
                            break;
                        }
                    }
                    if(game_over){
                        output.setText("Tie");
                        button.setText("Play Again");
                        playagain.setVisibility(View.VISIBLE);
                        playagain.animate().alpha(1).setDuration(1000);
                    }
                }
            }
        }
        else if(state[tag]==0&&game_active){
            counter.setImageResource(R.drawable.earth);
            Toast.makeText(HomeActivity.this,"Wrong Move!",Toast.LENGTH_LONG).show();
        }
        else if(state[tag]==1&&game_active){
            counter.setImageResource(R.drawable.moon);
            Toast.makeText(HomeActivity.this, "Wrong Move!", Toast.LENGTH_LONG).show();
        }
    }
    public void play(View v){
        System.out.println("I am here!");
        game_active=true;
        LinearLayout ly=findViewById(R.id.playagainlayout);
        ly.setVisibility(View.INVISIBLE);

        for(int i=0;i<state.length;i++){
            state[i]=2;

        }
        active_player=0;
        TextView turn=findViewById(R.id.player);
        turn.setText("Turn Player One");
        System.out.println("I was here");
        GridLayout gridlayout=findViewById(R.id.mygrid);
       for(int i=0;i<gridlayout.getChildCount();i++){
            ((ImageView) gridlayout.getChildAt(i)).setImageResource(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

}

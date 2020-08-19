package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int correct_count=0;
    int correct_answer;
    int total_count=0;
    int[] answers={0,0,0,0};
    public void check_answer(View v){
        TextView score=findViewById(R.id.score);
        TextView out=findViewById(R.id.output);
        int tag=Integer.parseInt(v.getTag().toString());
        if(answers[tag]==correct_answer){
            correct_count++;
            total_count++;
            score.setText(correct_count+" / "+total_count);
            out.setText("Correct!");
            generate_ques();
        }else{
            total_count++;
            out.setText("Wrong!");
            score.setText(correct_count+" / "+total_count);
            generate_ques();
        }

    }
    public void set_answer(int position){
        Button b1=findViewById(R.id.button0);
        Button b2=findViewById(R.id.button1);
        Button b3=findViewById(R.id.button2);
        Button b4=findViewById(R.id.button3);
        switch (position){
            case 0: b1.setText(String.valueOf(answers[position]));break;
            case 1: b2.setText(String.valueOf(answers[position]));break;
            case 2: b3.setText(String.valueOf(answers[position]));break;
            case 3: b4.setText(String.valueOf(answers[position]));break;
        }
    }
    public void generate_ques(){
        int i=new Random().nextInt(50)+1;
        int j=new Random().nextInt(50)+1;
        int answer=i+j;
        int position=new Random().nextInt(4);
        TextView problem=findViewById(R.id.problem);
        problem.setText(i+" + "+j);
        for(int k=0;k<answers.length;k++){
            if(k==position){
                answers[k]=answer;
                correct_answer=answer;
                set_answer(k);

            }else{
                answers[k]=new Random().nextInt(100)+1;
                while ((answers[k]==answer)){
                    answers[k]=new Random().nextInt(100)+1;
                }
                set_answer(k);
            }
        }

    }
    public void end(){
        Button b1=findViewById(R.id.button0);
        b1.setClickable(false);
        Button b2=findViewById(R.id.button1);
        b2.setClickable(false);
        Button b3=findViewById(R.id.button2);
        b3.setClickable(false);
        Button b4=findViewById(R.id.button3);
        b4.setClickable(false);
        Button replay=findViewById(R.id.restart);
        replay.setVisibility(View.VISIBLE);
        TextView out=findViewById(R.id.output);
        TextView score=findViewById(R.id.score);
        TextView problem=findViewById(R.id.problem);
        problem.setText("");
        out.setText("Final "+score.getText());
        score.setText("");
        total_count=0;
        correct_count=0;
    }
    public void game(View view) {
        Button b1=findViewById(R.id.button0);
        b1.setClickable(true);
        Button b2=findViewById(R.id.button1);
        b2.setClickable(true);
        Button b3=findViewById(R.id.button2);
        b3.setClickable(true);
        Button b4=findViewById(R.id.button3);
        b4.setClickable(true);
        TextView out=findViewById(R.id.output);
        out.setText("");
        Button replay=findViewById(R.id.restart);
        replay.setVisibility(View.INVISIBLE);
        final TextView time = findViewById(R.id.time);
        TextView ques = findViewById(R.id.problem);
        TextView score = findViewById(R.id.score);
        score.setText("Score: 0");
        time.setText("Time: 30s");
        generate_ques();
        new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long time_left) {
                time.setText("Time: " + Long.toString(time_left / 1000));
            }

            @Override
            public void onFinish() {
                end();
            }
        }.start();
    }

    public void gamestart(View v){
            FrameLayout fl = findViewById(R.id.frameLayout);
            fl.setVisibility(View.VISIBLE);
            Button startbut = findViewById(R.id.startbut);
            startbut.setVisibility(View.GONE);
            game(v);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout fl=findViewById(R.id.frameLayout);
        fl.setVisibility(View.INVISIBLE);
        Button startbut=findViewById(R.id.startbut);
        startbut.setVisibility(View.VISIBLE);
    }
}

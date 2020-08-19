package com.example.guessinggame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import java.util.Random;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    int random_num;
    int guesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand=new Random();
        random_num = rand.nextInt(10) + 1;
        guesses=0;
    }


    public void clickFunction(View v){
        Button b1=findViewById(R.id.button);
        TextView tv1=findViewById(R.id.tv2);
        TextView tv2=findViewById(R.id.output);
        EditText input=findViewById(R.id.input);
        if(b1.getText().toString()=="Restart")
        {
            Random rand=new Random();
            random_num = rand.nextInt(10) + 1;
            guesses=0;
            input.setText("");
            tv2.setText("");
            b1.setText("Guess");
            tv1.setText("Guess Number: "+(guesses+1));
        }
        else {
            if (guesses < 2) {
                guesses++;
                tv2.setText("");
                int num = Integer.parseInt(input.getText().toString());
                if (num == random_num) {
                    tv2.setText("You Guessed Correctly! " + random_num);
                    b1.setText("Restart");
                    guesses = 0;
                } else if (num > random_num) {
                    Toast.makeText(MainActivity.this, "Your number > my number", Toast.LENGTH_LONG).show();
                    input.setText("");
                    tv1.setText("Guess Number: " + (guesses + 1));
                } else {
                    Toast.makeText(MainActivity.this, "Your number < my number", Toast.LENGTH_LONG).show();
                    input.setText("");
                    tv1.setText("Guess Number: " + (guesses + 1));
                }
            } else {
                tv2.setText("You are Out of tries");
                b1.setText("Restart");
                Toast.makeText(MainActivity.this, "Correct Number: " + random_num, Toast.LENGTH_LONG).show();
                tv1.setText("");
                input.setText("");
                guesses = 0;
            }
        }
    }

}

package com.example.guesstheceleb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean first_time=true;
    List source=new ArrayList();
    List names=new ArrayList();
    int[] answers=new int[4];
    Bitmap my_img;
    int answer;
    public void store_data(String html){
        System.out.println("Data extraction Start");
        Pattern p=Pattern.compile("img src=\"(.*?)\"");
        Matcher m=p.matcher(html);

        while (m.find()){
            source.add(m.group(1));
        }
        p=Pattern.compile("alt=\"(.*?)\"/");
        m=p.matcher(html);
        while (m.find()){
          names.add(m.group(1));
        }
    }

    public void check_answer(View v){
       int tag=Integer.parseInt(v.getTag().toString());
       try {
           if (answers[tag]==answer) {
               Toast.makeText(this, "Right, Well Done!", Toast.LENGTH_SHORT).show();
               game();
           } else {
               Toast.makeText(this, "Wrong, right answer is: " + names.get(answer), Toast.LENGTH_SHORT).show();
               game();
           }
       }catch (Exception e){
           System.out.println("failed here");
           game();
       }

    }
    public void set_answer_button(){
        if(names.get(answer).toString().equals("Kenza")){
            game();
        }
        Button b1=findViewById(R.id.b1);
        b1.setText(names.get(answers[Integer.parseInt(b1.getTag().toString())]).toString());
        Button b2=findViewById(R.id.b2);
        b2.setText(names.get(answers[Integer.parseInt(b2.getTag().toString())]).toString());
        Button b3=findViewById(R.id.b3);
        b3.setText(names.get(answers[Integer.parseInt(b3.getTag().toString())]).toString());
        Button b4=findViewById(R.id.b4);
        b4.setText(names.get(answers[Integer.parseInt(b4.getTag().toString())]).toString());
    }
    public void game(){
        int len=names.size();
        System.out.println();
        Random random=new Random();
        answer= random.nextInt(len);
        ImageView img=findViewById(R.id.pic);
        imgDownloader pcd=new imgDownloader();
        try {
            String url_img=source.get(answer).toString();
            my_img=pcd.execute(url_img).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        img.setImageBitmap(my_img);
        int num=random.nextInt(4);
        for(int i=0;i<4;i++){
            if(i==num){
                answers[i]=answer;
            }else{
                int wrong_answer=random.nextInt(len);
                while (wrong_answer==answer){
                    wrong_answer=random.nextInt(len);
                }
                answers[i]=wrong_answer;
            }
        }

        set_answer_button();
    }

    public void run(){
        String result=null;
        pageDownloader pgd=new pageDownloader();
        try {
            System.out.println("Page load Start");
            result=pgd.execute("http://www.posh24.se/kandisar").get();
        }catch (InterruptedException e){
                e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        store_data(result);
       game();
    }
    public class pageDownloader extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection=null;
            System.out.println("Background Start");
            try {
                url = new URL(strings[0]);
                System.out.println("Here");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                System.out.println(data);
                String result="";
                while (data!=-1){
                    char curr=(char) data;
                    result+=curr;
                    data=reader.read();
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Failed");
            }
            return null;
        }
    }
  public class imgDownloader extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=url.openStream();
                my_img= BitmapFactory.decodeStream(inputStream);
                return my_img;
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(" App Start");
       if(first_time) {
           run();
           first_time=false;
       }else{
           game();
       }
    }
}

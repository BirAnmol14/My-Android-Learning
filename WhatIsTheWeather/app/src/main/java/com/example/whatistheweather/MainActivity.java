package com.example.whatistheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
public class MainActivity extends AppCompatActivity {
    public void check(View v){
        InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        EditText input=findViewById(R.id.input);
        mgr.hideSoftInputFromWindow(input.getWindowToken(),0);//hiding keyboard on button press
        TextView output=findViewById(R.id.output);
        Data task=new Data();
	String API_KEY="";//Add Api Key here
        String encodedcity="";

        try {
            encodedcity= URLEncoder.encode(input.getText().toString(),"UTF-8");
          task.execute("https://samples.openweathermap.org/data/2.5/weather?q=" + encodedcity+"&appid="+API_KEY);
        }catch (Exception e){
            e.printStackTrace();
            output.setText("Wrong Input");
        }

    }
    public class Data extends AsyncTask<String,Void,String > {
        URL url;
        TextView output=findViewById(R.id.output);
        HttpURLConnection urlConnection;
        String result="";
        @Override
        protected String doInBackground(String... strings) {

            try{
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream io=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(io);
                int data=reader.read();
                while (data!=-1){
                    result+=(char)data;
                    data=reader.read();
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                String message="";
                JSONObject obj = new JSONObject(s);
                String weatherinfo=obj.getString("weather");
                JSONArray arr=new JSONArray(weatherinfo);

                for(int i=0;i<arr.length();i++){
                    JSONObject jsonpart=arr.getJSONObject(i);
                    String main="",description="";
                    main=jsonpart.getString("main");
                    description=jsonpart.getString("description");
                    if(main!=""&&description!=""){
                        message=main+": "+description+"\r\n";
                    }
                }
                if(message!=""){
                    output.setText(message);
                }
            }catch (Exception e){
                e.printStackTrace();
                output.setText("Wrong Input");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

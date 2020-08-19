package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView my_list;
    static ArrayList<String> news_titles=new ArrayList<>();
    static ArrayList<String> news_content=new ArrayList<>();
    static ArrayAdapter adapter;
    SQLiteDatabase news_database;
    public void update_list(){
        Cursor c=news_database.rawQuery("SELECT * FROM news",null);
        int title_index=c.getColumnIndex("title");
        int content_index=c.getColumnIndex("content");
        if(c.moveToFirst()){
            news_titles.clear();
            news_content.clear();
            do{
                news_titles.add(c.getString(title_index));
                news_content.add(c.getString(content_index));
            }while (c.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_list=findViewById(R.id.lv);
        adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,news_titles);
        my_list.setAdapter(adapter);
        news_database=this.openOrCreateDatabase("News",MODE_PRIVATE,null);
        news_database.execSQL("CREATE TABLE IF NOT EXISTS news (id INTEGER PRIMARY KEY,title VARCHAR,content VARCHAR,news_id INTEGER)");
        update_list();
        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NewsViewer.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });
        top_stories task1=new top_stories();
        try {
            task1.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class top_stories extends AsyncTask<String,Void,String>{
        URL url;
        String html_data;
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... urls) {
            try {
                url = new URL(urls[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream io=urlConnection.getInputStream();
                InputStreamReader io_reader=new InputStreamReader(io);
                int data=io_reader.read();
                while (data!=-1){
                    html_data+=(char)data;
                    data=io_reader.read();
                }
                String crap="null";
                html_data=html_data.substring(crap.length(),html_data.length());
                JSONArray array=new JSONArray(html_data);
                int max_item=3;
                if(array.length()<max_item){
                    max_item=array.length();
                }
                news_database.execSQL("DELETE FROM news");//deleting previous data before adding new data if any
                for(int i=0;i<max_item;i++){
                    int id=array.getInt(i);
                    url=new URL("https://hacker-news.firebaseio.com/v0/item/"+id+".json?print=pretty");
                    String news_data="";
                    urlConnection=(HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    InputStream is=urlConnection.getInputStream();
                    InputStreamReader is_reader=new InputStreamReader(is);
                    int new_data=is_reader.read();
                    while (new_data!=-1){
                        news_data+=(char)new_data;
                        new_data=is_reader.read();
                    }
                    JSONObject object=new JSONObject(news_data);
                    if(!object.isNull("title")&&!object.isNull("url")) {
                        url=new URL(object.getString("url"));
                        String news_content="";
                        urlConnection=(HttpURLConnection) url.openConnection();
                        urlConnection.connect();
                        InputStream ios=urlConnection.getInputStream();
                        InputStreamReader ios_reader=new InputStreamReader(ios);
                        int newd=ios_reader.read();
                        while (newd!=-1){
                            news_content+=(char)newd;
                            newd=ios_reader.read();
                        }
                        String sql = "INSERT INTO news(title,content,news_id) VALUES (?,?,?)";
                        SQLiteStatement statement = news_database.compileStatement(sql);
                        statement.bindString(1, object.get("title").toString());
                        statement.bindString(2, news_content);
                        statement.bindString(3, String.valueOf(id));
                        statement.execute();
                    }

                }
                return html_data;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            update_list();
        }
    }
}

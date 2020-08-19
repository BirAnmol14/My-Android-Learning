package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsViewer extends AppCompatActivity {
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_viewer);
        WebView webView=findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent=getIntent();
        index=intent.getIntExtra("index",-1);
        if(index!=-1){
            String data=MainActivity.news_content.get(index);
            webView.loadData(data,"text/html","UTF-8");
        }
    }
}

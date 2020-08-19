package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView=findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);//majority websites use java script
        webView.setWebViewClient(new WebViewClient());//so that this does not jump to default browser
        webView.loadUrl("https://www.youtube.com/");//loading external url
        //webView.loadData("<html><body><h1>My Page</h1><p>It works yo</p></body></html>","text/html","UTF-8");
        //External URL
    }
}

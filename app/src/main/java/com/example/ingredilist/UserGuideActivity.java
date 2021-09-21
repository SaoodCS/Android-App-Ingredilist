package com.example.ingredilist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class UserGuideActivity extends AppCompatActivity {
//Userguide displays html files of the user guide within the assets folder of this app, all displayed within a webview of this activity's screen.
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        String userGuideURL = "file:///android_asset/UserGuide.html";
        webView = (WebView) this.findViewById(R.id.webView);
        // javascript enabled to improve responsiveness of the webpages.
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(userGuideURL);
    }
}
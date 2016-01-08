package com.example.lp.rest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Listener click bouton1
    public void onUrlButtonClick(View view){
        webview = (WebView) findViewById(R.id.webView);
        webview.loadUrl("http://10.0.2.2:80/androidlpmetinet/villes.php");
    }

    public void onHtmlButtonClick(View view){
        webview = (WebView) findViewById(R.id.webView);
        String strHtml = "<html><body><b> Ceci est un texte au format HTML </b></br>qui s'affiche très simplement</body></html>";
        webview.loadData(strHtml , "text/html; charset=utf-8", "UTF-8");
    }
}

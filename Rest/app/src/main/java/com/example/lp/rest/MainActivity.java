package com.example.lp.rest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et;
    ListView lv;
    WebView webview;
    //ArrayList<HashMap<String, HashMap<String, String>>> list;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter mSchedule;
    MainActivity current;

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
        webview.loadData(strHtml, "text/html; charset=utf-8", "UTF-8");
    }

    public void onAsyncButtonClick(View view) throws IOException {
        URL url = new URL("http://10.0.2.2:80/androidlpmetinet/villes.php");
        lv = (ListView) findViewById(R.id.listView);
        list = new ArrayList<HashMap<String, String>>();
        mSchedule = new SimpleAdapter(getApplicationContext(), list, R.layout.list_view,
                new String[] {"titre", "ville"}, new int[] { R.id.titre, R.id.ville });
        new MyAsyncTask(mSchedule, list).execute(url, lv, mSchedule);
        mSchedule.notifyDataSetChanged();
    }


}

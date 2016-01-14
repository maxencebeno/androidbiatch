package com.example.lp.rest;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
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
    ListView lv;
    ArrayList<Ville> list;
    SimpleAdapter mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onAsyncButtonClick(View view) throws IOException {
        EditText nomVille = (EditText) findViewById(R.id.search_input);
        if(!nomVille.getText().equals("")){

        }
        URL url = new URL("http://10.0.2.2:80/androidlpmetinet/villes.php");
        lv = (ListView) findViewById(R.id.listView);
        list = new ArrayList<Ville>();
        mSchedule = new SimpleAdapter(getApplicationContext(), list, R.layout.list_view,
                new Ville[] {}, new int[] { R.id.titre, R.id.ville });
        new MyAsyncTask(mSchedule, list).execute(url, lv, mSchedule);
        mSchedule.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<Ville> map = (HashMap<Ville>) lv.getItemAtPosition(position);
                //on créer une boite de dialogue
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                //on attribut un titre à notre boite de dialogue
                adb.setTitle("Ville choisie");
                //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
                adb.setMessage("Votre choix : " + map.get("ville"));
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Ok", null);
                //on affiche la boite de dialogue
                adb.show();
            }
        });

    }

}

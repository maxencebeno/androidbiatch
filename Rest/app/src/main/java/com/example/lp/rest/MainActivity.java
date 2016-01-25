package com.example.lp.rest;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    VilleAdapter adapter;

    String[] filtres = {"Nom de la ville", "Code postal", "Code INSEE", "Code région"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onAsyncButtonClick(View view) throws IOException {
        EditText nomVille = (EditText) findViewById(R.id.search_input);

        if (!nomVille.getText().equals("")) {
            RadioButton nom = (RadioButton) findViewById(R.id.nom_ville);
            RadioButton cp = (RadioButton) findViewById(R.id.cp_ville);
            RadioButton insee = (RadioButton) findViewById(R.id.code_insee);
            RadioButton region = (RadioButton) findViewById(R.id.code_region);
            URL url = null;

            RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.radioGroup);
            int selectedId = radioButtonGroup.getCheckedRadioButtonId();
            String filtre = "";
            Log.d("selectedId", String.valueOf(selectedId));
            Log.d("nom_ville", String.valueOf(R.id.nom_ville));
            Log.d("getText", String.valueOf(nomVille.getText()));

            // Check which radio button was clicked
            switch (selectedId) {
                case R.id.nom_ville:
                    filtre = this.getFiltre((String) nom.getText());
                    url = new URL("http://10.0.2.2:80?filtre=" + filtre + "&q=" + nomVille.getText());
                    break;
                case R.id.cp_ville:
                    filtre = this.getFiltre((String) cp.getText());
                    url = new URL("http://10.0.2.2:80?filtre=" + filtre + "&q=" + nomVille.getText());
                    break;
                case R.id.code_insee:
                    filtre = this.getFiltre((String) insee.getText());
                    url = new URL("http://10.0.2.2:80?filtre=" + filtre + "&q=" + nomVille.getText());
                    break;
                case R.id.code_region:
                    filtre = this.getFiltre((String) region.getText());
                    url = new URL("http://10.0.2.2:80?filtre=" + filtre + "&q=" + nomVille.getText());
                    break;
            }

            list = new ArrayList<Ville>();
            lv = (ListView) findViewById(R.id.listView);

            adapter = new VilleAdapter(this, R.layout.list_view, list);

            new MyAsyncTask(adapter, list).execute(url, lv, adapter);

        } else {
            URL url = new URL("http://10.0.2.2:80");

            list = new ArrayList<Ville>();
            lv = (ListView) findViewById(R.id.listView);

            adapter = new VilleAdapter(this, R.layout.list_view, list);

            new MyAsyncTask(adapter, list).execute(url, lv, adapter);
        }


    }

    public String getFiltre(String nomFiltre) {
        String filtre = "";

        for (int i = 0; i < filtres.length; i++) {
            switch (filtres[i]) {
                case "Nom de la ville":
                    filtre = "nom";
                    break;
                case "Code postal":
                    filtre = "codepostal";
                    break;
                case "Code INSEE":
                    filtre = "codeinsee";
                    break;
                case "Code région":
                    filtre = "coderegion";
                    break;
            }
        }

        return filtre;
    }

}

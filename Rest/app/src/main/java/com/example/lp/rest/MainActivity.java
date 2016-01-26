package com.example.lp.rest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    protected ListView lv;
    protected ArrayList<Ville> list;
    protected VilleAdapter adapter;
    protected View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<Ville>();
        lv = (ListView) findViewById(R.id.listView);

        adapter = new VilleAdapter(this, R.layout.list_view, list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Ville ville = (Ville) lv.getItemAtPosition(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                //on attribut un titre à notre boite de dialogue
                adb.setTitle("Ville choisie");
                //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
                adb.setMessage("Votre choix : " + ville.getNom());

                ImageView imageView = (ImageView) findViewById(R.id.img);

                imageView.setImageResource(R.drawable.delete);
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Ok", null);
                //on affiche la boite de dialogue
                adb.show();
            }
        });
        adapter.notifyDataSetChanged();

    }

    public void onAsyncButtonClick(View view) throws IOException {
        EditText nomVille = (EditText) findViewById(R.id.search_input);

        if (!nomVille.getText().equals("")) {
            URL url = null;

            RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.radioGroup);
            int selectedId = radioButtonGroup.getCheckedRadioButtonId();

            // Check which radio button was clicked
            switch (selectedId) {
                case R.id.nom_ville:
                    url = new URL("http://10.0.2.2:80/nom/" + nomVille.getText());
                    break;
                case R.id.cp_ville:
                    url = new URL("http://10.0.2.2:80/codepostal/" + nomVille.getText());
                    break;
                case R.id.code_insee:
                    url = new URL("http://10.0.2.2:80/codeinsee/" + nomVille.getText());
                    break;
                case R.id.code_region:
                    url = new URL("http://10.0.2.2:80/coderegion/" + nomVille.getText());
                    break;
            }

            new MyAsyncTask(adapter, list).execute(url, lv, adapter);

        } else {
            URL url = new URL("http://10.0.2.2:80");

            new MyAsyncTask(adapter, list).execute(url, lv, adapter);
        }

    }

    public static String performPostCall(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("response", response);
        return response;
    }

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.v("result", result.toString());
        return result.toString();
    }

}
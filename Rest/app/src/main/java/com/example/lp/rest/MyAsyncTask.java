package com.example.lp.rest;

import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lp on 08/01/2016.
 */
public class MyAsyncTask extends AsyncTask<Object, ListView, String> {
    ListView lv;
    ArrayList<Ville> list;
    VilleAdapter adapter;

    public MyAsyncTask(VilleAdapter adapter, ArrayList<Ville> list) {
        this.adapter = adapter;
        this.list = list;
    }

    @Override
    protected String doInBackground(Object... params) {
        HttpURLConnection urlConnection = null;
        String line;
        String content = "";
        lv = (ListView) params[1];
        try {
            URL url = (URL) params[0];
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                // Lecture
                do {
                    line = in.readLine();
                    content += line;
                }while(line != null);

                urlConnection.disconnect();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    protected void onPostExecute(String result) {

        try {
            JSONObject json = new JSONObject(result);

            JSONArray villes = json.getJSONArray("villes");

            for (int i = 0 ; i < villes.length() ; i++) {
                JSONObject ville = villes.getJSONObject(i);
                Ville villeArray = new Ville();
                villeArray.setNom(ville.getString("Nom_Ville"));
                villeArray.setNom(ville.getString("MAJ"));
                villeArray.setCodePostal(ville.getString("Code_Postal"));
                villeArray.setLongitude(ville.getString("Longitude"));
                villeArray.setLatitude(ville.getString("Latitude"));
                villeArray.setCodeinsee(ville.getString("Code_INSEE"));
                list.add(villeArray);
            }

            lv.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

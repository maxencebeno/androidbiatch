package com.example.lp.rest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lp on 08/01/2016.
 */
public class MyAsyncTask extends AsyncTask<Object, ListView, String> {
    ListView lv;
    ArrayList<HashMap<String, HashMap<String, String>>> list;
    HashMap<String, HashMap<String, String>> villeEtCoordonnees;
    HashMap<String, String> coordonnees;
    //ArrayList<HashMap<String, String>> list;
    SimpleAdapter mSchedule;
    HashMap<String, String> villeArray;

    public MyAsyncTask(SimpleAdapter mSchedule, ArrayList<HashMap<String, HashMap<String, String>>> list) {
        this.mSchedule = mSchedule;
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
                coordonnees.put(ville.getString("Latitude"), ville.getString("Longitude"));
                villeEtCoordonnees.put(ville.getString("MAJ"), coordonnees);
                list.add(villeEtCoordonnees);
                villeArray = new HashMap<String, String>();
                villeArray.put("titre", ville.getString("Code_Postal"));
                villeArray.put("ville", ville.getString("MAJ"));
                list.add(villeArray);
            }

            lv.setAdapter(mSchedule);
            mSchedule.notifyDataSetChanged();
            Log.v("test", "test");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    protected void onPostExecute(String result) {
        tv.setText(result);
    }*/
}

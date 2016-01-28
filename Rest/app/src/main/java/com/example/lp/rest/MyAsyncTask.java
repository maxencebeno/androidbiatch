package com.example.lp.rest;

import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by lp on 08/01/2016.
 */
public class MyAsyncTask extends AsyncTask<Object, ListView, String> {
    ListView lv;
    ArrayList<Ville> list;
    VilleAdapter adapter;
    String method;
    HashMap<String, String> postParams = null;

    public MyAsyncTask(VilleAdapter adapter, ArrayList<Ville> list, String method, HashMap<String, String> postParams) {
        this.adapter = adapter;
        this.list = list;
        this.method = method;
        this.postParams = postParams;
    }

    @Override
    protected String doInBackground(Object... params) {
        HttpURLConnection urlConnection = null;
        String line;
        String content = "";
        if (params[1] != null) {
            lv = (ListView) params[1];
        }
        method = (String) params[2];

        if (method == "GET") {
            try {
                URL url = (URL) params[0];
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    // Lecture
                    do {
                        line = in.readLine();
                        content += line;
                    } while (line != null);

                    urlConnection.disconnect();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String response = "";
            try {
                URL url = (URL) params[0];
                postParams = new HashMap<String, String>();
                postParams = (HashMap) params[3];

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postParams));

                writer.flush();
                writer.close();
                os.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        return content;
    }

    @Override
    protected void onPostExecute(String result) {

        if (method == "GET") {
            try {
                JSONObject json = new JSONObject(result);

                JSONArray villes = json.getJSONArray("villes");

                for (int i = 0; i < villes.length(); i++) {
                    JSONObject ville = villes.getJSONObject(i);
                    Ville villeArray = new Ville();
                    villeArray.setNom(ville.getString("Nom_Ville"));
                    villeArray.setNom(ville.getString("MAJ"));
                    villeArray.setCodePostal(ville.getString("Code_Postal"));
                    villeArray.setLongitude(ville.getString("Longitude"));
                    villeArray.setLatitude(ville.getString("Latitude"));
                    villeArray.setCodeinsee(ville.getString("Code_INSEE"));
                    villeArray.setCoderegion(ville.getString("Code_Region"));
                    villeArray.setEloignement(ville.getString("Eloignement"));
                    list.add(villeArray);
                }

                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            adapter.notifyDataSetChanged();
        }
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

        return result.toString();
    }
}

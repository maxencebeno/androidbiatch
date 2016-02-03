package com.example.lp.rest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewCity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNom;
    private EditText editTextCodePostal;
    private EditText editTextCodeRegion;
    private EditText editTextCodeInsee;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText editTextEloignement;

    private Button buttonUpdate;
    private Button buttonDelete;

    private String codeinsee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_city);

        Intent intent = getIntent();

        codeinsee = intent.getStringExtra(Config.VILLE_ID);

        editTextCodePostal = (EditText) findViewById(R.id.editTextCPShow);
        editTextCodeInsee = (EditText) findViewById(R.id.editTextCodeInseeShow);
        editTextNom = (EditText) findViewById(R.id.editTextNomVilleShow);
        editTextCodeRegion = (EditText) findViewById(R.id.editTextCodeRegionShow);
        editTextLatitude = (EditText) findViewById(R.id.editTextLatitudeShow);
        editTextLongitude = (EditText) findViewById(R.id.editTextLongitudeShow);
        editTextEloignement = (EditText) findViewById(R.id.editTextEloignementShow);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextCodeInsee.setText(codeinsee);

        getVille();
    }

    private void getVille(){
        class GetVille extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewCity.this,"Fetching...","Wait...",false,false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showVille(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_VILLE,codeinsee);

                return s;
            }
        }
        GetVille ge = new GetVille();
        ge.execute();
    }

    private void showVille(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String nom = c.getString(Config.TAG_NOM);
            String codepostal = c.getString(Config.TAG_CP);
            String coderegion = c.getString(Config.TAG_CODE_REGION);
            String latitude = c.getString(Config.TAG_LATITUDE);
            String longitude = c.getString(Config.TAG_LONGITUDE);
            String eloignement = c.getString(Config.TAG_ELOIGNEMENT);

            editTextNom.setText(nom);
            editTextLatitude.setText(latitude);
            editTextCodeRegion.setText(coderegion);
            editTextLongitude.setText(longitude);
            editTextEloignement.setText(eloignement);
            editTextCodePostal.setText(codepostal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateVille(){
        final String nom = editTextNom.getText().toString().trim();
        final String codepostal = editTextCodePostal.getText().toString().trim();
        final String coderegion = editTextCodeRegion.getText().toString().trim();
        final String latitude = editTextLatitude.getText().toString().trim();
        final String longitude = editTextLongitude.getText().toString().trim();
        final String eloignement = editTextEloignement.getText().toString().trim();
        final String maj = editTextNom.getText().toString().toUpperCase().trim();

        class UpdateVille extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewCity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewCity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_VILLE_NOM,nom);
                hashMap.put(Config.KEY_VILLE_MAJ,maj);
                hashMap.put(Config.KEY_VILLE_CODE_POSTAL,codepostal);
                hashMap.put(Config.KEY_VILLE_CODE_REGION,coderegion);
                hashMap.put(Config.KEY_VILLE_LATITUDE,latitude);
                hashMap.put(Config.KEY_VILLE_LONGITUDE,longitude);
                hashMap.put(Config.KEY_VILLE_ELOIGNEMENT, eloignement);
                hashMap.put(Config.KEY_VILLE_CODE_INSEE, codeinsee);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_VILLE,hashMap, "update");

                return s;
            }
        }

        UpdateVille ue = new UpdateVille();
        ue.execute();
    }

    private void deleteVille(){
        class DeleteVille extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewCity.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewCity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_VILLE_CODE_INSEE,codeinsee);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_DELETE_VILLE, hashMap, "delete");

                return s;
            }
        }

        DeleteVille de = new DeleteVille();
        de.execute();
    }

    private void confirmDeleteVille(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Etes vous sur de vouloir supprimer cette ville ?");

        alertDialogBuilder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteVille();
                        startActivity(new Intent(ViewCity.this,ViewAllCities.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Non",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateVille();
        }

        if(v == buttonDelete){
            confirmDeleteVille();
        }
    }
}
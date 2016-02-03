package com.example.lp.rest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextNomVille;
    private EditText editTextCP;
    private EditText editTextCodeInsee;
    private EditText editTextCodeRegion;
    private EditText editTextCodeLatitude;
    private EditText editTextCodeLongitude;
    private EditText editTextCodeEloignement;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing views
        editTextNomVille = (EditText) findViewById(R.id.editTextNomVille);
        editTextCP = (EditText) findViewById(R.id.editTextCP);
        editTextCodeInsee = (EditText) findViewById(R.id.editTextCodeInsee);
        editTextCodeRegion = (EditText) findViewById(R.id.editTextCodeRegion);
        editTextCodeLatitude = (EditText) findViewById(R.id.editTextLatitude);
        editTextCodeLongitude = (EditText) findViewById(R.id.editTextLongitude);
        editTextCodeEloignement = (EditText) findViewById(R.id.editTextEloignement);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }


    //Adding an employee
    private void addVille(){

        final String nomVille = editTextNomVille.getText().toString().trim();
        final String cpVille = editTextCP.getText().toString().trim();
        final String codeInsee = editTextCodeInsee.getText().toString().trim();
        final String codeRegion = editTextCodeRegion.getText().toString().trim();
        final String latitude = editTextCodeLatitude.getText().toString().trim();
        final String longitude = editTextCodeLongitude.getText().toString().trim();
        final String eloignement = editTextCodeEloignement.getText().toString().trim();

        class AddVille extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_VILLE_NOM,nomVille);
                params.put(Config.KEY_VILLE_MAJ,nomVille.toUpperCase());
                params.put(Config.KEY_VILLE_CODE_POSTAL,cpVille);
                params.put(Config.KEY_VILLE_CODE_INSEE,codeInsee);
                params.put(Config.KEY_VILLE_CODE_REGION,codeRegion);
                params.put(Config.KEY_VILLE_LATITUDE,latitude);
                params.put(Config.KEY_VILLE_LONGITUDE,longitude);
                params.put(Config.KEY_VILLE_ELOIGNEMENT,eloignement);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params, "create");
                return res;
            }
        }

        AddVille av = new AddVille();
        av.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addVille();
        }

        if(v == buttonView){
            startActivity(new Intent(this, ViewAllCities.class));
        }
    }
}
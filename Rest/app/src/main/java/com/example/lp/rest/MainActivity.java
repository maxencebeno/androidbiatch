package com.example.lp.rest;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Ok", null);
                //on affiche la boite de dialogue
                adb.show();
            }
        });

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
                    url = new URL("http://10.0.2.2:80?filtre=nom&q=" + nomVille.getText());
                    break;
                case R.id.cp_ville:
                    url = new URL("http://10.0.2.2:80?filtre=codepostal&q=" + nomVille.getText());
                    break;
                case R.id.code_insee:
                    url = new URL("http://10.0.2.2:80?filtre=codeinsee&q=" + nomVille.getText());
                    break;
                case R.id.code_region:
                    url = new URL("http://10.0.2.2:80?filtre=coderegion&q=" + nomVille.getText());
                    break;
            }

            new MyAsyncTask(adapter, list).execute(url, lv, adapter);

        } else {
            URL url = new URL("http://10.0.2.2:80");

            new MyAsyncTask(adapter, list).execute(url, lv, adapter);
        }

    }

}
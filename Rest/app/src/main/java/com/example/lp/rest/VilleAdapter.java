package com.example.lp.rest;

/**
 * Created by maxencebeno on 24/01/2016.
 */


import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VilleAdapter extends ArrayAdapter<Ville> {

        int resource;
        String response;
        Context context;
        //Initialize adapter
        public VilleAdapter(Context context, int resource, List<Ville> items) {
            super(context, resource, items);
            this.resource=resource;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LinearLayout listView;
            //Get the current alert object
            Ville v = getItem(position);

            //Inflate the view
            if(convertView==null)
            {
                listView = new LinearLayout(getContext());
                String inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater vi;
                vi = (LayoutInflater)getContext().getSystemService(inflater);
                vi.inflate(resource, listView, true);
            }
            else
            {
                listView = (LinearLayout) convertView;
            }
            //Get the text boxes from the listitem.xml file
            TextView nomVille = (TextView)listView.findViewById(R.id.ville);
            TextView cpVille = (TextView)listView.findViewById(R.id.codepostal);
            TextView latitudeVille = (TextView)listView.findViewById(R.id.latitude);
            TextView longitudeVille = (TextView)listView.findViewById(R.id.longitude);

            //Assign the appropriate data from our alert object above
            nomVille.setText(v.getNom());
            cpVille.setText(v.getCodePostal());
            latitudeVille.setText(v.getLongitude());
            longitudeVille.setText(v.getLatitude());

            return listView;
        }

    }
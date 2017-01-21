package fr.project.isep.beerspotter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.R.attr.country;

/**
 * Created by Boutaina on 04/12/2016.
 */

public class Connexion extends AsyncTask<Object, Void, String> {

    private Activity main;
    private String serveur = "http://beerspotter.mybluemix.net/api/places";
    private List<Places>places;
    public AsyncResponse delegate = null;

    public Connexion(AsyncResponse delegate){
        this.delegate = delegate;
    }
    @Override
    protected void onPreExecute(){}

    protected String doInBackground(Object... params)
    {   //ArrayList<String> result = new ArrayList<String>();
        this.main = (Activity) params[0];
         this.places = (ArrayList<Places>) params[1];
        String reponse = "";
        ConnectivityManager connMgr = (ConnectivityManager) main.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.w("Reseau", "connecté au réseau");
            URL url = null;

            try {
                url = new URL(serveur);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int statut = connection.getResponseCode();
                Log.w("Reseau", "requete envoyee " + statut);


                String ligne = null;
                BufferedReader inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((ligne = inStream.readLine()) != null)
                {
                    reponse = reponse + ligne;
                }
                inStream.close();
                connection.disconnect();
                Log.w("Reseau", "Réponse reçue : " + reponse);



            } catch(Exception e) {
                reponse = "ERROR : " + e.getMessage();
            }
         }
        else
        {
            reponse = "ERREUR : pas de réseau";
        }



        return reponse;
    }
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result)
    {
        //geocoder = new Geocoder(main.getContext(), Locale.getDefault());

        Log.w("Reseau", "resultat à afficher " + result);
        if (result.startsWith("ERREUR"))
        {
            Toast.makeText(main, result, Toast.LENGTH_LONG).show();

        }
        else
        {   delegate.processFinish(result);


        }

    }



}

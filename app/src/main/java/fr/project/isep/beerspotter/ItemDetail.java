package fr.project.isep.beerspotter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemDetail extends AppCompatActivity {
    ArrayList<Places>p;
    Geocoder geocoder;
    List<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("places", null);
        Type type = new TypeToken<ArrayList<Places>>() {}.getType();
        p = gson.fromJson(json, type);
        setContentView(R.layout.activity_item_detail);

        TextView price = (TextView) findViewById(R.id.price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        toolbar2.setTitle("41 rue des Pinsons");
        toolbar2.setSubtitle("Créteil, 94000");
        Toolbar happyhours = (Toolbar) findViewById(R.id.happyhours);
        happyhours.setTitle("Next Happy hours");
        happyhours.setSubtitle("Monday from 6pm to 12pm");
        ImageView imageView = (ImageView) findViewById(R.id.bar_icon);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("test");

        getSupportActionBar().setTitle(msg);
        imageView.setImageResource(R.drawable.bar);
        for(Places place: p){
            if(place.getName().equals(msg)){
                price.setText(""+place.getPrice());
            }
        }
        price.setTypeface(price.getTypeface(), Typeface.BOLD);



    }


}

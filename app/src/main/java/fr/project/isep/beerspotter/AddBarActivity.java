package fr.project.isep.beerspotter;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddBarActivity extends AppCompatActivity {
            private AsyncTask connexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView lineColorCode = (ImageView)findViewById(R.id.imageView);
        int color = Color.parseColor("#ffcc0000");
        lineColorCode.setColorFilter(color);
        toolbar.setTitle("Add a bar");
        final Button validate = (Button) findViewById(R.id.validate);
        final EditText barname = (EditText) findViewById(R.id.barname);
        final EditText lowerprice = (EditText) findViewById(R.id.lowerPrice);

        validate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String bn= barname.getText().toString();
                final Double lp = Double.parseDouble(lowerprice.getText().toString());
                final Double lt = 48.8867046;
                final Double lg = 2.3409156;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                startActivity(new Intent(AddBarActivity.this, ListFragment.class));
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddBarActivity.this);
                                builder.setMessage("Add Failed")
                                        .setNegativeButton("Retry",null)
                                        .create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddRequest registerRequest = new AddRequest(bn,lg,lt,lp,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddBarActivity.this);
                queue.add(registerRequest);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddBarActivity.this);
                builder.setMessage("Restart application to see new bar")
                        .setNegativeButton("OK",null)
                        .create().show();


            }
        });

    }


}

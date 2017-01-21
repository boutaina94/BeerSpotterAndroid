package fr.project.isep.beerspotter;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener , AsyncResponse {
    private ProgressDialog progress;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Places> places;
    private AsyncTask connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        places = new ArrayList<Places>();
        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        asyncexec();
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_map_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_list_black_24dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);


     /*   if(connexion.getStatus()== AsyncTask.Status.FINISHED){
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();

            String json = gson.toJson(places);

            editor.putString("places", json);
            editor.commit();
        }*/


 }
   public void asyncexec(){
       connexion=new Connexion(this);
       connexion.execute(this,places);
   }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    @Override
    public void processFinish(String output){
        try {
            places= new ArrayList<Places>();
            JSONObject jobj = new JSONObject(output);
            JSONArray markers = jobj.getJSONArray("data");
            for (int i = 0; i < markers.length(); i++) {
                JSONObject c = markers.getJSONObject(i);
                    /* googleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("")
                            .position(new LatLng(c.getDouble("latitude"), c.getDouble("longitude"))));*/
                if(!c.isNull("latitude")&&!c.isNull("longitude")) {
                    Places p = new Places(c.getString("name"),c.getDouble("price"),c.getDouble("latitude"),c.getDouble("longitude"));
                    places.add(p);}

            }
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(places);
            editor.putString("places", json);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addBar(View v) {
        Intent intent = new Intent(this, AddBarActivity.class);
        startActivity(intent);
    }


}


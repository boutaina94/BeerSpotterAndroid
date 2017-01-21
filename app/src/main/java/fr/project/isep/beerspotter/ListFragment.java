package fr.project.isep.beerspotter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listview=null;
    ArrayList<Places>p;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("places", null);
        Type type = new TypeToken<ArrayList<Places>>() {}.getType();
        p = gson.fromJson(json, type);
        //initialisation
        listview =(ListView)view.findViewById(R.id.listview);


        String[] items = new String[p.size()];
        Integer[] imgid = new Integer[p.size()];
        int i=0;
        for(Places place: p){
            items[i]=place.getName();
            imgid[i]=R.drawable.logobs;
            i++;
        }


        CustomListAdapter adapter=new CustomListAdapter(getActivity(), items, imgid);
        listview.setAdapter(adapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(this);
        return view;

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        TextView textview=(TextView)view.findViewById(R.id.item);
        String message =textview.getText().toString();
               // Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getActivity(), ItemDetail.class);
        intent.putExtra("test", message);
        startActivity(intent);


    }
}

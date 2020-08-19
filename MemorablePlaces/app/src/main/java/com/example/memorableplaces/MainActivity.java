package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places=new ArrayList<>();
    static ArrayList<LatLng>locations=new ArrayList<>();//Will be accessible in other activity
    static ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView my_list=findViewById(R.id.list);

        places.add("Add New Location");
        locations.add(new LatLng(0,0));
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        my_list.setAdapter(adapter);
        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                    intent.getIntExtra("index",i);
                    startActivity(intent);
            }
        });

    }
}

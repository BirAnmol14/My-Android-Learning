package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.appcompat.app.AlertDialog;
public class MainActivity extends AppCompatActivity {

    static  ArrayAdapter adapter;
    static ArrayList<String>notes=new ArrayList<>();
    static SharedPreferences sharedPreferences;
    Intent intent;
    public void create_note(int id){
        ListView my_list=findViewById(R.id.lv);
        System.out.println("Entering create note");
        adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,notes);
        my_list.setAdapter(adapter);
        intent=new Intent(MainActivity.this,note.class);
        intent.putExtra("note",id);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("Menu inflated");
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_resource,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("New text option selected");
        intent=new Intent(MainActivity.this,note.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView my_list=findViewById(R.id.lv);
        System.out.println("Main");
        sharedPreferences=getSharedPreferences("com.example.notes",MODE_PRIVATE);
        try {
            System.out.println("Loading note");
            notes = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));
            System.out.println("Note loaded");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("list and adapter set");
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        my_list.setAdapter(adapter);
        System.out.println("List and adapter working");

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("On click working");
                    create_note(i);

                }
        });
        my_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemindex=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Note?")
                        .setMessage("The note you have selected will be deleted")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemindex);
                                try {
                                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
                                }catch (Exception e){
                                    System.out.println("Deleting failed in storage");
                                    e.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                try {
                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
                }catch (Exception e){
                    System.out.println("Deleting failed in storage");
                    e.printStackTrace();
                }
                return true;//so as to not start single click listener
            }
        });


    }
}

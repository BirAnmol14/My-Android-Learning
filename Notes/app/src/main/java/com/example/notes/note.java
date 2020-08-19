package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.view.View;
public class note extends AppCompatActivity {

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        System.out.println("Inside the note");
        Intent it=getIntent();
        id=it.getIntExtra("note",-1);
        EditText input=findViewById(R.id.text);
        if(id!=-1) {
            String data = MainActivity.notes.get(id);
            input.setText(data);
        }
        else{
            MainActivity.notes.add("");
            id=MainActivity.notes.size()-1;
            MainActivity.adapter.notifyDataSetChanged();
        }
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("Add note working");
                MainActivity.notes.set(id, String.valueOf(charSequence));
                MainActivity.adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    System.out.println("Storing data");
                    MainActivity.sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                } catch (Exception e) {
                    System.out.println("Failed");
                    e.printStackTrace();
                }
            }
        });
    }

}

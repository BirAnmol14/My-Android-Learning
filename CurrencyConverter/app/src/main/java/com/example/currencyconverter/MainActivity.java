package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
        public void itou(View v){
            EditText tv =findViewById(R.id.value);
            Log.i("INR to USD: ",tv.getText().toString());
            double value=Double.parseDouble(tv.getText().toString());
            value/=68.37;
            TextView disp=findViewById(R.id.tv2);
            disp.setText("$ "+Double.toString(value));
            Toast.makeText(MainActivity.this,("$ "+Double.toString(value)),Toast.LENGTH_LONG).show();
        }
        public void utoi(View v){
            EditText tv =findViewById(R.id.value);
            Log.i("USD to INR: ",tv.getText().toString());
            double value=Double.parseDouble(tv.getText().toString());
            value*=68.37;
            TextView disp=findViewById(R.id.tv2);
            disp.setText("₹ "+Double.toString(value));
            Toast.makeText(MainActivity.this,"₹ "+Double.toString(value),Toast.LENGTH_LONG).show();
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.text.DecimalFormat;
public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    String latitude="";
    String address="";
    String accuracy="";
    String altitude="";
    String longitude="";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0) {
            if (grantResults.length > 0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                DecimalFormat df = new DecimalFormat("####.###");
                TextView add1=findViewById(R.id.Address);
                TextView acc=findViewById(R.id.Accuracy);
                TextView lat=findViewById(R.id.Latitude);
                TextView lon=findViewById(R.id.Longitude);
                TextView alt=findViewById(R.id.Altitude);
                altitude=df.format(location.getAltitude());
                accuracy=df.format(location.getAccuracy());
                Geocoder add=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list = add.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    latitude=(df.format(list.get(0).getLatitude()));
                    longitude=df.format(list.get(0).getLongitude());
                    address=list.get(0).getFeatureName()+"\r\n"+list.get(0).getLocality()+"\r\n"+list.get(0).getSubAdminArea()+"\r\n"+list.get(0).getAdminArea()+"\r\n"+list.get(0).getCountryName();
                }catch (Exception e){
                    e.printStackTrace();
                }
                add1.setText("Address\n"+address);
                acc.setText("Accuracy\n"+accuracy);
                lat.setText("Latitude\n"+latitude);
                lon.setText("Logitude\n"+longitude);
                alt.setText("Altitude\n"+altitude);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if(Build.VERSION.SDK_INT<23){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
        else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }
}

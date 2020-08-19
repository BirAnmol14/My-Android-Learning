package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Date;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Location last_known;
    public void CenterMapOnLocation(Location loc,String title){
        LatLng UserLocation=new LatLng(loc.getLatitude(),loc.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(UserLocation).title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UserLocation,15f));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100000,10,locationListener);
                    last_known=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    CenterMapOnLocation(last_known,"Your Location");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent it=getIntent();

        if(it.getIntExtra("index",0)==0) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    CenterMapOnLocation(location, "Your Location");
                    System.out.println(location);
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
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geo=new Geocoder(getApplicationContext(),Locale.getDefault());
                    String address="";
                    try {
                        List<Address> addressList = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if(addressList!=null&&addressList.size()>0){
                            if(addressList.get(0).getThoroughfare()!=null){
                                if(addressList.get(0).getSubThoroughfare()!=null){
                                    address+=addressList.get(0).getSubThoroughfare()+"\r\n";
                                }
                                address+=addressList.get(0).getThoroughfare()+"\r\n";
                            }
                            address+=addressList.get(0).getFeatureName()+"\r\n"+addressList.get(0).getLocality()+"\r\n"+addressList.get(0).getSubAdminArea();
                            address+="\r\n"+addressList.get(0).getLocality()+"\r\n"+addressList.get(0).getCountryName();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (address != "") {
                        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        address+="\r\n"+sdf.format(new Date());
                    }
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    MainActivity.places.add(address);
                    System.out.println(address);
                    MainActivity.locations.add(latLng);
                    System.out.println(latLng);
                    MainActivity.adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Location Saved",Toast.LENGTH_LONG).show();
                }
            });

        }
        if(it.getIntExtra("index",0)!=0){
            System.out.println(it.getIntExtra("index",0));
            mMap.clear();
            Location location=new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(MainActivity.locations.get(it.getIntExtra("index",0)).latitude);
            location.setLongitude(MainActivity.locations.get(it.getIntExtra("index",0)).longitude);
            CenterMapOnLocation(location,MainActivity.places.get(it.getIntExtra("index",0)));
            mMap.addMarker(new MarkerOptions().position(MainActivity.locations.get(it.getIntExtra("index",0))).title(MainActivity.places.get(it.getIntExtra("index",0))));
        }


        if(Build.VERSION.SDK_INT<23){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,locationListener);
            }
            else{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                }else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100000,100,locationListener);
                    last_known=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    CenterMapOnLocation(last_known,"Your Location");
                }
        }
    }


}

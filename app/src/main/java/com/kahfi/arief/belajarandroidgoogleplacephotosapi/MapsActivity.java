package com.kahfi.arief.belajarandroidgoogleplacephotosapi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.async.AsinkronNearbySearchRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    @BindView(R.id.placeSearch)
    Button placeSearch;
    @BindView(R.id.myPlaceClick)
    Button myPlace;
    @BindView(R.id.seePhotos)
    Button seePhotos;


    private Location location;


    private boolean isPicked ;

    private LatLng sharedLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);


        seePhotos.setEnabled(false);

        myPlace.setEnabled(false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
          myPlace.setEnabled(false);
        }else{
          myPlace.setEnabled(true);
        }
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

    @OnClick(R.id.seePhotos)
    public void seePhotosDetails(View v){
        Intent intent = new Intent(MapsActivity.this,BankActivity.class);

        Bundle b = new Bundle();

        b.putDouble("lat",sharedLatLng.latitude);
        b.putDouble("lng",sharedLatLng.longitude);

        intent.putExtras(b);

        startActivity(intent);
    }

    @OnClick(R.id.myPlaceClick)
    public void myPlaceClick(View v){
       //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),10f));
    }

    @OnClick(R.id.placeSearch)
    public void placeSearch(View v){

        if(isPicked){
            mMap.clear();
        }

        try {
            Intent intent = new PlacePicker.IntentBuilder()
                    .build(MapsActivity.this);
            startActivityForResult(intent,1);
        }catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){
            Place place = PlacePicker.getPlace(MapsActivity.this,data);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),10f));

            new AsinkronNearbySearchRequest(mMap,place.getLatLng()).execute();

            seePhotos.setEnabled(true);

            sharedLatLng = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);

            isPicked = true;

        }else{
            Log.e("PlacePicker","onCancel");
            seePhotos.setEnabled(false);
            isPicked = false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


}

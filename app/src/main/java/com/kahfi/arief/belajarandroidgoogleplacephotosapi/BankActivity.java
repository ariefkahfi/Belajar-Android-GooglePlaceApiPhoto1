package com.kahfi.arief.belajarandroidgoogleplacephotosapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.adapters.BankAdapter;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.async.AsinkronPlacePhotoToRecView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActivity extends AppCompatActivity {


    @BindView(R.id.recView)RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();

        double lat = b.getDouble("lat");
        double lng = b.getDouble("lng");

        LatLng latLng = new LatLng(lat,lng);


        initRecViewData(recyclerView,latLng);

    }


    private void initRecViewData(RecyclerView recyclerView,LatLng latLng){
        new AsinkronPlacePhotoToRecView(BankActivity.this,recyclerView,latLng).execute();
    }

}

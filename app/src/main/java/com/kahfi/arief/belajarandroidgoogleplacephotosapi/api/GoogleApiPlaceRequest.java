package com.kahfi.arief.belajarandroidgoogleplacephotosapi.api;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arief on 26/09/17.
 */

public class GoogleApiPlaceRequest extends AbstractGooglePlacesRequest {


    //https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters


    public void callLatLngRequest(LatLng latLng) {
        Map<String,String> map  = new HashMap<>();

        map.put("location",String.valueOf(latLng.latitude)+","+String.valueOf(latLng.longitude));
        map.put("type","bank");
        map.put("radius","1000");
        map.put("key","AIzaSyAR6WPg06EYG94R2Q1Oq9AAovRsDJCEDjU");


        setInitHttpUrlBuilder("https","maps.googleapis.com","/maps/api/place/nearbysearch/json",map);
    }



}

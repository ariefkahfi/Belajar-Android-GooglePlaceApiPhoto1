package com.kahfi.arief.belajarandroidgoogleplacephotosapi.async;

import android.os.AsyncTask;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.api.GoogleApiPlaceRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by arief on 26/09/17.
 */

public class AsinkronNearbySearchRequest extends AsyncTask<String,Integer,ArrayList<LatLng>> {

    private GoogleMap gMap;

    private GoogleApiPlaceRequest googlePlace;

    private LatLng latLng;

    public AsinkronNearbySearchRequest(GoogleMap gMap,LatLng latLng){
        this.gMap=gMap;
        googlePlace = new GoogleApiPlaceRequest();
        this.latLng=latLng;
    }

    @Override
    protected ArrayList<LatLng> doInBackground(String... strings) {
        ArrayList<LatLng> latLngList = new ArrayList<>();

        googlePlace.callLatLngRequest(latLng);



        try {
            String json  = googlePlace.getResponse().body().string();

            JsonElement element = new JsonParser().parse(json);

            JsonArray results = element.getAsJsonObject().getAsJsonArray("results");

            for(JsonElement el : results){
               double lat = el.getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble();
               double lng = el.getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble();

               latLngList.add(new LatLng(lat,lng));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return latLngList;
    }

    @Override
    protected void onPostExecute(ArrayList<LatLng> latLngs) {
        super.onPostExecute(latLngs);
        for (LatLng lng : latLngs){
            gMap.addMarker(new MarkerOptions().position(lng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            //System.out.println(lng.latitude+"\t"+lng.longitude);
        }
    }
}

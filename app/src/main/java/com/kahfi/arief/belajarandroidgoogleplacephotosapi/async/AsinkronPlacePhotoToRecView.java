package com.kahfi.arief.belajarandroidgoogleplacephotosapi.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.R;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.adapters.BankAdapter;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.api.GoogleApiPlaceRequest;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.model.PhotoUrl;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by arief on 26/09/17.
 */

public class AsinkronPlacePhotoToRecView extends AsyncTask<String,Integer,ArrayList<PhotoUrl>> {

    private RecyclerView recyclerView;
    private LatLng latLng;
    private Context context;

    private GoogleApiPlaceRequest api;

    public AsinkronPlacePhotoToRecView(Context context , RecyclerView recyclerView,LatLng latLng){
        this.recyclerView=recyclerView;
        this.latLng=latLng;
        this.context=context;
        api = new GoogleApiPlaceRequest();
    }

    @Override
    protected ArrayList<PhotoUrl> doInBackground(String... strings) {
        ArrayList<PhotoUrl> photoData = new ArrayList<>();

        api.callLatLngRequest(latLng);

        try {
            String json =  api.getResponse().body().string();

            JsonElement element = new JsonParser().parse(json);

            JsonArray results = element.getAsJsonObject().getAsJsonArray("results");

            for(JsonElement el : results){
               PhotoUrl photo = new PhotoUrl();

               String namaTempat  = el.getAsJsonObject().get("name").getAsString();
               String alamatTempat = el.getAsJsonObject().get("vicinity").getAsString();

               if(namaTempat!=null){
                photo.setNama(namaTempat);
               }else{
                photo.setNama("Not Set");
               }

               if(alamatTempat!=null){
                photo.setAlamat(alamatTempat);
               }else{
                photo.setAlamat("Not Set");
               }

               JsonArray photos = el.getAsJsonObject().getAsJsonArray("photos");

               if(photos!=null){
                 String photoReference = photos.get(0).getAsJsonObject().get("photo_reference").getAsString();
                 if(photoReference!=null){
                     String realUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&" +
                             "photoreference="+photoReference+"&key="+context.getResources().getString(R.string.google_maps_key);
                     photo.setUrl(realUrl);
                 }
               }

               photoData.add(photo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return photoData;
    }

    @Override
    protected void onPostExecute(ArrayList<PhotoUrl> photoUrls) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new BankAdapter(context,photoUrls));
    }
}

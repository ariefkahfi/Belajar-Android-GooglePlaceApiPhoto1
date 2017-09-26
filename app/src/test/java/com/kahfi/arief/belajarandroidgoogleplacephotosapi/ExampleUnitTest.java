package com.kahfi.arief.belajarandroidgoogleplacephotosapi;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.api.GoogleApiPlaceRequest;

import org.junit.Test;

import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?
    // location=-2.9829642,104.7273351&
    // radius=500&
    // type=restaurant
    // &key=AIzaSyAR6WPg06EYG94R2Q1Oq9AAovRsDJCEDjU

    private ArrayList<String> photoReferences = new ArrayList<>();

    //@Test
    public void testKelasNearbySearch()throws Exception{
        GoogleApiPlaceRequest placeRequest  = new GoogleApiPlaceRequest();

        placeRequest.callLatLngRequest(new LatLng(-2.9829642,104.7273351));

        Response response = placeRequest.getResponse();

        String json = response.body().string();

        System.out.println(json);
    }



    @Test
    public void  testRequestPhotoSearch() throws Exception{
        testRequestNearbySearchBank();


        //get json string

        for(String url : photoReferences){
            //System.out.println(url);
            System.out.println("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400" +
                    "&key=AIzaSyAR6WPg06EYG94R2Q1Oq9AAovRsDJCEDjU" +
                    "&photoreference="+url);
        }

        //System.out.println(json);

    }

    public String testRequestNearbySearch()throws Exception{
        OkHttpClient client = new OkHttpClient();

        HttpUrl https = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegments("/maps/api/place/nearbysearch/json")

                .addQueryParameter("location","-2.9829642,104.7273351")
                .addQueryParameter("radius","1000")
                .addQueryParameter("type","bank")
                .addQueryParameter("key","AIzaSyAR6WPg06EYG94R2Q1Oq9AAovRsDJCEDjU").build();

        //Logger.getLogger(this.getClass().getName()).info(String.valueOf(HttpUrl.parse(String.valueOf(https.toString()))));


        Request request = new Request.Builder()
                .url(https)
                .get()
                .build();

        Response json = client.newCall(request).execute();

        return json.body().string();
    }

    @Test
    public void testAmbilLatLng()throws Exception{
        GoogleApiPlaceRequest api = new GoogleApiPlaceRequest();

        api.callLatLngRequest(new LatLng(-2.9829642,104.7273351));

        String json  = api.getResponse().body().string();

        JsonElement element = new JsonParser().parse(json);

        JsonArray results = element.getAsJsonObject().getAsJsonArray("results");

        for(JsonElement el : results){
            double lat = el.getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble();
            double lng = el.getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble();

            System.out.println(lat+","+lng);
        }

    }


    @Test
    public void testRequestNearbySearchBank()throws Exception{
        String json = testRequestNearbySearch();

        JsonElement element = new JsonParser().parse(json);


        JsonArray results = element.getAsJsonObject().getAsJsonArray("results");

        for(JsonElement el : results){

            String name = el.getAsJsonObject().get("name").getAsString();
            String alamat = el.getAsJsonObject().get("vicinity").getAsString();

            if(name!=null){
                //System.out.println("Nama bank : " + name);
            }

            if(alamat!=null){
                //System.out.println("Alamat bank : " + alamat);
            }

            JsonArray photos = el.getAsJsonObject().getAsJsonArray("photos");

            if(photos!=null){
                for(JsonElement p : photos){
                    //System.out.println("Photo url : " + p.getAsJsonObject().get("photo_reference").getAsString());
                    //Logger.getLogger(this.getClass().getName());

                    photoReferences.add(p.getAsJsonObject().get("photo_reference").getAsString());
                }
            }
            //System.out.println();
        }

    }
}
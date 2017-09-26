package com.kahfi.arief.belajarandroidgoogleplacephotosapi.api;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by arief on 26/09/17.
 */

public abstract class AbstractGooglePlacesRequest implements HttpRequestForGoogleAPI{

    private HttpUrl.Builder httpUrl = new HttpUrl.Builder();


    private static OkHttpClient createInstanceOkHttpClient(){
        return new OkHttpClient.Builder()
                .build();
    }



    @Override
    public void setInitHttpUrlBuilder
            (String scheme, String host, String pathSegments, Map<String, String> queryParam) {
        httpUrl.scheme(scheme);
        httpUrl.host(host);
        httpUrl.addPathSegments(pathSegments);
        for(Map.Entry set : queryParam.entrySet()){
            httpUrl.addQueryParameter((String)set.getKey(),(String)set.getValue());
        }
        //System.out.println(String.valueOf(httpUrl.toString()));
        //System.out.println("Bla");
    }



    public final Request prepareRequest(){

        return new Request.Builder()
                .url(httpUrl.build())
                .get()
                .build();
    }

    public Response getResponse() throws IOException {
        return createInstanceOkHttpClient().newCall(prepareRequest())
                .execute();
    }
}

package com.kahfi.arief.belajarandroidgoogleplacephotosapi.api;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

/**
 * Created by arief on 26/09/17.
 */

public interface HttpRequestForGoogleAPI {
    void setInitHttpUrlBuilder(
            String scheme,
            String host,
            String pathSegments,
            Map<String,String> queryParam
    );

}

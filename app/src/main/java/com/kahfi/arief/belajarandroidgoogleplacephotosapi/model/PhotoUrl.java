package com.kahfi.arief.belajarandroidgoogleplacephotosapi.model;

/**
 * Created by arief on 26/09/17.
 */

public class PhotoUrl {
    private String nama;
    private String alamat;
    private String url;


    public PhotoUrl(){}

    public PhotoUrl(String nama, String alamat , String url){
        this.nama=nama;
        this.alamat=alamat;
        this.url=url;
    }

    public void setNama(String nama){
        this.nama=nama;
    }
    public void setAlamat(String alamat){
        this.alamat=alamat;
    }

    public String getNama(){
        return nama;
    }
    public String getAlamat(){
        return alamat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

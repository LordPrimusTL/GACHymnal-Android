package com.gacpedromediateam.primus.gachymnal.Http;

import com.gacpedromediateam.primus.gachymnal.Helper.hymn;
import com.gacpedromediateam.primus.gachymnal.Helper.verse;

import java.util.List;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LordPrimus on 10/3/2017.
 */


public interface Api {

    //String EndPoint = "http://gacserver.000webhostapp.com/API/";
    String EndPoint = "http://10.0.2.2:8000/";

    @GET("getmainhymn")
    Observable<List<hymn>> getMainHymn();

    @GET("getapphymn")
    Observable<List<hymn>> getAppHymn();

    @GET("getmainverse")
    Observable<List<verse>> getMainVerse();

    @GET("getappverse")
    Observable<List<verse>> getAppVerse();

    @FormUrlEncoded
    @POST("installations")
    Observable<String> postInstallations(@Field("UUID") String UUID,@Field("PhoneType") String PhoneType, @Field("AndroidID") String AndroidID);

}

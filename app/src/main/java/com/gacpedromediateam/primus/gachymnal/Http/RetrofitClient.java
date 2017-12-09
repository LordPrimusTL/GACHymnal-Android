package com.gacpedromediateam.primus.gachymnal.Http;

import android.content.Context;

import com.gacpedromediateam.primus.gachymnal.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LordPrimus on 10/3/2017.
 */

public class RetrofitClient {
    private Api apiService = null;
    private static RetrofitClient retrofitClient = null;
    public static  String Defaulthost = "http://gacserver.000webhostapp.com/api/";
    //public static String Defaulthost = "http://10.0.2.2:8000/api/";

    private static Context context;
    public static RetrofitClient getInstance(Context ctx, String url) {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient(ctx,url);

        }
        return retrofitClient;
    }

    public RetrofitClient(final Context ctx, String url) {
        context = ctx;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        apiService = retrofit.create(Api.class);
    }

    public Api getApiService() {
        return apiService;
    }
}

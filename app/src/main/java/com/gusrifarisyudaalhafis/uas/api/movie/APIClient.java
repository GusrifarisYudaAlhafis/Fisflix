package com.gusrifarisyudaalhafis.uas.api.movie;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //deklarasi retrofit
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        //gunakan OkHttpClient dan set API key
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
            Request originalRequest = chain.request();
            HttpUrl httpUrl = originalRequest.url().newBuilder().addQueryParameter("api_key", "722af6d6186fe5b159504e8123359a35").build();
            originalRequest = originalRequest.newBuilder().url(httpUrl).build();
            return chain.proceed(originalRequest);
        }).build();
        if (retrofit == null) {
            //set base URL movie
            retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

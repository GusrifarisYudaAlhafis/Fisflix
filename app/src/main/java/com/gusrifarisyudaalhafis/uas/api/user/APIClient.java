package com.gusrifarisyudaalhafis.uas.api.user;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //set base URL dan deklarasi retrofit
    public static final String BASE_URL = "https://afis.tif18e.com/";
    public static Retrofit retrofit = null;

    //set base URL dengan Gson converter
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

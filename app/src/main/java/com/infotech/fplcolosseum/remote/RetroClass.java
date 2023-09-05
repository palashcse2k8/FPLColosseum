package com.infotech.fplcolosseum.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {


    // PROD
//    public static final String BASE_URL = "https://sblwallet.com.bd:8787/";
    // UAT
     static public final String BASE_URL = "https://sblwallet.com.bd:9797/";
    //SIT
//   static public final String BASE_URL = "http://10.10.2.42:9797/";

    //razia
//    private static final String BASE_URL = "http://10.10.2.26:8080/";
//    public static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(180, TimeUnit.SECONDS) .readTimeout(180, TimeUnit.SECONDS)
//            .writeTimeout(180, TimeUnit.SECONDS) .build();

    public static OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(new CustomHeaderInterceptor("", "", ""))
            .build();

    /*
     .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
     */

    private static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static com.infotech.fplcolosseum.remote.APIServices getAPIService() {
        return getRetrofitInstance().create(com.infotech.fplcolosseum.remote.APIServices.class);
    }
}

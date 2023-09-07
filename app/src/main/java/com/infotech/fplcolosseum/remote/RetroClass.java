package com.infotech.fplcolosseum.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {


    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://fontendfunctionsnortheuropenew.azurewebsites.net/";
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
        if (retrofit == null) {
            retrofit = getRetrofitInstance();
        }
        return retrofit.create(com.infotech.fplcolosseum.remote.APIServices.class);
    }
}

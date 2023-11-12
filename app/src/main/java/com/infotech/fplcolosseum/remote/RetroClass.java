package com.infotech.fplcolosseum.remote;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {


    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://fontendfunctionsnortheuropenew.azurewebsites.net/";

    static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache;
    public static OkHttpClient okHttpClient;

    /*
     .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
     */

    private static Retrofit getRetrofitInstance(Context context) {
        cache = new Cache(new File(context.getCacheDir(), "httpCache"), cacheSize);
        okHttpClient =  new OkHttpClient()
                .newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .addInterceptor(new CustomHeaderInterceptor("", "", ""))
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static com.infotech.fplcolosseum.remote.APIServices getAPIService(Context context) {

        if (retrofit == null) {
            retrofit = getRetrofitInstance(context);
        }
        return retrofit.create(com.infotech.fplcolosseum.remote.APIServices.class);
    }
}

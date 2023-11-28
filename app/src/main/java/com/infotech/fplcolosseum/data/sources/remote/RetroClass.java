package com.infotech.fplcolosseum.data.sources.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    CookieHandler cookieHandler = new CookieManager();

    private static Retrofit getRetrofitInstance(Context context) {
        cache = new Cache(new File(context.getCacheDir(), "httpCache"), cacheSize);

        // Add logging interceptor for debugging (optional)
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        // init cookie manager
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        okHttpClient =  new OkHttpClient()
                .newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .addInterceptor(new CustomHeaderInterceptor("", "", ""))
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addInterceptor(new AddCookiesInterceptor(context))
                .cookieJar(new MyCookieStore())
                .addNetworkInterceptor(httpLoggingInterceptor)
                .followRedirects(true)
                .followSslRedirects(true)
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static APIServices getAPIService(Context context) {

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        if (retrofit == null) {
            retrofit = getRetrofitInstance(context);
        }
        return retrofit.create(APIServices.class);
    }
}

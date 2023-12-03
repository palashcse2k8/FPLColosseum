package com.infotech.fplcolosseum.data.sources.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    private static final String KEY_TOKEN = "pl_profile";
    private static final String COOKIES = "Cookies";

    Context context;
    AddCookiesInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE);
        HashSet<String> preferences =  (HashSet) sharedPreferences.getStringSet(COOKIES, new HashSet<>());;

        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }

        return chain.proceed(builder.build());
    }
}
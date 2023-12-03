package com.infotech.fplcolosseum.data.sources.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    Context context;
    private static final String KEY_TOKEN = "pl_profile";
    private static final String COOKIES = "Cookies";

    ReceivedCookiesInterceptor(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        Log.d("Raw Response", originalResponse.peekBody(Long.MAX_VALUE).string());

        List<String> cookiesHeaders = originalResponse.headers("set-cookie");
        if (cookiesHeaders != null && !cookiesHeaders.isEmpty()) {
            HashSet<String> cookies = new HashSet<>(cookiesHeaders);

            SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE);
            sharedPreferences.edit().putStringSet(COOKIES, cookies).apply();
        }

        return originalResponse;
    }
}
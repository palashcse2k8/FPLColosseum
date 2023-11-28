package com.infotech.fplcolosseum.data.sources.remote;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class MyCookieStore implements CookieJar {

    private List<Cookie> cookies;

    Context context;


    MyCookieStore(Context context) {
        this.context = context;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        this.cookies = cookies;

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.COOKIES, Context.MODE_PRIVATE);

        Set<String> cookieStrings = new HashSet<>();

        for (Cookie cookie : cookies) {
            cookieStrings.add(cookie.toString());
        }
        sharedPreferences.edit().putStringSet(Constants.ALL_COOKIES, cookieStrings).apply();

        for (Cookie cookie: cookies) {

            if (cookie.name().equalsIgnoreCase(Constants.PL_PROFILE)){
                sharedPreferences.edit().putString(Constants.PL_PROFILE, cookie.value()).apply();
            }

            if (cookie.name().equalsIgnoreCase(Constants.SESSION_ID)){
                sharedPreferences.edit().putString(Constants.SESSION_ID, cookie.value()).apply();
            }
        }
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookies != null ? cookies : new ArrayList<>(); // set to default
    }
}
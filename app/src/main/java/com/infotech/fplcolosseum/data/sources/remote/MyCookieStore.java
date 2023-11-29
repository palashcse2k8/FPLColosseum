package com.infotech.fplcolosseum.data.sources.remote;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.infotech.fplcolosseum.features.login.models.SessionManager;
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
    SessionManager sessionManager;


    MyCookieStore(Application context) {
        this.context = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        this.cookies = cookies;

        Set<String> cookieStrings = new HashSet<>();

        for (Cookie cookie : cookies) {
            cookieStrings.add(cookie.toString());
        }

        sessionManager.setAllCookies(cookieStrings.toString());

        for (Cookie cookie: cookies) {

            if (cookie.name().equalsIgnoreCase(SessionManager.PL_PROFILE)){
                sessionManager.setPlProfile(cookie.value());
            }

            if (cookie.name().equalsIgnoreCase(SessionManager.SESSION_ID)){
                sessionManager.setSessionID(cookie.value());
            }
        }
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookies != null ? cookies : new ArrayList<>(); // set to default
    }
}
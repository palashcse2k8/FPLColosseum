package com.infotech.fplcolosseum.data.sources.network;

import static com.infotech.fplcolosseum.utilities.Constants.LOGIN_REDIRECT_URL;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.infotech.fplcolosseum.features.login.models.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
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

        //if user logged in retrieve the cookies
        if(sessionManager.isLoggedIn()) {
            cookies = getSavedCookies();
        }
    }

    public List<Cookie> getSavedCookies(){
        String cookiesString = sessionManager.getAllCookies();

        if(cookiesString == null) return null;

        Set<String> cookieStrings = new HashSet<>(Arrays.asList(cookiesString.split(",")));
        ArrayList<Cookie> cookies = new ArrayList<>();

        for (String cookieString : cookieStrings) {
            Cookie cookie = Cookie.parse(HttpUrl.parse(LOGIN_REDIRECT_URL), cookieString);
            if(cookie != null){
                cookies.add(cookie);
            }
        }

        return cookies;
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        if (this.cookies != null) {
            // Create a new ArrayList and add existing cookies
            List<Cookie> newCookies = new ArrayList<>(this.cookies);
            // Add the new cookies
            for (Cookie cookie: cookies) {
                if(!newCookies.contains(cookie)){
                    Log.d("Cookies", "New Cookie found!" + cookie.name()+ " " + cookie.value());
                    newCookies.add(cookie);
                }
            }

            // Update the reference to the modifiable list
            this.cookies = newCookies;
        } else {
            this.cookies = cookies;
        }

        Set<String> cookieStrings = new HashSet<>();

        for (Cookie cookie : cookies) {
            cookieStrings.add(cookie.toString());
        }

        sessionManager.setAllCookies(cookieStrings.toString());

        for (Cookie cookie : cookies) {

            if (cookie.name().equalsIgnoreCase(SessionManager.PL_PROFILE)) {
                sessionManager.setPlProfile(cookie.value());
            }

            if (cookie.name().equalsIgnoreCase(SessionManager.SESSION_ID)) {
                sessionManager.setSessionID(cookie.value());
            }
        }
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
//        if(url.toString().contains("login")){
//            return cookies != null ? cookies : new ArrayList<>(); // set to default
//        }


        return cookies != null ? cookies : new ArrayList<>(); // set to default
    }

}
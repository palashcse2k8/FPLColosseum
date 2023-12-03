package com.infotech.fplcolosseum.features.login.models;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class SessionManager {

    public static final String COOKIES = "cookies";

    public static final String ALL_COOKIES = "all_cookies";

    public static final String PL_PROFILE = "pl_profile";
    public static final String SESSION_ID = "sessionid";
    private static final String KEY_USERNAME = "user_name";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Application context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(COOKIES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public UserResponseModel createSession(String username, String token, String manager_id) {

        editor.putString(PL_PROFILE, token);
        editor.apply();

//        UserModel currentUser = new UserModel(username,);

        return null;

    }

    public boolean isLoggedIn() {
        return sharedPreferences.getString(PL_PROFILE, null) != null;
    }

    public void checkLogin() {
        if (!isLoggedIn()) {
            // Redirect to login activity
            // Example:
            // Intent intent = new Intent(context, LoginActivity.class);
            // context.startActivity(intent);
        }
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getSessionID() {
        return sharedPreferences.getString(SESSION_ID, null);
    }

    public void setSessionID(String sessionID) {
        editor.putString(SESSION_ID, sessionID).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username).apply();
    }

    public String getAllCookies() {
        return sharedPreferences.getString(ALL_COOKIES, null);
    }

    public void setAllCookies(String allCookies) {
        editor.putString(ALL_COOKIES, allCookies).apply();
    }


    public String getPlProfile() {
        return sharedPreferences.getString(PL_PROFILE, null);
    }

    public void setPlProfile(String plProfile) {
        editor.putString(PL_PROFILE, plProfile).apply();
    }

    public void logout() {
        editor.clear();
        editor.apply();
        redirectToLoginPage();
        // Redirect to login activity
        // Example:
        // Intent intent = new Intent(context, LoginActivity.class);
        // context.startActivity(intent);
    }

    public void redirectToLoginPage() {

        FragmentActivity fragmentActivity = (FragmentActivity) context;
        // Clear the entire back stack
        fragmentActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        //Go to login fragment
//        FragmentUtils.replace(
//                fragmentActivity.getSupportFragmentManager(),
//                GameWeekDashboardFragment_.builder().build(),
//                R.id.contentFrame,
//                true,
//                R.anim.enter_from_right, // enter
//                R.anim.exit_to_left,      // exit
//                R.anim.enter_from_right,   // popEnter
//                R.anim.exit_to_left      // popExit
//        );
    }
}
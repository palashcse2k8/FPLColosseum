package com.infotech.fplcolosseum.features.login.models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;

public class SessionManager {

    public static final String COOKIES = "cookies";

    private static final String PREFS_NAME = "ApplicationData";

    public static final String ALL_COOKIES = "all_cookies";

    public static final String PL_PROFILE = "pl_profile";
    public static final String SESSION_ID = "sessionid";
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_USER_DATA = "user_name";

    public static final String MANAGER_ID = "manager_id";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public UserResponseModel createSession(String username, String token, String manager_id) {

        editor.putString(PL_PROFILE, token);
        editor.apply();

//        UserModel currentUser = new UserModel(username,);

        return null;

    }

    public boolean isLoggedIn() {
        return sharedPreferences.getString(KEY_USER_DATA, null) != null && sharedPreferences.getString(ALL_COOKIES, null) != null;
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

    public long getManagerId() {

        UserResponseModel userResponseModel = getUserData();
        if(userResponseModel == null) return 0;
        return userResponseModel.getPlayer().getEntry();
    }

    public void saveManagerId(long managerId) {
        editor.putLong(MANAGER_ID, managerId).apply();
    }

    public void saveUserData(UserResponseModel userResponseModel) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userResponseModel);
        editor.putString(KEY_USER_DATA, json);
        editor.apply();
    }

    public UserResponseModel getUserData() {
        String json = sharedPreferences.getString(KEY_USER_DATA, null);
        if (json != null) {
            Gson gson = new Gson();
            return gson.fromJson(json, UserResponseModel.class);
        }
        return null;
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_DATA);
        editor.apply();
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
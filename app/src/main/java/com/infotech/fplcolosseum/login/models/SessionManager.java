package com.infotech.fplcolosseum.login.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.FragmentUtils;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.gameweek.views.GameWeekDashboardFragment_;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TOKEN = "pl_profile";
    private static final String KEY_MANAGER_ID = "manager_id";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String username, String token, String manager_id) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_MANAGER_ID, manager_id);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getString(KEY_TOKEN, null) != null;
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

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
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

    public void redirectToLoginPage(){

        FragmentActivity fragmentActivity = (FragmentActivity) context;
        // Clear the entire back stack
        fragmentActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        //Go to login fragment
        FragmentUtils.replace(
                fragmentActivity.getSupportFragmentManager(),
                GameWeekDashboardFragment_.builder().build(),
                R.id.contentFrame,
                true,
                R.anim.enter_from_right, // enter
                R.anim.exit_to_left,      // exit
                R.anim.enter_from_right,   // popEnter
                R.anim.exit_to_left      // popExit
        );
    }
}
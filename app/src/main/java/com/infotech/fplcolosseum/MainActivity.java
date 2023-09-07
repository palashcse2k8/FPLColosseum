package com.infotech.fplcolosseum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.infotech.fplcolosseum.gameweek.views.GameWeekDashboard;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        setupActionBar();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        Fragment fragment = new GameWeekDashboard();
        String tag = fragment.getClass().getSimpleName();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.contentFrame, fragment, tag).commit();

    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        actionBar.setTitle("Game Week Standings");
    }

    public void showDialogProgress() {

        if(mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideProgressDialog() {

        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
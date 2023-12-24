package com.infotech.fplcolosseum;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.infotech.fplcolosseum.features.homepage.views.HomePageFragment;
import com.infotech.fplcolosseum.features.homepage.views.MyTeamFragment;
import com.infotech.fplcolosseum.features.login.views.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        setupActionBar();
//        Fragment fragment = new GameWeekDashboardFragment();
//        Fragment fragment = new LoginFragment();
//        Fragment fragment = new HomePageFragment();
        Fragment fragment = new MyTeamFragment("MyTeam");
        String tag = fragment.getClass().getSimpleName();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.contentFrame, fragment, tag).commit();

    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        assert actionBar != null;
        actionBar.setTitle("Game Week Standings");
    }

}
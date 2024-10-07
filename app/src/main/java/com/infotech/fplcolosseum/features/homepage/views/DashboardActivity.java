package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.login.views.TestFragment;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout tabContainer;
    private long managerId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("managerID")) {
            managerId = intent.getLongExtra("managerID", 0L);

        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        tabContainer = findViewById(R.id.tab_container);


        // Set default fragment (TabLayout with ViewPager2) on initial load
        if (savedInstanceState == null) {
            loadFragment(HomePageFragment.newInstance(managerId));
        }
        setUpBottomNavigation();
    }

    public void setUpBottomNavigation() {
        // Assuming you have 5 items in your bottom navigation
        int middleItemId = bottomNavigationView.getMenu().getItem(2).getItemId();

        // Select the middle item programmatically
        bottomNavigationView.setSelectedItemId(middleItemId);

        // Handle BottomNavigationView item clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                loadFragment(HomePageFragment.newInstance(managerId));
                return true;
            } else if (item.getItemId() == R.id.navigation_fixture) {
                loadFragment(new StatusFragment());
                return true;
            } else if (item.getItemId() == R.id.navigation_status) {
                loadFragment(new StatusFragment());
                return true;
            } else {
                return false;
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        // Replace the content in tab_container with the selected fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tab_container, fragment)
                .commit();
        //        HomePageFragment_ homePageFragment = (HomePageFragment_) HomePageFragment.newInstance(10359552);
//        FragmentUtils.replace(
//                getSupportFragmentManager(),
//                HomePageFragment_.builder().arg(HomePageFragment.ARG_MANAGER_ID, managerId).build(),
//                R.id.tab_container,
//                true,
//                R.anim.enter_from_right, // enter
//                R.anim.exit_to_left,      // exit
//                R.anim.enter_from_right,   // popEnter
//                R.anim.exit_to_left      // popExit
//        );
    }
}

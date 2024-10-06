package com.infotech.fplcolosseum.features.homepage.views;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.login.views.TestFragment;

public class DashboardActivity extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout tabContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        tabContainer = view.findViewById(R.id.tab_container);

        // Set default fragment (TabLayout with ViewPager2) on initial load
        if (savedInstanceState == null) {
            loadFragment(new HomePageFragment());
        }

        // Handle BottomNavigationView item clicks
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.navigation_home){
                    loadFragment(new HomePageFragment());
                    return true;
                } else if (item.getItemId() == R.id.navigation_fixture) {
                    loadFragment(new TestFragment());
                    return true;
                } else {
                    return false;
                }
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        // Replace the content in tab_container with the selected fragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.tab_container, fragment)
                .commit();
    }
}

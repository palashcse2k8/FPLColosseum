package com.infotech.fplcolosseum.features.homepage.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.utilities.Constants;

import es.dmoral.toasty.Toasty;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout tabContainer;
    private long managerId;

    private DrawerLayout drawerLayout;
    NavigationView navigationView;


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

        drawerLayout = findViewById(R.id.drawer_layout);

        // Set up the navigation drawer
        navigationView = findViewById(R.id.nav_view);
        setUpDrawerNavigation();
        // Set default fragment (TabLayout with ViewPager2) on initial load
        if (savedInstanceState == null) {
            loadFragment(HomePageFragment.newInstance(managerId));
        }
        setUpBottomNavigation();
    }


    public void setUpDrawerNavigation() {

        updateDrawerHeader();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Handle navigation view item clicks here
                        int id = menuItem.getItemId();
                        if (id == R.id.dreamTeam) {
                            Toasty.info(getApplicationContext(), "Dream Team clicked", Toasty.LENGTH_SHORT).show();
                        } else if (id == R.id.logout) {
                            Toasty.info(getApplicationContext(), "Log out clicked", Toasty.LENGTH_SHORT).show();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
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
                loadFragment(new FixturesFragment());
                return true;
            } else if (item.getItemId() == R.id.navigation_status) {
                loadFragment(new StatusFragment());
                return true;
            } else {
                return false;
            }
        });
    }

    // Method to update the header of the drawer
    public void updateDrawerHeader() {
        // Get the header view
        View headerView = navigationView.getHeaderView(0);

        // Find the views in the header and update them
        TextView nameTextView = headerView.findViewById(R.id.managerName);
        TextView teamIDTextView = headerView.findViewById(R.id.teamID);
        TextView emailTextView = headerView.findViewById(R.id.managerEmail);
        ImageButton copyButton = headerView.findViewById(R.id.copyButton);

        // Update with user information
        if(Constants.LoggedInUser.getPlayer() != null){
            String managerFullName = Constants.LoggedInUser.getPlayer().getFirst_name() + " " + Constants.LoggedInUser.getPlayer().getLast_name();
            nameTextView.setText(managerFullName);
            String idText = "ID : " + Constants.LoggedInUser.getPlayer().getEntry();
            teamIDTextView.setText(idText);
            emailTextView.setText(Constants.LoggedInUser.getPlayer().getEmail());
            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String teamID = String.valueOf(Constants.LoggedInUser.getPlayer().getEntry());
                    copyToClipboard(teamID);
                }
            });

            TooltipCompat.setTooltipText(copyButton, getString(R.string.copy_team_id_tooltip));
        }
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

    // Method to copy the team ID to clipboard
    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Team ID", text);
        clipboard.setPrimaryClip(clip);

        // Show a toast message indicating the text is copied
//        Toasty.info(this, "Team ID copied to clipboard", Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Method to toggle drawer, can be called from fragments
    public void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}

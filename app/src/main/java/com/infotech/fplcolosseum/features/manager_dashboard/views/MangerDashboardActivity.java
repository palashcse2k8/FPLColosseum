package com.infotech.fplcolosseum.features.manager_dashboard.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.ActivityManagerDashboarBinding;
import com.infotech.fplcolosseum.databinding.FragmentHomepageBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.ViewPagerAdapter;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.features.homepage.views.DashboardActivity;
import com.infotech.fplcolosseum.features.homepage.views.FixturesFragment;
import com.infotech.fplcolosseum.features.homepage.views.HomePageFragment;
import com.infotech.fplcolosseum.features.homepage.views.LeaguesFragment;
import com.infotech.fplcolosseum.features.homepage.views.MyTeamFragment;
import com.infotech.fplcolosseum.features.homepage.views.PointsFragment;
import com.infotech.fplcolosseum.features.homepage.views.StatusFragment;
import com.infotech.fplcolosseum.features.homepage.views.TransferFragment;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MangerDashboardActivity extends AppCompatActivity {

    ActivityManagerDashboarBinding binding;

    private long managerId;
    public static final String ARG_MANAGER_ID = "manager_id";

    HomePageSharedViewModel viewModel;
    private Toolbar currentToolbar;

    List<Toolbar> toolbarList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManagerDashboarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(ARG_MANAGER_ID)) {
            managerId = intent.getLongExtra(ARG_MANAGER_ID, 0L);
        }

        viewModel = new ViewModelProvider(this).get(HomePageSharedViewModel.class);

        setupViewPager(binding.viewPager, binding.tabLayout);

        observeToolbarChanges();

    }

    private void observeToolbarChanges() {
        // Observe toolbar title changes
        viewModel.getToolbarTitle().observe(this, title -> {
            if (currentToolbar != null) {
                TextView titleView = currentToolbar.findViewById(R.id.teamName);
                titleView.setText(title);
                titleView.setSelected(true);
            }
        });

        // Observe toolbar subtitle changes
        viewModel.getToolbarSubTitle().observe(this, subtitle -> {
            if (currentToolbar != null) {
                TextView subtitleView = currentToolbar.findViewById(R.id.managerName);
                subtitleView.setText(subtitle);
                subtitleView.setSelected(true);
            }
        });
    }


    private void setupViewPager(ViewPager2 viewPager, TabLayout tabLayout) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        adapter.addFragment(new PointsFragment().newInstance(managerId), "Points");
        adapter.addFragment(new LeaguesFragment().newInstance(managerId, true), "Leagues");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())
        ).attach();

        handleTabSelection();

        // Initialize toolbars for each tab
        toolbarList = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            createToolbarForPosition(i);
        }

        switchToolbar(0);
    }

    private void handleTabSelection() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switchToolbar(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void createToolbarForPosition(int position) {

        LayoutInflater inflater = LayoutInflater.from(this);
        Toolbar toolbar;

        if (position == 0 || position == 1) {
            toolbar = (Toolbar) inflater.inflate(R.layout.toolbar_other_manager_point_fragment, null, false);
//            setupPointFragmentToolbar(toolbar);
        } else {
            toolbar = (Toolbar) inflater.inflate(R.layout.toolbar_league_fragment, null, false);
        }

        toolbarList.add(toolbar);

    }

    private void setupPointFragmentToolbar(Toolbar toolbar) {
        TextView teamNameTextView = toolbar.findViewById(R.id.teamName);
        TextView managerNameTextView = toolbar.findViewById(R.id.managerName);

        if (viewModel.getToolbarTitle() != null) {
            teamNameTextView.setText(viewModel.getToolbarTitle().getValue());
        }

        if (viewModel.getToolbarSubTitle() != null) {
            managerNameTextView.setText(viewModel.getToolbarSubTitle().getValue());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void switchToolbar(int position) {

        ViewGroup rootView = binding.toolbarContainer;

        // Remove the current toolbar if exists
        if (currentToolbar != null) {
            rootView.removeView(currentToolbar);
        }

        // Get the toolbar for the current position
        if (position < toolbarList.size()) {
            currentToolbar = toolbarList.get(position);

            // Add the toolbar to the root view
            rootView.addView(currentToolbar);

//                setupPointFragmentToolbar(currentToolbar);

            // Set the new toolbar as the action bar
            setSupportActionBar(currentToolbar);

            currentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(Constants.LOG_TAG, "Icon Clicked!");
                    finish();
                }
            });
        }
    }
}

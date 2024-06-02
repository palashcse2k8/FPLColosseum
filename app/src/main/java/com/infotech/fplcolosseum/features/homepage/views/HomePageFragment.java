package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentHomepageBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.ViewPagerAdapter;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import org.androidannotations.annotations.EFragment;

@EFragment(resName = "fragment_homepage")
public class HomePageFragment extends Fragment {

    private static final String ARG_MANAGER_ID = "manager_id";
    FragmentHomepageBinding binding;

    HomePageSharedViewModel viewModel;
    private Toolbar currentToolbar;


    private long managerId;

    public static HomePageFragment newInstance(long managerId) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MANAGER_ID, managerId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            managerId = getArguments().getInt(ARG_MANAGER_ID);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
//        viewModel.getTeamCurrentGameWeekAllData(managerId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(binding.topViewPager, binding.topTabLayout);
//        setupViewPager(binding.bottomViewPager, binding.bottomTabLayout); // for tab layout if needed
    }

    private void setupViewPager(ViewPager2 viewPager, TabLayout tabLayout) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());

        // show if user logged in
        if(Constants.LoggedInUser != null){
            adapter.addFragment(new MyTeamFragment(), "My Team");
        }
        adapter.addFragment(new PointsFragment(), "Points");

        // show if user logged in
        if(Constants.LoggedInUser != null){
            adapter.addFragment(new TransferFragment().newInstance(Constants.LoggedInUser.getPlayer().getEntry()), "Transfers");
        }

//        adapter.addFragment(new MyTeamFragment(), "Leagues");
        adapter.addFragment(new PointsFragment(), "Leagues");

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())
        ).attach();


        handleTabSelection();

        // Set the initial toolbar
        switchToolbar(0);
    }

    private void handleTabSelection() {
        binding.topTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switchToolbar(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void switchToolbar(int position) {
        if (getActivity() != null) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup rootView = getView().findViewById(R.id.toolbar_container);

            // Remove the current toolbar if exists
            if (currentToolbar != null) {
                rootView.removeView(currentToolbar);
            }

            // Inflate the new toolbar layout
            if (position == 0) {
                currentToolbar = (Toolbar) inflater.inflate(R.layout.toolbar_point_fragment, rootView, false);
            } else if (position == 1) {
                currentToolbar = (Toolbar) inflater.inflate(R.layout.toolbar_league_fragment, rootView, false);
            }
            // Add more cases for additional tabs

            // Add the new toolbar to the root view
            rootView.addView(currentToolbar);

            // Set the new toolbar as the action bar
            ((AppCompatActivity) getActivity()).setSupportActionBar(currentToolbar);
        }
    }
}

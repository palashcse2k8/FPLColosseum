
package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.databinding.FragmentHomepageBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.ViewPagerAdapter;
import com.infotech.fplcolosseum.features.homepage.models.MyTeamMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

@EFragment(resName = "fragment_homepage")
public class HomePageFragment extends Fragment {

    public static final String ARG_MANAGER_ID = "manager_id";
    FragmentHomepageBinding binding;

    HomePageSharedViewModel viewModel;
    private Toolbar currentToolbar;

    private int lastSelectedTab = 0;

    List<Toolbar> toolbarList = new ArrayList<>();

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
            managerId = getArguments().getLong(ARG_MANAGER_ID);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        if (viewModel.getMyTeamMergedResponseLiveData().getValue() == null || viewModel.getMyTeamMergedResponseLiveData().getValue().getData() == null) {
            viewModel.getMyTeamMergedData(managerId);
        }

        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current tab position
        outState.putInt("selected_tab", lastSelectedTab);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        // Restore last selected tab if saved instance exists
        if (savedInstanceState != null) {
            lastSelectedTab = savedInstanceState.getInt("selected_tab", 0);
        }

        setupViewPager(binding.topViewPager, binding.topTabLayout);
        observeToolbarChanges();

        return binding.getRoot();
    }

    private void observeToolbarChanges() {
        // Observe toolbar title changes
        viewModel.getToolbarTitle().observe(getViewLifecycleOwner(), title -> {
            if (currentToolbar != null) {
                TextView titleView = currentToolbar.findViewById(R.id.teamName);
                titleView.setText(title);
                titleView.setSelected(true);
            }
        });

        // Observe toolbar subtitle changes
        viewModel.getToolbarSubTitle().observe(getViewLifecycleOwner(), subtitle -> {
            if (currentToolbar != null) {
                TextView subtitleView = currentToolbar.findViewById(R.id.managerName);
                subtitleView.setText(subtitle);
                subtitleView.setSelected(true);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the initial toolbar
        switchToolbar(lastSelectedTab);
    }

    private void setupViewPager(ViewPager2 viewPager, TabLayout tabLayout) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());

        adapter.addFragment(new MyTeamFragment(), "My Team");

        adapter.addFragment(new TransferFragment().newInstance(managerId), "Transfers");

        adapter.addFragment(new PointsFragment().newInstance(managerId), "Points");
        adapter.addFragment(new LeaguesFragment().newInstance(managerId, false), "Leagues");

        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(1);

        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                lastSelectedTab = position;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())
        ).attach();

        handleTabSelection();

        // Initialize toolbars for each tab
        toolbarList = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            createToolbarForPosition(i);
        }
    }

    private void handleTabSelection() {
        binding.topTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        if (getActivity() != null) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            Toolbar toolbar;

            if (position == 0 || position == 1 || position == 2 || position == 3) {
                toolbar = (Toolbar) inflater.inflate(R.layout.toolbar_point_fragment, null, false);
            } else {
                toolbar = (Toolbar) inflater.inflate(R.layout.toolbar_league_fragment, null, false);
            }

            toolbarList.add(toolbar);
        }
    }

    private void setupPointFragmentToolbar(Toolbar toolbar) {
        TextView teamNameTextView = toolbar.findViewById(R.id.teamName);
        TextView managerNameTextView = toolbar.findViewById(R.id.managerName);

        if (viewModel.getToolbarTitle() != null) {
            teamNameTextView.setText(viewModel.getToolbarTitle().getValue());

        }

        if (viewModel.getToolbarSubTitle() != null) {
            managerNameTextView.setText(viewModel.getToolbarSubTitle().getValue());
            managerNameTextView.setSelected(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        toolbarList.clear();
    }

    private void switchToolbar(int position) {
        if (getActivity() != null) {
            ViewGroup rootView = requireView().findViewById(R.id.toolbar_container);

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
                ((AppCompatActivity) getActivity()).setSupportActionBar(currentToolbar);

                // Set up the navigation icon click listener
                currentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(Constants.LOG_TAG, "Icon Clicked!");
                        ((DashboardActivity) requireActivity()).toggleDrawer();
                    }
                });
            }
        }

        if (currentToolbar != null)
            setupPointFragmentToolbar(currentToolbar);
    }

    public boolean isAtFirstTab() {
        return binding.topViewPager.getCurrentItem() == 0;
    }

    public void handleFragmentBackPress() {
        if (!isAtFirstTab()) {
            // Move to the previous tab
            binding.topViewPager.setCurrentItem(0, true);
        }
    }
}
//package com.infotech.fplcolosseum.features.homepage.views;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.tabs.TabLayoutMediator;
//import com.infotech.fplcolosseum.R;
//import com.infotech.fplcolosseum.databinding.FragmentHomepageBinding;
//import com.infotech.fplcolosseum.features.homepage.adapter.ViewPagerAdapter;
//import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
//import com.infotech.fplcolosseum.utilities.Constants;
//
//import org.androidannotations.annotations.EFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@EFragment(resName = "fragment_homepage")
//public class HomePageFragment extends Fragment {
//
//    public static final String ARG_MANAGER_ID = "manager_id";
//    FragmentHomepageBinding binding;
//
//    HomePageSharedViewModel viewModel;
//    private Toolbar currentToolbar;
//
//    List<Toolbar> toolbarList = new ArrayList<>();
//
//    private long managerId;
//
//    public static HomePageFragment newInstance(long managerId) {
//        HomePageFragment fragment = new HomePageFragment();
//        Bundle args = new Bundle();
//        args.putLong(ARG_MANAGER_ID, managerId);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            managerId = getArguments().getLong(ARG_MANAGER_ID);
//        }
//        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
////        viewModel.getTeamCurrentGameWeekAllData(managerId);
//        viewModel.getMyTeamMergedData(managerId);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentHomepageBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        setupViewPager(binding.topViewPager, binding.topTabLayout);
////        setupViewPager(binding.bottomViewPager, binding.bottomTabLayout); // for tab layout if needed
//    }
//
//    private void setupViewPager(ViewPager2 viewPager, TabLayout tabLayout) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());
//
//        // show if user logged in
//        if (Constants.LoggedInUser != null) {
//            adapter.addFragment(new MyTeamFragment(), "My Team");
//            adapter.addFragment(new TransferFragment().newInstance(Constants.LoggedInUser.getPlayer().getEntry()), "Transfers");
//        }
//        adapter.addFragment(new PointsFragment(), "Points");
//
////        adapter.addFragment(new MyTeamFragment(), "Leagues");
////        adapter.addFragment(new PointsFragment(), "Leagues");
//
//        viewPager.setAdapter(adapter);
//
//        new TabLayoutMediator(tabLayout, viewPager,
//                (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())
//        ).attach();
//
//
//        handleTabSelection();
//
//        // Set the initial toolbar
//        switchToolbar(0);
//    }
//
//    private void handleTabSelection() {
//        binding.topTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                switchToolbar(position);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//    }
//
//    private void switchToolbar(int position) {
//        if (getActivity() != null) {
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            ViewGroup rootView = requireView().findViewById(R.id.toolbar_container);
//
//            // Remove the current toolbar if exists
//            if (currentToolbar != null) {
//
//                rootView.removeView(currentToolbar);
//            }
//
//            // Inflate the new toolbar layout
//            if (position == 0 || position == 1 || position == 2) {
//                currentToolbar = (Toolbar) inflater.inflate(R.layout.toolbar_point_fragment, rootView, false);
//
//                // Set team and manager names dynamically
//                if (currentToolbar != null) {
//                    TextView teamNameTextView = currentToolbar.findViewById(R.id.teamName);
//                    TextView managerNameTextView = currentToolbar.findViewById(R.id.managerName);
//
//                    // Set the team and manager names (these could come from a model or passed as arguments)
//
//                    if (Constants.managerName != null) {
//                        managerNameTextView.setText(Constants.managerName);
//                    }
//
//                    if (Constants.teamName != null) {
//                        teamNameTextView.setText(Constants.teamName);
//                    }
//                }
//            } else if (position == -1) {
//                currentToolbar = (Toolbar) inflater.inflate(R.layout.toolbar_league_fragment, rootView, false);
//            }
//            // Add more cases for additional tabs
//
//            // Add the new toolbar to the root view
//            rootView.addView(currentToolbar);
//
//            // Set the new toolbar as the action bar
//            ((AppCompatActivity) getActivity()).setSupportActionBar(currentToolbar);
//        }
//    }
//}


package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

@EFragment(resName = "fragment_homepage")
public class HomePageFragment extends Fragment {

    public static final String ARG_MANAGER_ID = "manager_id";
    FragmentHomepageBinding binding;

    HomePageSharedViewModel viewModel;
    private Toolbar currentToolbar;

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
        viewModel.getMyTeamMergedData(managerId);
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
//        if(viewModel.getPreviousFragment().getValue() == null || viewModel.getPreviousFragment().getValue().isEmpty()){
//
//        }

        setupViewPager(binding.topViewPager, binding.topTabLayout);

    }

    private void setupViewPager(ViewPager2 viewPager, TabLayout tabLayout) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());

        adapter.addFragment(new MyTeamFragment(), "My Team");

        adapter.addFragment(new TransferFragment().newInstance(managerId), "Transfers");

        adapter.addFragment(new PointsFragment(), "Points");
//        adapter.addFragment(new PointsFragment(), "Points");
//        adapter.addFragment(new PointsFragment(), "Points");
//        adapter.addFragment(new PointsFragment(), "Leagues");

        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())
        ).attach();

        handleTabSelection();

        // Initialize toolbars for each tab
        toolbarList = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            createToolbarForPosition(i);
        }

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

            if (position == 0 || position == 1 || position == 2) {
                toolbar = (Toolbar) inflater.inflate(R.layout.toolbar_point_fragment, null, false);
                setupPointFragmentToolbar(toolbar);
            } else {
                toolbar = (Toolbar) inflater.inflate(R.layout.toolbar_league_fragment, null, false);
            }

            toolbarList.add(toolbar);
        }
    }

    private void setupPointFragmentToolbar(Toolbar toolbar) {
        TextView teamNameTextView = toolbar.findViewById(R.id.teamName);
        TextView managerNameTextView = toolbar.findViewById(R.id.managerName);

//        if (Constants.managerName != null) {
//            managerNameTextView.setText(Constants.managerName);
//        }
//
//        if (Constants.teamName != null) {
//            teamNameTextView.setText(Constants.teamName);
//        }

        if(viewModel.getToolbarTitle() != null){
            teamNameTextView.setText(viewModel.getToolbarTitle().getValue());
        }

        if(viewModel.getToolbarSubTitle() != null){
            managerNameTextView.setText(viewModel.getToolbarSubTitle().getValue());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
            }
        }
    }


}
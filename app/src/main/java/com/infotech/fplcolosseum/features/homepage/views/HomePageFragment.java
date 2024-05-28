package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.databinding.FragmentHomepageBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.ViewPagerAdapter;
import com.infotech.fplcolosseum.utilities.Constants;

import org.androidannotations.annotations.EFragment;

@EFragment(resName = "fragment_homepage")
public class HomePageFragment extends Fragment {

    FragmentHomepageBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        adapter.addFragment(new GameWeekPointsFragment(), "Points");

        // show if user logged in
        if(Constants.LoggedInUser != null){
            adapter.addFragment(new TransferFragment().newInstance(Constants.LoggedInUser.getPlayer().getEntry()), "Transfers");
        }

        adapter.addFragment(new MyTeamFragment(), "Leagues");

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position).toString())
        ).attach();
    }
}

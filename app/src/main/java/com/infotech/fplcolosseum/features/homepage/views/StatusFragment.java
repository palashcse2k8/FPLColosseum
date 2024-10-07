package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.FragmentUtils;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentLoginBinding;
import com.infotech.fplcolosseum.databinding.FragmentStatusBinding;
import com.infotech.fplcolosseum.features.gameweek.views.GameWeekDashboardFragment_;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.features.login.models.SessionManager;
import com.infotech.fplcolosseum.features.login.models.UserResponseModel;
import com.infotech.fplcolosseum.features.login.viewmodel.LoginViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

public class StatusFragment extends Fragment {
    FragmentStatusBinding binding;
    private HomePageSharedViewModel sharedViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStatusBinding.inflate(inflater, container, false);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.appbarLogin.toolbar);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        binding.setHomePageViewModel(sharedViewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Constants.LOG_TAG, sharedViewModel.getPointsMergedResponseLiveData().getValue().getData().getGameWeekPicksModel().getEntry_history().getPoints() + "");
        Log.d(Constants.LOG_TAG, sharedViewModel.getPointsMergedResponseLiveData().getValue().getData().getGameWeekPicksModel().getEntry_history().getTotal_points() + "");
    }

    public void goToStanding() {
        FragmentUtils.replace(
                requireActivity().getSupportFragmentManager(),
                GameWeekDashboardFragment_.builder().build(),
                R.id.contentFrame,
                true,
                R.anim.enter_from_right, // enter
                R.anim.exit_to_left,      // exit
                R.anim.enter_from_right,   // popEnter
                R.anim.exit_to_left      // popExit
        );
    }

    public void goToHomePage(long managerID) {

//        HomePageFragment_ homePageFragment = (HomePageFragment_) HomePageFragment.newInstance(10359552);
        FragmentUtils.replace(
                requireActivity().getSupportFragmentManager(),
                HomePageFragment_.builder().arg(HomePageFragment.ARG_MANAGER_ID, managerID).build(),
                R.id.contentFrame,
                true,
                R.anim.enter_from_right, // enter
                R.anim.exit_to_left,      // exit
                R.anim.enter_from_right,   // popEnter
                R.anim.exit_to_left      // popExit
        );
    }

    public void gotoDashboardActivity(long managerID) {

//        HomePageFragment_ homePageFragment = (HomePageFragment_) HomePageFragment.newInstance(10359552);
//        FragmentUtils.replace(
//                requireActivity().getSupportFragmentManager(),
//                HomePageFragment_.builder().arg(HomePageFragment.ARG_MANAGER_ID, managerID).build(),
//                R.id.contentFrame,
//                true,
//                R.anim.enter_from_right, // enter
//                R.anim.exit_to_left,      // exit
//                R.anim.enter_from_right,   // popEnter
//                R.anim.exit_to_left      // popExit
//        );


        Intent myIntent = new Intent(requireActivity(), DashboardActivity.class);
        myIntent.putExtra("managerID", managerID); //Optional parameters
        startActivity(myIntent);
    }


}

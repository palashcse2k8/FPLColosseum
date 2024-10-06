package com.infotech.fplcolosseum.features.login.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.infotech.fplcolosseum.features.gameweek.views.GameWeekDashboardFragment_;
import com.infotech.fplcolosseum.features.homepage.views.DashboardActivity;
import com.infotech.fplcolosseum.features.homepage.views.HomePageFragment;
import com.infotech.fplcolosseum.features.homepage.views.HomePageFragment_;
import com.infotech.fplcolosseum.features.homepage.views.PlayerSelectionFragment;
import com.infotech.fplcolosseum.features.login.models.SessionManager;
import com.infotech.fplcolosseum.features.login.models.UserResponseModel;
import com.infotech.fplcolosseum.features.login.viewmodel.LoginViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;


import org.androidannotations.annotations.EFragment;

@EFragment(resName = "fragment_login")
public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.appbarLogin.toolbar);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        //        Toolbar pointToolbar = binding.getRoot().findViewById(R.id.toolbar_test);
//        AppCompatActivity activity = (AppCompatActivity) requireActivity();
//        activity.getSupportActionBar();
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnFocusChangeListener();

        loginViewModel.getApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            // Update the LiveData with the result from the repository
            if (apiResponse != null) {
                loginViewModel.dataLoading.setValue(false);
                switch (apiResponse.getStatus()) {
                    case SUCCESS:
                        // Handle success
                        if (apiResponse.getData() instanceof UserResponseModel) {
                            UserResponseModel data = (UserResponseModel) apiResponse.getData();
                            if (data.getPlayer().getEntry() != 0) {
                                UIUtils.toast(requireContext(), "Successfully logged in, Welcome Mr. " + data.getPlayer().getFirst_name() + " " + data.getPlayer().getLast_name(), ToastLevel.SUCCESS);
                            }

                            saveUserInfo(data);
                            Constants.LoggedInUser = data;

                            goToHomePage(Constants.LoggedInUser.getPlayer().getEntry());

                        } else if (apiResponse.getData() instanceof String) {
                            UIUtils.toast(requireContext(), apiResponse.getMessage(), ToastLevel.WARNING);
                            // Handle success for AnotherModel
                        } else {
                            UIUtils.toast(requireContext(), "Login Failed!, Check your credential.", ToastLevel.WARNING);
                        }

                        break;
                    case ERROR:
                        // Handle error
                        UIUtils.toast(requireContext(), apiResponse.getMessage(), ToastLevel.WARNING);

                        break;
                    case LOADING:
                        // Handle loading

                        String errorMessage = apiResponse.getMessage();
                        UIUtils.toast(requireContext(), apiResponse.getMessage(), ToastLevel.WARNING);
                        break;
                }
            }
        });

        binding.fplcStandings.setOnClickListener(view1 -> {
            goToStanding();
        });

        //TODO
        binding.buttonGuestUser.setOnClickListener(v -> {
//            goToHomePage();

            FragmentUtils.replace(
                    requireActivity().getSupportFragmentManager(),
                    new PlayerSelectionFragment(),
                    R.id.contentFrame,
                    true,
                    R.anim.enter_from_right, // enter
                    R.anim.exit_to_left,      // exit
                    R.anim.enter_from_right,   // popEnter
                    R.anim.exit_to_left      // popExit
            );
        });
    }

    public void saveUserInfo(UserResponseModel userData) {

        SessionManager sessionManager = new SessionManager(requireContext().getApplicationContext());
        sessionManager.saveUserData(userData);

    }

    public void setOnFocusChangeListener() {
        binding.textInputUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    binding.textInputLayoutUsername.setHint("");
                else {
                    if (TextUtils.isEmpty(binding.textInputUsername.getText())) {
                        binding.textInputLayoutUsername.setHint("Enter Username or Email");
                    }
                }
            }
        });

        binding.textInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // If the user starts typing, hide the hint
                if (charSequence.length() > 0) {
                    binding.textInputLayoutUsername.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });

        binding.textInputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    binding.textInputLayoutPassword.setHint("");
                else {
                    if (TextUtils.isEmpty(binding.textInputPassword.getText())) {
                        binding.textInputLayoutPassword.setHint("Enter your password");
                    }
                }
            }
        });

        binding.textInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // If the user starts typing, hide the hint
                if (charSequence.length() > 0) {
                    binding.textInputLayoutPassword.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
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


        Intent myIntent = new Intent(requireActivity(), DashboardActivity.class);
//        myIntent.putExtra("key", value); //Optional parameters
        startActivity(myIntent);
    }


}

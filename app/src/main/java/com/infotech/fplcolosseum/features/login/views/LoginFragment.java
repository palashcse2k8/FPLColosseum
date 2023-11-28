package com.infotech.fplcolosseum.features.login.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.FragmentUtils;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentLoginBinding;
import com.infotech.fplcolosseum.gameweek.views.GameWeekDashboardFragment_;
import com.infotech.fplcolosseum.features.login.viewmodel.LoginViewModel;

import org.androidannotations.annotations.EFragment;

@EFragment(resName = "fragment_login")
public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnFocusChangeListener();

        loginViewModel.isLoggedIn.observe(getViewLifecycleOwner(), aBoolean -> {

            if (aBoolean) {
                goToStanding();
            }
        });

        binding.buttonGuestLogin.setOnClickListener(view1 -> {
            goToStanding();
        });
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
}

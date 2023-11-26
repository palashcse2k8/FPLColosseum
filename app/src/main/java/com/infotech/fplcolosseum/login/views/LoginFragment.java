package com.infotech.fplcolosseum.login.views;

import static com.infotech.fplcolosseum.remote.APIHandler.callAPI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.FragmentUtils;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentLoginBinding;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerStatsResponseModel;
import com.infotech.fplcolosseum.gameweek.viewmodels.GameWeekViewModel;
import com.infotech.fplcolosseum.gameweek.views.GameWeekDashboardFragment_;
import com.infotech.fplcolosseum.login.viewmodel.LoginViewModel;
import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import org.androidannotations.annotations.EFragment;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

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

//        binding.buttonLogin.setOnClickListener(v -> {
//            performLogin();
//        });

        binding.buttonGuestLogin.setOnClickListener(view1 -> {
            goToStanding();
        });
    }
    private boolean isLoginSuccessful(String cookies) {
        // Implement your logic to determine if the login was successful
        // For example, check for specific cookies, response content, or URL patterns
        // Return true if the login was successful, false otherwise
        return cookies != null && cookies.contains("your_success_indicator");
    }

    // Example method to perform login using WebView
    private void performLogin() {
        // Load the login URL in the WebView

//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("password", "Fantasy@2023");
//        queryParams.put("login", "palashcse2k8@gmail.com");
//        queryParams.put("app", "plfpl-web");
//        queryParams.put("redirect_uri", "https://fantasy.premierleague.com/");
//
//        APIServices apiServices = RetroClass.getAPIService(requireContext());
//        Call<ResponseBody> callAPI = apiServices.getUserSession(queryParams);
//        callAPI(callAPI, PlayerStatsResponseModel.class);
//
//        goToStanding();
    }

    // Example method to extract cookies from the WebView
    private void extractCookies() {
        // Get cookies from the WebView's CookieManager
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(Constants.LOGIN_URL); // Replace with the actual URL

        // Do something with the cookies (e.g., store or process them)
        if (cookies != null) {
            // Example: Log the cookies
            System.out.println("Cookies: " + cookies);
        }
    }

    public void setOnFocusChangeListener(){
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

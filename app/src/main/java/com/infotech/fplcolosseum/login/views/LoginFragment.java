package com.infotech.fplcolosseum.login.views;

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

import com.blankj.utilcode.util.FragmentUtils;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentLoginBinding;
import com.infotech.fplcolosseum.gameweek.views.GameWeekDashboardFragment_;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import org.androidannotations.annotations.EFragment;

@EFragment(resName = "fragment_login")
public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    private String url = "https://users.premierleague.com/accounts/login/";
    private String redirect_url = "https://users.premierleague.com/";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
//        binding.setGameWeekViewModel(viewModel);
        return binding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnFocusChangeListener();

        binding.webViewLogin.getSettings().setJavaScriptEnabled(true);
        binding.webViewLogin.getSettings().setDomStorageEnabled(true);
        binding.webViewLogin.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView wb, WebResourceRequest request) {
//                wb.loadUrl(url);
//
//                return true;
//            }

            @Override
            public void onPageFinished(WebView web, String url) {

                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d(Constants.LOG_TAG, "All the cookies in a string:" + cookies);
                // Check if the login was successful based on the cookies or other indicators

                web.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                        UIUtils.toast(requireContext(), consoleMessage.message(), ToastLevel.SUCCESS);
                        Log.d(Constants.LOG_TAG, "console msg"+  consoleMessage.message());
                        return true;
                    }
                });

                web.evaluateJavascript("javascript:console.log('Executing JavaScript');", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(Constants.LOG_TAG, "javascript" + value);
                    }
                });

                String uname = "palashcse2k8@gmail.com";
                String pass = "Fantasy@2023";
                String app = "plusers";
//                $("#loginUsername").val("");
//                $("#loginLoginWrap").val("");
//                $("form").submit();

                String javascriptCode = "javascript:var x = document.getElementById(\"loginUsername\").value='"
                        + uname
                        + "';";


//                String javascriptCode = "javasriptc:(function(){document.getElementsByName('login').value='"
//                        + uname
//                        + "';document.getElementsByName('password').value='"
//                        + pass
//                        + "';document.getElementsByName('redirect_uri').value='"
//                        + redirect_url
//                        + "';document.getElementsByName('app').value='"
//                        + app
//                        +
//                        "';document.getElementsByTagName('form')[0].submit();})()";
//                web.evaluateJavascript(javascriptCode, new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//                        Log.d(Constants.LOG_TAG, "javascript" + value);
//                    }
//                });

                web.loadUrl(javascriptCode);

//                document.getElementById(\"loginLoginWrap\").value='"
//                                + pass
////                        + "';document.getElementsByName('redirect_uri').value='"
////                        + redirect_url
////                        + "';document.getElementsByName('app').value='"
////                        + app
//                                +
//                                "';document.getElementsByTagName(\"form\")[0].submit();
                //web.loadUrl(javascriptCode);
                //web.loadUrl(javascriptCode);

                if (isLoginSuccessful(cookies)) {
                    // Login was successful, proceed with further actions
                    UIUtils.toast(requireContext(),"Login Successful", ToastLevel.SUCCESS);
                } else {
                    // Login failed, display an error message
                    UIUtils.toast(requireContext(),"Login Failed. Please check your credentials.", ToastLevel.ERROR);
                }

            }
        });

        binding.buttonLogin.setOnClickListener(v -> {
//            goToStanding();

            performLogin();
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
        binding.webViewLogin.loadUrl(url);
    }

    // Example method to extract cookies from the WebView
    private void extractCookies() {
        // Get cookies from the WebView's CookieManager
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(url); // Replace with the actual URL

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

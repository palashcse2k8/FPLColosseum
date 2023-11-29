package com.infotech.fplcolosseum.features.login.views;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentTestBinding;
import com.infotech.fplcolosseum.features.gameweek.views.GameWeekDashboardFragment_;
import com.infotech.fplcolosseum.utilities.Constants;

import org.androidannotations.annotations.EFragment;

@EFragment(resName = "fragment_login")
public class TestFragment extends Fragment {
    FragmentTestBinding binding;

    private String url = "https://fantasy.premierleague.com/";
    private String redirect_url = "https://fantasy.premierleague.com/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTestBinding.inflate(inflater, container, false);
//        binding.setGameWeekViewModel(viewModel);
        binding.webViewLogin.getSettings().setJavaScriptEnabled(true);
        binding.webViewLogin.getSettings().setDomStorageEnabled(true);

        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webViewLogin, true);


        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webViewLogin, true);
        binding.webViewLogin.setWebViewClient(new WebViewClient() {


//            @Override
//            public boolean shouldOverrideUrlLoading(WebView wb, WebResourceRequest request) {
//                wb.loadUrl(url);
//
//                return true;
//            }

            @Override
            public void onPageFinished(WebView web, String url) {
                super.onPageFinished(web, url);

                // Accept all cookies after the page has finished loading
                CookieManager.getInstance().acceptCookie();

                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d(Constants.LOG_TAG, "All the cookies in a string:" + cookies);
                // Check if the login was successful based on the cookies or other indicators

                web.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                        UIUtils.toast(requireContext(), consoleMessage.message(), ToastLevel.SUCCESS);
                        Log.d(Constants.LOG_TAG, "console msg" + consoleMessage.message());
                        return true;
                    }
                });

                web.evaluateJavascript("javascript:console.log('Executing JavaScript');", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d(Constants.LOG_TAG, "javascript" + value);
                    }
                });
//
                String uname = "palashcse2k8@gmail.com";
                String pass = "Fantasy@2023";
                String app = "plusers";

//                if (isLoginSuccessful(cookies)) {
//                    // Login was successful, proceed with further actions
//                    UIUtils.toast(requireContext(),"Login Successful", ToastLevel.SUCCESS);
//                } else {
//                    // Login failed, display an error message
//                    UIUtils.toast(requireContext(),"Login Failed. Please check your credentials.", ToastLevel.ERROR);
//                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Inject JavaScript to click on the "Accept" button
                        String javascript1 = "javascript:(function() {" +
                                "var clicked = false;" +
                                "setInterval(function() {" +
                                "if (document.querySelectorAll('#onetrust-accept-btn-handler').length == 1 && !clicked) {" +
                                "document.getElementById('onetrust-accept-btn-handler').click();" +
                                "clicked = true;" +
                                "setTimeout(function(){" +
                                "" +
                                "document.querySelectorAll('#loginUsername')[0].value='" + uname + "';" +
                                "document.querySelectorAll('#loginLoginWrap')[0].value='" + pass + "';" +
                                "document.getElementsByTagName('form')[0].submit();" +
                                "},1000);" +
                                "}" +
                                "}, 100);" +
                                "})()";

                        String javascript = "javascript:(function() {" +
                                "var clicked = false;" +
                                "setInterval(function() {" +
                                "if (document.querySelectorAll('#onetrust-accept-btn-handler').length == 1 && !clicked) {" +
                                "document.getElementById('onetrust-accept-btn-handler').click();" +
                                "clicked = true;" +
                                "setTimeout(function(){" +
                                "" +
                                "document.querySelectorAll('#loginUsername')[0].value='" + uname + "';" +
                                "document.querySelectorAll('#loginLoginWrap')[0].value='" + pass + "';" +
                                "document.getElementsByTagName('form')[0].submit();" +
                                "},1000);" +
                                "}" +
                                "}, 100);" +
                                "})()";

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            web.evaluateJavascript(javascript, null);
                        } else {
                            web.loadUrl(javascript);
                        }
                    }
                }, 0); // Adjust the delay time as needed
            }
        });
        binding.webViewLogin.loadUrl(url);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    private void captureCookies(String url) {
        // Get the CookieManager instance
        CookieManager cookieManager = CookieManager.getInstance();

        // For Android API level 21 and above, use the following code
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            // For versions below 21, use CookieSyncManager
            CookieSyncManager.createInstance(getContext());
            CookieManager.getInstance().removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }

        // Get cookies for the current URL
        String cookies = cookieManager.getCookie(url);

        // Do something with the captured cookies
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

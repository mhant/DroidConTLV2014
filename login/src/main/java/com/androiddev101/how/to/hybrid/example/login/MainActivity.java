package com.androiddev101.how.to.hybrid.example.login;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity {

    private final static int ACTION_SHOW_LOADING = 0;
    private final static int ACTION_FINISHED_LOADING = 1;

    private WebView mWebView;
    private WebSettings mWebSettings;

    private DroidCon mJSBridge;

    private Handler mHandler;

    private ProgressBar mLoading;

    private Button mLogin;

    private class DroidCon {
        @JavascriptInterface
        public void login(String state) {
            int stateInt = Integer.parseInt(state);
            //insure we are on UI thread
            mHandler.post(new LoginUpdateRunnable(stateInt));
        }
    }

    private class LoginUpdateRunnable implements Runnable {
        int mAction;

        public LoginUpdateRunnable(int action) {
            mAction = action;
        }

        @Override
        public void run() {
            if (mAction == ACTION_SHOW_LOADING) {
                mLoading.setVisibility(View.VISIBLE);
            } else if (mAction == ACTION_FINISHED_LOADING) {
                mLoading.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                mLogin.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogin = (Button) findViewById(R.id.btnLogin);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.setVisibility(View.VISIBLE);
                mLogin.setVisibility(View.GONE);
            }
        });

        mLoading = (ProgressBar) findViewById(R.id.pbLoading);

        mJSBridge = new DroidCon();

        mHandler = new Handler();

        mWebView = (WebView) findViewById(R.id.wvLogin);
        mWebView.loadUrl("file:///android_asset/www/index.html");
        mWebView.setWebChromeClient(new WebChromeClient());//to show console and alerts
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(mJSBridge, "DroidCon");
        mWebView.setWebViewClient(new WebViewClient() {
        });
    }

}

package com.androiddev101.how.to.hybrid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;
    private AlertDialog mAccessDenied;
    private Handler mHandler;

    private class DroidCon{
        @JavascriptInterface
        public void cookinToast(){
            mHandler.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView)findViewById(R.id.web_container);
        mWebView.loadUrl("file:///android_asset/www/index.html");
        mWebView.setWebChromeClient(new WebChromeClient());//to show console and alerts
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new DroidCon(), "DroidCon");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("file")) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                else{
                    showAccessDenied();
                    return true;
                }
            }
        });

        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(MainActivity.this, "Fresh and buttered", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void showAccessDenied() {
        if(mAccessDenied == null){
            mAccessDenied = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("No no no")
                    .setPositiveButton("I surrender", null)
                    .setMessage("Access Denied!!!")
                        .create();
        }
        mAccessDenied.show();
    }

}

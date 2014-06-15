package com.androiddev101.how.to.hybrid.example.webdrawer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;
    private ImageView mImage1;
    private ImageView mImage2;

    private DrawerLayout mDrawerLayout;

    private DroidCon mJSBridge;

    private Handler mHandler;

    private class ChangeViewRunnable implements Runnable{
        boolean mShowFirst = false;
        public ChangeViewRunnable(boolean showFirst){
            mShowFirst = showFirst;
        }

        @Override
        public void run() {
            mDrawerLayout.closeDrawers();

            if(mShowFirst){
                mImage1.setVisibility(View.VISIBLE);
                mImage2.setVisibility(View.GONE);
            }
            else{
                mImage2.setVisibility(View.VISIBLE);
                mImage1.setVisibility(View.GONE);
            }
        }
    }

    private class DroidCon{
        @JavascriptInterface
        public void showImage(String which){
            boolean showImage1 = Integer.parseInt(which) == 1;
            //insure we are on UI thread
            mHandler.post(new ChangeViewRunnable(showImage1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage1 = (ImageView) findViewById(R.id.ivMic);
        mImage2 = (ImageView) findViewById(R.id.ivLock);

        mJSBridge = new DroidCon();

        mHandler = new Handler();

        mWebView = (WebView)findViewById(R.id.wvSideDrawer);
        mWebView.loadUrl("file:///android_asset/www/index.html");
        mWebView.setWebChromeClient(new WebChromeClient());//to show console and alerts
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(mJSBridge, "DroidCon");
        mWebView.setWebViewClient(new WebViewClient(){
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dlWebDrawer);
    }



}

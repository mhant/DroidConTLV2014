package com.androiddev101.how.to.hybrid.example.ads;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by michael hantler on 6/8/2014.
 */
public class WebAdDialog extends Dialog {

    private WebView mWebView;
    private WebSettings mWebSettings;
    private Context mContext;

    private Handler mHandler;


    private class DroidCon {
        @JavascriptInterface
        public void closeAd() {
            mHandler.sendEmptyMessage(0);
        }
    }

    public void addTextToAd(String textToAdd) {
        mWebView.loadUrl("javascript:nativeCall(\"" + textToAdd + "\")");
    }

    public WebAdDialog(Context context) {
        super(context);
        init(context);
    }

    public WebAdDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected WebAdDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        getWindow().getAttributes().windowAnimations = R.style.AdAnimation;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_web_ad);


        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                WebAdDialog.this.dismiss();
            }
        };

        Button addTextButton = (Button) findViewById(R.id.btAddText);
        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToAdd = ((EditText) findViewById(R.id.etTextToAdd)).getText().toString();
                addTextToAd(textToAdd);
            }
        });

        mWebView = (WebView) findViewById(R.id.web_container);
        mWebView.loadUrl("file:///android_asset/www/index.html");
        mWebView.setWebChromeClient(new WebChromeClient());//to show console and alerts
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new DroidCon(), "DroidCon");
        mWebView.setWebViewClient(new WebViewClient() {

        });
    }
}

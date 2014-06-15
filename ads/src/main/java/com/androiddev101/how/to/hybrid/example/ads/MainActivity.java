package com.androiddev101.how.to.hybrid.example.ads;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private WebAdDialog mWebAdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebAdDialog = new WebAdDialog(this);
    }


    public void buttonClick(View view){
        if(view.getId() == R.id.btOpenAd){
            mWebAdDialog.show();
        }
    }
}

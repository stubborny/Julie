package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bjtu.julie.R;

public class Pub_footActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_foot);
        ActionBar actionbar=getSupportActionBar();
        if (actionbar!=null){
            actionbar.hide();
        }
    }
}

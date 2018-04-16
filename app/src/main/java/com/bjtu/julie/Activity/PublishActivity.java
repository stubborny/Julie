package com.bjtu.julie.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.bjtu.julie.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PublishActivity extends AppCompatActivity {
    @BindView(R.id.mypublish_backward)
    Button back;
    @BindView(R.id.Ipublish)
    ListView Ipublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
    }

    @OnClick(R.id.mypublish_backward)
    public void onMyrecieveBackwardClicked() {

        Intent intent = new Intent(PublishActivity.this, UserActivity.class);
        startActivity(intent);
        finish();

    }
}
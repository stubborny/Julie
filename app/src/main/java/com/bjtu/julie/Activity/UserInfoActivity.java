package com.bjtu.julie.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserInfoActivity extends Activity {

    @BindView(R.id.textView)
    TextView textView;
    //声明变量
    private String sex;
    @BindView(R.id.person_title)
    TextView personTitle;
    @BindView(R.id.person_save)
    Button personSave;
    @BindView(R.id.person_titlebar)
    RelativeLayout personTitlebar;
    @BindView(R.id.tv_userInfo_sex)
    Button tvUserInfoSex;
    @BindView(R.id.tv_userInfo_id)
    TextView tvUserInfoId;
    @BindView(R.id.tv_userInfo_name)
    EditText tvUserInfoName;
    @BindView(R.id.tv_userInfo_birth)
    EditText tvUserInfoBirth;
    @BindView(R.id.tv_userInfo_location)
    EditText tvUserInfoLocation;
    @BindView(R.id.tv_userInfo_introduction)
    EditText tvUserInfoIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sp.getString("name", "null");
        tvUserInfoId.setText(name);
    }

    @OnClick(R.id.tv_userInfo_sex)
    public void onTvUserInfoSexClicked() {
        if (tvUserInfoSex.getText().toString().equals("♂")) {
            tvUserInfoSex.setText("♀");
            tvUserInfoSex.setTextColor(Color.rgb(255, 105, 180));
        } else if (tvUserInfoSex.getText().toString().equals("♀")) {
            tvUserInfoSex.setText("♂");
            tvUserInfoSex.setTextColor(Color.rgb(135, 206, 235));
        }
    }

    @OnClick(R.id.person_save)
    public void onViewClicked() {
        Toast.makeText(this, "暂时不可用", Toast.LENGTH_SHORT).show();
    }
}


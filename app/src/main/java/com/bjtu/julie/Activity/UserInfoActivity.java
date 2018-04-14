package com.bjtu.julie.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjtu.julie.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bjtu.julie.R.id.iv_userInfo_head;

public class UserInfoActivity extends Activity {

    //声明变量
    private String sex;
    @BindView(R.id.person_title)
    TextView personTitle;
    @BindView(R.id.person_backward)
    Button personBackward;
    @BindView(R.id.person_save)
    Button personSave;
    @BindView(R.id.person_titlebar)
    RelativeLayout personTitlebar;
    @BindView(iv_userInfo_head)
    ImageView ivUserInfoHead;
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


    @OnClick(R.id.person_backward)
    public void onPersonBackwardClicked() {

        Intent intent = new Intent(UserInfoActivity.this, UserActivity.class);
        startActivity(intent);
        finish();

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


}


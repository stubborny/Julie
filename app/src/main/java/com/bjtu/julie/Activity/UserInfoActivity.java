package com.bjtu.julie.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.MainActivity;
import com.bjtu.julie.Model.UserInfo;
import com.bjtu.julie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bjtu.julie.R.id.tv_userInfo_id;


public class UserInfoActivity extends Activity {
    private List<UserInfo> userinfoList=new ArrayList<>();
   private UserInfo userinfo = new UserInfo(null,null,null,null,null);
    @BindView(R.id.textView)
    TextView textView;
    //声明变量
    @BindView(R.id.person_title)
    TextView personTitle;
    @BindView(R.id.person_save)
    Button personSave;
    @BindView(R.id.person_titlebar)
    RelativeLayout personTitlebar;
    @BindView(R.id.tv_userInfo_sex)
    Button tvUserInfoSex;
    @BindView(tv_userInfo_id)
    TextView tvUserInfoId;
    @BindView(R.id.tv_userInfo_name)
    EditText tvUserInfoName;
    @BindView(R.id.tv_userInfo_location)
    EditText tvUserInfoLocation;
    @BindView(R.id.tv_userInfo_introduction)
    EditText tvUserInfoIntroduction;
private String sex = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sp.getString("name", "null");
        tvUserInfoId.setText(name);

        //加载个人信息
        String url = "http://39.107.225.80:8080//julieServer/ShowInfoServlet";

        RequestParams params = new RequestParams(url);
        params.addParameter("username", tvUserInfoId.getText().toString());
        //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    JSONArray infoArray = jb.getJSONArray("uiList");
                    if(infoArray.length()>0){
                        for(int i = 0;i<infoArray.length();i++) {
                            JSONObject job = infoArray.getJSONObject(i);
                            //String username,String picString,String sex,String location,String describe, String nickname
                              userinfo = new UserInfo(job.getString("username"),
                                    job.getString("sex"),job.getString("location"),job.getString("describe"),job.getString("nickname"));
                            userinfoList.add(userinfo);

                        }
                    }
                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinished() {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();

            }
        });



      if(userinfo.getSex()!=null) {
            if (userinfo.getSex() == "female") {
                tvUserInfoSex.setText("♀");
                tvUserInfoSex.setTextColor(Color.rgb(255, 105, 180));
            } else {
                tvUserInfoSex.setText("♂");
                tvUserInfoSex.setTextColor(Color.rgb(135, 206, 235));
            }
        }
       if(userinfo.getDescribe()!=null|| userinfo.getLocation()!=null) {
            tvUserInfoIntroduction.setText(userinfo.getDescribe());
            tvUserInfoLocation.setText(userinfo.getLocation());
        }
    }

    @OnClick(R.id.tv_userInfo_sex)
    public void onTvUserInfoSexClicked() {
        if (tvUserInfoSex.getText().toString().equals("♂")) {
            tvUserInfoSex.setText("♀");
            tvUserInfoSex.setTextColor(Color.rgb(255, 105, 180));
            sex="female";
        } else if (tvUserInfoSex.getText().toString().equals("♀")) {
            tvUserInfoSex.setText("♂");
            tvUserInfoSex.setTextColor(Color.rgb(135, 206, 235));
            sex="male";
        }
    }

    @OnClick(R.id.person_save)
    public void onViewClicked() {
        String url = "http://39.107.225.80:8080/julieServer/ChangeInfoServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("username", tvUserInfoId.getText().toString());
        params.addParameter("usersex", sex);
        params.addParameter("userlocation", tvUserInfoLocation.getText().toString());
        params.addParameter("userdescribe", tvUserInfoIntroduction.getText().toString());
        params.addParameter("nickname", tvUserInfoName.getText().toString());
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {

            public void onSuccess(String result) {
                try {

                    if(!TextUtils.isEmpty(result)) {
                        JSONObject jb = new JSONObject(result);
                        //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UserInfoActivity.this, MainActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }
}


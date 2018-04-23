package com.bjtu.julie.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Model.UserInfo;
import com.bjtu.julie.R;
import com.bjtu.julie.View.ShapeImageView;

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

public class ImpressionActivity extends AppCompatActivity {
    @BindView(R.id.impression_head)
    ShapeImageView impressionHead;
    @BindView(R.id.impression_name)
    TextView impressionName;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.total_thumb_up)
    TextView totalThumbUp;
    @BindView(R.id.total_thumb_down)
    TextView totalThumbDown;
    @BindView(R.id.change_all)
    LinearLayout changeAll;
    private String name;
    private List<UserInfo> userinfoList=new ArrayList<>();
    private UserInfo userinfo = new UserInfo(null,null,null,null,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_impression);
        ButterKnife.bind(this);
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        name = sp.getString("name", "null");
       impressionName.setText(name);



        /*String s = userinfo.getPicString();
        if(s!=null){
            byte[] bytes = Base64.decode(s,Base64.DEFAULT);
            impressionHead.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
        }*/
    }

    @OnClick({R.id.impression_head, R.id.impression_name, R.id.ll_info, R.id.total_thumb_up, R.id.total_thumb_down, R.id.change_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.impression_head:
                break;
            case R.id.impression_name:
                break;
            case R.id.ll_info:
                break;
            case R.id.total_thumb_up:
                break;
            case R.id.total_thumb_down:
                break;
            case R.id.change_all:
                break;
        }
    }
}

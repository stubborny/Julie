package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class P_MessageActivity extends AppCompatActivity {

    //@BindView(R.id.pubOkButton)
    Button pubOkButton;
//    @BindView(R.id.pubMessEdit)
   EditText pubMessEdit;
//    @BindView(R.id.pubMessImageBut)
    ImageButton pubMessImageBut;
//    @BindView(R.id.pubMessNameEdit)
    EditText pubMessNameEdit;
//    @BindView(R.id.pubMessChatEdit)
    EditText pubMessChatEdit;
//    @BindView(R.id.pubMessPhoneEdit)
    EditText pubMessPhoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_message);
        ActionBar actionbar=getSupportActionBar();
        if (actionbar!=null){
            actionbar.hide();
        }

        pubOkButton=(Button)findViewById(R.id.pubOkButton);
        pubMessEdit=(EditText)findViewById(R.id.pubMessEdit);
        pubMessImageBut=(ImageButton)findViewById(R.id.pubMessImageBut);
        pubMessNameEdit=(EditText)findViewById(R.id.pubMessNameEdit);
        pubMessChatEdit=(EditText)findViewById(R.id.pubMessChatEdit);
        pubMessPhoneEdit=(EditText)findViewById(R.id.pubMessPhoneEdit);

        pubOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pubMessEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "不说点什么？？", Toast.LENGTH_SHORT).show();
                    pubMessEdit.requestFocus();
                } else if (pubMessPhoneEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "记得填联系方式", Toast.LENGTH_SHORT).show();
                    pubMessPhoneEdit.getText();
                } else if (Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$").matcher(pubMessPhoneEdit.getText().toString()).matches() == false) {
                    //判断手机号码是否合法
                    Toast.makeText(getApplicationContext(), "请输入真实有效的手机号！", Toast.LENGTH_SHORT).show();
                } else if (pubMessNameEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "记得填联系姓名", Toast.LENGTH_SHORT).show();
                    pubMessNameEdit.requestFocus();
                } else {
                    String url = "http://39.107.225.80:8080/julieServer/PubMessServlet";
                    RequestParams params = new RequestParams(url);
                    params.setCharset("utf-8");
                    params.addParameter("userId", "1");
                    params.addParameter("content", pubMessEdit.getText().toString());
                    params.addParameter("wechat", pubMessChatEdit.getText().toString());
                    params.addParameter("phone", pubMessPhoneEdit.getText().toString());
                    params.addParameter("name", pubMessNameEdit.getText().toString());

                    x.http().get(params, new Callback.CommonCallback<String>() {

                        public void onSuccess(String result) {
                            try {
                                JSONObject jb = new JSONObject(result);
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                                finish();
                                //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
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
        });
    }



//    @OnClick({R.id.pubOkButton, R.id.pubMessImageBut})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case pubOkButton:
//
//                if (pubMessEdit.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "不说点什么？？", Toast.LENGTH_SHORT).show();
//                    pubMessEdit.requestFocus();
//                } else if (pubMessPhoneEdit.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "记得填联系方式", Toast.LENGTH_SHORT).show();
//                    pubMessPhoneEdit.getText();
//                } else if (pubMessChatEdit.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "记得填微信号哦", Toast.LENGTH_SHORT).show();
//                    pubMessChatEdit.getText();
//                } else if (Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$").matcher(pubMessPhoneEdit.getText().toString()).matches() == false) {
//                    //判断手机号码是否合法
//                    Toast.makeText(getApplicationContext(), "请输入真实有效的手机号！", Toast.LENGTH_SHORT).show();
//                } else if (pubMessNameEdit.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "记得填联系姓名", Toast.LENGTH_SHORT).show();
//                    pubMessNameEdit.requestFocus();
//                } else {
//                    String url = "http://39.107.225.80:8080/julieServer/PubMessServlet";
//                    RequestParams params = new RequestParams(url);
//                    params.setCharset("utf-8");
//                    params.addParameter("userId", "1");
//                    params.addParameter("content", pubMessEdit.getText().toString());
//                    params.addParameter("wechat", pubMessChatEdit.getText().toString());
//                    params.addParameter("phone", pubMessPhoneEdit.getText().toString());
//                    params.addParameter("name", pubMessNameEdit.getText().toString());
//
//                    x.http().get(params, new Callback.CommonCallback<String>() {
//
//                        public void onSuccess(String result) {
//                            try {
//                                JSONObject jb = new JSONObject(result);
//                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
//                                finish();
//                                //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        //请求异常后的回调方法
//                        @Override
//                        public void onError(Throwable ex, boolean isOnCallback) {
//                        }
//
//                        //主动调用取消请求的回调方法
//                        @Override
//                        public void onCancelled(CancelledException cex) {
//                        }
//
//                        @Override
//                        public void onFinished() {
//
//                        }
//                    });
//                }
//                break;
//            case R.id.pubMessImageBut:
//                break;
//        }
//    }
}

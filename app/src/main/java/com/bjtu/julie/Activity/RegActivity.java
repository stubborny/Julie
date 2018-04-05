package com.bjtu.julie.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bjtu.julie.MainActivity;
import com.bjtu.julie.R;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegActivity extends AppCompatActivity {

    //创建控件的对象
    private EditText textPhoneNumber;
    private EditText textPassword;
    private EditText textIdentifyingCode;
    private Button buttonRegister;
    private Button buttonSendMessage;
    private int i = 60;//倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        init();//初始化界面

        // 启动短信验证sdk
        SMSSDK.initSDK(RegActivity.this, "24efb2626d370", "12f54f7d0137b44c970e1118ad0cd208");

        //initSDK方法是短信SDK的入口，需要传递您从MOB应用管理后台中注册的SMSSDK的应用AppKey和AppSecrete，如果填写错误，后续的操作都将不能进行
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

        x.view().inject(this);//绑定注解


        /**
         * 发送验证码
         */
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNum = textPhoneNumber.getText().toString().trim();
                //判断手机号码是否合法
                Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$");
                Matcher m = p.matcher(phoneNum);
                if(m.matches()==false) {
                    Toast.makeText(getApplicationContext(), "请输入真实有效的手机号！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.getVerificationCode("86", phoneNum);
                buttonSendMessage.setClickable(false);
                //开始倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-1);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-2);
                    }
                }).start();
            }
        });

        /**
         * 注册按钮点击事件，向服务器发出请求，返回结果
         */
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String phoneNum = textPhoneNumber.getText().toString().trim();
                String code = textIdentifyingCode.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(getApplicationContext(), "手机号码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), "验证码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", phoneNum, code);


            }
        });
    }


    /*
     界面初始化
     */
    private void init() {
        //获取注册界面的各个控件
        textPhoneNumber = (EditText) findViewById(R.id.RegisterPhoneNumber);
        textPassword=(EditText)findViewById(R.id.RegisterPassword);
        textIdentifyingCode = (EditText) findViewById(R.id.RegisterIdentifyingCode);
        buttonSendMessage = (Button) findViewById(R.id.SendIdentify);
        buttonRegister = (Button) findViewById(R.id.RegisterDetermination);

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                buttonSendMessage.setText(i + " s");
            } else if (msg.what == -2) {
                buttonSendMessage.setText("重新发送");
                buttonSendMessage.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("asd", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口

                        //Toast.makeText(getApplicationContext(), "短信验证成功",
                        //       Toast.LENGTH_SHORT).show();

                        /**
                         * 服务器地址，传递两个参数
                         */
                        String url = "http://39.107.225.80:8080/julieServer/RegisterServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("username", textPhoneNumber.getText().toString());
                        params.addParameter("password", textPassword.getText().toString());
                        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
                            public void onSuccess(String result) {
                                try {
                                    JSONObject jb = new JSONObject(result);
                                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();

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

                        startActivity(new Intent(RegActivity.this, LoginActivity.class));

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Log.e("asd", "des: " + des);
                            Toast.makeText(RegActivity.this, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
    }

}

package com.bjtu.julie.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bjtu.julie.MainActivity;
import com.bjtu.julie.Model.User;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.MyApplication;
import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    //创建控件的对象
    private EditText textPhoneNumber;
    private EditText textPassword;
    private EditText textIdentifyingCode;
    private EditText textPrompt;
    private Button buttonLogin;
    private Button btnForget;
    private Button btnReg;
    private CheckBox checkBoxLoginChoose;
    private CheckBox checkBoxRemindPassword;
    private CheckBox checkBoxAutomaticLogin;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();//初始化界面
//        //判断是否有登陆记录
//        if ((sp.getString("USER_NAME", "")) != null) {
//            //直接提供用户号码
//            textPhoneNumber.setText(sp.getString("PHONENUMBER", ""));
//        }
//        //判断是否记住密码
//        if (sp.getBoolean("ISCHECK", true)) {
//            //直接提供密码
//            textPassword.setText(sp.getString("PASSWORD", ""));
//            checkBoxRemindPassword.setChecked(true);
//            //判断是否自动登录
//            if (sp.getBoolean("AUTO_ISCHECK", true)) {
//               /* //直接登录
//                checkBoxAutomaticLogin.setChecked(true);
//
//                // 登录信息获取
//                phoneNumber = textPhoneNumber.getText().toString();
//                password = textPassword.getText().toString();
//
//                // 链接服务器
//                loginResultByPassword(phoneNumber, password);*/
//            }
        final MyApplication us = (MyApplication) getApplication();
        /**
         * 登陆按钮点击事件
         */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断手机号码是否合法
                Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$");
                Matcher m = p.matcher(textPhoneNumber.getText().toString());
                if (m.matches() == false) {
                    Toast.makeText(getApplicationContext(), "请输入真实有效的手机号！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "http://39.107.225.80:8080/julieServer/LoginServlet";
                RequestParams params = new RequestParams(url);
                params.addParameter("username", textPhoneNumber.getText().toString());
                params.addParameter("password", textPassword.getText().toString());

                x.http().get(params, new Callback.CommonCallback<String>() {

                    public void onSuccess(String result) {
                        try {
                            JSONObject jb = new JSONObject(result);
                            //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));

                            if (jb.getInt("code") == 1) {
                                us.setStatus(1);//设置用户状态为在线
//                                //记住密码
//                                //获取修改本地用户信息的能力
//                                SharedPreferences.Editor editor = sp.edit();
//                                //修改本地用户信息，更改其记住的号码
//                                editor.putString("PHONENUMBER", textPhoneNumber.getText().toString());
//                                //判断是否选择了记住密码
//                                if (checkBoxRemindPassword.isChecked()) {
//                                    //修改本地用户信息，使之记住密码
//                                    editor.putBoolean("ISCHECK", true);
//                                    editor.putString("PASSWORD", textPassword.getText().toString());
//                                    editor.commit();
//                                    //判断是否选择了自动登录
//                                    if (checkBoxAutomaticLogin.isChecked()) {
//                                        //修改本地用户信息，使之能自动登录
//                                        editor.putBoolean("AUTO_ISCHECK", true);
//                                        editor.commit();
//                                    } else {//修改本地用户信息，使之不能自动登录
//                                        editor.putBoolean("AUTO_ISCHECK", false);
//                                        editor.commit();
//                                    }
//                                } else {//修改本地用户信息，使之不能记住密码
//                                    editor.putBoolean("ISCHECK", false);
//                                    editor.putBoolean("AUTO_ISCHECK", false);
//                                    editor.commit();
//                                }
                                JSONObject job = jb.getJSONObject("data");
                                User user = new User(job.getString("username"), job.getString("password"), job.getInt("id"), job.getString("userpicUrl"), job.getString("nickname"), job.getString("sex"), job.getString("location"), job.getString("describe"),job.getString("wallet"),job.getInt("isAuthentication"),job.getInt("isLegal"));
                                UserManager userManager = UserManager.getInstance();
                                userManager.setUser(user);
//                                sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor edit = sp.edit();
//                                edit.putString("name",textPhoneNumber.getText().toString());
//                                edit.apply();
//                                finish();
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
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
        });

        // 注册按钮，跳转到注册界面
        btnReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });

        // 忘记密码按钮，跳转到忘记密码界面
        btnForget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

    }
    //}

    /*
    界面初始化
    */
    private void init() {
        //获取注册界面的各个控件
        textPhoneNumber = (EditText) findViewById(R.id.LoginPhoneNumber);
        textPassword = (EditText) findViewById(R.id.LoginPassword);
        buttonLogin = (Button) findViewById(R.id.LoginDetermination);
        btnForget = (Button) findViewById(R.id.Forget);
        btnReg = (Button) findViewById(R.id.NewRegister);
        checkBoxRemindPassword = (CheckBox) findViewById(R.id.LoginRemindPassword);
        checkBoxAutomaticLogin = (CheckBox) findViewById(R.id.AutomaticLogin);

        //测试用，省得每次要输入
        textPhoneNumber.setText("15510798789");
        textPassword.setText("123456");
    }
}

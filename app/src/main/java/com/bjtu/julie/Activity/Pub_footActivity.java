package com.bjtu.julie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Pub_footActivity extends AppCompatActivity {

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.pubFootEdit)
    EditText pubFootEdit;
    @BindView(R.id.pubFootAddress_Edit)
    EditText pubFootAddressEdit;
    // @BindView(R.id.pubFootAddition_Edit)
    //EditText pubFootAdditionEdit;
    @BindView(R.id.pubFootMoney_Edit)
    EditText pubFootMoneyEdit;
    @BindView(R.id.pubFootDiscount_Edit)
    TextView pubFootDiscountEdit;
    @BindView(R.id.pubFootChooseDiscount)
    TextView pubFootChooseDiscount;
    @BindView(R.id.pubFootPhone_Edit)
    EditText pubFootPhoneEdit;
    @BindView(R.id.pubFootName_Edit)
    EditText pubFootNameEdit;
    //@BindView(R.id.addNeed1)
    // TextView addNeed1;
    @BindView(R.id.addNeed2)
    TextView addNeed2;
    @BindView(R.id.addNeed3)
    TextView addNeed3;
    @BindView(R.id.addNeed4)
    TextView addNeed4;
    private int status1 = 0;
    private int status2 = 0;
    private int status3 = 0;
    private int status4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_foot);
        ButterKnife.bind(this);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        TextView choose = (TextView) findViewById(R.id.pubFootChooseDiscount);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pub_footActivity.this, DiscountActivity.class);
                startActivity(intent);
            }
        });
        final TextView addneed1 = (TextView) findViewById(R.id.addNeed1);
        final TextView addneed2 = (TextView) findViewById(R.id.addNeed2);
        final TextView addneed3 = (TextView) findViewById(R.id.addNeed3);
        final TextView addneed4 = (TextView) findViewById(R.id.addNeed4);

        addneed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status1) {
                    case 0:
                        addneed1.setBackgroundResource(R.drawable.textview_border);
                        addneed1.setSelected(false);
                        status1 = 1;
                        break;
                    case 1:
                        addneed1.setBackgroundResource(R.drawable.textview_border_click);
                        addneed1.setSelected(true);
                        status1 = 0;
                        break;
                }
            }
        });
        addneed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status2) {
                    case 0:
                        addneed2.setBackgroundResource(R.drawable.textview_border);
                        addneed2.setSelected(false);
                        status2 = 1;
                        break;
                    case 1:
                        addneed2.setBackgroundResource(R.drawable.textview_border_click);
                        addneed2.setSelected(true);
                        status2 = 0;
                        break;
                }
            }
        });
        addneed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status3) {
                    case 0:
                        addneed3.setBackgroundResource(R.drawable.textview_border);
                        addneed3.setSelected(false);
                        status3 = 1;
                        break;
                    case 1:
                        addneed3.setBackgroundResource(R.drawable.textview_border_click);
                        addneed3.setSelected(true);
                        status3 = 0;
                        break;
                }
            }
        });
        addneed4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status4) {
                    case 0:
                        addneed4.setBackgroundResource(R.drawable.textview_border);
                        addneed4.setSelected(false);
                        status4 = 1;
                        break;
                    case 1:
                        addneed4.setBackgroundResource(R.drawable.textview_border_click);
                        addneed4.setSelected(true);
                        status4 = 0;
                        break;
                }
            }
        });

        titleText.setText("发布");

    }

    @OnClick({R.id.title_btn_ok, R.id.pubFootChooseDiscount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_ok:
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getApplicationContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                } else if (pubFootEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "不说说你要做什么？？", Toast.LENGTH_SHORT).show();
                    pubFootEdit.requestFocus();
                } else if (pubFootAddressEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "记得填写地址呢", Toast.LENGTH_SHORT).show();
                    pubFootAddressEdit.requestFocus();
                } else if (pubFootMoneyEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "赏金忘写了", Toast.LENGTH_SHORT).show();
                    pubFootMoneyEdit.requestFocus();
                } else if (pubFootPhoneEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "记得填联系方式", Toast.LENGTH_SHORT).show();
                    pubFootPhoneEdit.getText();
                } else if (Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$").matcher(pubFootPhoneEdit.getText().toString()).matches() == false) {
                    //判断手机号码是否合法
                    Toast.makeText(getApplicationContext(), "请输入真实有效的手机号！", Toast.LENGTH_SHORT).show();
                } else if (pubFootPhoneEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "记得填联系姓名", Toast.LENGTH_SHORT).show();
                    pubFootNameEdit.requestFocus();
                } else {
                    String url = "http://39.107.225.80:8080/julieServer/PubFootServlet";
                    RequestParams params = new RequestParams(url);
                    params.setCharset("utf-8");
                    params.addParameter("userId", UserManager.getInstance().getUser().getId());
                    params.addParameter("content", pubFootEdit.getText().toString());
                    params.addParameter("address", pubFootAddressEdit.getText().toString());
                    params.addParameter("reward", pubFootMoneyEdit.getText().toString());
                    params.addParameter("phone", pubFootPhoneEdit.getText().toString());
                    params.addParameter("name", pubFootNameEdit.getText().toString());

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
                break;
            case R.id.pubFootChooseDiscount:
                break;
        }
    }
}

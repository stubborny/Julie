package com.bjtu.julie.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Model.Discount;
import com.bjtu.julie.Model.MessageEvent;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DoubleKeyValueMap;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
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
    Discount discount = null;
    Double totalMoney = 0.0;
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
        final TextView addneed1 = (TextView) findViewById(R.id.addNeed1);
        final TextView addneed2 = (TextView) findViewById(R.id.addNeed2);
        final TextView addneed3 = (TextView) findViewById(R.id.addNeed3);
        final TextView addneed4 = (TextView) findViewById(R.id.addNeed4);

        addneed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status1) {
                    case 1:
                        addneed1.setBackgroundResource(R.drawable.textview_border);
                        addneed1.setSelected(false);
                        status1 = 0;
                        break;
                    case 0:
                        addneed1.setBackgroundResource(R.drawable.textview_border_click);
                        addneed1.setSelected(true);
                        status1 = 1;
                        break;
                }
            }
        });
        addneed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status2) {
                    case 1:
                        addneed2.setBackgroundResource(R.drawable.textview_border);
                        addneed2.setSelected(false);
                        status2 = 0;
                        break;
                    case 0:
                        addneed2.setBackgroundResource(R.drawable.textview_border_click);
                        addneed2.setSelected(true);
                        status2 = 1;
                        break;
                }
            }
        });
        addneed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status3) {
                    case 1:
                        addneed3.setBackgroundResource(R.drawable.textview_border);
                        addneed3.setSelected(false);
                        status3 = 0;
                        break;
                    case 0:
                        addneed3.setBackgroundResource(R.drawable.textview_border_click);
                        addneed3.setSelected(true);
                        status3 = 1;
                        break;
                }
            }
        });
        addneed4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status4) {
                    case 1:
                        addneed4.setBackgroundResource(R.drawable.textview_border);
                        addneed4.setSelected(false);
                        status4 = 0;
                        break;
                    case 0:
                        addneed4.setBackgroundResource(R.drawable.textview_border_click);
                        addneed4.setSelected(true);
                        status4 = 1;
                        break;
                }
            }
        });
        pubFootPhoneEdit.setText(UserManager.getInstance().getUser().getUsername());
        pubFootNameEdit.setText(UserManager.getInstance().getUser().getNickname());
        pubFootEdit.setText("测试");
        pubFootAddressEdit.setText("测试");
        pubFootMoneyEdit.setText("3.0");
        titleText.setText("发布");
        pubFootMoneyEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                // 输入后的监听
                if (discount != null) {
                    if (discount.getUsable().equals("0")) {
                        pubFootMoneyEdit.setHint("请填写金额");
                    }
                    //如果当前金额大于满多少
                    if (pubFootMoneyEdit.getText().toString().equals("")) {
                        pubFootMoneyEdit.setHint("不能小于" + discount.getUsable() + "元哦");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.title_btn_ok, R.id.pubFootChooseDiscount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_ok:
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getApplicationContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                } else if (UserManager.getInstance().getUser().getIsLegal() == 0) {
                    Toast.makeText(getApplicationContext(), "信用差，功能暂不可用，请联系客服 （qq:stubborny）", Toast.LENGTH_SHORT).show();

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
                    if (discount != null) {
                        if (Double.valueOf(pubFootMoneyEdit.getText().toString()) < Double.valueOf(discount.getUsable())) {
                            Toast.makeText(getApplicationContext(), "赏金不得低于" + discount.getUsable() + "元哦", Toast.LENGTH_SHORT).show();
                            pubFootMoneyEdit.setText(discount.getUsable());
                            return;
                        }
                    }
                    showDialog(Pub_footActivity.this);
                }
                break;
            case R.id.pubFootChooseDiscount:
                Intent intent = new Intent(Pub_footActivity.this, DiscountActivity.class);
                intent.putExtra("ud_id", "-1");
                if (discount != null) {
                    intent.putExtra("ud_id", "" + discount.getId());
                }
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        discount = event.getDiscount();
        if (discount == null) {
            pubFootDiscountEdit.setText("选择可用的优惠券");
        } else {
            String reward = pubFootMoneyEdit.getText().toString();
            String disMoney = discount.getMoney();//减多少
            String disUsable = discount.getUsable();//满多少

            if (disUsable.equals("0")) {
                pubFootDiscountEdit.setText(disMoney + "（满任意金额可用）");
            } else {
                pubFootDiscountEdit.setText(disMoney + "（满" + disUsable + "元可用）");
            }
            //如果当前金额大于满多少
            if (reward.equals("")) {
                pubFootMoneyEdit.setText(disUsable);
            } else {
                if (Double.valueOf(reward) < Double.valueOf(disUsable)) {
                    pubFootMoneyEdit.setText(disUsable);
                }
            }
        }


    }

    /**
     * 展示对话框
     *
     * @param mContext
     */
    public void showDialog(final Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_fragment_pay, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("发布");
        builder.setView(view);
        //绑定控件
        TextView dpTextMoney = (TextView) view.findViewById(R.id.dp_text_money);
        TextView dpTextAll = (TextView) view.findViewById(R.id.dp_text_all);
        TextView dpTextRecharge = (TextView) view.findViewById(R.id.dp_text_recharge);

        if (discount != null) {
            totalMoney = Double.valueOf(pubFootMoneyEdit.getText().toString()) - Double.valueOf(discount.getMoney());
        } else {
            totalMoney = Double.valueOf(pubFootMoneyEdit.getText().toString());
        }

        dpTextMoney.setText(totalMoney.floatValue() + "元");
        dpTextAll.setText(UserManager.getInstance().getUser().getWallet());
        dpTextRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pub_footActivity.this, WalletActivity.class));
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Double wallet = Double.valueOf(UserManager.getInstance().getUser().getWallet()) - totalMoney.floatValue();
                if (wallet < 0) {
                    canCloseDialog(dialogInterface, false);
                    Toast.makeText(getApplicationContext(), "余额不足，请充值或选择线下付款", Toast.LENGTH_SHORT).show();
                } else {
                    canCloseDialog(dialogInterface, true);
                    String url = "http://39.107.225.80:8080/julieServer/PubFootServlet";
                    RequestParams params = new RequestParams(url);
                    params.setCharset("utf-8");
                    params.addParameter("userId", UserManager.getInstance().getUser().getId());
                    params.addParameter("content", pubFootEdit.getText().toString());
                    params.addParameter("address", pubFootAddressEdit.getText().toString());
                    params.addParameter("reward", pubFootMoneyEdit.getText().toString());
                    params.addParameter("phone", pubFootPhoneEdit.getText().toString());
                    params.addParameter("name", pubFootNameEdit.getText().toString());
                    params.addParameter("addNeed", status1 + "," + status2 + "," + status3 + "," + status4);
                    params.addParameter("totalMoney", totalMoney.floatValue());
                    params.addParameter("payOnline", "1");

                    if (discount != null) {
                        params.addParameter("udId", discount.getUdId());
                    } else {
                        params.addParameter("udId", "-1");
                    }
                    x.http().get(params, new Callback.CommonCallback<String>() {

                        public void onSuccess(String result) {
                            try {
                                JSONObject jb = new JSONObject(result);
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                                Double wallet = Double.valueOf(UserManager.getInstance().getUser().getWallet()) - totalMoney.floatValue();
                                UserManager.getInstance().getUser().setWallet(wallet.floatValue() + "");
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
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                canCloseDialog(dialogInterface, true);
            }
        });
        builder.setNeutralButton("不想充钱，线下付款", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //  提示信息
                canCloseDialog(arg0, true);
                if (discount != null) {
                    Toast.makeText(getApplicationContext(), "线下付款不能使用优惠券哦", Toast.LENGTH_SHORT).show();
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
                    params.addParameter("addNeed", status1 + "," + status2 + "," + status3 + "," + status4);
                    params.addParameter("totalMoney", totalMoney.floatValue());
                    params.addParameter("payOnline", "0");
                    if (discount != null) {
                        params.addParameter("udId", discount.getId());
                    } else {
                        params.addParameter("udId", "-1");
                    }
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
        dpTextRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "充值", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    //  关键部分在这里
    private void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, close);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

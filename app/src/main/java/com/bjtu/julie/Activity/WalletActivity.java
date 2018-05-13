package com.bjtu.julie.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bjtu.julie.DepositActivity;
import com.bjtu.julie.Model.PayResult;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.OrderInfoUtil2_0;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends AppCompatActivity {

    @BindView(R.id.wallet_text_balance)
    TextView walletTextBalance;

    private static final int SDK_PAY_FLAG = 1;
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016091000480898";
    public static final String URL = "https://openapi.alipay.com/gateway.do";//支付宝网关
    //支付宝公钥，由支付宝生成
    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "";
    //开发者应用私钥，由开发者自己生成
    public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDZj64uDXwEsmNySYNDp+lLybb1BS0uN5NCdJTo2z8XArVcoCYVS9in2mWOKislLWn3DRKc0THeIvBZE32/swuOeoafaudHOX8fByrgV3wJpQdzFhHPQEE7F+GNDh/X84syA6IWd9iaemT48ydnIFSJs3F4dqZckHYlvDZW97fToFbSPLVaYJ0eTdGxliYjXOyrR0kA7MEDVYHcEyeVsk+KJ7Qhs4oF9L6azRWixDDINy8u2SdzebnC8TFzj9dgFqgPnVhiL1NG/GHiA4JBGEGdc4lB/MtcKlMF4TPuBfaMgDyebelXM4QY9fYwUsFHtsnKnaVb5V04cYTXfaNlDGWzAgMBAAECggEAMBlE3+eZSIyL0eOQwq9ns5q3+TmYwIQG6YVOuqBmNVci84RwhvrKvmlZVHHcMg71Q2o/eC4DwWGmE3tsrKoP3wMVqj7/PM7oXNq/zvNod9VlbTsu2swQ39iqw/2gaqTsXDVTa3Y5jAWNN0DCsFkEd6EVcBWDOCm0BSm0Ncv0dzOuA9rIb8VxxEOt4egnscYKNmOqkUNONYEEiTtnGr8BvF5Dg0BdcBAjpuluViVcK5+3MNH7vIFlRITXs+Pzh0DsNButFeEAY0v628nDbUIJCdyp7K1oA0Zzxn8AnXVtyQ9LroL239du9c4o2eTKG6Isgl6JljTL5KsR8G1G6RMK+QKBgQD89Ia3Fc0QE8HjPeTSs2XvlwO6tGIzK7rPVQeYtPemsS1zNNC2RsdH/R+DRViPR2wYtKT8nHwoCbue0USk8brB/nIweHqp/ZSyJ4rpUJI44u2X0EGX4w/Gh4F9P/KlENcedFGXwvQyT8w09yr8geQGPxTkdAsHaHP9kJAXLAGlBwKBgQDcLha9wAdgal+Cky1rXKPbshTt4yH7rGaOg7l7TGZEE7m/bFlskolc1im5dI3QYMwrOnzhoVisI7anzj3ZjfXIrwWSSR1OuuJAJ8sSDqhyfpi/DCYVcS4SWgiWf1LZpWj3waSAUN8in8f7ondmoImhjwymddkz4W4Mgcu5YKNa9QKBgQCIZCzTQV0NgymzJqdf+L1I/4tSO3sLjlOvAXEUJNe3uKcCDINRFT7UkSZRuK10rBUcCSNA1fuX9w+EJeA2c+S4P0NA4WV6jTGFEg8zmG8PX0Su6+rCQ/s4l835Q+bInBjx4dQw2TykeCRqlq9F8Z+Kwq64M93Sg76vBSi8Zc0JNwKBgADAp5M+dMf/lRP9LMqRJn45vZiSjisuC6uxB5FEUZUp/BiLZkLYvV9z2/CmVVXA/vGm4YZj5smv1Y/9RHjZ410sO/ikB1WdjehqOmd1ZV3+0MbWY8ru+BlX9W+OP9o+ln1CTC2kGR8lLKnPhFj1c4L52jE3deaXfqjMSMX5bpWBAoGBAJYE/ATuByb795DyUY5bTPYnfbwuyTiBNVgX3hGTFw8NWdfaZjSSq7bG7iNBYJUa+amCZCiZUbhae+t6kGahSuKWcbC23EEYCsfmGxZwpriQLNojR1NTA3YRwlSE+r24iMnMOU9FPtseT8UPnBJ3Pk1xRoDmRKZRulPJHuW0LSpV";
    AlertDialog dialogIn;
    AlertDialog dialogOut;

    EditText et;
    Double money;
    EditText dmTextMoney;
    EditText dmTextAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沙箱环境，不用可注释
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        money = Double.valueOf(UserManager.getInstance().getUser().getWallet());
        walletTextBalance.setText(String.valueOf(money.floatValue()));

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        String url = "http://39.107.225.80:8080/julieServer/ChangeMoneyServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("userId", UserManager.getInstance().getUser().getId());
                        params.addParameter("wallet", et.getText().toString());
                        params.addParameter("operation", "+");
                        params.addParameter("account", "");

                        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {

                            public void onSuccess(String result) {
                                try {

                                    JSONObject jb = new JSONObject(result);
                                    if (jb.getInt("code") == 1) {
                                        //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                                        money = Double.valueOf(UserManager.getInstance().getUser().getWallet()) + Double.valueOf(et.getText().toString());
                                        walletTextBalance.setText(String.valueOf(money.floatValue()));
                                        UserManager.getInstance().getUser().setWallet(String.valueOf(money.floatValue()));

                                        dialogIn.dismiss();
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
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(WalletActivity.this, "支付成功" + et.getText(), Toast.LENGTH_SHORT).show();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(WalletActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }

    };

    @OnClick({R.id.wallet_layout_moneyin, R.id.wallet_layout_moneyout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_layout_moneyin:
                et = new EditText(this);
                et.setHint("请输入充值金额");
                et.setSingleLine();
                et.setWidth(200);
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialogIn = new AlertDialog.Builder(this).setTitle("充值")
                        .setIcon(R.mipmap.ic_action_creditcard)
                        .setView(et)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .show();
                dialogIn.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "还没输入哦", Toast.LENGTH_LONG).show();
                            return;
                        }
                        moneyIn(v, et.getText().toString());
                    }
                });
                break;
            case R.id.wallet_layout_moneyout:
                LayoutInflater inflater = LayoutInflater.from(WalletActivity.this);
                View view1 = inflater.inflate(R.layout.dialog_fragment_moneyout, null);
                //绑定控件
                dmTextAccount = (EditText) view1.findViewById(R.id.dm_text_account);
                dmTextMoney = (EditText) view1.findViewById(R.id.dm_text_money);

                dialogOut = new AlertDialog.Builder(this).setTitle("提现")
                        .setIcon(R.mipmap.ic_action_creditcard)
                        .setView(view1)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .setNeutralButton("全部提现", null)
                        .show();
                String account="exkxea4366@sandbox.com";
                //支付宝账号设置为用户手机号，沙箱环境下设置为上面的
                //dmTextAccount.setText(UserManager.getInstance().getUser().getUsername());
                dmTextAccount.setText(account);
                dmTextMoney.requestFocus();
                dialogOut.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = dmTextMoney.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "请输入金额", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String input1 = dmTextAccount.getText().toString();
                        if (input1.equals("")) {
                            Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_LONG).show();
                            return;
                        }
                        money = Double.valueOf(UserManager.getInstance().getUser().getWallet()) - Double.valueOf(dmTextMoney.getText().toString());
                        if(money<0){
                            Toast.makeText(getApplicationContext(), "账户余额不足", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String account="exkxea4366@sandbox.com";

                        String url = "http://39.107.225.80:8080/julieServer/ChangeMoneyServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("userId", UserManager.getInstance().getUser().getId());
                        params.addParameter("wallet", dmTextMoney.getText().toString());
                        params.addParameter("operation", "-");
                        params.addParameter("account", dmTextAccount.getText().toString());

                        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {

                            public void onSuccess(String result) {
                                try {

                                    JSONObject jb = new JSONObject(result);
                                    if (jb.getInt("code") == 1) {
                                        //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                                        Toast.makeText(WalletActivity.this, jb.getString("msg"), Toast.LENGTH_SHORT).show();
                                        money = Double.valueOf(UserManager.getInstance().getUser().getWallet()) - Double.valueOf(dmTextMoney.getText().toString());
                                        walletTextBalance.setText(String.valueOf(money.floatValue()));
                                        UserManager.getInstance().getUser().setWallet(String.valueOf(money.floatValue()));
                                        dialogOut.dismiss();
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
                dialogOut.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        money = Double.valueOf(UserManager.getInstance().getUser().getWallet());
                        dmTextMoney.setText(String.valueOf(money.floatValue()));
                        dmTextMoney.setSelection(String.valueOf(money.floatValue()).length());
                    }
                });
                break;
        }
    }

    public void moneyIn(View v, String money) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new android.app.AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, money, "充值", "body是个啥");
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WalletActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}

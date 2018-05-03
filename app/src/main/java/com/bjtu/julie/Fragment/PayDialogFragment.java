//package com.bjtu.julie.Fragment;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bjtu.julie.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.xutils.common.Callback;
//import org.xutils.http.RequestParams;
//import org.xutils.x;
//
///**
// * Created by carrey on 2018/4/19.
// */
//
//public class PayDialogFragment extends DialogFragment {
//
//    TextView dpTextMoney;
//    TextView dpTextAll;
//    TextView dpTextRecharge;
//
//
//    public static PayDialogFragment newInstance(String content, String address, String reward, String phone, String name, int discountId) {
//        PayDialogFragment cDialog = new PayDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("content", content);
//        args.putString("address", address);
//        args.putString("reward", reward);
//        args.putString("phone", phone);
//        args.putString("name", name);
//        args.putInt("discountId", discountId);
//        cDialog.setArguments(args);
//        return cDialog;
//    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        final String content = getArguments().getString("content");
//        final String address = getArguments().getString("address");
//        final String reward = getArguments().getString("reward");
//        final String phone = getArguments().getString("phone");
//        final String name = getArguments().getString("name");
//        final int discountId = getArguments().getInt("discountId");
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_fragment_pay, null);
//        builder.setView(view);
//
//        //绑定控件
//        dpTextMoney = (TextView) view.findViewById(R.id.dp_text_money);
//        dpTextAll = (TextView) view.findViewById(R.id.dp_text_all);
//        dpTextRecharge = (TextView) view.findViewById(R.id.dp_text_recharge);
//
//        dpTextMoney.setText(reward);
//        dpTextAll.setText("50元");
//        builder.setView(view)
//                // Add action buttons
//                .setPositiveButton("取消", null)
//                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        String url = "http://39.107.225.80:8080/julieServer/PubFootServlet";
//                        RequestParams params = new RequestParams(url);
//                        params.setCharset("utf-8");
//                        params.addParameter("userId", "1");
//                        params.addParameter("content", pubFootEdit.getText().toString());
//                        params.addParameter("address", pubFootAddressEdit.getText().toString());
//                        params.addParameter("reward", pubFootMoneyEdit.getText().toString());
//                        params.addParameter("phone", pubFootPhoneEdit.getText().toString());
//                        params.addParameter("name", pubFootNameEdit.getText().toString());
//                        params.addParameter("discountId", cur_discount.getId());
//
//                        x.http().get(params, new Callback.CommonCallback<String>() {
//
//                            public void onSuccess(String result) {
//                                try {
//                                    JSONObject jb = new JSONObject(result);
//                                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
//
//                                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            //请求异常后的回调方法
//                            @Override
//                            public void onError(Throwable ex, boolean isOnCallback) {
//                            }
//
//                            //主动调用取消请求的回调方法
//                            @Override
//                            public void onCancelled(CancelledException cex) {
//                            }
//
//                            @Override
//                            public void onFinished() {
//
//                            }
//                        });
//
//                    }
//                });
//        return builder.create();
//    }
//
//}

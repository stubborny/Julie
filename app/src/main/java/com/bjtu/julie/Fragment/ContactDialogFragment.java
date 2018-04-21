package com.bjtu.julie.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by carrey on 2018/4/19.
 */

public class ContactDialogFragment extends DialogFragment {
    LinearLayout dfLayoutSms;
    LinearLayout dfLayoutPhone;
    TextView dfTextView;

    public static ContactDialogFragment newInstance(String phone,int current_user) {
        ContactDialogFragment cDialog = new ContactDialogFragment();
        Bundle args = new Bundle();
        args.putString("phone", phone);
        args.putInt("current_user",current_user);
        cDialog.setArguments(args);
        return cDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String phone = getArguments().getString("phone");
        final int current_user=getArguments().getInt("current_user");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_contact, null);
        builder.setView(view);
        //绑定控件
        dfLayoutSms = (LinearLayout) view.findViewById(R.id.df_layout_sms);
        dfLayoutPhone = (LinearLayout) view.findViewById(R.id.df_layout_phone);
        dfTextView = (TextView) view.findViewById(R.id.df_text_phonenum);
        dfLayoutSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "发短信"+ phone, Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse("smsto:"+phone);
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                if(current_user==1){
                    dfTextView.setText("发单同学电话  ");
                    intent.putExtra("sms_body", "您好，我在交立达上面接了您的订单");//sms_body 不能改
                }else{
                    dfTextView.setText("接单同学电话  ");
                    intent.putExtra("sms_body", "您好，交立达app显示您接了我发的订单");//sms_body 不能改
                }
                startActivity(intent);
            }
        });
        dfLayoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "打电话"+phone, Toast.LENGTH_LONG).show();
                String myPhoneNumberUri = "tel:"+phone;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(myPhoneNumberUri));
                startActivity(intent);
            }
        });
        dfTextView.setText("发单同学电话: " + phone);
        return builder.create();
    }

}

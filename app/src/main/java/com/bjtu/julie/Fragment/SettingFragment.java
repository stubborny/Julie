package com.bjtu.julie.Fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjtu.julie.Activity.P_MessageActivity;
import com.bjtu.julie.Activity.Pub_footActivity;
import com.bjtu.julie.R;

import static com.bjtu.julie.R.id.view;

public class SettingFragment extends Fragment{

    private TextView hello;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.activity_setting, container, false);


        hello = (TextView) settingLayout.findViewById(R.id.hello);
        hello.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), P_MessageActivity.class);
                                //启动
                               startActivity(intent);
            }
        });

        return settingLayout;
    }

}

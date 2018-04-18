package com.bjtu.julie.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bjtu.julie.R;

public class PayActivity extends AppCompatActivity {
    private TextView textRecharge;
    private Button buttonRecharge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        textRecharge=(TextView)findViewById(R.id.text_recharge);
        buttonRecharge=(Button)findViewById(R.id.button_recharge);
        buttonRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i=new Intent(PayActivity.this,RechargeActivity.class);
                startActivity(i);

            }
        });
        textRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PayActivity.this,RechargeActivity.class);
                startActivity(i);
            }
        });

    }

}

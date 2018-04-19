package com.bjtu.julie.Activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bjtu.julie.R;

public class OrderEvaluateActivity extends AppCompatActivity {
    int flag=0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private View view1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_evaluate);

        final Drawable dr = this.getResources().getDrawable(R.drawable.button_back_press);
        final Drawable sr = this.getResources().getDrawable(R.drawable.button_normal);
        final Drawable ar = this.getResources().getDrawable(R.drawable.sad);
        final Drawable cr = this.getResources().getDrawable(R.drawable.saded);
        final Drawable br = this.getResources().getDrawable(R.drawable.satisfy);
        final Drawable yr = this.getResources().getDrawable(R.drawable.satisfied);
        final Drawable tr = this.getResources().getDrawable(R.drawable.ok);
        final Drawable rr = this.getResources().getDrawable(R.drawable.oked);
        button1=(Button)findViewById(R.id.button_evaluate1);
        button2=(Button)findViewById(R.id.button_evaluate2);
        button3=(Button)findViewById(R.id.button_evaluate3);
        button4=(Button)findViewById(R.id.image_evaluate1);
        button5=(Button)findViewById(R.id.image_evaluate2);
        button6=(Button)findViewById(R.id.image_evaluate3);
        button7=(Button)findViewById(R.id.button_evaluate4);
        view1=(View)findViewById(R.id.view_hidden);
        view1.setVisibility(View.INVISIBLE);
        button1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
              switch (flag){
                  case 0:
                      button1.setBackgroundDrawable(dr);
                      flag=1;
                      break;
                  case 1:
                      button1.setBackgroundDrawable(sr);
                      flag=0;
                      break;
              }

            }
        });
        button2.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        button2.setBackgroundDrawable(dr);
                        flag=1;
                        break;
                    case 1:
                        button2.setBackgroundDrawable(sr);
                        flag=0;
                        break;
                }

            }
        });
        button3.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        button3.setBackgroundDrawable(dr);
                        flag=1;
                        break;
                    case 1:
                        button3.setBackgroundDrawable(sr);
                        flag=0;
                        break;
                }

            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        button4.setBackgroundDrawable(cr);
                        flag=1;
                        break;
                    case 1:
                        button4.setBackgroundDrawable(ar);
                        flag=0;
                        break;
                }

            }
        });

        button5.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        button5.setBackgroundDrawable(rr);
                        flag=1;
                        break;
                    case 1:
                        button5.setBackgroundDrawable(tr);
                        flag=0;
                        break;
                }

            }
        });
        button6.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        button6.setBackgroundDrawable(yr);
                        flag=1;
                        break;
                    case 1:
                        button6.setBackgroundDrawable(br);
                        flag=0;
                        break;
                }

            }
        });
        button7.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (flag){
                    case 0:
                        view1.setVisibility(View.VISIBLE);
                        flag=1;
                        break;
                    case 1:
                        view1.setVisibility(View.INVISIBLE);
                        flag=0;
                        break;
                }

            }
        });
    }

}

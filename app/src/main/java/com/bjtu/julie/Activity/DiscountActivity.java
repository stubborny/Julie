package com.bjtu.julie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bjtu.julie.Adapter.DiscountAdapter;
import com.bjtu.julie.Model.Discount;
import com.bjtu.julie.R;

import java.util.ArrayList;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {
    private List<Discount> discountList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        initDiscountInfo();
        DiscountAdapter adapter=new DiscountAdapter(DiscountActivity.this,R.layout.discount_item,discountList);
        ListView listview=(ListView)findViewById(R.id.DiscountList);
        listview.setAdapter(adapter);

        Button title2Back=(Button) findViewById(R.id.DiscountBack_btn);
        title2Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button title1Ok=(Button) findViewById(R.id.DiscountOk_btn);
        title1Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initDiscountInfo(){
        Discount dct1=new Discount("￥3.9","女生节特惠");
        discountList.add(dct1);
        Discount dct2=new Discount("￥3.2","3.12特惠");
        discountList.add(dct2);
        Discount dct3=new Discount("￥5.8","五一特惠");
        discountList.add(dct3);
    }
}

package com.bjtu.julie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Adapter.DiscountAdapter;
import com.bjtu.julie.Model.Discount;
import com.bjtu.julie.Model.MessageEvent;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscountActivity extends AppCompatActivity {
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.discount_layout_nothing)
    LinearLayout discountLayoutNothing;
    @BindView(R.id.discount_layout_having)
    LinearLayout discountLayoutHaving;
    private List<Discount> discountList = new ArrayList<>();
    DiscountAdapter adapter;
    int udId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);
        titleText.setText("使用抵用券");
        udId=Integer.valueOf(getIntent().getStringExtra("ud_id"));
        String url = "http://39.107.225.80:8080//julieServer/DiscountServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("userId", UserManager.getInstance().getUser().getId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    if (jb.getInt("code") == 1) {
                        discountLayoutNothing.setVisibility(View.GONE);
                        discountLayoutHaving.setVisibility(View.VISIBLE);
                        JSONArray orderArray = jb.getJSONArray("discountList");
                        if (orderArray.length() > 0) {
                            for (int i = 0; i < orderArray.length(); i++) {
                                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                JSONObject job = orderArray.getJSONObject(i);
                                Discount discount = new Discount(job.getInt("udId"), job.getInt("id"), job.getString("name"), job.getString("money"), job.getString("usable"), job.getString("deadline"), job.getString("limit"));
                                discountList.add(discount);
                            }
                        }
                        adapter = new DiscountAdapter(DiscountActivity.this, R.layout.discount_item, discountList,udId);
                        ListView listview = (ListView) findViewById(R.id.discount_list);
                        listview.setAdapter(adapter);
                    } else {
                        discountLayoutNothing.setVisibility(View.VISIBLE);
                        discountLayoutHaving.setVisibility(View.GONE);
                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.title_btn_ok)
    public void onViewClicked() {
        Discount discount = null;
        if (adapter == null) {
            EventBus.getDefault().post(new MessageEvent(discount));
            finish();
        } else {
            //根据得到的位置,获取选中item的数据Bean
            int selectPosition = adapter.getSelectPosition();
            //没选
            if (selectPosition == -1) {
                // 发布事件
                EventBus.getDefault().post(new MessageEvent(discount));
                finish();
            } else {
                discount = discountList.get(selectPosition);
                // 发布事件
                EventBus.getDefault().post(new MessageEvent(discount));
                finish();
                // Toast.makeText(getApplicationContext(), discount.getName(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}

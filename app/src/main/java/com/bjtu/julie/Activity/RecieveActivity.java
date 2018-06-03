package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bjtu.julie.Adapter.FootManAdaper;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Model.Order;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

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

public class RecieveActivity extends AppCompatActivity {
    public List<Order> myorderlist = new ArrayList<>();

    @BindView(R.id.my_recycle_rec_footman_item)
    RecyclerView myRecycleRecFootmanItem;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    SuperSwipeRefreshLayout swipeRefreshLayout;
    FootManAdaper adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
        ButterKnife.bind(this);
        titleBtnOk.setText("");
        titleText.setText("我的接单");
        //  final View orderLayout = inflater.inflate(R.layout.fragment_my_footman, container, false);
        //initGuide();
        String url = "http://39.107.225.80:8080//julieServer/MyRecFootManServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("userid", UserManager.getInstance().getUser().getId());
        x.http().get(params, new Callback.CommonCallback<String>() {

            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);

                    JSONArray orderArray = jb.getJSONArray("orderList");
                    if (orderArray.length() > 0) {
                        for (int i = 0; i < orderArray.length(); i++) {
                            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            JSONObject job = orderArray.getJSONObject(i);
                            Order exorder = new Order(job.getString("footId"), job.getInt("userId"), job.getInt("receiveId"), job.getString("userpicUrl"), job.getString("username"), job.getString("state"), job.getString("content"), job.getString("address"), job.getString("reward"), job.getString("time"), job.getString("phone"));
                            exorder.setPayOnline(job.getInt("payOnline"));
                            exorder.setAddNeed(job.getString("addNeed"));
                            myorderlist.add(exorder);
                        }
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.my_recycle_rec_footman_item);
                    FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(RecieveActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);
                    adapter = new FootManAdaper(myorderlist);
                    recyclerView.setAdapter(adapter);

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
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        String url = "http://39.107.225.80:8080//julieServer/MyRecFootManServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("userid", UserManager.getInstance().getUser().getId());
                        x.http().get(params, new Callback.CommonCallback<String>() {

                            public void onSuccess(String result) {
                                try {
                                    myorderlist.clear();
                                    JSONObject jb = new JSONObject(result);
                                    JSONArray orderArray = jb.getJSONArray("orderList");
                                    if (orderArray.length() > 0) {
                                        for (int i = 0; i < orderArray.length(); i++) {
                                            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                            JSONObject job = orderArray.getJSONObject(i);
                                            Order exorder = new Order(job.getString("footId"), job.getInt("userId"), job.getInt("receiveId"), job.getString("userpicUrl"), job.getString("username"), job.getString("state"), job.getString("content"), job.getString("address"), job.getString("reward"), job.getString("time"), job.getString("phone"));
                                            exorder.setPayOnline(job.getInt("payOnline"));
                                            exorder.setAddNeed(job.getString("addNeed"));
                                            myorderlist.add(exorder);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
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

                    @Override
                    public void onPullDistance(int distance) {
                        //TODO 下拉距离
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        //TODO 下拉过程中，下拉的距离是否足够出发刷新
                    }
                });
    }


    @OnClick(R.id.my_recycle_rec_footman_item)
    public void onViewClicked() {
    }

}

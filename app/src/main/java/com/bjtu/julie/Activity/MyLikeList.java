package com.bjtu.julie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Adapter.MessageAdaper;
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
import butterknife.Unbinder;

import static java.security.AccessController.getContext;

public class MyLikeList extends AppCompatActivity {


    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    Unbinder unbinder;
    @BindView(R.id.my_like_nothing)
    TextView showmesg;
    private List<Exchange> exchangeList = new ArrayList<>();
    RecyclerView recyclerView;
    MessageAdaper adaper;
    SuperSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like_list);
        unbinder = ButterKnife.bind(this);
        titleText.setText("我收藏的消息");
        titleBtnOk.setText("");


        String url = "http://39.107.225.80:8080//julieServer/MyLikeListServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("userId", UserManager.getInstance().getUser().getId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    if (jb.getInt("code") == 0) {
                        showmesg.setVisibility(View.VISIBLE);
                        //Toast.makeText(getApplicationContext(), "暂无收藏的消息哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray messArray = jb.getJSONArray("messList");
                        if (messArray.length() > 0) {
                            for (int i = 0; i < messArray.length(); i++) {
                                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                JSONObject job = messArray.getJSONObject(i);

                                Exchange exchange = new Exchange(job.getString("messId"), job.getInt("userId"), job.getString("name"), job.getString("userpicUrl"), job.getString("phone"), job.getString("content"), job.getString("wechat"), job.getString("time"), job.getString("commentNum"), job.getString("likeNum"));
                                exchange.setIsLikePage(1);
                                exchangeList.add(exchange);
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.MyLikeRecycleView);
                                LinearLayoutManager layoutmanager = new LinearLayoutManager(MyLikeList.this);
                                recyclerView.setLayoutManager(layoutmanager);
                                recyclerView.setNestedScrollingEnabled(false);

                                MessageAdaper adapter = new MessageAdaper(exchangeList);
                                recyclerView.setAdapter(adapter);
                            }
                        }
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
        recyclerView = (RecyclerView) findViewById(R.id.MyLikeRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyLikeList.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        adaper = new MessageAdaper(exchangeList);
        recyclerView.setAdapter(adaper);
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        String url = "http://39.107.225.80:8080//julieServer/MyLikeListServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("userId", UserManager.getInstance().getUser().getId());
                        x.http().get(params, new Callback.CommonCallback<String>() {
                                @Override
                            public void onSuccess(String result) {
                                try {
                                    exchangeList.clear();
                                    JSONObject jb = new JSONObject(result);
                                    if (jb.getInt("code") == 0) {
                                        showmesg.setVisibility(View.VISIBLE);
                                        swipeRefreshLayout.setRefreshing(false);
                                    } else {
                                        JSONArray messArray = jb.getJSONArray("messList");
                                        if (messArray.length() > 0) {
                                            for (int i = 0; i < messArray.length(); i++) {
                                                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                                JSONObject job = messArray.getJSONObject(i);
                                                Exchange exchange = new Exchange(job.getString("messId"), job.getInt("userId"), job.getString("name"), job.getString("userpicUrl"), job.getString("phone"), job.getString("content"), job.getString("wechat"), job.getString("time"), job.getString("commentNum"), job.getString("likeNum"));
                                                exchangeList.add(exchange);
                                            }
                                        }
                                        adaper.notifyDataSetChanged();
                                        swipeRefreshLayout.setRefreshing(false);
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
}

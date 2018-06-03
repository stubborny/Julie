package com.bjtu.julie.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMessageFragment extends Fragment {
    @BindView(R.id.my_recycle_message_item)
    RecyclerView myRecycleMessageItem;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    private List<Exchange> myexchangeList = new ArrayList<>();
    Unbinder unbinder;
    MessageAdaper adapter;
    SuperSwipeRefreshLayout swipeRefreshLayout;

    public MyMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mymessageLayout = inflater.inflate(R.layout.fragment_my_message, container, false);
        unbinder = ButterKnife.bind(this, mymessageLayout);
        titleBtnOk.setText("");
        titleText.setText("我的发布");
        String url = "http://39.107.225.80:8080//julieServer/MyMessageServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("userid", UserManager.getInstance().getUser().getId());
        //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);

                    JSONArray messArray = jb.getJSONArray("messList");

                    if (messArray.length() > 0) {
                        for (int i = 0; i < messArray.length(); i++) {
                            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            JSONObject job = messArray.getJSONObject(i);
                            Exchange exchange = new Exchange(job.getString("messId"), job.getInt("userId"), job.getString("name"), job.getString("userpicUrl"), job.getString("phone"),
                                    job.getString("content"), job.getString("wechat"), job.getString("time"), job.getString("commentNum"),
                                    job.getString("likeNum"));
                            myexchangeList.add(exchange);
                        }
                    }
                    RecyclerView recyclerView = (RecyclerView) mymessageLayout.findViewById(R.id.my_recycle_message_item);
                    FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);
                    adapter = new MessageAdaper(myexchangeList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();

            }

        });

        swipeRefreshLayout = (SuperSwipeRefreshLayout) mymessageLayout.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        String url = "http://39.107.225.80:8080//julieServer/MyMessageServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("userid", UserManager.getInstance().getUser().getId());
                        //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
                        x.http().get(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    myexchangeList.clear();
                                    JSONObject jb = new JSONObject(result);
                                    JSONArray messArray = jb.getJSONArray("messList");
                                    if (messArray.length() > 0) {
                                        for (int i = 0; i < messArray.length(); i++) {
                                            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                            JSONObject job = messArray.getJSONObject(i);
                                            Exchange exchange = new Exchange(job.getString("messId"), job.getInt("userId"), job.getString("name"), job.getString("userpicUrl"), job.getString("phone"),
                                                    job.getString("content"), job.getString("wechat"), job.getString("time"), job.getString("commentNum"),
                                                    job.getString("likeNum"));
                                            myexchangeList.add(exchange);
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
                                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
                            }

                            //主动调用取消请求的回调方法
                            @Override
                            public void onCancelled(CancelledException cex) {
                                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFinished() {
                                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();

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
        return mymessageLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.my_recycle_message_item)
    public void onViewClicked() {
    }
}

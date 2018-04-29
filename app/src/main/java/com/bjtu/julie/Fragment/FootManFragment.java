package com.bjtu.julie.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjtu.julie.Activity.P_MessageActivity;
import com.bjtu.julie.Activity.Pub_footActivity;
import com.bjtu.julie.Adapter.FootManAdaper;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Order;
import com.bjtu.julie.R;
import com.jimi_wu.ptlrecyclerview.PullToRefresh.PullToRefreshRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FootManFragment extends Fragment {
    public List<Order> orderlist = new ArrayList<>();
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View orderLayout = inflater.inflate(R.layout.activity_foot_man, container, false);
        //initGuide()

        String url = "http://39.107.225.80:8080//julieServer/FootManServlet";
        RequestParams params = new RequestParams(url);
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
                            orderlist.add(exorder);
                        }
                    }
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    //Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                    RecyclerView recyclerView = (RecyclerView) orderLayout.findViewById(R.id.recycle_order_item);
                    FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);

                    FootManAdaper adapter = new FootManAdaper(orderlist);
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

        unbinder = ButterKnife.bind(this, orderLayout);
        return orderLayout;
    }

    private void initGuide() {

//        Order exorder = new Order("footId", "userpicUrl", "username", "1", "content", "address", "reward", "2018-09-09 09:12:33.0");
//        orderlist.add(exorder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.push_order, R.id.push_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.push_order:
                Intent intent = new Intent(getActivity(), Pub_footActivity.class);
                startActivity(intent);
                break;
            case R.id.push_message:
                Intent intent1 = new Intent(getActivity(), P_MessageActivity.class);
                startActivity(intent1);
                break;
        }
    }

}



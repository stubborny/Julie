package com.bjtu.julie.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bjtu.julie.Activity.P_MessageActivity;
import com.bjtu.julie.Activity.Pub_footActivity;
import com.bjtu.julie.Adapter.FootManAdaper;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.MessageEvent;
import com.bjtu.julie.Model.Order;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;
import com.jimi_wu.ptlrecyclerview.PullToRefresh.PullToRefreshRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

public class FootManFragment extends Fragment {
    public List<Order> orderlist = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.actionMenu)
    ActionMenu actionMenu;
    RecyclerView recyclerView;
    FootManAdaper adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化悬浮菜单按钮选项
        actionMenu.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
        actionMenu.addView(R.drawable.write, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
        actionMenu.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                switch (index) {
                    case 0:
                        //加号按钮，点击后弹出子菜单
                        break;
                    case 1:
                        //从下往上第2个子菜单
                        //判断是否登录
                        if (!UserManager.getInstance().isLogined()) {
                            Toast.makeText(getContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent1 = new Intent(getActivity(), P_MessageActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        //从下往上第3个子菜单
                        //判断是否登录
                        if (!UserManager.getInstance().isLogined()) {
                            Toast.makeText(getContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(getActivity(), Pub_footActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getActivity().getApplicationContext(), "菜单", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onAnimationEnd(boolean isOpen) {

            }
        });
    }

    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View orderLayout = inflater.inflate(R.layout.activity_foot_man, container, false);
        //initGuide();
        // 注册订阅者
        EventBus.getDefault().register(this);

        recyclerView = (RecyclerView) orderLayout.findViewById(R.id.recycle_order_item);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

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
                            Order exorder = new Order(job.getString("footId"), job.getInt("userId"), job.getInt("receiveId"), job.getString("userpicUrl"), job.getString("username"), job.getString("state"), job.getString("content"), job.getString("address"), job.getString("reward"), job.getString("time"), job.getString("phone"), Integer.parseInt(job.getString("isEvaluate")));
                            exorder.setPayOnline(job.getInt("payOnline"));
                            exorder.setAddNeed(job.getString("addNeed"));
                            orderlist.add(exorder);
                        }
                    }
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    //Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();

                    adapter = new FootManAdaper(orderlist);
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
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), Pub_footActivity.class);
                startActivity(intent);
                break;
            case R.id.push_message:
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(getActivity(), P_MessageActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("state"))
            adapter.notifyItemChanged(1);
    }


}




package com.bjtu.julie.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Activity.P_MessageActivity;
import com.bjtu.julie.Activity.Pub_footActivity;
import com.bjtu.julie.Adapter.MessageAdaper;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

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

public class MessageFragment extends Fragment {
    @BindView(R.id.title_btn_back)
    TextView titleBtnBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    private List<Exchange> exchangeList = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.actionMenu)
    ActionMenu actionMenu;

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

        final View messageLayout = inflater.inflate(R.layout.activity_message, container, false);
        unbinder = ButterKnife.bind(this, messageLayout);
        titleText.setText("消息");
        titleBtnOk.setText("");
        titleBtnBack.setText("");
        String url = "http://39.107.225.80:8080//julieServer/MessageServlet";
        RequestParams params = new RequestParams(url);
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

                            Exchange exchange = new Exchange(job.getString("messId"), job.getString("name"), job.getString("userpicUrl"), job.getString("phone"), job.getString("content"), job.getString("wechat"), job.getString("time"), job.getString("commentNum"),job.getString("likeNum"));
                            exchangeList.add(exchange);
                        }
                    }
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    //Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();

                    //finish();
                    RecyclerView recyclerView = (RecyclerView) messageLayout.findViewById(R.id.messRecycleView);
                    FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);

                    MessageAdaper adapter = new MessageAdaper(exchangeList);
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

        //initExchange();

        RecyclerView recyclerView = (RecyclerView) messageLayout.findViewById(R.id.messRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MessageAdaper adaper = new MessageAdaper(exchangeList);
        recyclerView.setAdapter(adaper);
        return messageLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.title_btn_back)
    public void onViewClicked() {
    }


//    private void initExchange(){
//
//            Exchange aaa=new Exchange("aaa",R.mipmap.aaa_pic);
//            exchangeList.add(aaa);
//            Exchange bbb=new Exchange("bbb",R.mipmap.bbb_pic);
//            exchangeList.add(bbb);
//            Exchange ccc=new Exchange("ccc",R.mipmap.ccc_pic);
//            exchangeList.add(ccc);
//            Exchange ddd=new Exchange("ddd",R.mipmap.ddd_pic);
//            exchangeList.add(ddd);
//
//    }


}

package com.bjtu.julie.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bjtu.julie.Activity.RecieveActivity;
import com.bjtu.julie.Adapter.MessageAdaper;
import com.bjtu.julie.Adapter.MyMessageAdapter;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

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
    private List<Exchange> myexchangeList = new ArrayList<>();
    Unbinder unbinder;

    public MyMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View mymessageLayout = inflater.inflate(R.layout.fragment_my_message, container, false);
        unbinder = ButterKnife.bind(this, mymessageLayout);

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
                            Exchange exchange = new Exchange(job.getString("messId"), job.getString("name"), job.getString("userpicUrl"), job.getString("phone"), job.getString("content"), job.getString("wechat"), job.getString("time"), messArray.length());
                            myexchangeList.add(exchange);
                        }
                    }
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();

                    //finish();
                    RecyclerView recyclerView = (RecyclerView) mymessageLayout.findViewById(R.id.my_recycle_message_item);
                    FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);

                    MyMessageAdapter adapter = new MyMessageAdapter(myexchangeList);
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

        RecyclerView recyclerView = (RecyclerView) mymessageLayout.findViewById(R.id.my_recycle_message_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyMessageAdapter adapter = new MyMessageAdapter(myexchangeList);
        recyclerView.setAdapter(adapter);

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

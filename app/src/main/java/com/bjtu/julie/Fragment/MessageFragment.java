package com.bjtu.julie.Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Adapter.MessageAdaper;
import com.bjtu.julie.R;

public class MessageFragment extends android.support.v4.app.Fragment {
    private List<Exchange> exchangeList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View messageLayout = inflater.inflate(R.layout.activity_message, container, false);
        initExchange();
        RecyclerView recyclerView=(RecyclerView)messageLayout.findViewById(R.id.messRecycleView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MessageAdaper adaper=new MessageAdaper(exchangeList);
        recyclerView.setAdapter(adaper);
        return messageLayout;
    }
    private void initExchange(){

            Exchange aaa=new Exchange("aaa",R.mipmap.aaa_pic);
            exchangeList.add(aaa);
            Exchange bbb=new Exchange("bbb",R.mipmap.bbb_pic);
            exchangeList.add(bbb);
            Exchange ccc=new Exchange("ccc",R.mipmap.ccc_pic);
            exchangeList.add(ccc);
            Exchange ddd=new Exchange("ddd",R.mipmap.ddd_pic);
            exchangeList.add(ddd);

    }
}

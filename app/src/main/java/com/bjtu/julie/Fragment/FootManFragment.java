package com.bjtu.julie.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjtu.julie.Activity.P_MessageActivity;
import com.bjtu.julie.Activity.Pub_footActivity;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bjtu.julie.R;

public class FootManFragment extends Fragment {
    public List<Order> orderlist = new ArrayList<>();
    @BindView(R.id.push_order)
    LinearLayout pushOrder;
    @BindView(R.id.push_message)
    LinearLayout pushMessage;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View orderLayout = inflater.inflate(R.layout.activity_foot_man, container, false);
        initGuide();
        RecyclerView recyclerView = (RecyclerView) orderLayout.findViewById(R.id.recycle_order_item);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        OrderAdapter adapter = new OrderAdapter();
        recyclerView.setAdapter(adapter);
        unbinder = ButterKnife.bind(this, orderLayout);
        return orderLayout;
    }

    private void initGuide() {
        Order exorder = new Order("面和抄手代取", "3元", "主校区18号楼408", "12时20分");
        orderlist.add(exorder);
        Order exorder2 = new Order("面和抄手代取", "3元", "主校区18号楼408", "12时20分");
        orderlist.add(exorder2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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


    public class OrderAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Order exorder = orderlist.get(position);
            holder.text_title.setText(exorder.getTitle());
            holder.text_money.setText(exorder.getMoney());
            holder.text_title.setText(exorder.getAddress());
            holder.text_money.setText(exorder.getTime());

        }

        @Override
        public int getItemCount() {
            return orderlist.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        TextView text_money;
        TextView text_address;
        TextView text_time;

        public ViewHolder(View view) {
            super(view);
            text_title = (TextView) view.findViewById(R.id.text_title);
            text_money = (TextView) view.findViewById(R.id.text_money);
            text_address = (TextView) view.findViewById(R.id.text_address);
            text_time = (TextView) view.findViewById(R.id.text_time);
        }
    }
}



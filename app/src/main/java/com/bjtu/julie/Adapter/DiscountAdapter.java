package com.bjtu.julie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bjtu.julie.Model.Discount;
import com.bjtu.julie.R;

import java.util.List;

public class DiscountAdapter extends ArrayAdapter<Discount> {
    private int resourceId;
    public DiscountAdapter(Context context, int textViewResourceId, List<Discount> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Discount discount=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView discountMoney=(TextView)view.findViewById(R.id.DiscountMoney);
        TextView discountName=(TextView)view.findViewById(R.id.DiscountName);
        discountMoney.setText(discount.getMoney());
        discountName.setText(discount.getDiscountName());
        return view;

    }
}

package com.bjtu.julie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bjtu.julie.Model.Discount;
import com.bjtu.julie.R;

import java.util.List;

public class MyDiscountAdapter extends ArrayAdapter<Discount> {
    private int resourceId;
    private Context context;

    public MyDiscountAdapter(Context context, int textViewResourceId, List<Discount> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        Discount discount = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView discountMoney = (TextView) view.findViewById(R.id.discount_text_money);
        TextView discountName = (TextView) view.findViewById(R.id.discount_text_name);
        TextView discountDeadline = (TextView) view.findViewById(R.id.discount_text_deadline);
        TextView discountLimit = (TextView) view.findViewById(R.id.discount_text_limit);
        TextView discountUsable = (TextView) view.findViewById(R.id.discount_text_usable);

        discountMoney.setText("￥" + discount.getMoney());
        discountName.setText(String.valueOf(discount.getName()));
        discountDeadline.setText(discount.getDeadline() + "到期");
        discountLimit.setText(discount.getLimit());
        discountUsable.setText("满" + discount.getUsable() + "元可用");
        return view;

    }

}

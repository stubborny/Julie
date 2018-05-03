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

public class DiscountAdapter extends ArrayAdapter<Discount> {
    private int resourceId;
    private int tempPosition = -1;  //记录已经点击的CheckBox的位置
    private Context context;
    private int udId;
    public DiscountAdapter(Context context, int textViewResourceId, List<Discount> objects,int udId){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        this.udId=udId;
    }
    @Override
    public View getView(int position, final View convertView, ViewGroup parent){
        Discount discount=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView discountMoney=(TextView)view.findViewById(R.id.discount_text_money);
        TextView discountName=(TextView)view.findViewById(R.id.discount_text_name);
        TextView discountDeadline=(TextView)view.findViewById(R.id.discount_text_deadline);
        TextView discountLimit=(TextView)view.findViewById(R.id.discount_text_limit);
        TextView discountUsable=(TextView)view.findViewById(R.id.discount_text_usable);
        CheckBox discountCBoxChoose=(CheckBox)view.findViewById(R.id.discount_cbox_choose);

        if(discount.getUdId()==udId){
            tempPosition=position;
        }
        discountCBoxChoose.setId(position);    //设置当前position为CheckBox的id
        discountCBoxChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (tempPosition != -1) {
                        //根据id找到上次点击的CheckBox,将它设置为false.
                        Activity activity=(Activity)context;
                        CheckBox tempCheckBox = (CheckBox)activity.findViewById(tempPosition);
                        if (tempCheckBox != null) {
                            tempCheckBox.setChecked(false);
                        }
                    }
                    //保存当前选中CheckBox的id值
                    tempPosition = buttonView.getId();

                } else {    //当CheckBox被选中,又被取消时,将tempPosition重新初始化.
                    tempPosition = -1;
                }
            }
        });
        if (position == tempPosition)   //比较位置是否一样,一样就设置为选中,否则就设置为未选中.
            discountCBoxChoose.setChecked(true);
        else discountCBoxChoose.setChecked(false);

        discountMoney.setText("￥"+discount.getMoney());
        discountName.setText(String.valueOf(discount.getName()));
        discountDeadline.setText(discount.getDeadline()+"到期");
        discountLimit.setText(discount.getLimit());
        discountUsable.setText("满"+discount.getUsable()+"元可用");
        return view;

    }
    //返回当前CheckBox选中的位置,便于获取值.
    public int getSelectPosition() {
        return tempPosition;
    }
}

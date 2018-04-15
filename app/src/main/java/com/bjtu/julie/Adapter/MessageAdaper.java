package com.bjtu.julie.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Activity.FootDetail;
import com.bjtu.julie.Activity.MessageDetail;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.R;

import java.util.List;

/**
 * Created by dadada on 2018/3/31.
 */

public class MessageAdaper extends RecyclerView.Adapter<MessageAdaper.ViewHolder> {
    private List<Exchange> mMessList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View messView;
        ImageView messImage;
        TextView messName;
        public ViewHolder(View view){
            super(view);
            messView=view;
            messImage=(ImageView) view.findViewById(R.id.messUserImage);
            messName=(TextView) view.findViewById(R.id.messUserName);
        }
    }
    public  MessageAdaper(List<Exchange> MessList){
        mMessList=MessList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
       final ViewHolder holder=new ViewHolder(view);
        holder.messView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                Exchange exchange=mMessList.get(position);
                Intent intent = new Intent(v.getContext(),MessageDetail .class);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void  onBindViewHolder(ViewHolder holder,int position){
        Exchange exchange=mMessList.get(position);
        holder.messImage.setImageResource(exchange.getImageId());
        holder.messName.setText(exchange.getName());
    }
    @Override
    public int getItemCount(){
        return mMessList.size();
    }
}

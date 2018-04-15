package com.bjtu.julie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.R;

import java.util.List;

/**
 * Created by dadada on 2018/4/15.
 */

public class MessCommentAdapter extends ArrayAdapter<Comment> {
    private int resourceId;
    public MessCommentAdapter(Context context, int textViewResourceId, List<Comment> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Comment comment=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView messCommImage=(ImageView)view.findViewById(R.id.commentUserImage);
        TextView messCommName=(TextView)view.findViewById(R.id.commentUserName);
        messCommImage.setImageResource(comment.getCommImageId());
        messCommName.setText(comment.getCommName());
        return view;
    }
}

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

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by dadada on 2018/4/15.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private int resourceId;
    public CommentAdapter(Context context, int textViewResourceId, List<Comment> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Comment comment=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView ciImgUserpic=(ImageView)view.findViewById(R.id.ci_img_userpic);
        TextView ciTextNickname=(TextView)view.findViewById(R.id.ci_text_nickname);
        TextView ciTextComment=(TextView)view.findViewById(R.id.ci_text_comment);
        TextView ciTextFloor=(TextView)view.findViewById(R.id.ci_text_floor);
        TextView ciTextTime=(TextView)view.findViewById(R.id.ci_text_time);

        ciTextNickname.setText(comment.getNickname());
        ciTextTime.setText(comment.getTime().substring(0,19));
        ciTextFloor.setText(comment.getFloor()+"楼");
        ciTextComment.setText(comment.getComment());
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(ciImgUserpic, comment.getUserpicUrl(), imageOptions);
        return view;
    }
}

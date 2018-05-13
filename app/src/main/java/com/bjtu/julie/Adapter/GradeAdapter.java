package com.bjtu.julie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.Model.Grade;
import com.bjtu.julie.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by dadada on 2018/4/27.
 */

public class GradeAdapter extends ArrayAdapter<Grade> {
    private int resourceId;
    public GradeAdapter(Context context, int textViewResourceId, List<Grade> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Grade grade=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView impImgUserpic=(ImageView)view.findViewById(R.id.imp_img_userpic);
        TextView impTextName=(TextView)view.findViewById(R.id.imp_text_nickname);
        TextView impTextGrade=(TextView)view.findViewById(R.id.imp_text_grade);
        TextView impTextFloor=(TextView)view.findViewById(R.id.imp_text_floor);
        TextView impTextTime=(TextView)view.findViewById(R.id.imp_text_time);

        impTextName.setText(grade.getName());
       // impTextTime.setText(grade.getTime().substring(0,19));
       // impTextFloor.setText(grade.getFloor()+"楼");
        //impTextGrade.setText(grade.getGradeLevel());
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(impImgUserpic, grade.getUserpicUrl(), imageOptions);
        return view;
    }
}

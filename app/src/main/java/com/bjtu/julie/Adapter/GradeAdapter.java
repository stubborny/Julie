package com.bjtu.julie.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bjtu.julie.Model.Grade;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.DateUtil;

import org.w3c.dom.Text;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {
    private List<Grade> gradeList;
    Integer number;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View messView;

        ImageView impressionImage;
        TextView impressionName;
        TextView impressionContent;
        TextView impressionTime;
        TextView impressionRate;
        TextView evaluate1;
        TextView evaluate2;
        TextView evaluate3;
        public ViewHolder(View view){
            super(view);
            messView=view;
            impressionImage=(ImageView) view.findViewById(R.id.imp_img_userpic);
            impressionName=(TextView) view.findViewById(R.id.imp_text_nickname);
            impressionTime=(TextView)view.findViewById(R.id.imp_text_time);
            impressionContent=(TextView)view.findViewById(R.id.content);
            impressionRate=(TextView)view.findViewById(R.id.imp_text_grade);
            evaluate1=(TextView)view.findViewById(R.id.evaluate1);
            evaluate2=(TextView)view.findViewById(R.id.evaluate2);
            evaluate3=(TextView)view.findViewById(R.id.evaluate3);
        }
    }
    public  GradeAdapter(List<Grade> gradelist){
        gradeList=gradelist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.impression_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.messView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                Grade exchange=gradeList.get(position);
//                Intent intent = new Intent(v.getContext(),MessageDetailActivity.class);
//                intent.putExtra("exchange",exchange);
//                v.getContext().startActivity(intent);
            }
        });

        return holder;
    }
    @Override
    public void  onBindViewHolder(ViewHolder holder,int position){
        Grade grade=gradeList.get(position);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(holder.impressionImage, grade.getUserpicUrl(), imageOptions);

        holder.impressionName.setText(grade.getName());
        holder.impressionContent.setText(grade.getContent());
        holder.impressionTime.setText(new DateUtil().diffDate(grade.getTime().substring(0,19)));
        holder.impressionRate.setText(grade.getRate()+"颗星");
        if(grade.getEvaluate1()==1){
            holder.evaluate1.setTextColor(Color.RED);
        }
        if(grade.getEvaluate2()==1){
            holder.evaluate2.setTextColor(Color.RED);
        }
        if(grade.getEvaluate3()==1){
            holder.evaluate3.setTextColor(Color.RED);
        }

    }
    @Override
    public int getItemCount(){
        return gradeList.size();
    }
}


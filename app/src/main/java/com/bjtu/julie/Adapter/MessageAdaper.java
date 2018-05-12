package com.bjtu.julie.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Activity.MessageDetailActivity;
import com.bjtu.julie.Activity.P_MessageActivity;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by dadada on 2018/3/31.
 */

public class MessageAdaper extends RecyclerView.Adapter<MessageAdaper.ViewHolder> {
    private List<Exchange> mMessList;
    Integer number;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View messView;

        ImageView messImage;
        TextView messName;
        TextView messContent;
        TextView messTime;
        TextView messComment;
        TextView messLike;
        public ViewHolder(View view){
            super(view);
            messView=view;
            messImage=(ImageView) view.findViewById(R.id.messUserImage);
            messName=(TextView) view.findViewById(R.id.messUserName);
            messTime=(TextView)view.findViewById(R.id.messPubTime);
            messContent=(TextView)view.findViewById(R.id.messContent);
            messComment=(TextView)view.findViewById(R.id.messComment);
            messLike=(TextView)view.findViewById(R.id.messAttention);
        }
    }
    public  MessageAdaper(List<Exchange> MessList){
        mMessList=MessList;
        //number=mMessList.size();
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
                Intent intent = new Intent(v.getContext(),MessageDetailActivity.class);
                intent.putExtra("exchange",exchange);
                v.getContext().startActivity(intent);
            }
        });
//        holder.messLike.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                int position=holder.getAdapterPosition();
//                Exchange exchange=mMessList.get(position);
//                //Intent intent = new Intent(v.getContext(),MessageDetailActivity.class);
//                //intent.putExtra("exchange",exchange);
//                //v.getContext().startActivity(intent);
////                String url = "http://39.107.225.80:8080/julieServer/PubLikeServlet";
////                RequestParams params = new RequestParams(url);
////                params.setCharset("utf-8");
////                //params.addParameter("userId", UserManager.getInstance().getUser().getId());
////                params.addParameter("userId", 1);
////                params.addParameter("messId", exchange.getMessId());
////                x.http().get(params, new Callback.CommonCallback<String>() {
////
////                    public void onSuccess(String result) {
////                        try {
////                            JSONObject jb = new JSONObject(result);
////                            Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
////                            //finish();
////                            //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
////
////                    }
////
////                    //请求异常后的回调方法
////                    @Override
////                    public void onError(Throwable ex, boolean isOnCallback) {
////                    }
////
////                    //主动调用取消请求的回调方法
////                    @Override
////                    public void onCancelled(CancelledException cex) {
////                    }
////
////                    @Override
////                    public void onFinished() {
////
////                    }
////                });
////                //Toast.makeText(,"发布成功",Toast.LENGTH_SHORT).show();
////            }
//        });
        return holder;
    }
    @Override
    public void  onBindViewHolder(ViewHolder holder,int position){
        Exchange exchange=mMessList.get(position);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(holder.messImage, exchange.getUserpicUrl(), imageOptions);

        holder.messName.setText(exchange.getName());
        holder.messContent.setText(exchange.getContent());
        holder.messTime.setText(new DateUtil().diffDate(exchange.getTime().substring(0,19)));
        holder.messComment.setText("评论（"+ exchange.getcommentNum()+")");
        holder.messLike.setText("收藏（"+exchange.getLikeNum()+")");
    }
    @Override
    public int getItemCount(){
        return mMessList.size();
    }
}

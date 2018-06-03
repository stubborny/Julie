package com.bjtu.julie.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.x;

import com.bjtu.julie.Activity.FootDetailActivity;
import com.bjtu.julie.Activity.FootDetailOwnerActivity;
import com.bjtu.julie.Activity.FootDetailVisitorActivity;
import com.bjtu.julie.Model.Order;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.DateUtil;

import org.xutils.image.ImageOptions;

import java.util.List;

/**
 * Created by carrey
 */
public class FootManAdaper extends RecyclerView.Adapter<FootManAdaper.ViewHolder> {
    private List<Order> mOrderList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        ImageView mImgUserpic;
        TextView mTextUsername;
        TextView mTextState;
        TextView mTextConent;
        TextView mTextAddress;
        TextView mTextReward;
        TextView mTextTime;

        public ViewHolder(View view) {
            super(view);
            orderView = view;
            mImgUserpic = (ImageView) view.findViewById(R.id.oi_img_userpic);
            mTextUsername = (TextView) view.findViewById(R.id.oi_text_username);
            mTextState = (TextView) view.findViewById(R.id.oi_text_state);
            mTextConent = (TextView) view.findViewById(R.id.oi_text_content);
            mTextAddress = (TextView) view.findViewById(R.id.oi_text_address);
            mTextReward = (TextView) view.findViewById(R.id.oi_text_reward);
            mTextTime = (TextView) view.findViewById(R.id.oi_text_time);
        }
    }

    public FootManAdaper(List<Order> oList) {
        mOrderList = oList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Order order = mOrderList.get(position);
                /**
                 * 如果是发单者，跳转到 FootDetailOwnerActivity
                 * 否则 如果订单状态为1新发布，跳转到 FootDetailActivity
                 *      否则 判断是接单者，跳转到  FootDetailActivity
                 *                 游客，跳转到 FootDetailVisitorActivity
                 *
                 */
                Intent intent;
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(v.getContext(), "登陆后才能接单哦", Toast.LENGTH_SHORT).show();
                    intent = new Intent(v.getContext(), FootDetailVisitorActivity.class);
                } else if (UserManager.getInstance().getUser().getId() == order.getUserId()) {
                    intent = new Intent(v.getContext(), FootDetailOwnerActivity.class);
                } else if (order.getState().equals("1")) {
                    intent = new Intent(v.getContext(), FootDetailActivity.class);
                } else if (UserManager.getInstance().getUser().getId() == order.getReceiveId()) {
                    intent = new Intent(v.getContext(), FootDetailActivity.class);
                } else {
                    intent = new Intent(v.getContext(), FootDetailVisitorActivity.class);
                }
                intent.putExtra("order", order);
                intent.putExtra("position", position);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (mOrderList.get(position).getState().equals("1")) {
                holder.mTextState.setText("新发布");
                holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.red));
            } else if (mOrderList.get(position).getState().equals("2")) {
                holder.mTextState.setText("被抢啦");
                holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.darkgrey));
            } else if (mOrderList.get(position).getState().equals("3")) {
                holder.mTextState.setText("已送达");
                holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.darkgrey));
            } else if (mOrderList.get(position).getState().equals("4")) {
                holder.mTextState.setText("已结单");
                holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.darkgrey));
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrderList.get(position);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .setCircular(true)
                .build();
        x.image().bind(holder.mImgUserpic, order.getUserpicUrl(), imageOptions);
        holder.mTextUsername.setText(order.getNickname());
        holder.mTextAddress.setText(order.getAddress());
        holder.mTextConent.setText(order.getContent());
        if (order.getState().equals("1")) {
            holder.mTextState.setText("新发布");
            holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.red));
        } else if (order.getState().equals("2")) {
            holder.mTextState.setText("被抢啦");
            holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.darkgrey));
        } else if (order.getState().equals("3")) {
            holder.mTextState.setText("已送达");
            holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.darkgrey));
        } else if (order.getState().equals("4")) {
            holder.mTextState.setText("已结单");
            holder.mTextState.setTextColor(holder.mTextState.getResources().getColor(R.color.darkgrey));
        }
        holder.mTextReward.setText("赏金" + order.getReward() + "元");
        holder.mTextTime.setText(new DateUtil().diffDate(order.getTime().substring(0, 19)));
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}

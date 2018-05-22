package com.bjtu.julie.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Adapter.CommentAdapter;
import com.bjtu.julie.Adapter.MyFootManAdapter;
import com.bjtu.julie.Fragment.ContactDialogFragment;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.Model.MessageEvent;
import com.bjtu.julie.Model.Order;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.DateUtil;
import com.lhz.stateprogress.StateProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FootDetailOwnerActivity extends AppCompatActivity {

    @BindView(R.id.foot_detail_img_userpic)
    ImageView footDetailImgUserpic;
    @BindView(R.id.foot_detail_text_nickname)
    TextView footDetailTextNickname;
    @BindView(R.id.foot_detail_text_time)
    TextView footDetailTextTime;
    @BindView(R.id.foot_detail_text_content)
    TextView footDetailTextContent;
    @BindView(R.id.foot_detail_text_address)
    TextView footDetailTextAddress;
    @BindView(R.id.foot_detail_text_reward)
    TextView footDetailTextReward;
    @BindView(R.id.spv)
    StateProgressView spv;
    @BindView(R.id.foot_detail_comment_list)
    ListView footDetailCommentList;
    @BindView(R.id.foot_detail_btn_receive)
    Button footDetailBtnReceive;
    @BindView(R.id.foot_detail_btn_comment)
    Button footDetailBtnComment;
    Order order;
    int oState;
    List<String> list;
    @BindView(R.id.foot_detail_layout_phone)
    LinearLayout footDetailLayoutPhone;
    @BindView(R.id.foot_detail_img_receive_userpic)
    ImageView footDetailImgReceiveUserpic;
    @BindView(R.id.foot_detail_text_receive_nickname)
    TextView footDetailTextReceiveNickname;
    @BindView(R.id.foot_detail_text_receive_phone)
    TextView footDetailTextReceivePhone;
    @BindView(R.id.foot_detail_layout_receive_phone)
    RelativeLayout footDetailLayoutReceivePhone;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.foot_detail_text_phone)
    TextView footDetailTextPhone;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.foot_detail_view)
    View footDetailView;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    @BindView(R.id.foot_detail_addNeed1)
    TextView footDetailAddNeed1;
    @BindView(R.id.foot_detail_addNeed2)
    TextView footDetailAddNeed2;
    @BindView(R.id.foot_detail_addNeed3)
    TextView footDetailAddNeed3;
    @BindView(R.id.foot_detail_addNeed4)
    TextView footDetailAddNeed4;
    @BindView(R.id.foot_detail_addNeed)
    LinearLayout footDetailAddNeed;
    private List<Comment> commList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_detail);
        ButterKnife.bind(this);
        // 注册订阅者
        EventBus.getDefault().register(this);

        titleText.setText("详情");
        titleBtnOk.setText("");
        footDetailTextContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        order = (Order) getIntent().getSerializableExtra("order");
        if (order.getState().equals("1")) {
            titleBtnOk.setText("删除");
        }
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .setCircular(true)
                .build();
        x.image().bind(footDetailImgUserpic, order.getUserpicUrl(), imageOptions);
        footDetailTextNickname.setText(order.getNickname());
        footDetailTextContent.setText(order.getContent());
        footDetailTextAddress.setText(order.getAddress());
        footDetailTextTime.setText(new DateUtil().diffDate(order.getTime().substring(0, 19)));
        if (order.getPayOnline() == 1) {
            footDetailTextReward.setText("报酬" + order.getReward() + "元[已在线支付]");
        } else {
            footDetailTextReward.setText("报酬" + order.getReward() + "元[不经平台，线下支付]");
        }
        if (order.getAddNeed().equals("0,0,0,0")) {
            footDetailAddNeed.setVisibility(View.GONE);
        } else {
            String[] strArray = null;
            strArray = order.getAddNeed().split(","); //拆分字符为"," ,然后把结果交给数组strArray
            if (strArray[0].equals("0")) {
                footDetailAddNeed1.setVisibility(View.GONE);
            }
            if (strArray[1].equals("0")) {
                footDetailAddNeed2.setVisibility(View.GONE);
            }
            if (strArray[2].equals("0")) {
                footDetailAddNeed3.setVisibility(View.GONE);
            }
            if (strArray[3].equals("0")) {
                footDetailAddNeed4.setVisibility(View.GONE);
            }

        }
        footDetailLayoutPhone.setVisibility(View.GONE);
        footDetailLayoutReceivePhone.setVisibility(View.VISIBLE);

        oState = Integer.valueOf(order.getState());
        if (oState == 1) {
            footDetailBtnReceive.setText("等待接单");
            footDetailBtnReceive.setBackgroundColor(footDetailBtnReceive.getResources().getColor(R.color.darkgrey));
            footDetailBtnReceive.setClickable(false);
            footDetailLayoutReceivePhone.setVisibility(View.GONE);
        } else if (oState == 2) {
            getReceiver();
            footDetailTextReceivePhone.setText(order.getPhone());
            footDetailBtnReceive.setText("联系接单者");
        } else if (oState == 3) {
            getReceiver();
            //判断是否评价过 没有 可评价同学，已经评价过按钮设置为不可点击状态，字改为已评价
            footDetailBtnReceive.setText("确认送达");
        } else if (oState == 4) {
            getReceiver();
            //判断是否评价过 没有 可评价同学，已经评价过按钮设置为不可点击状态，字改为已评价
            footDetailBtnReceive.setText("可评价同学");
        }
        if (order.getIsEvaluate() == 1) {
            //isEvaluate();
            footDetailBtnReceive.setText("已评价");
            footDetailBtnReceive.setBackgroundColor(footDetailBtnReceive.getResources().getColor(R.color.darkgrey));
            footDetailBtnReceive.setClickable(false);
        }
        //物流节点
        list = new ArrayList<String>();
        //list.add("");
        list.add("新发布");
        list.add("被抢啦");
        list.add("已送达");
        list.add("已结单");
        spv.setItems(list, Integer.valueOf(order.getState()) - 1, 200);
        //spv.setItems(list,0, 200);
        initFootCommInfo();


    }

    private void initFootCommInfo() {
        String url = "http://39.107.225.80:8080//julieServer/CommentListServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("footId", order.getFootId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    JSONArray orderArray = jb.getJSONArray("commList");
                    if (orderArray.length() > 0) {
                        for (int i = 0; i < orderArray.length(); i++) {
                            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            JSONObject job = orderArray.getJSONObject(i);
                            Comment comm = new Comment(job.getString("footId"), job.getInt("userId"), job.getString("userpicUrl"), job.getString("nickname"), job.getInt("floor"), job.getString("comment"), job.getString("time"));
                            commList.add(comm);
                        }
                    }
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    //Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                    CommentAdapter adapter = new CommentAdapter(FootDetailOwnerActivity.this, R.layout.comment_item, commList);
                    footDetailCommentList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });

    }

    @OnClick({R.id.foot_detail_btn_receive, R.id.foot_detail_btn_comment,R.id.title_btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.foot_detail_btn_receive:
                //页面改变--
                // 接单者按钮字改为联系发单者，同时更新订单状态，变为被抢了，
                //发单者按钮字改变为联系接单者，界面显示接单者电话！
                //发单者及接单者之外的同学，订单详情页隐藏接单按钮，只剩评论功能
                //1.更新订单状态
                if (oState == 2) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    //第二个参数 1表示接单者，0表示发单者
                    ContactDialogFragment cDialog = new ContactDialogFragment().newInstance(footDetailTextReceivePhone.getText().toString(), 0);
                    cDialog.show(ft, "ContactDialog");
                } else if (oState == 3) {
                    //确认送达按钮点击了，更新订单状态
                    AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                    alertdialogbuilder.setTitle("确认结单");
                    if (order.getPayOnline() == 1) {
                        alertdialogbuilder.setMessage("此单为线上支付，接单人已确认送达（如有意外，请先于接单人联系，无果请联系客服），确认结单后报酬将存入接单人钱包");
                    } else {
                        alertdialogbuilder.setMessage("此单为线下支付，不经平台，请自行与接单人联系结清钱款");
                    }
                    alertdialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url = "http://39.107.225.80:8080/julieServer/UpdateOrderServlet";
                            RequestParams params = new RequestParams(url);
                            params.addParameter("footId", order.getFootId());
                            params.addParameter("state", "4");
                            x.http().get(params, new Callback.CommonCallback<String>() {
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject jb = new JSONObject(result);
                                        //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                                        if (jb.getInt("code") == 1) {
                                            Toast.makeText(x.app(), "已结单", Toast.LENGTH_SHORT).show();
                                            footDetailBtnReceive.setText("可评价同学");
                                            oState = 4;//修改当前订单状态
                                            spv.setItems(list, 3, 200);
                                        } else {
                                            Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                //请求异常后的回调方法
                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                }

                                //主动调用取消请求的回调方法
                                @Override
                                public void onCancelled(CancelledException cex) {
                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        }
                    });
                    alertdialogbuilder.setNegativeButton("取消", null);
                    AlertDialog alertdialog1 = alertdialogbuilder.create();
                    alertdialog1.show();



                } else if (oState == 4) {
                    //Order order=mMessList.get(position);
                    Intent intent = new Intent(FootDetailOwnerActivity.this, EvaluateActivity.class);
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
                break;

            case R.id.foot_detail_btn_comment:
                final EditText et = new EditText(this);
                final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("评论")
                        .setIcon(R.mipmap.comment)
                        .setView(et)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "还没输入哦", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (input.length() > 50) {
                            Toast.makeText(getApplicationContext(), "字数太多啦", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String url = "http://39.107.225.80:8080/julieServer/PubCommentServlet";
                        RequestParams params = new RequestParams(url);
                        params.addParameter("userId", UserManager.getInstance().getUser().getId());
                        params.addParameter("footId", order.getFootId());
                        params.addParameter("comment", input);
                        x.http().get(params, new Callback.CommonCallback<String>() {
                            public void onSuccess(String result) {
                                try {
                                    JSONObject jb = new JSONObject(result);
                                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                                    if (jb.getInt("code") == 1) {
                                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                            }

                            //主动调用取消请求的回调方法
                            @Override
                            public void onCancelled(CancelledException cex) {
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                });
                break;

            case R.id.title_btn_ok:
               // String footId = order.getFootId();//把单号发送给服务器
                //Toast.makeText(this, footId, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定删除吗? ");

                builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String url = "http://39.107.225.80:8080//julieServer/DeleMyOrderServlet";

                        RequestParams params = new RequestParams(url);
                        params.addParameter("footId", order.getFootId());

                        x.http().get(params, new Callback.CommonCallback<String>() {

                            public void onSuccess(String result) {
                                try {
                                    JSONObject jb = new JSONObject(result);
                                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                                    finish();
                                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                            }

                            //主动调用取消请求的回调方法
                            @Override
                            public void onCancelled(CancelledException cex) {
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        return ;
                    }
                }).show();



                break;

        }
    }



    @OnClick(R.id.foot_detail_text_receive_phone)
    public void onViewClicked() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //第二个参数 1表示接单者，0表示发单者
        ContactDialogFragment cDialog = new ContactDialogFragment().newInstance(footDetailTextReceivePhone.getText().toString(), 0);
        cDialog.show(ft, "ContactDialog");
    }

    private void getReceiver() {
        String url = "http://39.107.225.80:8080/julieServer/GetReceiverServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("footId", order.getFootId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    if (jb.getInt("code") == 1) {
                        JSONObject job = jb.getJSONObject("data");
                        footDetailTextReceiveNickname.setText(job.getString("nickname"));
                        footDetailTextReceivePhone.setText(job.getString("username"));
                        ImageOptions imageOptions = new ImageOptions.Builder()
                                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                .setFailureDrawableId(R.mipmap.load_error)
                                .setLoadingDrawableId(R.mipmap.loading)
                                .build();
                        x.image().bind(footDetailImgReceiveUserpic, job.getString("userpicUrl"), imageOptions);
                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                        //receivePhone= jb.getString("msg");
                    } else {
                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("评价成功")) {
            order.setIsEvaluate(1);
            footDetailBtnReceive.setText("已评价");
            footDetailBtnReceive.setBackgroundColor(footDetailBtnReceive.getResources().getColor(R.color.darkgrey));
            footDetailBtnReceive.setClickable(false);
        }
    }
}

package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Adapter.CommentAdapter;
import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.Model.Order;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.DateUtil;
import com.lhz.stateprogress.StateProgressView;

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


public class FootDetailVisitorActivity extends AppCompatActivity {
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
    Order order;
    int oState;
    List<String> list;
    @BindView(R.id.foot_detail_btn_receive)
    Button footDetailBtnReceive;
    @BindView(R.id.foot_detail_view)
    View footDetailView;
    @BindView(R.id.title_text)
    TextView titleText;
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
        titleText.setText("详情");
        titleBtnOk.setText("");
        footDetailTextContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        order = (Order) getIntent().getSerializableExtra("order");
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
        footDetailBtnReceive.setVisibility(View.GONE);
        footDetailView.setVisibility(View.GONE);
        //物流节点
        list = new ArrayList<String>();
        //list.add("");
        list.add("新发布");
        list.add("被抢啦");
        list.add("已送达");
        list.add("已结单");
        spv.setItems(list, Integer.valueOf(order.getState()) - 1, 200);
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
                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                    CommentAdapter adapter = new CommentAdapter(FootDetailVisitorActivity.this, R.layout.comment_item, commList);
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

    @OnClick({R.id.foot_detail_btn_receive, R.id.foot_detail_btn_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.foot_detail_btn_comment:
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getApplicationContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                            Toast.makeText(getApplicationContext(), "还没输入哦", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (input.length() > 50) {
                            Toast.makeText(getApplicationContext(), "字数太多啦", Toast.LENGTH_LONG).show();
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
                                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
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

        }
    }
}

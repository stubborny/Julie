package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Adapter.CommentAdapter;
import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;
import com.bjtu.julie.Util.DateUtil;

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

public class MessageDetailActivity extends AppCompatActivity {

    @BindView(R.id.messUserImage)
    ImageView footDetailImgUserpic;
    @BindView(R.id.messUserName)
    TextView messDetailUserName;
    @BindView(R.id.foootTime)
    TextView messDetailTextTime;
    @BindView(R.id.foootContent)
    TextView messDetailContent;
    @BindView(R.id.editText)
    EditText messCommentEditText;
    @BindView(R.id.textView2)
    TextView textViewShowComment;
    @BindView(R.id.textView)
    TextView textViewLike;
    @BindView(R.id.messCommList)
    ListView footDetailCommentList;
    @BindView(R.id.comment_btn)
    Button commentBtn;

    Exchange exchange;
    @BindView(R.id.title_btn_back)
    TextView titleBtnBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    private List<Comment> commList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        //初始化评论信息
        //initMessCommInfo();
        titleBtnOk.setText("");
        titleText.setText("详情");
        ButterKnife.bind(this);
        messDetailContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        exchange = (Exchange) getIntent().getSerializableExtra("exchange");

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.load_error)
                .setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(footDetailImgUserpic, exchange.getUserpicUrl(), imageOptions);
        //footDetailTextNickname.setText(order.getNickname());
        messDetailContent.setText(exchange.getContent());
        //footDetailTextAddress.setText(order.getAddress());
        messDetailTextTime.setText(new DateUtil().diffDate(exchange.getTime().substring(0, 19)));
        //footDetailTextReward.setText("已支付在线报酬" + order.getReward() + "元");

        initMessCommInfo();

        //CommentAdapter adapter=new CommentAdapter(MessageDetailActivity.this,R.layout.comment_item,commList);
//        ListView listview=(ListView)findViewById(R.id.messCommList);
//        listview.setAdapter(adapter);
//        //返回按钮点击事件
//        Button title1Back=(Button) findViewById(R.id.MessDetailBack_btn);
//        title1Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private void initMessCommInfo() {
//        Comment comm1=new Comment("走你",R.mipmap.ccc_pic);
//        commList.add(comm1);
//        Comment comm2=new Comment("就是俺",R.mipmap.bbb_pic);
//        commList.add(comm2);
//        Comment comm3=new Comment("一朵花花",R.mipmap.ccc_pic);
//        commList.add(comm3);

        String url = "http://39.107.225.80:8080//julieServer/MessCommentListServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("messId", exchange.getMessId());
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

                    CommentAdapter adapter = new CommentAdapter(MessageDetailActivity.this, R.layout.comment_item, commList);
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

    @OnClick({R.id.MessDetailBack_btn, R.id.comment_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comment_btn:

                String input = messCommentEditText.getText().toString();
                if (input.equals("")) {
                    Toast.makeText(getApplicationContext(), "还没输入哦", Toast.LENGTH_LONG).show();
                    return;
                }
                String url = "http://39.107.225.80:8080/julieServer/PubMessCommentServlet";
                RequestParams params = new RequestParams(url);
                params.addParameter("userId", UserManager.getInstance().getUser().getId());
                params.addParameter("messId", exchange.getMessId());
                params.addParameter("comment", input);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    public void onSuccess(String result) {
                        try {
                            JSONObject jb = new JSONObject(result);
                            //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                            if (jb.getInt("code") == 1) {
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                                messCommentEditText.setText("");
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


                break;
            case R.id.MessDetailBack_btn:
                finish();
                break;
        }
    }
}


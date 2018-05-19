package com.bjtu.julie.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Model.Order;
import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluateActivity extends AppCompatActivity {

    //@BindView(R.id.EvaluateRate)
    //EditText evaluateRate;
    @BindView(R.id.EvaluateContent)
    EditText evaluateContent;
    Order order;
    private RatingBar mRatingBar;
    @BindView(R.id.button_evaluate1)
    TextView button_evaluate1;
    @BindView(R.id.button_evaluate2)
    TextView button_evaluate2;
    @BindView(R.id.button_evaluate3)
    TextView button_evaluate3;
    String [] standardEvaluate;

    int a,b,c;
    int rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        a=0;
        b=0;
        c=0;
        standardEvaluate=null;
        rate=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        order = (Order) getIntent().getSerializableExtra("order");

        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(MainActivity.this, "评价了" + rating + "星", Toast.LENGTH_SHORT).show();
                rate=(int)rating;
            }
        });

        button_evaluate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a==0){
                    a=1;
                    button_evaluate1.setTextColor(Color.RED);
                }else{
                    a=0;
                    button_evaluate1.setTextColor(Color.BLACK);
                }
            }
        });
        button_evaluate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b==0){
                    b=1;
                    button_evaluate2.setTextColor(Color.RED);
                }else{
                    b=0;
                    button_evaluate2.setTextColor(Color.BLACK);
                }
            }
        });
        button_evaluate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c==0){
                    c=1;
                    button_evaluate3.setTextColor(Color.RED);
                }else{
                    c=0;
                    button_evaluate3.setTextColor(Color.BLACK);
                }
            }
        });

    }

    @OnClick({ R.id.title_btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_ok:

                if(rate==0){
                    Toast.makeText(getApplicationContext(), "不先给评个分？", Toast.LENGTH_SHORT).show();
                }else{
                    //String rate=evaluateRate.getText().toString();
                    String content = evaluateContent.getText().toString();
                    //standardEvaluate[0] = ;
                    //standardEvaluate[1] = Integer.toString(b);
                    //standardEvaluate[2] = Integer.toString(c);


                    String url = "http://39.107.225.80:8080/julieServer/PubEvaluateServlet";
                    RequestParams params = new RequestParams(url);
                    params.addParameter("userId", order.getReceiveId());
                    params.addParameter("receiveId", order.getReceiveId());
                    params.addParameter("footId", order.getFootId());
                    params.addParameter("evaluateRate", rate);
                    params.addParameter("evaluateContent", content);
                    params.addParameter("evaluate1",a);
                    params.addParameter("evaluate2",b);
                    params.addParameter("evaluate3",c);
                    //params.addParameter("standardEvaluate", standardEvaluate);
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        public void onSuccess(String result) {
                            try {
                                JSONObject jb = new JSONObject(result);
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                                finish();
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


                break;
        }
    }
}

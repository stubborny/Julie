package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Adapter.GradeAdapter;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.Model.Grade;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

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
import butterknife.Unbinder;

public class ImpressionActivity extends AppCompatActivity {

    @BindView(R.id.total_grade_text)
    TextView totalGrade;
    @BindView(R.id.impression_head)
    ImageView impression_head;
    @BindView(R.id.impression_name)
    TextView impression_name;
    @BindView(R.id.identification_text)
    TextView identification_text;
    @BindView(R.id.title_btn_ok)
            TextView title_btn_ok;
    Unbinder unbinder;

    public List<Grade> gradelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_impression);
        unbinder = ButterKnife.bind(this);
        initImpression();

        initList();

        title_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initList(){
        String url = "http://39.107.225.80:8080//julieServer/MyImpressionServlet";
        RequestParams params = new RequestParams(url);
        params.setCharset("utf-8");
        params.addParameter("userId", UserManager.getInstance().getUser().getId());
        x.http().get(params, new Callback.CommonCallback<String>() {

            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);

                    JSONArray impressionArray = jb.getJSONArray("impressionList");
                    if (impressionArray.length() > 0) {
                        for (int i = 0; i < impressionArray.length(); i++) {
                            // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            JSONObject job = impressionArray.getJSONObject(i);
                            Grade grade = new Grade(job.getInt("receiveId"),job.getString("userpicUrl"), job.getString("name"),job.getInt("rate"),job.getString("time"),job.getString("content"),job.getInt("evaluate1"),job.getInt("evaluate2"),job.getInt("evaluate3"));

                            gradelist.add(grade);
                        }
                    }
                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    //Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.ImpressionRecycleView);
                    FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(ImpressionActivity.this);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);

                    GradeAdapter adapter = new GradeAdapter(gradelist);
                    recyclerView.setAdapter(adapter);

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

    public void initImpression(){
        //int userId=UserManager.getInstance().getUser().getId();

        String url = "http://39.107.225.80:8080/julieServer/GetScoreServlet";
        RequestParams params = new RequestParams(url);
        params.setCharset("utf-8");
        params.addParameter("userId", UserManager.getInstance().getUser().getId());


        x.http().get(params, new Callback.CommonCallback<String>() {

            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    Integer score=jb.getInt("data");
                    totalGrade.setText(score.toString()+"分");
                    ImageOptions imageOptions = new ImageOptions.Builder()
                            .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.load_error)
                            .setLoadingDrawableId(R.mipmap.loading)
                            .build();
                    x.image().bind(impression_head, UserManager.getInstance().getUser().getUserpicUrl(), imageOptions);
                    impression_name.setText(UserManager.getInstance().getUser().getNickname());
                    int islegal= UserManager.getInstance().getUser().getIsLegal();
                    if(UserManager.getInstance().getUser().getIsAuthentication()==0){
                        if(islegal==0){
                            identification_text.setText("未认证(信用风险)");
                        }
                        else {
                            identification_text.setText("未认证");
                        }
                    }else{
                        if(islegal==0){
                            identification_text.setText("已认证(信用风险)");
                        }
                        else {
                            identification_text.setText("已认证");
                        }
                    }
                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                    //finish();
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

}

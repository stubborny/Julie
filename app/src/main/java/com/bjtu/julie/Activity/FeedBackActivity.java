package com.bjtu.julie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedBackActivity extends AppCompatActivity {
    @BindView(R.id.feedbackEdit)
    EditText feedbackEdit;
    @BindView(R.id.commitFeedback)
    Button commitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        ButterKnife.bind(this);

        commitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getApplicationContext(), "还没登陆哦", Toast.LENGTH_SHORT).show();
                } else if (feedbackEdit.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "可能是我们太优秀了？？", Toast.LENGTH_SHORT).show();
                    feedbackEdit.requestFocus();
                }else{
                    String url = "http://39.107.225.80:8080/julieServer/PubFeedbackServlet";
                    RequestParams params = new RequestParams(url);
                    params.setCharset("utf-8");
                    params.addParameter("userId", UserManager.getInstance().getUser().getId());
                    params.addParameter("content", feedbackEdit.getText().toString());

                    x.http().get(params, new Callback.CommonCallback<String>() {

                        public void onSuccess(String result) {
                            try {
                                JSONObject jb = new JSONObject(result);
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
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
            }
        });
    }
}

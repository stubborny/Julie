package com.bjtu.julie.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bjtu.julie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        ButterKnife.bind(this);
        titleText.setText("常见问题");
        titleBtnOk.setText("");
    }
}

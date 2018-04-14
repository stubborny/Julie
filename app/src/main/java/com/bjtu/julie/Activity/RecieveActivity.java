package com.bjtu.julie.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bjtu.julie.R;

public class RecieveActivity extends AppCompatActivity {
    @BindView(R.id.myrecieve_backward)
    Button backButton;
    @BindView(R.id.Irecieve)
    ListView Irecieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
    }

    @OnClick(R.id.myrecieve_backward)
    public void onMyrecieveBackwardClicked() {

        Intent intent = new Intent(RecieveActivity.this, UserActivity.class);
        startActivity(intent);
        finish();

    }

}

package com.bjtu.julie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bjtu.julie.Adapter.CommentAdapter;
import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.R;

import java.util.ArrayList;
import java.util.List;

public class MessageDetailActivity extends AppCompatActivity {
    private List<Comment> commList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        //初始化评论信息
        initMessCommInfo();
        CommentAdapter adapter=new CommentAdapter(MessageDetailActivity.this,R.layout.comment_item,commList);
        ListView listview=(ListView)findViewById(R.id.messCommList);
        listview.setAdapter(adapter);
        //返回按钮点击事件
        Button title1Back=(Button) findViewById(R.id.MessDetailBack_btn);
        title1Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initMessCommInfo(){
//        Comment comm1=new Comment("走你",R.mipmap.ccc_pic);
//        commList.add(comm1);
//        Comment comm2=new Comment("就是俺",R.mipmap.bbb_pic);
//        commList.add(comm2);
//        Comment comm3=new Comment("一朵花花",R.mipmap.ccc_pic);
//        commList.add(comm3);
    }
}


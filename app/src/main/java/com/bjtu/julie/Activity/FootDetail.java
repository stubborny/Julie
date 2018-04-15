package com.bjtu.julie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bjtu.julie.Adapter.MessCommentAdapter;
import com.bjtu.julie.Model.Comment;
import com.bjtu.julie.R;
import com.lhz.stateprogress.StateProgressView;

import java.util.ArrayList;
import java.util.List;

public class FootDetail extends AppCompatActivity {
    private List<Comment> commList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_detail);
        //物流节点
        final List<String> list = new ArrayList<String>();
        list.add("新发布");
        list.add("被抢啦");
        list.add("已送达");
        list.add("已结单");
        list.add("已评价");
        final StateProgressView stateProgressView01 = (StateProgressView) findViewById(R.id.spv);
        stateProgressView01.setItems(list, 4, 200);
        // //返回按钮点击事件
        Button title1Back=(Button) findViewById(R.id.FootDetailBack_btn);
        title1Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFootCommInfo();
        MessCommentAdapter adapter=new MessCommentAdapter(FootDetail.this,R.layout.comment_item,commList);
        ListView listview=(ListView)findViewById(R.id.footCommList);
        listview.setAdapter(adapter);

    }
    private void initFootCommInfo(){
        Comment comm1=new Comment("走你",R.mipmap.ccc_pic);
        commList.add(comm1);
        Comment comm2=new Comment("就是俺",R.mipmap.bbb_pic);
        commList.add(comm2);
        Comment comm3=new Comment("一朵花花",R.mipmap.ccc_pic);
        commList.add(comm3);
    }
}

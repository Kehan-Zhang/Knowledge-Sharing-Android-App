package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.RVMyFollowsAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class MyFollowsActivity extends AppCompatActivity {

    private RecyclerView my_follows;
    private Button show_my_follows, follows_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follows);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        my_follows = this.findViewById(R.id.rv_my_follows);
        final RVMyFollowsAdapter mAdapter = new RVMyFollowsAdapter(MyFollowsActivity.this);
        mAdapter.setOnItemClickListener(new RVMyFollowsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_my_follow);
                String clickQuestion = textview.getText().toString();
                Intent intent = new Intent(MyFollowsActivity.this, ShowAnswerListActivity.class);
                intent.putExtra("clickQuestion", clickQuestion);
                startActivity(intent);
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyFollowsActivity.this);
        my_follows.setLayoutManager(linearLayoutManager);
        if (my_follows.getItemDecorationCount() == 0){
            my_follows.addItemDecoration(new SpaceItemDecoration(25));
        }

        show_my_follows = this.findViewById(R.id.bt_my_follows);
        show_my_follows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_follows.setAdapter(mAdapter);
            }
        });

        follows_back = this.findViewById(R.id.bt_follows_back);
        follows_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyFollowsActivity.this, SelectFunctionActivity.class);
                startActivity(intent);
            }
        });
    }
}

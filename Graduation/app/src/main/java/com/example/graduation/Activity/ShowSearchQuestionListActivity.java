package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.RVSearchAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class  ShowSearchQuestionListActivity extends AppCompatActivity {

    private RecyclerView search_questions;
    private TextView show_tag;
    private String searchContent;
    private Button show_search,cancel_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        Intent intent=getIntent();
        searchContent =intent.getStringExtra("searchContent");
        search_questions = this.findViewById(R.id.rv_search_questions);
        show_tag = this.findViewById(R.id.tv_show_tag);
        show_tag.setText(searchContent);

        final RVSearchAdapter mAdapter = new RVSearchAdapter(ShowSearchQuestionListActivity.this,searchContent);
        mAdapter.setOnItemClickListener(new RVSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_search_question);
                String clickQuestion = textview.getText().toString();
                Intent intent = new Intent(ShowSearchQuestionListActivity.this, ShowAnswerListActivity.class);
                intent.putExtra("clickQuestion", clickQuestion);
                startActivity(intent);
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowSearchQuestionListActivity.this);
        search_questions.setLayoutManager(linearLayoutManager);
        if (search_questions.getItemDecorationCount() == 0){
            search_questions.addItemDecoration(new SpaceItemDecoration(25));
        }

        show_search = this.findViewById(R.id.bt_show_search);
        show_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_questions.setAdapter(mAdapter);
            }
        });

        cancel_search = this.findViewById(R.id.bt_cancel_search);
        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}

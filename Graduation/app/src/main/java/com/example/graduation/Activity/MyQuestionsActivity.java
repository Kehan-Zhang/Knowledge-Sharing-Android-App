package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.RVMyQuestionsAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class MyQuestionsActivity extends AppCompatActivity {
    private RecyclerView my_questions;
    private Button show_my_questions, questions_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        my_questions = this.findViewById(R.id.rv_my_questions);
        final RVMyQuestionsAdapter mAdapter = new RVMyQuestionsAdapter(MyQuestionsActivity.this);
        mAdapter.setOnItemClickListener(new RVMyQuestionsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_my_question);
                String clickQuestion = textview.getText().toString();
                Intent intent = new Intent(MyQuestionsActivity.this, ShowAnswerListActivity.class);
                intent.putExtra("clickQuestion", clickQuestion);
                startActivity(intent);
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyQuestionsActivity.this);
        my_questions.setLayoutManager(linearLayoutManager);
        if (my_questions.getItemDecorationCount() == 0){
            my_questions.addItemDecoration(new SpaceItemDecoration(25));
        }

        show_my_questions = this.findViewById(R.id.bt_my_questions);
        show_my_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_questions.setAdapter(mAdapter);
            }
        });

        questions_back = this.findViewById(R.id.bt_questions_back);
        questions_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyQuestionsActivity.this, SelectFunctionActivity.class);
                startActivity(intent);
            }
        });
    }
}

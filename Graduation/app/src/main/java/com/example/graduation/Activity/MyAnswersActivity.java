package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.RVMyAnswersAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class MyAnswersActivity extends AppCompatActivity {
    private RecyclerView my_answers;
    private Button show_my_answers, answers_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answers);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        my_answers = this.findViewById(R.id.rv_my_answers);
        final RVMyAnswersAdapter mAdapter = new RVMyAnswersAdapter(MyAnswersActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAnswersActivity.this);
        my_answers.setLayoutManager(linearLayoutManager);
        if (my_answers.getItemDecorationCount() == 0){
            my_answers.addItemDecoration(new SpaceItemDecoration(25));
        }

        show_my_answers = this.findViewById(R.id.bt_my_answers);
        show_my_answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_answers.setAdapter(mAdapter);
            }
        });

        answers_back = this.findViewById(R.id.bt_answers_back);
        answers_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAnswersActivity.this, SelectFunctionActivity.class);
                startActivity(intent);
            }
        });
    }
}

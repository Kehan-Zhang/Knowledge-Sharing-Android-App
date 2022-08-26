package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.RVAnswerAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ShowAnswerListActivity extends AppCompatActivity {
    private TextView question_title, question_author;
    private RecyclerView answers;
    private String clickQuestion;
    private Button see_answer, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");
        Intent intent=getIntent();
        clickQuestion =intent.getStringExtra("clickQuestion");

        answers = this.findViewById(R.id.rv_answers);
        question_title = this.findViewById(R.id.tv_question_title);
        question_author = this.findViewById(R.id.tv_question_author);
        question_title.setText(clickQuestion);
        question_title.setMovementMethod(new ScrollingMovementMethod());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowAnswerListActivity.this);
        answers.setLayoutManager(linearLayoutManager);
        if (answers.getItemDecorationCount() == 0){
            answers.addItemDecoration(new SpaceItemDecoration(25));
        }
        final RVAnswerAdapter rvAnswerAdapter = new RVAnswerAdapter(ShowAnswerListActivity.this, clickQuestion);
        rvAnswerAdapter.setOnItemClickListener(new RVAnswerAdapter.OnItemClickListener() {
            @Override
            public void onButtonClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_answer_content);
                String answerContent = textview.getText().toString();
                makeSupport(answerContent);
            }
        });

        BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
        bmobQuery.addWhereEqualTo("title",clickQuestion);
        bmobQuery.include("author");
        bmobQuery.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {
                if(e == null){
                    Question question=object.get(0);
                    question_author.setText(question.getAuthor().getNickName());
                    question.addClickCount();
                    question.update(question.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });
                } else {
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        see_answer = this.findViewById(R.id.bt_see_answer);
        see_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answers.setAdapter(rvAnswerAdapter);
            }
        });

        back = this.findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAnswerListActivity.this, SelectFunctionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void makeSupport(String answerContent){
        BmobQuery<Answer> bmobQuery = new BmobQuery<Answer>();
        bmobQuery.addWhereEqualTo("content", answerContent);
        bmobQuery.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> object, BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    Answer answer = object.get(0);
                    BmobRelation relation = new BmobRelation();
                    relation.add(user);
                    answer.setSupporter(relation);
                    answer.addClickCount();
                    answer.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {

                            } else {
                                Toast.makeText(ShowAnswerListActivity.this, "赞同失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AnswerQuestionActivity extends AppCompatActivity {

    private String clickQuestion, answer_content;
    private TextView click_question;
    private EditText input_answer;
    private Button cancel_answer, post_answer;
    private String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        Intent intent=getIntent();
        clickQuestion =intent.getStringExtra("clickQuestion");
        click_question = this.findViewById(R.id.tv_click_question);
        click_question.setText(clickQuestion);
        input_answer = this.findViewById(R.id.et_input_answer);

        post_answer = this.findViewById(R.id.bt_post_answer);
        post_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer_content = input_answer.getText().toString();
                if (answer_content.equals("")){
                    Toast.makeText(AnswerQuestionActivity.this,"回答不能为空",Toast.LENGTH_LONG).show();
                } else {
                    BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
                    bmobQuery.addWhereEqualTo("title", clickQuestion);
                    bmobQuery.findObjects(new FindListener<Question>() {
                        @Override
                        public void done(List<Question> object, BmobException e) {
                            if (e == null) {
                                Question question = object.get(0);
                                questionId = question.getObjectId();
                                question.setObjectId(questionId);
                                saveAnswer(question);
                            } else {
                                Log.e("BMOB", e.toString());
                            }
                        }
                    });
                    finish();
                }
            }
        });

        cancel_answer = this.findViewById(R.id.bt_cancel_answer);
        cancel_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveAnswer(Question question) {
        if (BmobUser.isLogin()){
            Answer answer = new Answer();
            answer.setContent(answer_content);
            answer.setAuthor(BmobUser.getCurrentUser(User.class));
            answer.setQuestion(question);
            answer.setSupportCount(0);
            answer.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(AnswerQuestionActivity.this,"回答发布成功",Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText(AnswerQuestionActivity.this,"回答发布失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(AnswerQuestionActivity.this,"请先登录",Toast.LENGTH_LONG).show();
        }
    }

}

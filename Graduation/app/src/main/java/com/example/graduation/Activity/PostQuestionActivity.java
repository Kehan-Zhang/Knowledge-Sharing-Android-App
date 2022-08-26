package com.example.graduation.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;
import com.example.graduation.Util.StringSimilarityUtil;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class PostQuestionActivity extends AppCompatActivity {

    private EditText input_question;
    private AlertDialog select_tag;
    private Button cancel_question, post_question, add_tag;
    private String question_content, tag_one, tag_two, tag_three;
    private int countTag = 0, number = 0;
    final String[] tags={"","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        input_question = findViewById(R.id.et_input_question);
        input_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_question.setText("");
            }
        });

        post_question = this.findViewById(R.id.bt_post_question);
        post_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question_content = input_question.getText().toString();
                if (question_content.equals("")){
                    Toast.makeText(PostQuestionActivity.this,"问题不能为空",Toast.LENGTH_LONG).show();
                } else if (countTag < 1 || countTag >3) {
                    Toast.makeText(PostQuestionActivity.this,"请选择话题标签",Toast.LENGTH_LONG).show();
                } else {
                    BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
                    bmobQuery.addQueryKeys("title");
                    bmobQuery.findObjects(new FindListener<Question>() {
                        @Override
                        public void done(List<Question> object, BmobException e) {
                            if(e == null){
                                boolean flag = false;
                                for(int i = object.size()-1; i >= 0; i --)
                                {
                                    Question question=object.get(i);
                                    if (StringSimilarityUtil.getSimilarityRatio(question_content,question.getTitle()) > 0.7) {
                                        flag = true;
                                    }
                                }
                                if ( flag ) {
                                    Toast.makeText(PostQuestionActivity.this,"问题重复",Toast.LENGTH_LONG).show();
                                } else {
                                    tag_one = tags[0];
                                    tag_two = tags[1];
                                    tag_three = tags[2];
                                    saveQuestion();
                                    finish();
                                }
                            }
                        }
                    });
                }
            }
        });

        cancel_question = this.findViewById(R.id.bt_cancel_question);
        cancel_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_tag = this.findViewById(R.id.bt_add_tag);
        add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultipleAlertDialog(view);
            }
        });
    }

    private void saveQuestion() {
        if (BmobUser.isLogin()){
            Question question = new Question();
            question.setTitle(question_content);
            question.setAuthor(BmobUser.getCurrentUser(User.class));
            question.setClickCount(0);
            question.addAll("tags", Arrays.asList(tag_one, tag_two, tag_three, ""));
            question.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(PostQuestionActivity.this,"问题发布成功",Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText(PostQuestionActivity.this,"问题发布失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(PostQuestionActivity.this,"请先登录",Toast.LENGTH_LONG).show();
        }
    }

    public void showMultipleAlertDialog(View view){
        final String[] items = {"生活", "娱乐", "学习", "体育", "科技",
                                "政治", "历史", "军事", "工作", "地理",
                                "国家", "专业", "计算机", "健康", "搞笑",
                                "情感", "游戏", "电影", "电视剧", "综艺",
                                "歌曲", "常识", "行为", "留学", "异类"};
        final boolean selected[] = {false, false, false, false, false,
                                    false, false, false, false, false,
                                    false, false, false, false, false,
                                    false, false, false, false, false,
                                    false, false, false, false, false};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this).setTitle("话题标签")
                .setPositiveButton("确定", null)
                .setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    selected[i] = true;
                    countTag++;
                }else {
                    selected[i] = false;
                    countTag--;
                }
            }
        });
        select_tag = alertBuilder.create();
        select_tag.show();
        select_tag.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countTag >= 1 && countTag <=3) {
                    for(int position = 0; position < items.length; position++){
                        if(selected[position] == true){
                            tags[number++] = items[position];
                        }
                    }
                    select_tag.dismiss();
                } else {
                    Toast.makeText(PostQuestionActivity.this,"请选择适量话题标签",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

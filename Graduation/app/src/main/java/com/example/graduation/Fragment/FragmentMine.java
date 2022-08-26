package com.example.graduation.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.graduation.Activity.LoginActivity;
import com.example.graduation.Activity.MyAnswersActivity;
import com.example.graduation.Activity.MyFollowsActivity;
import com.example.graduation.Activity.MyQuestionsActivity;
import com.example.graduation.Activity.MySupportsActivity;
import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.UpdateListener;

public class FragmentMine extends Fragment {

    private TextView my_nickName, my_vip, follow_number, support_number, question_number, answer_number;
    private Button view_follows, view_supports, view_questions, view_answers, logout;
    private ImageButton change_nickName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mine,container,false);
        Bmob.initialize(getActivity(), "bca7c117369ec7149a5a2fef8e2641a8");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        my_nickName = getActivity().findViewById(R.id.tv_my_nickName);
        my_vip = getActivity().findViewById(R.id.tv_my_vip);
        follow_number = getActivity().findViewById(R.id.tv_follow_number);
        support_number = getActivity().findViewById(R.id.tv_support_number);
        question_number = getActivity().findViewById(R.id.tv_question_number);
        answer_number = getActivity().findViewById(R.id.tv_answer_number);

        User user = BmobUser.getCurrentUser(User.class);
        my_nickName.setText(user.getNickName());
        if (user.getIsVip()){
            my_vip.setText("黄金会员");
        } else {
            my_vip.setText("普通用户");
        }
        BmobQuery<Question> followQuery = new BmobQuery<Question>();
        followQuery.addWhereEqualTo("followers",BmobUser.getCurrentUser(User.class) );
        followQuery.count(Question.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    follow_number.setText(count.toString());
                }else{

                }
            }
        });
        BmobQuery<Answer> supportQuery = new BmobQuery<Answer>();
        supportQuery.addWhereEqualTo("supporter",BmobUser.getCurrentUser(User.class) );
        supportQuery.count(Answer.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    support_number.setText(count.toString());
                }else{

                }
            }
        });
        BmobQuery<Question> questionQuery = new BmobQuery<Question>();
        questionQuery.addWhereEqualTo("author",BmobUser.getCurrentUser(User.class) );
        questionQuery.count(Question.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    question_number.setText(count.toString());
                }else{

                }
            }
        });
        BmobQuery<Answer> answerQuery = new BmobQuery<Answer>();
        answerQuery.addWhereEqualTo("author",BmobUser.getCurrentUser(User.class) );
        answerQuery.count(Answer.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    answer_number.setText(count.toString());
                }else{

                }
            }
        });

        change_nickName = getActivity().findViewById(R.id.bt_change_nickName);
        change_nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText=new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入昵称").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(getActivity(),"昵称不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            my_nickName.setText(editText.getText().toString());
                            BmobUser user = BmobUser.getCurrentUser(User.class);
                            ((User) user).setNickName(editText.getText().toString());
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        view_follows = getActivity().findViewById(R.id.bt_view_follows);
        view_follows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyFollowsActivity.class);
                startActivity(intent);
            }
        });

        view_supports = getActivity().findViewById(R.id.bt_view_supports);
        view_supports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MySupportsActivity.class);
                startActivity(intent);
            }
        });

        view_questions = getActivity().findViewById(R.id.bt_view_questions);
        view_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyQuestionsActivity.class);
                startActivity(intent);
            }
        });

        view_answers = getActivity().findViewById(R.id.bt_view_answers);
        view_answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyAnswersActivity.class);
                startActivity(intent);
            }
        });

        logout = getActivity().findViewById(R.id.bt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

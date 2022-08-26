package com.example.graduation.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Activity.AnswerQuestionActivity;
import com.example.graduation.Activity.ShowAnswerListActivity;
import com.example.graduation.Adapter.RVQuestionAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class FragmentRecommendation extends Fragment {

    private RecyclerView recommend_questions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommendation,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recommend_questions = getActivity().findViewById(R.id.rv_recommend_questions);
        RVQuestionAdapter mAdapter = new RVQuestionAdapter(getActivity());
        mAdapter.setOnItemClickListener(new RVQuestionAdapter.OnItemClickListener() {
            @Override
            public void onButtonClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_question_content);
                String clickQuestion = textview.getText().toString();
                Intent intent = new Intent(getActivity(), AnswerQuestionActivity.class);
                intent.putExtra("clickQuestion", clickQuestion);
                startActivity(intent);
            }

            public void onItemClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_question_content);
                String clickQuestion = textview.getText().toString();
                Intent intent = new Intent(getActivity(), ShowAnswerListActivity.class);
                intent.putExtra("clickQuestion", clickQuestion);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_question_content);
                String clickQuestion = textview.getText().toString();
                makeFollowers(clickQuestion);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recommend_questions.setLayoutManager(linearLayoutManager);
        if (recommend_questions.getItemDecorationCount() == 0){
            recommend_questions.addItemDecoration(new SpaceItemDecoration(25));
        }
        recommend_questions.setAdapter(mAdapter);
    }

    public void makeFollowers(String clickQuestion){
        BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
        bmobQuery.addWhereEqualTo("title", clickQuestion);
        bmobQuery.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    Question question = object.get(0);
                    BmobRelation relation = new BmobRelation();
                    relation.add(user);
                    question.setFollowers(relation);
                    question.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "收藏失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

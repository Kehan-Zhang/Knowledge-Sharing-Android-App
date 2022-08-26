package com.example.graduation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVMyAnswersAdapter extends RecyclerView.Adapter<RVMyAnswersAdapter.MyTVMyAnswersHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData, mTitle;

    public RVMyAnswersAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();
        mTitle = new ArrayList<>();

        BmobQuery<Answer> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
        bmobQuery.include("question");
        bmobQuery.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> object, BmobException e) {
                if(e == null){
                    for(int i = object.size()-1; i >= 0; i --)
                    {
                        Answer answer=object.get(i);
                        mData.add(answer.getContent());
                        mTitle.add(answer.getQuestion().getTitle());
                    }
                } else {
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public RVMyAnswersAdapter.MyTVMyAnswersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVMyAnswersAdapter.MyTVMyAnswersHolder(mLayoutInflater.inflate(R.layout.rv_my_answers_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVMyAnswersAdapter.MyTVMyAnswersHolder holder, int pos) {
        holder.my_answer.setText(mData.get(pos));
        holder.my_answer_question.setText(mTitle.get(pos));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyTVMyAnswersHolder extends RecyclerView.ViewHolder {
        TextView my_answer, my_answer_question;
        MyTVMyAnswersHolder(View itemView) {
            super(itemView);
            my_answer = itemView.findViewById(R.id.tv_my_answer);
            my_answer_question = itemView.findViewById(R.id.tv_my_answer_question);
        }
    }
}

package com.example.graduation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVMySupportsAdapter extends RecyclerView.Adapter<RVMySupportsAdapter.MyTVMySupportsHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData, mTitle;

    public RVMySupportsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();
        mTitle = new ArrayList<>();

        BmobQuery<Answer> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("supporter", BmobUser.getCurrentUser(User.class));
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
    public RVMySupportsAdapter.MyTVMySupportsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVMySupportsAdapter.MyTVMySupportsHolder(mLayoutInflater.inflate(R.layout.rv_my_supports_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVMySupportsAdapter.MyTVMySupportsHolder holder, int pos) {
        holder.my_support.setText(mData.get(pos));
        holder.my_support_question.setText(mTitle.get(pos));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyTVMySupportsHolder extends RecyclerView.ViewHolder {
        TextView my_support, my_support_question;
        MyTVMySupportsHolder(View itemView) {
            super(itemView);
            my_support = itemView.findViewById(R.id.tv_my_support);
            my_support_question = itemView.findViewById(R.id.tv_my_support_question);
        }
    }
}

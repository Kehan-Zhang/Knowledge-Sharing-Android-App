package com.example.graduation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVMyQuestionsAdapter extends RecyclerView.Adapter<RVMyQuestionsAdapter.MyTVMyQuestionsHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData;
    private RVMyQuestionsAdapter.OnItemClickListener mOnItemClickListener;

    public RVMyQuestionsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();

        BmobQuery<Question> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
        bmobQuery.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {
                if(e == null){
                    for(int i = object.size()-1; i >= 0; i --)
                    {
                        Question question=object.get(i);
                        mData.add(question.getTitle());
                    }
                } else {
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public RVMyQuestionsAdapter.MyTVMyQuestionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVMyQuestionsAdapter.MyTVMyQuestionsHolder(mLayoutInflater.inflate(R.layout.rv_my_questions_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVMyQuestionsAdapter.MyTVMyQuestionsHolder holder, int pos) {
        holder.my_question.setText(mData.get(pos));
        if (mOnItemClickListener != null) {
            holder.my_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyTVMyQuestionsHolder extends RecyclerView.ViewHolder {
        TextView my_question;
        MyTVMyQuestionsHolder(View itemView) {
            super(itemView);
            my_question = itemView.findViewById(R.id.tv_my_question);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(RVMyQuestionsAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

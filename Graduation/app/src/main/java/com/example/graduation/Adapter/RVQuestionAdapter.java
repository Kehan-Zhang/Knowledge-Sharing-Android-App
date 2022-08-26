package com.example.graduation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVQuestionAdapter extends RecyclerView.Adapter<RVQuestionAdapter.MyTVHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData;
    private final ArrayList<String> mTags;
    private RVQuestionAdapter.OnItemClickListener mOnItemClickListener;

    public RVQuestionAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();

        User user = BmobUser.getCurrentUser(User.class);
        mTags = user.getTags();

        for (int i = 0; i < mTags.size(); i ++){
            String [] tags = {"", mTags.get(i)};
            BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
            bmobQuery.addWhereContainsAll("tags", Arrays.asList(tags));
            bmobQuery.addQueryKeys("title");
            bmobQuery.findObjects(new FindListener<Question>() {
                @Override
                public void done(List<Question> object, BmobException e) {
                    if(e == null){
                        for(int i = object.size()-1; i >= 0; i --)
                        {
                            Question question=object.get(i);
                            boolean flag = mData.contains(question.getTitle());
                            if(!flag) {
                                mData.add(question.getTitle());
                            }
                        }
                    } else {
                        Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }

    }

    @Override
    public RVQuestionAdapter.MyTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVQuestionAdapter.MyTVHolder(mLayoutInflater.inflate(R.layout.rv_question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVQuestionAdapter.MyTVHolder holder, int pos) {
        holder.question_content.setText(mData.get(pos));
        if (mOnItemClickListener != null) {
            holder.write_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onButtonClick(holder.itemView, pos);
                }
            });
            holder.question_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.question_content, pos);
                }
            });
            holder.question_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.question_content, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyTVHolder extends RecyclerView.ViewHolder {
        TextView question_content;
        Button write_answer;
        MyTVHolder(View itemView) {
            super(itemView);
            question_content = itemView.findViewById(R.id.tv_question_content);
            write_answer = itemView.findViewById(R.id.bt_write_answer);
        }
    }

    public interface OnItemClickListener {
        void onButtonClick(View view, int position);
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(RVQuestionAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}


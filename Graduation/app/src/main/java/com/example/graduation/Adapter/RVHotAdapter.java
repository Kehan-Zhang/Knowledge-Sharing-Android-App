package com.example.graduation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Entity.Question;
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVHotAdapter extends RecyclerView.Adapter<RVHotAdapter.MyTVHotHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData;
    private RVHotAdapter.OnItemClickListener mOnItemClickListener;

    public RVHotAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();

        BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
        bmobQuery.addWhereGreaterThan("clickCount", 5);
        bmobQuery.order("clickCount");
        bmobQuery.addQueryKeys("title");
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
    public RVHotAdapter.MyTVHotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVHotAdapter.MyTVHotHolder(mLayoutInflater.inflate(R.layout.rv_hot_question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVHotAdapter.MyTVHotHolder holder, int pos) {
        holder.hot_question.setText(mData.get(pos));
        if (mOnItemClickListener != null) {
            holder.hot_question.setOnClickListener(new View.OnClickListener() {
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

    public class MyTVHotHolder extends RecyclerView.ViewHolder {
        TextView hot_question;

        MyTVHotHolder(View itemView) {
            super(itemView);
            hot_question = itemView.findViewById(R.id.tv_hot_question);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(RVHotAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

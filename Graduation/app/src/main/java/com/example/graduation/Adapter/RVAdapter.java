package com.example.graduation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Entity.Question;
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyTVHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData;
    private RVAdapter.OnItemClickListener mOnItemClickListener;

    public RVAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();

        BmobQuery<Question> bmobQuery = new BmobQuery<Question>();
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
    public RVAdapter.MyTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapter.MyTVHolder(mLayoutInflater.inflate(R.layout.rv_question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVAdapter.MyTVHolder holder, int pos) {
        holder.question_content.setText(mData.get(pos));
        holder.write_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
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
            question_content = (TextView) itemView.findViewById(R.id.tv_question_content);
            write_answer = itemView.findViewById(R.id.bt_write_answer);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(RVAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}


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
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVSearchAdapter extends RecyclerView.Adapter<RVSearchAdapter.MyTVSearchHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData;
    private RVSearchAdapter.OnItemClickListener mOnItemClickListener;

    public RVSearchAdapter(Context context, String searchQuestion) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();

        BmobQuery<Question> bmobQuery = new BmobQuery<>();
        String fill = "";
        String [] tag = {searchQuestion,fill};
        bmobQuery.addWhereContainsAll("tags", Arrays.asList(tag));
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
    public RVSearchAdapter.MyTVSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVSearchAdapter.MyTVSearchHolder(mLayoutInflater.inflate(R.layout.rv_search_question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVSearchAdapter.MyTVSearchHolder holder, int pos) {
        holder.search_question.setText(mData.get(pos));
        if (mOnItemClickListener != null) {
            holder.search_question.setOnClickListener(new View.OnClickListener() {
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

    public class MyTVSearchHolder extends RecyclerView.ViewHolder {
        TextView search_question;
        MyTVSearchHolder(View itemView) {
            super(itemView);
            search_question = itemView.findViewById(R.id.tv_search_question);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(RVSearchAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

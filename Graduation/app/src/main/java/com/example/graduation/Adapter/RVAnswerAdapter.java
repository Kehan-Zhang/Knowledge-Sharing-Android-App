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

import com.example.graduation.Activity.PostQuestionActivity;
import com.example.graduation.Entity.Answer;
import com.example.graduation.Entity.Question;
import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RVAnswerAdapter extends RecyclerView.Adapter<RVAnswerAdapter.MyTVAnswerHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ArrayList<String> mData;
    private RVAnswerAdapter.OnItemClickListener mOnItemClickListener;

    public RVAnswerAdapter(Context context, String clickQuestion) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();

        BmobQuery<Answer> bmobQuery = new BmobQuery<Answer>();
        BmobQuery<Question> innerQuery = new BmobQuery<>();
        innerQuery.addWhereEqualTo("title", clickQuestion);
        bmobQuery.addWhereMatchesQuery("question","Question",innerQuery);
        bmobQuery.order("supportCount");
        bmobQuery.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> object, BmobException e) {
                if(e == null){
                    for(int i = object.size()-1; i >= 0; i --)
                    {
                        Answer answer=object.get(i);
                        mData.add(answer.getContent());
                    }
                } else {
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public RVAnswerAdapter.MyTVAnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAnswerAdapter.MyTVAnswerHolder(mLayoutInflater.inflate(R.layout.rv_answer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVAnswerAdapter.MyTVAnswerHolder holder, int pos) {
        holder.answer_content.setText(mData.get(pos));
        if (mOnItemClickListener != null) {

            BmobQuery<User> bmobQuery = new BmobQuery<User>();
            Answer answer = new Answer();
            answer.setObjectId("b5c819d198");
            bmobQuery.addWhereRelatedTo("supporter", new BmobPointer(answer));
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object,BmobException e) {
                    if(e ==  null){
                        for (int i = 0; i < object.size(); i++){
                            if (object.get(i) == BmobUser.getCurrentUser(User.class)) {
                                holder.support.setEnabled(false);
                                holder.support.setText("已赞同");
                                break;
                            }
                        }
                    }else{

                    }
                }
            });

            holder.support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onButtonClick(holder.itemView, pos);
                    holder.support.setEnabled(false);
                    holder.support.setText("已赞同");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyTVAnswerHolder extends RecyclerView.ViewHolder {
        TextView answer_content;
        Button support;
        MyTVAnswerHolder(View itemView) {
            super(itemView);
            answer_content = itemView.findViewById(R.id.tv_answer_content);
            support = itemView.findViewById(R.id.bt_support);
        }
    }

    public interface OnItemClickListener {
        void onButtonClick(View view, int position);
    }

    public void setOnItemClickListener(RVAnswerAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}


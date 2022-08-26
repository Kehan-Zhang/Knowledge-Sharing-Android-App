package com.example.graduation.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.graduation.Activity.AnswerQuestionActivity;
import com.example.graduation.Activity.PostQuestionActivity;
import com.example.graduation.Activity.ShowAnswerListActivity;
import com.example.graduation.Activity.ShowSearchQuestionListActivity;
import com.example.graduation.Adapter.RVHotAdapter;
import com.example.graduation.Adapter.RVQuestionAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.R;

public class  FragmentSearch extends Fragment {
    private Button compile_question;
    private SearchView search_questions;
    private RecyclerView hot_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        compile_question = this.getActivity().findViewById(R.id.bt_compile_question);
        compile_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostQuestionActivity.class);
                startActivity(intent);
            }
        });

        search_questions = this.getActivity().findViewById(R.id.sv_search_question);
        search_questions.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getActivity(), ShowSearchQuestionListActivity.class);
                intent.putExtra("searchContent",s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        hot_search = this.getActivity().findViewById(R.id.rv_hot_search);
        RVHotAdapter mAdapter = new RVHotAdapter(getActivity());
        mAdapter.setOnItemClickListener(new RVHotAdapter.OnItemClickListener() {

            public void onItemClick(View view, int position) {
                TextView textview = view.findViewById(R.id.tv_hot_question);
                String clickQuestion = textview.getText().toString();
                Intent intent = new Intent(getActivity(), ShowAnswerListActivity.class);
                intent.putExtra("clickQuestion", clickQuestion);
                startActivity(intent);
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        hot_search.setLayoutManager(staggeredGridLayoutManager);
        if (hot_search.getItemDecorationCount() == 0){
            hot_search.addItemDecoration(new SpaceItemDecoration(25));
        }
        hot_search.setAdapter(mAdapter);
    }
}


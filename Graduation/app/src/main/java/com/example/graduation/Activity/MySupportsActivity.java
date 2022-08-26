package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Adapter.RVMySupportsAdapter;
import com.example.graduation.Adapter.SpaceItemDecoration;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class MySupportsActivity extends AppCompatActivity {
    private RecyclerView my_supports;
    private Button show_my_supports, supports_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_supports);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        my_supports = this.findViewById(R.id.rv_my_supports);
        final RVMySupportsAdapter mAdapter = new RVMySupportsAdapter(MySupportsActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MySupportsActivity.this);
        my_supports.setLayoutManager(linearLayoutManager);
        if (my_supports.getItemDecorationCount() == 0){
            my_supports.addItemDecoration(new SpaceItemDecoration(25));
        }

        show_my_supports = this.findViewById(R.id.bt_my_supports);
        show_my_supports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_supports.setAdapter(mAdapter);
            }
        });

        supports_back = this.findViewById(R.id.bt_supports_back);
        supports_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySupportsActivity.this, SelectFunctionActivity.class);
                startActivity(intent);
            }
        });
    }
}

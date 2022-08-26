package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.Entity.User;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SetPersonalTagsActivity extends AppCompatActivity {
    private Button next_step;
    private CheckBox life, normal, study, country, technology, military, entertainment, politic, funny, computer;
    private boolean[] checkedArray = new boolean[] {false, false, false, false, false, false, false, false, false, false};
    private String[] tags = new String[] {"生活","常识","学习","国家","科技","军事","娱乐","政治","搞笑","计算机"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_personal_tags);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        life = this.findViewById(R.id.cb_life);
        normal = this.findViewById(R.id.cb_normal);
        study = this.findViewById(R.id.cb_study);
        country = this.findViewById(R.id.cb_country);
        technology = this.findViewById(R.id.cb_technology);
        military = this.findViewById(R.id.cb_military);
        entertainment = this.findViewById(R.id.cb_entertainment);
        politic = this.findViewById(R.id.cb_politic);
        funny = this.findViewById(R.id.cb_funny);
        computer = this.findViewById(R.id.cb_country);

        life.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[0] = b;
            }
        });

        normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[1] = b;
            }
        });

        study.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[2] = b;
            }
        });

        country.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[3] = b;
            }
        });

        technology.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[4] = b;
            }
        });

        military.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[5] = b;
            }
        });

        entertainment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[6] = b;
            }
        });

        politic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[7] = b;
            }
        });

        funny.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[8] = b;
            }
        });

        computer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedArray[9] = b;
            }
        });

        next_step = this.findViewById(R.id.bt_next_step);
        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 10; i ++){
                    if (checkedArray[i]){
                        User user  = BmobUser.getCurrentUser(User.class);
                        user.add("tags",tags[i]);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                    }
                }
                BmobUser.logOut();
                Intent intent = new Intent(SetPersonalTagsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

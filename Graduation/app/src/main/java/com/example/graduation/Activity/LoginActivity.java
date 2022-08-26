package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.Entity.User;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity {

    private EditText input_username, input_password;
    private Button register, login;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        input_username = this.findViewById(R.id.et_account);
        input_password = this.findViewById(R.id.et_password);

        register = this.findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                startActivity(intent);
            }
        });

        login = this.findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = input_username.getText().toString();
                password = input_password.getText().toString();

                BmobUser.loginByAccount(username, password, new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Intent intent = new Intent(LoginActivity.this, SelectFunctionActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

package com.example.graduation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.Entity.User;
import com.example.graduation.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.UpdateListener;

public class SetPasswordActivity extends AppCompatActivity {

    private EditText input_password, confirm_password;
    private Button register_confirm;
    private TextView phone_number;
    private String password, passwordConfirm, phoneNumber;
    private int userCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        Intent intent=getIntent();
        phoneNumber =intent.getStringExtra("phoneNumber");
        phone_number = this.findViewById(R.id.tv_phone_number);
        phone_number.setText(phoneNumber);
        input_password = this.findViewById(R.id.et_input_password);
        confirm_password = this.findViewById(R.id.et_comfirm_password);

        register_confirm = this.findViewById(R.id.bt_register_confirm);
        register_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = input_password.getText().toString();
                passwordConfirm = confirm_password.getText().toString();
                if (password.equals("")) {
                    Toast.makeText(SetPasswordActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordConfirm)) {
                    Toast.makeText(SetPasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    BmobQuery<User> bmobQuery = new BmobQuery<User>();
                    bmobQuery.count(User.class, new CountListener() {
                        @Override
                        public void done(Integer count, BmobException e) {
                            if(e==null){
                                userCount = count;
                                User user = BmobUser.getCurrentUser(User.class);
                                user.setPassword(password);
                                user.setIsVip(false);
                                user.setNickName("用户1000"+userCount);
                                user.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(SetPasswordActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SetPasswordActivity.this, SetPersonalTagsActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(SetPasswordActivity.this, "密码设置失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(SetPasswordActivity.this, "用户个数查询失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

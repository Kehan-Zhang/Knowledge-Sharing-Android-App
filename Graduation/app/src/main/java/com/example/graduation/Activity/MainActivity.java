package com.example.graduation.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.R;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"bca7c117369ec7149a5a2fef8e2641a8");

        //插入数据
//        Person person = new Person();
//        person.setName("name");
//        person.setAddress("address");
//        person.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        //查询数据
//        BmobQuery<Person> query = new BmobQuery<>();
//        query.getObject("12aa2743f0", new QueryListener<Person>() {
//            @Override
//            public void done(Person person, BmobException e) {
//                if(e==null){
//                    show(person.toString());
//                }else{
//                    show(e.getMessage());
//                }
//            }
//        });

        //更新数据
//        Person person = new Person();
//        person.setName("denny");
//        person.update("12aa2743f0", new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    show("update success");
//                }else{
//                    show("update fail" + e.getMessage());
//                }
//            }
//        });

        //删除数据
//          Person person = new Person();
//          person.setObjectId("12aa2743f0");
//          person.delete(new UpdateListener() {
//              @Override
//              public void done(BmobException e) {
//                  if(e==null){
//                      show("delete success");
//                  }else{
//                      show("delete fail" + e.getMessage());
//                  }
//              }
//          });
    }

    private void show(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}

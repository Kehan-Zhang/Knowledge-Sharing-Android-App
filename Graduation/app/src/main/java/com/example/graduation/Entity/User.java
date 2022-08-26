package com.example.graduation.Entity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String nickName;
    private Boolean isVip;
    private ArrayList<String> tags;

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getIsVip(){
        return isVip;
    }

    public void setIsVip(Boolean isVip){
        this.isVip = isVip;
    }
}

package com.example.graduation.Entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Question extends BmobObject {
    private String title;
    private User author;
    private BmobRelation followers;
    private int clickCount;
    private List<String> tags;

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void addClickCount(){
        ++this.clickCount;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Question setAuthor(User author) {
        this.author = author;
        return this;
    }

    public BmobRelation getFollowers() {
        return followers;
    }

    public Question setFollowers(BmobRelation forks) {
        this.followers = forks;
        return this;
    }
}

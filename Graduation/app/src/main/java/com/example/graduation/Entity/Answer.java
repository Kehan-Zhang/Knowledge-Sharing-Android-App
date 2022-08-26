package com.example.graduation.Entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Answer extends BmobObject {
    private Question question;
    private String content;
    private User author;
    private int supportCount;
    private BmobRelation supporter;

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    public void addClickCount(){
        ++this.supportCount;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer setQuestion(Question question) {
        this.question = question;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Answer setContent(String content) {
        this.content = content;
        return this;
    }

    public Answer setAuthor(User author) {
        this.author = author;
        return this;
    }

    public BmobRelation getSupporter() {
        return supporter;
    }

    public Answer setSupporter(BmobRelation supporter) {
        this.supporter = supporter;
        return this;
    }

}


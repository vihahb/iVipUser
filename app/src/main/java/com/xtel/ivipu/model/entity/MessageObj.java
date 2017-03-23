package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 3/15/2017.
 */

public class MessageObj {

    @Expose
    private String title;
    @Expose
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    @Override
//    public String toString() {
//        return "MessageObj{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}

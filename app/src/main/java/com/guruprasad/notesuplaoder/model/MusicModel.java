package com.guruprasad.notesuplaoder.model;

public class MusicModel {
    String name , url , userid ;

    public MusicModel(String name, String url, String userid) {
        this.name = name;
        this.url = url;
        this.userid = userid;
    }

    public MusicModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

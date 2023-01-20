package com.guruprasad.notesuplaoder.model;

public class Request_model {

    String name , email , author , category , uid , key;

    public Request_model(String name, String email, String author, String category, String uid, String key) {
        this.name = name;
        this.email = email;
        this.author = author;
        this.category = category;
        this.uid = uid;
        this.key = key;
    }

    public Request_model() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

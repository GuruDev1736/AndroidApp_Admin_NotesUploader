package com.guruprasad.notesuplaoder.model;

public class upload_course_model {

    String course_name , author_name , video_id , language ;

    public upload_course_model(String course_name, String author_name, String video_id, String language) {
        this.course_name = course_name;
        this.author_name = author_name;
        this.video_id = video_id;
        this.language = language;
    }

    public upload_course_model() {
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

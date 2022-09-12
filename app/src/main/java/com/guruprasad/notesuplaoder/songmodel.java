package com.guruprasad.notesuplaoder;

public class songmodel {

    String song_name , song_url ;

    public songmodel() {
    }

    public songmodel(String song_name, String song_url) {
        this.song_name = song_name;
        this.song_url = song_url;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }
}

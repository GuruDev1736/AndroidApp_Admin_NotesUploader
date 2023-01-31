package com.guruprasad.notesuplaoder.model;

public class upload_stream_model {

    String stream_name , video_id , category ;

    public upload_stream_model() {
    }

    public upload_stream_model(String stream_name, String video_id, String category) {
        this.stream_name = stream_name;
        this.video_id = video_id;
        this.category = category;
    }

    public String getStream_name() {
        return stream_name;
    }

    public void setStream_name(String stream_name) {
        this.stream_name = stream_name;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

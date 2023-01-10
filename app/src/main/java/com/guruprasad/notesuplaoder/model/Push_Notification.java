package com.guruprasad.notesuplaoder.model;

public class Push_Notification {

    private Notification_Data data ;
    private  String to ;

    public Push_Notification(Notification_Data data, String to) {
        this.data = data;
        this.to = to;
    }

    public Notification_Data getData() {
        return data;
    }

    public void setData(Notification_Data data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

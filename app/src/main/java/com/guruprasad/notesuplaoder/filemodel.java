package com.guruprasad.notesuplaoder;

public class filemodel {
    String filetitle , fileurl ;

    public filemodel() {
    }

    public filemodel(String filetitle, String fileurl) {
        this.filetitle = filetitle;
        this.fileurl = fileurl;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}

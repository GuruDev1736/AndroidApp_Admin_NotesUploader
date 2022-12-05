package com.guruprasad.notesuplaoder;

public class file_model {
        String file_title ,file_url, userd_id ;

    public file_model() {
    }

    public file_model(String file_title, String file_url, String userd_id) {
        this.file_title = file_title;
        this.file_url = file_url;
        this.userd_id = userd_id;
    }

    public String getFile_title() {
        return file_title;
    }

    public void setFile_title(String file_title) {
        this.file_title = file_title;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getUserd_id() {
        return userd_id;
    }

    public void setUserd_id(String userd_id) {
        this.userd_id = userd_id;
    }
}

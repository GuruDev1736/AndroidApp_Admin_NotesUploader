package com.guruprasad.notesuplaoder.API;

import static com.guruprasad.notesuplaoder.Constants.CONTENT_TYPE;
import static com.guruprasad.notesuplaoder.Constants.SERVER_KEY;

import com.guruprasad.notesuplaoder.model.Push_Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API_Interface {


    @Headers({"Authorization:"+SERVER_KEY, "Content-Type:"+CONTENT_TYPE})
    @POST("fcm/send")
    Call<Push_Notification> send_notification(@Body Push_Notification notification);
}

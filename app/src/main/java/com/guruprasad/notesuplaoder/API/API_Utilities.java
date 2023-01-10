package com.guruprasad.notesuplaoder.API;

import static com.guruprasad.notesuplaoder.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_Utilities {

    private static Retrofit retrofit = null ;

    public  static API_Interface getClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build() ;

        }
        return retrofit.create(API_Interface.class);
    }


}

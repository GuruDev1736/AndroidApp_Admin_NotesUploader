package com.guruprasad.notesuplaoder.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.guruprasad.notesuplaoder.R;

public class Custom_progrsss_bar {

    private Activity activity ;
    private AlertDialog alertDialog ;

    public Custom_progrsss_bar(Activity my_activity)
    {
        activity=my_activity;
    }

    public void start_progress()
    {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        alert.setView(inflater.inflate(R.layout.custom_progress_bar, null));
        alert.setCancelable(false);

        alertDialog = alert.create();
        alertDialog.show();
    }

    public void dismiss_progress()
    {
        alertDialog.dismiss();
    }


}

package com.guruprasad.notesuplaoder;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public final class Constants {


    public static void error_toast(Context context ,String message)
    {
        Toasty.error(context, message , Toast.LENGTH_LONG, true).show();
    }

    public static void success_toast(Context context ,String message)
    {
        Toasty.success(context, message , Toast.LENGTH_LONG, true).show();
    }

    public static void info_toast(Context context ,String message)
    {
        Toasty.info(context, message , Toast.LENGTH_LONG, true).show();
    }

    public static void warning_toast(Context context ,String message)
    {
        Toasty.warning(context, message , Toast.LENGTH_LONG, true).show();
    }








}

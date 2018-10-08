package com.app.api.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static final String URL_STRING =
            "https://blogurl-3f73f.firebaseapp.com/";
    public static ProgressDialog progressDialog;

    /**
     * Method to show Toast msg
     *
     * @param msg     msg to show
     * @param context context fo activity showing
     */
    public static void showToast(int msg, Context context) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}

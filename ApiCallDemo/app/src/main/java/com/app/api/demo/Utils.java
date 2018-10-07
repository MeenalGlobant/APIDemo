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

    public static void showProgressDialog(Context context) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.progress_dialog_msg));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
    }

    public static void dismissProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

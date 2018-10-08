package com.app.api.demo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.api.demo.R;

public class BaseActivity extends AppCompatActivity {

    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method to show progress dialog
     */
    public void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.progress_dialog_msg));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
        }
        progressDialog.show();
    }

    /**
     * Method to dismiss progress dialog
     */
    public void dismissProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Method to start new activity
     *
     * @param activityToLaunch
     */
    public void startCorrespondingActivity(Class<?> activityToLaunch) {
        Intent intent = new Intent(this, activityToLaunch);
        startActivity(intent);
    }
}

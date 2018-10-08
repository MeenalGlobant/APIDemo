package com.app.api.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.api.demo.R;

import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    public void onClickHttp(View view) {
        startCorrespondingActivity(HttpActivity.class);
    }


    public void onClickVolley(View view) {
        startCorrespondingActivity(VolleyActivity.class);
    }

    public void onClickRetrofit(View view) {
        startCorrespondingActivity(RetrofitActivity.class);
    }

}
package com.app.api.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.api.demo.R;
import com.app.api.demo.Utils;
import com.app.api.demo.adapter.WarDataAdapter;
import com.app.api.demo.interfaces.GetDataService;
import com.app.api.demo.model.ModelWarDetails;
import com.app.api.demo.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends Activity {

    @BindView(R.id.rvHttp)
    RecyclerView rvHttp;
    private WarDataAdapter warDataAdapter;
    private List<ModelWarDetails> listWarDetails = new ArrayList<>();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        ButterKnife.bind(this);
        initViews();
    }

    /**
     * Method to Initialise View
     */
    private void initViews() {
        context = this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvHttp.setLayoutManager(linearLayoutManager);
        getWarDatafromApi();
    }

    private void getWarDatafromApi() {
        Utils.showProgressDialog(context);
        GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ModelWarDetails>> listCall = getDataService.getWarDataList();
        listCall.enqueue(new Callback<List<ModelWarDetails>>() {
            @Override
            public void onResponse(Call<List<ModelWarDetails>> call, Response<List<ModelWarDetails>> response) {
                Utils.dismissProgressDialog();
                if (response.body() != null) {
                    listWarDetails = response.body();
                    //calling RecyclerViewAdapter constructor by passing context and list
                    warDataAdapter = new WarDataAdapter(context, listWarDetails);

                    //setting adapter on recyclerView
                    rvHttp.setAdapter(warDataAdapter);

                    // to notify adapter about changes in list data(if changes)
                    warDataAdapter.notifyDataSetChanged();
                } else {
                    Utils.showToast(R.string.error_msg, context);
                }
            }

            @Override
            public void onFailure(Call<List<ModelWarDetails>> call, Throwable t) {
                Utils.dismissProgressDialog();
            }
        });

    }
}

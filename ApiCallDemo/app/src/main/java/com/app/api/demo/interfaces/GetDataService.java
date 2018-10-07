package com.app.api.demo.interfaces;

import com.app.api.demo.model.ModelWarDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET(" ")
    Call<List<ModelWarDetails>> getWarDataList();
}

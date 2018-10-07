package com.app.api.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.api.demo.R;
import com.app.api.demo.Utils;
import com.app.api.demo.adapter.WarDataAdapter;
import com.app.api.demo.model.ModelWarDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.api.demo.Utils.URL_STRING;

public class VolleyActivity extends Activity {

    @BindView(R.id.rvHttp)
    RecyclerView rvHttp;
    private WarDataAdapter warDataAdapter;
    private List<ModelWarDetails> listWarDetails = new ArrayList<>();
    private RequestQueue requestQueue;
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
        requestQueue = Volley.newRequestQueue(this);
        getWarData();
    }

    /**
     * Method to make api call to get war data
     */
    private void getWarData() {

        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest()

        Utils.showProgressDialog(context);
        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, URL_STRING, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            Utils.dismissProgressDialog();
                            if (response != null) {
                                parseJsonData(response);
                            } else {
                                Utils.showToast(R.string.error_msg, context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.dismissProgressDialog();
                        Utils.showToast(R.string.error_msg, context);
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonRequest);
    }

    /**
     * Method to parse JSON response received from api
     *
     * @param responseArray
     */
    private void parseJsonData(JSONArray responseArray) {

        /* converting JSON response string into JSONArray*/

        try {

            if (responseArray.length() > 0) {

               /*Iterating JSON object from JSON Array one by one*/

                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject battleObj = responseArray.getJSONObject(i);

                    //creating object of model class(ModelWarDetails)
                    ModelWarDetails modelWarDetails = new ModelWarDetails();
                            /*
                            fetching data based on key from JSON and setting into model class
                            */
                    modelWarDetails.setName(battleObj.optString("name"));
                    modelWarDetails.setAttacker_king
                            (battleObj.optString("attacker_king"));
                    modelWarDetails.setDefender_king
                            (battleObj.optString("defender_king"));
                    modelWarDetails.setLocation(battleObj.optString("location"));

                    //adding data into List
                    listWarDetails.add(modelWarDetails);

                }

                //calling RecyclerViewAdapter constructor by passing context and list
                warDataAdapter = new WarDataAdapter(context, listWarDetails);

                //setting adapter on recyclerView
                rvHttp.setAdapter(warDataAdapter);

                // to notify adapter about changes in list data(if changes)
                warDataAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

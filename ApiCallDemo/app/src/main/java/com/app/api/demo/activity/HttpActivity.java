package com.app.api.demo.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.api.demo.R;
import com.app.api.demo.Utils;
import com.app.api.demo.adapter.WarDataAdapter;
import com.app.api.demo.model.ModelWarDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpActivity extends BaseActivity {

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
        new FetchWarsInfo().execute();
    }

    /**
     * Method to create URL connection
     *
     * @param GET_URL
     * @return
     */
    public String creatingURLConnection(String GET_URL) {
        String response = "";
        HttpURLConnection conn;
        StringBuilder jsonResults = new StringBuilder();
        try {
            //setting URL to connect with
            URL url = new URL(GET_URL);
            //creating connection
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            /*
            converting response into String
            */
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                response = jsonResults.toString();
            } else {
                showToast(R.string.error_msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Async task class to get json response by making HTTP call
     * Async task class is used because
     * you cannot create a network connection on main thread
     */
    public class FetchWarsInfo extends AsyncTask<Void, Void, Void> {


        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*
            creatingURLConnection is a function use to establish connection
            */
            if (!isNetworkAvailable()) {
                return null;
            }
            response = creatingURLConnection(Utils.URL_STRING);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();

            if (response != null && !response.equals("")) {
                showToast(R.string.connection_success_msg);
                parseJsonData(response);
            } else {
                showToast(R.string.no_internet_msg);
            }

        }

        /**
         * Method to parse JSON response received from api
         *
         * @param response
         */
        private void parseJsonData(String response) {

            /*converting JSON response string into JSONArray*/
            JSONArray responseArray = null;
            try {
                responseArray = new JSONArray(response);

                if (responseArray.length() > 0) {
                        /*
                        Iterating JSON object from JSON Array one by one
                        */
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

}

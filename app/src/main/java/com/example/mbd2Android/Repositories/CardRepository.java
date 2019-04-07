package com.example.mbd2Android.Repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Utils.RequestQueueSingleton;

import org.json.*;


public class CardRepository {


    private SharedPreferences sharedPreferences;
    private RequestQueueSingleton requestQueueSingleton;

    public CardRepository(Application application) {
        this.sharedPreferences = application.getSharedPreferences(application.getResources().getString(R.string.myPreferences), Context.MODE_PRIVATE);
        this.requestQueueSingleton = RequestQueueSingleton.getInstance(application);
    }

    public interface VolleyResponseListener {
        void onError(VolleyError error);

        void onResponse(Object response);
    }

    public void getCardsFromApi(int page, String searchQuery, final VolleyResponseListener listener) {
        int pageLimit = sharedPreferences.getInt("cardLimit", 12);
        String parameters = "?page=" + page + "&limit=" + pageLimit + "&query=" + searchQuery;
        String url = this.requestQueueSingleton.getApiURL() + "/cards" + parameters;

        this.requestQueueSingleton.addToRequestQueue(this.createNewJsonArrayRequest(url, listener));
    }


    private JsonArrayRequest createNewJsonArrayRequest(String url, final VolleyResponseListener listener) {
        return new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });
    }
}

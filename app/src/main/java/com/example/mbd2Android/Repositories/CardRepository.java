package com.example.mbd2Android.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.Utils.RequestQueueSingleton;

import org.json.*;

import java.util.ArrayList;
import java.util.List;


public class CardRepository {

    private int pageLimit = 12;

    private RequestQueueSingleton requestQueueSingleton;
    private LiveData<List<Card>> cards;

    public CardRepository(Application application) {
        this.requestQueueSingleton = RequestQueueSingleton.getInstance(application);
    }

    public interface VolleyResponseListener {
        void onError(VolleyError error);
        void onResponse(Object response);
    }

    public void getCardsFromApi(int page, String searchQuery, final VolleyResponseListener listener) {
        String parameters = "?page=" + page + "&limit=" + pageLimit + "&query=" + searchQuery;
        String url = this.requestQueueSingleton.getApiURL() + "/cards" + parameters;

        final List<Card> cardList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
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

        this.requestQueueSingleton.addToRequestQueue(jsonArrayRequest);
    }

    public LiveData<List<Card>> getCards() {
        return this.cards;
    }

}

package com.example.mbd2Android.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mbd2Android.R;

public class RequestQueueSingleton {

    private static RequestQueueSingleton requestQueueSingleton;
    private static Context context;

    private String ApiURL;
    private RequestQueue requestQueue;


    private RequestQueueSingleton(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
        this.ApiURL = context.getResources().getString(R.string.API_URL);
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (requestQueueSingleton == null) {
            requestQueueSingleton = new RequestQueueSingleton(context);
        }
        return requestQueueSingleton;
    }

    public void addToRequestQueue(Request req){
        getRequestQueue().add(req);
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public String getApiURL(){
       return this.ApiURL;
    }
}


package com.example.mbd2Android.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.mbd2Android.R;

public class RequestQueueSingleton {

    private static RequestQueueSingleton requestQueueSingleton;
    private static Context context;

    private String ApiURL;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    private RequestQueueSingleton(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
        this.ApiURL = context.getResources().getString(R.string.API_URL);
        this.imageLoader = new ImageLoader(requestQueue, createImageCache());
    }

    private ImageLoader.ImageCache createImageCache() {
        return new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        };
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (requestQueueSingleton == null) {
            requestQueueSingleton = new RequestQueueSingleton(context);
        }
        return requestQueueSingleton;
    }

    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public String getApiURL() {
        return this.ApiURL;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}


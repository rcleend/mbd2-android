package com.example.mbd2Android.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.mbd2Android.R;

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private static Context context;

    private String ApiURL;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    /**
     * De VolleySingelton is een singleton instantie van Volley.
     * Volley biedt o.a. asynchroon http request mogelijkheden en een imageLoader met cache.
     * @param ctx
     */
    private VolleySingleton(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
        this.ApiURL = context.getResources().getString(R.string.API_URL);
        this.imageLoader = new ImageLoader(requestQueue, createImageCache());
    }

    /**
     * createImageCache instantieert een imageCache.
     * @return
     */
    private ImageLoader.ImageCache createImageCache() {
        return new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(100);

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


    public static synchronized VolleySingleton getInstance(Context context) {
        if (volleySingleton == null) {
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
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


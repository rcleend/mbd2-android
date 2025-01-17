package com.example.mbd2Android.Repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Utils.VolleySingleton;

import org.json.*;

import java.util.ArrayList;
import java.util.List;


public class CardRepository {


    private SharedPreferences sharedPreferences;
    private VolleySingleton volleySingleton;

    private List<Card> allCards;

    /**
     * de CardRepository is een extra abstractielaag wat dient voor interactie met de Card data.
     * @param application
     */
    public CardRepository(Application application) {
        this.sharedPreferences = application.getSharedPreferences(application.getResources().getString(R.string.myPreferences), Context.MODE_PRIVATE);
        this.volleySingleton = VolleySingleton.getInstance(application);
        this.allCards = new ArrayList<>();
    }

    /**
     * VolleyResponseListener is een interface wat als callback funtioneert wanneer volley klaar is met een requestt.
     */
    public interface VolleyResponseListener {
        void onError(VolleyError error);

        void onResponse(Object response);
    }

    /**
     * getCardsFromApi instantieert een JsonArrayRequest waarin de kaarten worden opgehaald.
     * Deze request wordt aan de volleySingleton asynchrone requestQueue meegegeven.
     *
     * @param page
     * @param listener
     */
    public void getCardsFromApi(int page, final VolleyResponseListener listener) {
        int pageLimit = sharedPreferences.getInt("cardLimit", 12);
        String parameters = "?page=" + page + "&limit=" + pageLimit;
        String url = this.volleySingleton.getApiURL() + "/cards" + parameters;

        this.volleySingleton.addToRequestQueue(this.createNewJsonArrayRequest(url, listener));
    }


    /**
     * createNewJsonArrayRequest creeert de nieuwe JsonArrayRequest.
     * De onResponse methode wordt uitgevoerd wanneer de request klaar is.
     *
     * @param url
     * @param listener
     * @return
     */
    private JsonArrayRequest createNewJsonArrayRequest(String url, final VolleyResponseListener listener) {
        return new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        List<Card> cardList = parseJsonArrayResponse(response);
                        allCards.addAll(cardList);
                        listener.onResponse(cardList);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });
    }

    /**
     * pareJSonArrayResponse zet de JSONArray om tot een List<Card>
     *
     * @param responseArray
     * @return
     */
    private List<Card> parseJsonArrayResponse(JSONArray responseArray) {
        List<Card> cardList = new ArrayList<>();
        try {
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject jsonCard = responseArray.getJSONObject(i);
                Card card = createCardFromJson(jsonCard);
                cardList.add(card);
            }
        } catch (JSONException e) {
            Log.e("ERROR", e.toString());
        }
        return cardList;
    }

    /**
     * createCardFromJson zet een JSONObject om tot een Card
     *
     * @param jsonCard
     * @return
     */
    private Card createCardFromJson(JSONObject jsonCard) {
        Card card;
        try {
            int id = jsonCard.getInt("multiverseid");
            String name = jsonCard.getString("name");
            String imageUrl = jsonCard.getString("imageUrl");
            card = new Card(id, name, imageUrl);
        } catch (JSONException e) {
            Log.e("ERROR", e.toString());
            return null;
        }
        return card;
    }

    public List<Card> getAllCards(){
        return this.allCards;
    }
}

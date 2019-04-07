package com.example.mbd2Android.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.Repositories.CardRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<Card> selectedCard = new MutableLiveData<>();
    private MutableLiveData<List<Card>> cards = new MutableLiveData<>();

    private int page = 0;
    private String searchQuery = "";

    private CardRepository cardRepo;

    public MainViewModel(Application application) {
        super(application);
        this.cardRepo = new CardRepository(application);
        this.loadCards();

    }

    public void loadCards() {
        this.cardRepo.getCardsFromApi(this.page, this.searchQuery, new CardRepository.VolleyResponseListener() {
            @Override
            public void onError(VolleyError error) {
                Log.e("ERROR", error.toString());
            }

            @Override
            public void onResponse(Object response) {
                JSONArray responseArray = (JSONArray) response;
                parseJsonArrayResponse(responseArray);
            }
        });
    }

    private void parseJsonArrayResponse(JSONArray responseArray) {
        try {
            List<Card> cardList = new ArrayList<>();
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject jsonCard = responseArray.getJSONObject(i);
                Card card = createCardFromJson(jsonCard);
                cardList.add(card);
            }
            cards.setValue(cardList);

            if (this.selectedCard.getValue() == null)
                setSelectedCard(cardList.get(0));

        } catch (JSONException e) {
            Log.e("ERROR", e.toString());
        }
    }

    private Card createCardFromJson(JSONObject jsonCard) {
        Card card;
        try {
            String id = jsonCard.getString("id");
            String name = jsonCard.getString("name");
            String imageUrl = jsonCard.getString("imageUrl");
            card = new Card(id, name, imageUrl);
        } catch (JSONException e) {
            Log.e("ERROR", e.toString());
            return null;
        }
        return card;
    }

    public Intent createSharingIntent() {
        String name = selectedCard.getValue().getName();
        String imageUrl = selectedCard.getValue().getImageUrl();
        String message = "Look at this MTG card! " + name + ":\n" + imageUrl;

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

        return sharingIntent;
    }

    public MutableLiveData<Card> getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard.setValue(selectedCard);
    }

    public MutableLiveData<List<Card>> getCards() {
        return this.cards;
    }

}

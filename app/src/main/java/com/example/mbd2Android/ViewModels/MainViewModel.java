package com.example.mbd2Android.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

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
            public void onError(String message) {

            }

            @Override
            public void onResponse(Object response) {
                JSONArray responseArray = (JSONArray) response;
                try {
                    List<Card> cardList = new ArrayList<>();
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject jsonCard = responseArray.getJSONObject(i);

                        String id = jsonCard.getString("id");
                        String name = jsonCard.getString("name");
                        String imageUrl = jsonCard.getString("imageUrl");
                        Card card = new Card(id, name, imageUrl);
                        Log.d("CARD", card.getName());
                        cardList.add(card);
                    }
                    cards.setValue(cardList);

                } catch (JSONException e) {
                    Log.e("ERROOOR", e.toString());
                }
            }
        });
    }

    public MutableLiveData<Card> getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard.setValue(selectedCard);
    }

    public MutableLiveData<List<Card>> getCards() { return this.cards; }

}

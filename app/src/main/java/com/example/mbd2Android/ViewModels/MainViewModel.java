package com.example.mbd2Android.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Repositories.CardRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<Card> selectedCard = new MutableLiveData<>();
    private MutableLiveData<List<Card>> cards = new MutableLiveData<>();

    private int page = 1;
    private CardRepository cardRepo;

    /**
     * De MainViewModel is verantwoordelijk voor de logica achter de mainActivity
     *
     * @param application
     */
    public MainViewModel(Application application) {
        super(application);
        this.cardRepo = new CardRepository(application);
        this.loadCards();
    }

    /**
     * loadCards haalt via de cardRepo een lijst met kaarten op.
     * Dit gebeurt asynchroon. Wanneer de resultaten zijn opgehaald wordt de onResponse functie uitgevoerd.
     */
    public void loadCards() {
        this.cardRepo.getCardsFromApi(this.page, new CardRepository.VolleyResponseListener() {
            @Override
            public void onError(VolleyError error) {
                Log.e("ERROR", error.toString());
            }

            @Override
            public void onResponse(Object newCards) {
                List<Card> newCardsList = (List<Card>) newCards;
                if (selectedCard.getValue() == null)
                    setSelectedCard(newCardsList.get(0));
                    cards.setValue(newCardsList);
                page++;
            }
        });
    }


    /**
     * createSharingIntent creeert een nieuwe ACTION_SEND intent met daarin de data van de selectedCard.
     * @return
     */
    public Intent createSharingIntent() {
        String name = selectedCard.getValue().getName();
        String url = getApplication().getResources().getString(R.string.shareUrl) + selectedCard.getValue().getId();
        String message = "Look at this MTG card! " + name + ":\n" + url;

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

    public void setAllCards(){
        this.cards.setValue(this.cardRepo.getAllCards());
    }

}

package com.example.mbd2Android.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.Repositories.CardRepository;

public class MainViewModel extends ViewModel {
    //    private String[] cards = {"Ancestor's Chosen", "Angel of Mercy", "Angelic Blessing", "Angelic Chorus", "Angelic Wall", "Aura of Silence", "Aven Cloudchases", "Ballista Squad", "Bandage"};
    private Card[] cards = {
            new Card("1", "Card 1", "This is Card 1", "http://icons.iconarchive.com/icons/yellowicon/game-stars/256/Mario-icon.png"),
            new Card("1", "Card 1", "This is Card 1", "http://icons.iconarchive.com/icons/yellowicon/game-stars/256/Mario-icon.png")
    };
    private MutableLiveData<Card> selectedCard = new MutableLiveData<>();
    private CardRepository cardRepo;

    public Card[] getCards() {
        return this.cards;
    }

    public MutableLiveData<Card> getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard.setValue(selectedCard);
    }

}

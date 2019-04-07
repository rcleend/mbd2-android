package com.example.mbd2Android.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Utils.VolleySingleton;
import com.example.mbd2Android.ViewModels.MainViewModel;


public class DetailFragment extends Fragment {

    private MainViewModel viewmodel;

    /**
     * onCreate instantieert de viewModel.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    /**
     * onCreateView instantieert de layout, shareButton en cardObserver.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.setupCardObserver();
        this.setupShareButton(view);

        return view;
    }

    /**
     * setupCardObserver instantieert een nieuwe Observer voor de selectedCard in de viewModel.
     * Dit maakt de selectedCard reactief.
     */
    private void setupCardObserver() {
        final Observer<Card> cardObserver = new Observer<Card>() {
            @Override
            public void onChanged(@Nullable final Card newCard) {
                setImage(newCard);
            }
        };
        this.viewmodel.getSelectedCard().observe(this, cardObserver);
    }


    /**
     * setImage verandert de afbeelding in de view.
     * Dit wordt gerealiseerd dmv. de VolleySingelton die een imageLoader bevat.
     *
     * @param card
     */
    private void setImage(Card card) {
        NetworkImageView cardThumbnail = getActivity().findViewById(R.id.detailImage);
        final String url = card.getImageUrl();

        ImageLoader imageLoader = VolleySingleton.getInstance(getContext()).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(cardThumbnail, R.drawable.mtg_back, android.R.drawable.ic_dialog_alert));
        cardThumbnail.setImageUrl(url, imageLoader);
    }

    /**
     * setupShareButton instanteert een onClickListener voor de shareButton.
     * Wanneer er op de knop wordt geklikt wordt er een nieuwe shareIntent in de viewModel opgehaald.
     * Deze wordt meegegeven aan de een nieuwe activity.
     *
     * @param view
     */
    private void setupShareButton(View view) {
        FloatingActionButton shareButton = view.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = viewmodel.createSharingIntent();
                startActivity(sharingIntent);
            }
        });
    }

}

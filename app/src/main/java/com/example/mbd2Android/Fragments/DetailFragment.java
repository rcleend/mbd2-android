package com.example.mbd2Android.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Utils.RequestQueueSingleton;
import com.example.mbd2Android.ViewModels.MainViewModel;

import java.util.Set;

public class DetailFragment extends Fragment {

    private MainViewModel viewmodel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.setupCardObserver();
        this.setupShareButton(view);

        return view;
    }

    private void setupCardObserver() {
        final Observer<Card> cardObserver = new Observer<Card>() {
            @Override
            public void onChanged(@Nullable final Card newCard) {
                setContent(newCard);
            }
        };
        this.viewmodel.getSelectedCard().observe(this, cardObserver);
    }

    private void setContent(Card card) {
        NetworkImageView cardThumbnail = getActivity().findViewById(R.id.detailImage);
        this.setImage(card, cardThumbnail);
    }

    private void setImage(Card card, NetworkImageView cardThumbnail) {
        final String url = card.getImageUrl();
        ImageLoader imageLoader = RequestQueueSingleton.getInstance(getContext()).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(cardThumbnail, R.drawable.mtg_back, android.R.drawable.ic_dialog_alert));
        cardThumbnail.setImageUrl(url, imageLoader);
    }

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

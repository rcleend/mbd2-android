package com.example.mbd2Android.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.ViewModels.MainViewModel;

public class DetailFragment extends Fragment {

    private MainViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        final Observer<Card> cardObserver = new Observer<Card>() {
            @Override
            public void onChanged(@Nullable final Card newCard) {
                TextView textView = (TextView) getActivity().findViewById(R.id.textView);
                textView.setText(newCard.getName());
            }
        };

        model.getSelectedCard().observe(this, cardObserver);

        return view;
    }
}

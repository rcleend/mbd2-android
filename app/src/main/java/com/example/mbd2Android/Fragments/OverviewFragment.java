package com.example.mbd2Android.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Utils.CardsAdapter;
import com.example.mbd2Android.ViewModels.MainViewModel;

import java.util.List;

public class OverviewFragment extends Fragment {

    private MainViewModel viewModel;
    private ArrayAdapter<Card> cardsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        this.setupListView(view);
        this.setupCardsObserver();
        return view;
    }

    private void setupListView(View view) {
        this.cardsAdapter = new CardsAdapter(this.getContext());
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(this.cardsAdapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = setupItemClickListener();
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    private AdapterView.OnItemClickListener setupItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setSelectedCard(cardsAdapter.getItem(position));
                navigateOnePaneFragment();
            }
        };
    }

    private void setupCardsObserver() {
        final Observer<List<Card>> cardsObserver = new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable final List<Card> newCards) {
                cardsAdapter.addAll(newCards);
                cardsAdapter.notifyDataSetChanged();
            }
        };

        this.viewModel.getCards().observe(this, cardsObserver);
    }

    private void navigateOnePaneFragment() {
        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        if (!isDualPane) {
            DetailFragment fragment = new DetailFragment();
            getFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, fragment).addToBackStack(null).commit();
        }
    }
}

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
import android.widget.Button;
import android.widget.ListView;

import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;
import com.example.mbd2Android.Utils.CardsAdapter;
import com.example.mbd2Android.ViewModels.MainViewModel;

import java.util.List;

public class OverviewFragment extends Fragment {

    private MainViewModel viewModel;
    private ArrayAdapter<Card> cardsAdapter;

    /**
     * onCreate instantieert haalt de viewModel op en maakt een nieuwe CardAdapter aan.
     * Hierna wordt er een cardObserver toegevoegd aan de cardAdapter.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        this.cardsAdapter = new CardsAdapter(this.getContext());

        this.setupCardsObserver();
    }

    /**
     * onCreateView biedt een inflater voor de layout.
     * Ook wordt hier de setup voor de listView geinstantieerd.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        this.setupListView(view);
        return view;
    }

    /**
     * setupListView instantieerd de listView, en een setup voor de onItemClickListener en loadMoreButton.
     *
     * @param view
     */
    private void setupListView(View view) {
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(this.cardsAdapter);


        AdapterView.OnItemClickListener mMessageClickedHandler = setupItemClickListener();
        listView.setOnItemClickListener(mMessageClickedHandler);


        Button loadMoreButton = setupLoadMoreButton();
        listView.addFooterView(loadMoreButton);
    }

    /**
     * setupLoadMoreButton instantieert de loadMoreButton onderaan de listView.
     *
     * @return
     */
    private Button setupLoadMoreButton() {
        Button loadMoreButton = new Button(this.getContext());
        loadMoreButton.setText("Load More");
        loadMoreButton.setOnClickListener(setupLoadMoreButtonListener());
        return loadMoreButton;
    }

    /**
     * setupLoadMoreButtonListener instanteert een onClickListener voor de loadMoreButton
     *
     * @return
     */
    private View.OnClickListener setupLoadMoreButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadCards();
            }
        };
    }

    /**
     * setupItemClickListener instanteert een OnItemClickListener voor de listView.
     * Wanneer er om een listItem wordt geklikt wordt er een navigateOnPaneFragment() aangeroepen.
     *
     * @return
     */
    private AdapterView.OnItemClickListener setupItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setSelectedCard(cardsAdapter.getItem(position));
                navigateOnePaneFragment();
            }
        };
    }

    /**
     * setupCardsObserver instantieert een nieuwe observer voor de cards in de viewModel.
     * Wanneer er nu een wijzging plaatsvind in de cards wordt het automatisch geupdate.
     * Het is dus reactief.
     */
    private void setupCardsObserver() {
        final Observer<List<Card>> cardsObserver = new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable final List<Card> newCards) {
                cardsAdapter.addAll(newCards);
                cardsAdapter.notifyDataSetChanged();
            }
        };

        this.viewModel.setAllCards();
        this.viewModel.getCards().observe(this, cardsObserver);
    }

    /**
     * navigateOnePaneFragment checkt of dat het apparaat in landscape of portrait modes is.
     * Wanneer het in portrait modes is vervangt die de overviewFragment met een detailFragment.
     */
    private void navigateOnePaneFragment() {
        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        if (!isDualPane) {
            DetailFragment fragment = new DetailFragment();
            getFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, fragment).addToBackStack(null).commit();
        }
    }
}

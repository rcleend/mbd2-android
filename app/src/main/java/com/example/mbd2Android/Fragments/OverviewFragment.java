package com.example.mbd2Android.Fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mbd2Android.MainActivity;
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

        final Observer<List<Card>> cardsObserver = new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable final List<Card> newCards) {
                cardsAdapter.clear();
                cardsAdapter.addAll(newCards);
                cardsAdapter.notifyDataSetChanged();

                Log.d("DEBUGG", "changinnnng");
            }
        };

        viewModel.getCards().observe(this, cardsObserver);

        return view;
    }

    public void setupListView(View view) {
        this.cardsAdapter = new CardsAdapter(this.getContext());
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(this.cardsAdapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = setupItemClickListener(this.cardsAdapter);
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    public AdapterView.OnItemClickListener setupItemClickListener(final ArrayAdapter<Card> adapter) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setSelectedCard(adapter.getItem(position));
                navigateOnePaneFragment();
            }
        };
    }

    public void navigateOnePaneFragment() {
        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        if (!isDualPane) {
            DetailFragment fragment = new DetailFragment();
            getFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, fragment).addToBackStack(null).commit();
        }
    }
}

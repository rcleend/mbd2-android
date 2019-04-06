package com.example.mbd2Android.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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

public class OverviewFragment extends Fragment {

    private MainViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        this.setupListView(view);
        return view;
    }

    public void setupListView(View view) {
        final ArrayAdapter<Card> adapter = new CardsAdapter(this.getContext(), this.model.getCards());
        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = setupItemClickListener(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    public AdapterView.OnItemClickListener setupItemClickListener(final ArrayAdapter<Card> adapter) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model.setSelectedCard(adapter.getItem(position));
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

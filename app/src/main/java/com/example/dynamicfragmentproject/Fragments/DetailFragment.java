package com.example.dynamicfragmentproject.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dynamicfragmentproject.R;
import com.example.dynamicfragmentproject.ViewModels.MainViewModel;

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

        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                TextView textView = (TextView) getActivity().findViewById(R.id.textView);
                textView.setText(newName);
            }
        };

        model.getSelectedName().observe(this, nameObserver);

        return view;
    }
}

package com.example.dynamicfragmentproject.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dynamicfragmentproject.R;
import com.example.dynamicfragmentproject.ViewModels.MainViewModel;

public class OverviewFragment extends Fragment {

    private MainViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, model.getNames());
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model.setSelectedName(adapter.getItem(position));

                boolean mHasOnePane = !getResources().getBoolean(R.bool.dual_pane);
                if(mHasOnePane){
                    DetailFragment fragment = new DetailFragment();
                    getFragmentManager().beginTransaction().replace(R.id.dynamic_frame_layout, fragment).addToBackStack(null).commit();
                }
            }
        };

        listView.setOnItemClickListener(mMessageClickedHandler);

        return view;
    }
}

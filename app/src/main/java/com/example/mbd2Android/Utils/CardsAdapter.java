package com.example.mbd2Android.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;

import java.util.ArrayList;

public class CardsAdapter extends ArrayAdapter<Card> {

    public CardsAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_list_item, parent, false);

        NetworkImageView cardThumbnail = convertView.findViewById(R.id.thumbnail);
        TextView cardName = convertView.findViewById(R.id.name);

        cardName.setText(card.getName());
        Log.d("Card", cardThumbnail.toString());

        return convertView;
    }

}

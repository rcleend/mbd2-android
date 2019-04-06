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
import com.example.mbd2Android.ApplicationController;
import com.example.mbd2Android.Models.Card;
import com.example.mbd2Android.R;

public class CardsAdapter extends ArrayAdapter<Card> {
    private ImageLoader imageLoader = ApplicationController.getInstance().getImageLoader();

    public CardsAdapter(Context context, Card[] cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_list_item, parent, false);

        NetworkImageView cardThumbnail = convertView.findViewById(R.id.thumbnail);
        TextView cardName = convertView.findViewById(R.id.name);

        cardName.setText(card.getName());
        cardThumbnail.setImageUrl(card.getImageUrl(), imageLoader);
        Log.d("Card", cardThumbnail.toString());

        return convertView;
    }

}

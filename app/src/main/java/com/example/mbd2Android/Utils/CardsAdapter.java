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

public class CardsAdapter extends ArrayAdapter<Card> {

    public CardsAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_list_item, parent, false);

        Card card = getItem(position);
        this.SetContent(card, convertView);


        return convertView;
    }

    private void SetContent(Card card, View convertView) {
        NetworkImageView cardThumbnail = convertView.findViewById(R.id.thumbnail);
        TextView cardName = convertView.findViewById(R.id.name);

        this.SetImage(card, cardThumbnail);
        cardName.setText(card.getName());
    }

    private void SetImage(Card card, NetworkImageView cardThumbnail) {
        final String url = card.getImageUrl();
        ImageLoader imageLoader = RequestQueueSingleton.getInstance(getContext()).getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(cardThumbnail, android.R.drawable.ic_menu_report_image, android.R.drawable.ic_dialog_alert));
        cardThumbnail.setImageUrl(url, imageLoader);
    }

}

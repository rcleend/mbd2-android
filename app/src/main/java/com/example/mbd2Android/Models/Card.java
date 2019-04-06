package com.example.mbd2Android.Models;

public class Card {
    private String id;
    private String name;
    private String text;
    private String imageUrl;

    public Card(String id, String name, String text, String imageUrl){
       this.id = id;
       this.name = name;
       this.text = text;
       this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}

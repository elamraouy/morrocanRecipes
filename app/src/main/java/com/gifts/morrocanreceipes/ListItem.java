package com.gifts.morrocanreceipes;

import android.net.Uri;

public class ListItem {
    private String title;
    private String description;
    private Uri receipe_image;


    public ListItem(String title, String body, Uri gift_image) {
        this.title = title;
        this.description = body;
        this.receipe_image = gift_image;
    }


    public String getTitle() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public Uri getReceipe_image() {

        return receipe_image;
    }


}

package com.uprisingscallscreen.theme.flashscreen.callertheme.ui;

import android.net.Uri;

public class ParseItem {

    private String imgUrl;
    private Uri Uri;

    private String name;

    public ParseItem() {
    }

    public ParseItem(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Uri getUri() {
        return Uri;
    }

    public void setUri(Uri uri) {
        Uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

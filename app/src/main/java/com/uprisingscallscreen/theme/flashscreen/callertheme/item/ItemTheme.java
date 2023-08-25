package com.uprisingscallscreen.theme.flashscreen.callertheme.item;


public class ItemTheme {
    private String link;
    private String name;
    private String thumb;

    public ItemTheme(String str, String str2, String str3) {
        this.name = str;
        this.link = str2;
        this.thumb = str3;
    }

    public String getName() {
        return this.name;
    }

    public String getLink() {
        return this.link;
    }

    public String getThumb() {
        return this.thumb;
    }
}

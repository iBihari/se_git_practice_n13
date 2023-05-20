package com.shubhamgupta16.simplewallpaper.models;


import java.io.Serializable;

public class WallsPOJO implements Serializable {
    private final int viewType;
    private final String name, previewUrl, url, categories;
    private final boolean premium;

    public WallsPOJO(String url, String name, String previewUrl, String categories, boolean premium) {
        this.viewType = 0;
        this.name = name;
        this.previewUrl = previewUrl;
        this.url = url;
        this.categories = categories;
        this.premium = premium;
    }

    public WallsPOJO(){
        viewType = -2;
        this.name = null;
        this.previewUrl = null;
        this.url = null;
        this.categories = null;
        this.premium = false;
    }
    public WallsPOJO(boolean fullSize){
        viewType = fullSize ? -3 : -1;
        this.name = null;
        this.previewUrl = null;
        this.url = null;
        this.categories = null;
        this.premium = false;
    }

    public int getViewType() {
        return viewType;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isPremium() {
        return premium;
    }

    public String getCategories() {
        return categories;
    }

}

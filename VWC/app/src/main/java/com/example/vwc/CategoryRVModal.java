package com.example.vwc;

public class CategoryRVModal {

    private String category;
    private String CategoryIVUrl;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryIVUrl() {
        return CategoryIVUrl;
    }

    public void setCategoryIVUrl(String categoryIVUrl) {
        CategoryIVUrl = categoryIVUrl;
    }

    public CategoryRVModal(String category, String categoryIVUrl) {
        this.category = category;
        CategoryIVUrl = categoryIVUrl;
    }
}

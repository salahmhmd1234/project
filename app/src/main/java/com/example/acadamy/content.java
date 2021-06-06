package com.example.acadamy;

public class content {
    String name,price,imageUri;

    public content() {
    }

    public content(String name, String price, String imageUri) {
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}


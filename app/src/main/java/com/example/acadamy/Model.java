package com.example.acadamy;

public class Model {
    private String imageUri;
        public Model(){

        }

    public Model(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

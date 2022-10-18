package com.example.daily_fume;

import android.graphics.Bitmap;

public class FragranceData {
    private Bitmap fragrance_image;
    private String fragrance_brand;
    private String fragrance_name;

    public Bitmap getFragrance_image() {
        return fragrance_image;
    }

    public String getFragrance_brand() {
        return fragrance_brand;
    }

    public String getFragrance_name() {
        return fragrance_name;
    }

    public void setFragrance_image(Bitmap fragrance_image) {
        this.fragrance_image = fragrance_image;
    }

    public void setFragrance_brand(String fragrance_brand) {
        this.fragrance_brand = fragrance_brand;
    }

    public void setFragrance_name(String fragrance_name) {
        this.fragrance_name = fragrance_name;
    }

}

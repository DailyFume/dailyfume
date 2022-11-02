package com.example.daily_fume;

import android.graphics.Bitmap;

public class PickFume {
    private String brand;
    private String fumeName;
    private Bitmap iamgesResId;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFumeName() {
        return fumeName;
    }

    public void setFumeName(String fumeName) {
        this.fumeName = fumeName;
    }

    public Bitmap getIamgesResId() {
        return iamgesResId;
    }

    public void setIamgesResId(Bitmap iamgesResId) {
        this.iamgesResId = iamgesResId;
    }
}

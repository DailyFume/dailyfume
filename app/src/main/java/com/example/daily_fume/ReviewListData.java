package com.example.daily_fume;

import android.graphics.Bitmap;

public class ReviewListData {
    private String brand; // 브랜드명
    private String name; // 향수명
    private String content; // 리뷰내용
    private double rate; // 별점
    private Bitmap fimg; // 포토리뷰


    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public double getRate() {
        return rate;
    }

    public Bitmap getImage() {
        return fimg;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setImage(Bitmap fimg) {
        this.fimg = fimg;
    }
}
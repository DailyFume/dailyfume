package com.example.daily_fume;

import android.graphics.Bitmap;


public class PickFume implements  Comparable<PickFume> {
    private int likeid;
    private int fid;
    private String brand;
    private String fumeName;
    private Bitmap iamgesResId;

    public String getBrand() {
        return brand;
    }

    public String getFumeName() {
        return fumeName;
    }

    public Bitmap getIamgesResId() {
        return iamgesResId;
    }

    public int getFid() { return fid; }

    public int getLikeid() { return likeid; }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setFumeName(String fumeName) {
        this.fumeName = fumeName;
    }

    public void setIamgesResId(Bitmap iamgesResId) {
        this.iamgesResId = iamgesResId;
    }

    public void setFid(int fid) { this.fid = fid; }

    public void setLikeid(int likeid) { this.likeid = likeid; }

    @Override
    public int compareTo(PickFume o) {
        return this.likeid - o.likeid; // 정렬
    }
}

package com.example.daily_fume;

public class ReviewData {
    private int reviewImg;
    private String nickName;
    private String reviewStr;

    public ReviewData(int reviewImg, String nickName, String reviewStr) {
        this.reviewImg = reviewImg;
        this.nickName = nickName;
        this.reviewStr = reviewStr;
    }

    public int getReviewImg() {
        return this.reviewImg;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getReviewStr() {
        return this.reviewStr;
    }

}

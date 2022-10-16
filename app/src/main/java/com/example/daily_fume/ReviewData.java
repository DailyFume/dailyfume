package com.example.daily_fume;

public class ReviewData {
    private int reviewImg;
    private String nickName;
    private String reviewStr;
    private int reviewStars;

    public ReviewData(int reviewImg, String nickName, String reviewStr, int reviewStars) {
        this.reviewImg = reviewImg;
        this.nickName = nickName;
        this.reviewStr = reviewStr;
        this.reviewStars = reviewStars;
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

    public int getReviewStars() { return this.reviewStars; }

}

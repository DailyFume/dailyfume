package com.example.daily_fume;

public class ReviewListData {
    private String RBrand; // 브랜드명
    private String RTitle; // 향수명
    private String Rstr; // 리뷰내용
    private int IntStars; // 별점
    private int RImg; // 포토리뷰

    public ReviewListData( String RBrand, String RTitle, String Rstr, int IntStars, int RImg ) {
        this.RBrand = RBrand;
        this.RTitle = RTitle;
        this.Rstr = Rstr;
        this.IntStars = IntStars;
        this.RImg = RImg;
}

    public String getRBrand() {
        return this.RBrand;
    }

    public String getRTitle() {
        return this.RTitle;
    }

    public String getRstr() {
        return this.Rstr;
    }

    public int getIntStars() {
        return this.IntStars;
    }

    public int getRImg() {
        return this.RImg;
    }

}

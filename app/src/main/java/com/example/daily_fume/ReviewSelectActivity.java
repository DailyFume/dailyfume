package com.example.daily_fume;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReviewSelectActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    RatingBar review_ratingBar;
    ImageView photo_choiceBtn;
    TextView review_box;
    Button reviewCreateBtn;
    TextView brandText, titleText;
    LinearLayout brandboxs, titleboxs;
    TextView nickname_st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("리뷰 상세보기");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.searchIcon);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // searchIcon.setOnClickListener();
        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 인텐트 값 전달받기
//        Intent intent = getIntent();
//        int Select_reviewImg = intent.getIntExtra("reviewImg", 0);
//        String Select_nickName = intent.getStringExtra("nickName");
//        String Select_reviewStr = intent.getStringExtra("reviewStr");
//        int Select_reviewStars = intent.getIntExtra("reviewStars", 0);

        // 기본값 (단 모든 값은 수정이 안됨. 보는 용도임)
        review_box = (TextView) findViewById(R.id.review_box);
        review_ratingBar = (RatingBar) findViewById(R.id.review_ratingBar);
        photo_choiceBtn = (ImageView) findViewById(R.id.photo_choiceBtn);
        brandText = (TextView) findViewById(R.id.brandText);
        titleText = (TextView) findViewById(R.id.titleText);
        reviewCreateBtn = (Button) findViewById(R.id.reviewCreateBtn);

        brandboxs = (LinearLayout) findViewById(R.id.brandboxs);
        titleboxs = (LinearLayout) findViewById(R.id.titleboxs);
        nickname_st = (TextView) findViewById(R.id.nickname_st);

        brandText.setEnabled(false);
        titleText.setEnabled(false);
        review_box.setEnabled(false);
        review_ratingBar.setIsIndicator(true);
        photo_choiceBtn.setEnabled(false);
        nickname_st.setEnabled(false);
        titleboxs.setEnabled(false);
        brandboxs.setVisibility(View.GONE);
        reviewCreateBtn.setVisibility(View.INVISIBLE);
        reviewCreateBtn.setEnabled(false);

//        nickname_st.setText("닉네임 ");
//        titleText.setText(Select_nickName);
//        titleText.setTextSize(16);
//        titleText.setTextColor(Color.GRAY);
//        review_box.setText(Select_reviewStr);
//        photo_choiceBtn.setImageResource(Select_reviewImg);
//        review_ratingBar.setRating(Select_reviewStars);

    }

}
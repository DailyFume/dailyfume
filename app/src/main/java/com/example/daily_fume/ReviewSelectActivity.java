package com.example.daily_fume;

import android.content.Intent;
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

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");
        String uname = intent.getStringExtra("uname");
        String uemail = intent.getStringExtra("uemail");


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
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });


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


    }

}
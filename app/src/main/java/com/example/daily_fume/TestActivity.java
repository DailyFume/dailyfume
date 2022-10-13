package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;
    int progressn = 10; // 프로그래스바 변경할 값 변수 (기본값 10)
    ProgressBar testProgressBar;
    ImageView prevBtn, nextBtn;
    TextView prevText, nextText, testNumber;
    LinearLayout prevLayout, nextLayout;
    ImageView aImgSelect, bImgSelect, selectorVisible01, selectorVisible02;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("향수 추천받기");

        backBtn = (ImageView) findViewById(R.id.back_icon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        // searchIcon = (ImageView) findViewById(R.id.);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        // searchIcon.setOnClickListener();

        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                startActivity(intent);
            }
        });

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });


        testProgressBar = (ProgressBar) findViewById(R.id.testProgressBar);
        testNumber = (TextView) findViewById(R.id.testNumber);
        prevLayout = (LinearLayout) findViewById(R.id.prevLayout);
        nextLayout = (LinearLayout) findViewById(R.id.nextLayout);

        nextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressn != 100)
                progressn = progressn + 10;
                testProgressBar.setProgress(progressn);
            }
        });

        prevLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressn != 10)
                progressn = progressn - 10;
                testProgressBar.setProgress(progressn);
            }
        });

        aImgSelect = (ImageView) findViewById(R.id.aTestSelect);
        bImgSelect = (ImageView) findViewById(R.id.bTestSelect);
        selectorVisible01 = (ImageView) findViewById(R.id.selectorVisibleT1);
        selectorVisible02 = (ImageView) findViewById(R.id.selectorVisibleT2);

        aImgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aImgSelect.setAlpha(50);
                selectorVisible01.setVisibility(View.VISIBLE);
                selectorVisible02.setVisibility(View.GONE);
            }
        });

        bImgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorVisible02.setVisibility(View.VISIBLE);
                selectorVisible01.setVisibility(View.GONE);
            }
        });
    }
}
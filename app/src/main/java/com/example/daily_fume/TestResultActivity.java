package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TestResultActivity extends AppCompatActivity {

    ImageView ResultBackIcon;
    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;
    ImageView ResultTopButton;
    ScrollView TestResultView;

    // 뷰페이저 변수
    private ViewPager ResultViewPager;
    private TextViewPagerAdapter pagerAdapter;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;


    // 임시 랜덤함수 (결과페이지 10개)
    Integer[] resultPage = {R.layout.result_citrus, R.layout.result_floral, R.layout.result_green, R.layout.result_woody,
            R.layout.result_oriental, R.layout.result_fruity, R.layout.result_oceanic, R.layout.result_spicy,
            R.layout.result_aldeyde, R.layout.result_musk };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test_result);

        // ★ 나중에 if문 이용하거나 답변 갯수에 따라 setContentView 바꿔주고 아래 메서드 실행하면 가능
        // 현재 임시로 결과 페이지 랜덤하게 나오게 함
        Random ram = new Random();
        int num = ram.nextInt(resultPage.length);
        //setContentView(R.layout.result_citrus);
        setContentView(resultPage[num]);
        resultPage();
    }

    // 메서드 (결과페이지 이벤트)
    void resultPage() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        ResultBackIcon = (ImageView) findViewById(R.id.ResultBackIcon);
        ResultBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
            }
        });

        ResultTopButton = (ImageView) findViewById(R.id.ResultTopButton);
        TestResultView = (ScrollView) findViewById(R.id.TestResultView);
        ResultTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestResultView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.);
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

        // 뷰페이저
        ResultViewPager = findViewById(R.id.ResultViewPager);
        pagerAdapter = new TextViewPagerAdapter(this);
        ResultViewPager.setAdapter(pagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 9) {
                    currentPage = 0;
                }
                ResultViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}
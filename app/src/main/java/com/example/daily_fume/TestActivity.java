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
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;
    int progressn = 10; // 프로그래스바 변경할 값 변수 (기본값 10)
    ProgressBar testProgressBar;
    ImageView prevBtn, nextBtn;
    TextView prevText, nextText, testNumber;
    LinearLayout prevLayout, nextLayout;
    Integer tNum;
    ImageView aTestSelect, bTestSelect, cTestSelect, dTestSelect, selectorVisibleT1, selectorVisibleT2, selectorVisibleT3, selectorVisibleT4;

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
        tNum = 1; // 초기값 설정
        testNumber.setText(tNum.toString());
        testNumPlay();

        nextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressn != 100)
                progressn = progressn + 10;
                testProgressBar.setProgress(progressn);
                tNum += 1;
                testNumber.setText(tNum.toString());
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
                testNumPlay();
            }
        });

        prevLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressn != 10)
                progressn = progressn - 10;
                testProgressBar.setProgress(progressn);
                tNum -= 1;
                testNumber.setText(tNum.toString());
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
                testNumPlay();
            }
        });





        aTestSelect = (ImageView) findViewById(R.id.aTestSelect);
        bTestSelect = (ImageView) findViewById(R.id.bTestSelect);
        cTestSelect = (ImageView) findViewById(R.id.cTestSelect);
        dTestSelect = (ImageView) findViewById(R.id.dTestSelect);
        selectorVisibleT1 = (ImageView) findViewById(R.id.selectorVisibleT1);
        selectorVisibleT2 = (ImageView) findViewById(R.id.selectorVisibleT2);
        selectorVisibleT3 = (ImageView) findViewById(R.id.selectorVisibleT3);
        selectorVisibleT4 = (ImageView) findViewById(R.id.selectorVisibleT4);

        aTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorVisibleT1.setVisibility(View.VISIBLE);
                selectorVisibleT2.setVisibility(View.GONE);
                selectorVisibleT3.setVisibility(View.GONE);
                selectorVisibleT4.setVisibility(View.GONE);
            }
        });

        bTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorVisibleT1.setVisibility(View.GONE);
                selectorVisibleT2.setVisibility(View.VISIBLE);
                selectorVisibleT3.setVisibility(View.GONE);
                selectorVisibleT4.setVisibility(View.GONE);
            }
        });

        cTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorVisibleT1.setVisibility(View.GONE);
                selectorVisibleT2.setVisibility(View.GONE);
                selectorVisibleT3.setVisibility(View.VISIBLE);
                selectorVisibleT4.setVisibility(View.GONE);
            }
        });

        dTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorVisibleT1.setVisibility(View.GONE);
                selectorVisibleT2.setVisibility(View.GONE);
                selectorVisibleT3.setVisibility(View.GONE);
                selectorVisibleT4.setVisibility(View.VISIBLE);
            }
        });
    }

    void testNumPlay() {
        switch (tNum.intValue()) {
            case 1:
                prevLayout.setVisibility(View.INVISIBLE);
                nextLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                prevLayout.setVisibility(View.VISIBLE);
                nextLayout.setVisibility(View.VISIBLE);
                break;
            case 3:
                prevLayout.setVisibility(View.VISIBLE);
                nextLayout.setVisibility(View.VISIBLE);
                break;
            case 4:
                prevLayout.setVisibility(View.VISIBLE);
                nextLayout.setVisibility(View.VISIBLE);
                break;
            case 5:
                prevLayout.setVisibility(View.VISIBLE);
                nextLayout.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
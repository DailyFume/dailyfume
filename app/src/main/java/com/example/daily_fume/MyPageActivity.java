package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPageActivity extends AppCompatActivity {

    ImageView backBtn, home_icon;
    TextView title_change;
    Button nickNameBtn;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("마이페이지");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        home_icon = (ImageView) findViewById(R.id.home_icon);
        home_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);


        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
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


        nickNameBtn = (Button) findViewById(R.id.nickNameBtn); // 사용자의 닉네임이 보여지는 창

        findViewById(R.id.menu1Btn).setOnClickListener(menuClick); // 개인정보 수정 페이지
        findViewById(R.id.menu2Btn).setOnClickListener(menuClick); // 찜 목록 페이지
        findViewById(R.id.menu3Btn).setOnClickListener(menuClick); // 후기 리스트 페이지
        findViewById(R.id.menu4Btn).setOnClickListener(menuClick); // 문의사항
        findViewById(R.id.menu5Btn).setOnClickListener(menuClick); // 공지사항

    }

    View.OnClickListener menuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu1Btn:
                    Intent intent = new Intent(getApplicationContext(), UserPrivacyActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu2Btn:
                    intent = new Intent(getApplicationContext(), PickListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu3Btn:
                    //
                    break;
                case R.id.menu4Btn:
                    intent = new Intent(getApplicationContext(), QnaActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu5Btn:
                    //
                    break;
            }
        }
    };

}





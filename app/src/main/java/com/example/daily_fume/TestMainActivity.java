package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TestMainActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;
    Button noviceBtn, buffBtn;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

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

        /*noviceBtn = (Button) findViewById(R.id.noviceBtn);
        buffBtn = (Button) findViewById(R.id.buffBtn);
        noviceBtn.setOnClickListener(new View.OnClickListener() { // 초보자 버튼 클릭시
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestMainActivity.this, TestNoviceActivity.class);
                startActivity(intent);
            }
        });
        buffBtn.setOnClickListener(new View.OnClickListener() { // 매니아 버튼 클릭시
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestMainActivity.this, TestBuffActivity.class);
                startActivity(intent);
            }
        });*/
    }
}

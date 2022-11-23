package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyPageActivity extends AppCompatActivity {

    ImageView backBtn, imgVdf;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    ImageView menu1Btn, menu2Btn, menu3Btn, menu4Btn;
    int uid;
    String uemail;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");
        String uemail = intent.getStringExtra("uemail");

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        uid = intent.getExtras().getInt("uid");
        uname = intent.getStringExtra("uname");
        uemail = intent.getStringExtra("uemail");

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setTextColor(Color.parseColor("#D77F8F"));
        title_change.setText(uname);

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

        menu1Btn = (ImageView) findViewById(R.id.menu1Btn); // 개인정보 수정 페이지
        menu2Btn = (ImageView) findViewById(R.id.menu2Btn); // 찜 목록 페이지
        menu3Btn = (ImageView) findViewById(R.id.menu3Btn); // 후기 리스트 페이지
        menu4Btn = (ImageView) findViewById(R.id.menu4Btn); // 문의사항

        menu1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserPrivacyActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        menu2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                intent.putExtra("uemail", uemail);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });

        menu3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewListActivity.class);
                intent.putExtra("uemail", uemail);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });

        menu4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QnaActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });

    }

    View.OnClickListener menuClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu1Btn:
                    Intent intent = new Intent(getApplicationContext(), UserPrivacyActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("uemail", uemail);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                    break;
                case R.id.menu2Btn:
                    intent = new Intent(getApplicationContext(), PickListActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("uname", uname);
                    intent.putExtra("uemail", uemail);
                    startActivity(intent);
                    break;
                case R.id.menu3Btn:
                    intent = new Intent(getApplicationContext(), ReviewListActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("uname", uname);
                    intent.putExtra("uemail", uemail);
                    startActivity(intent);
                    break;
                case R.id.menu4Btn:
                    intent = new Intent(getApplicationContext(), QnaActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("uname", uname);
                    intent.putExtra("uemail", uemail);
                    startActivity(intent);
                    break;
            }
        }
    };

}

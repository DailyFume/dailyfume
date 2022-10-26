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
import android.widget.Toast;

public class MyPageActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    ImageView menu1Btn, menu2Btn, menu3Btn, menu4Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent = getIntent();
        String uemail = intent.getStringExtra("uemail");

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



        menu1Btn = (ImageView) findViewById(R.id.menu1Btn); // 개인정보 수정 페이지
        menu2Btn = (ImageView) findViewById(R.id.menu2Btn); // 찜 목록 페이지
        menu3Btn = (ImageView) findViewById(R.id.menu3Btn); // 후기 리스트 페이지
        menu4Btn = (ImageView) findViewById(R.id.menu4Btn); // 문의사항

        //imgVdf = (ImageView) findViewById(R.id.imgVdf);
        //imgVdf.setColorFilter(Color.LTGRAY);

        menu1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserPrivacyActivity.class);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        menu2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
               //intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });

//        menu3Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ReviewListActivity.class);
//                intent.putExtra("uid", uid);
//                startActivity(intent);
//                finish();
//            }
//        });

        menu4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QnaActivity.class);
                //intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });

//        findViewById(R.id.menu1Btn).setOnClickListener(menuClick); // 개인정보 수정 페이지
//        findViewById(R.id.menu2Btn).setOnClickListener(menuClick); // 찜 목록 페이지
//        findViewById(R.id.menu3Btn).setOnClickListener(menuClick); // 후기 리스트 페이지
//        findViewById(R.id.menu4Btn).setOnClickListener(menuClick); // 문의사항
//        findViewById(R.id.menu5Btn).setOnClickListener(menuClick); // 공지사항

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





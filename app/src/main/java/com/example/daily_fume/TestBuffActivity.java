package com.example.daily_fume;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class TestBuffActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;
    int progressn = 10; // 프로그래스바 변경할 값 변수 (기본값 10)
    ProgressBar testProgressBar;
    TextView testNumber, testText;
    Integer tNum;
    ImageView aTestSelect, bTestSelect, cTestSelect, dTestSelect;

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
        testIcon = (ImageView) findViewById(R.id.testIcon);
        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
        testText = (TextView) findViewById(R.id.testText);

        aTestSelect = (ImageView) findViewById(R.id.aTestSelect);
        bTestSelect = (ImageView) findViewById(R.id.bTestSelect);
        cTestSelect = (ImageView) findViewById(R.id.cTestSelect);
        dTestSelect = (ImageView) findViewById(R.id.dTestSelect);

        // 초기값 설정
        tNum = 1;
        testNumber.setText(tNum.toString());
        testNumPlay();

        aTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), selectorVisibleT1.getVisibility() + "A선택", Toast.LENGTH_SHORT).show();
                progressn = progressn + 10;
                testProgressBar.setProgress(progressn);
                tNum += 1;
                testNumber.setText(tNum.toString());
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
                testNumPlay();

            }
        });

        bTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), selectorVisibleT2.getVisibility() + "B선택", Toast.LENGTH_SHORT).show();
                progressn = progressn + 10;
                testProgressBar.setProgress(progressn);
                tNum += 1;
                testNumber.setText(tNum.toString());
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
                testNumPlay();
            }
        });

        cTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), selectorVisibleT1.getVisibility() + "C선택", Toast.LENGTH_SHORT).show();
                progressn = progressn + 10;
                testProgressBar.setProgress(progressn);
                tNum += 1;
                testNumber.setText(tNum.toString());
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
                testNumPlay();
            }
        });

        dTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), selectorVisibleT1.getVisibility() + "D선택", Toast.LENGTH_SHORT).show();
                progressn = progressn + 10;
                testProgressBar.setProgress(progressn);
                tNum += 1;
                testNumber.setText(tNum.toString());
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
                testNumPlay();
            }
        });


    }

    // 메서드
    void testNumPlay() {
        switch (tNum.intValue()) {
            case 1:
                testText.setText("1 . 자주 사용하는 향수의 계열은?");
                aTestSelect.setImageResource(R.drawable.test_buff_1_1);
                bTestSelect.setImageResource(R.drawable.test_buff_1_2);
                cTestSelect.setImageResource(R.drawable.test_buff_1_3);
                dTestSelect.setImageResource(R.drawable.test_buff_1_4);
                break;
            case 2:
                testText.setText("2 . 제일 좋아하는 계절은?");
                aTestSelect.setImageResource(R.drawable.test_buff_2_1);
                bTestSelect.setImageResource(R.drawable.test_buff_2_2);
                cTestSelect.setImageResource(R.drawable.test_buff_2_3);
                dTestSelect.setImageResource(R.drawable.test_buff_2_4);
                break;
            case 3:
                testText.setText("3 . 평소 즐겨 입는 옷 스타일은?");
                aTestSelect.setImageResource(R.drawable.test_buff_3_1);
                bTestSelect.setImageResource(R.drawable.test_buff_3_2);
                cTestSelect.setImageResource(R.drawable.test_buff_3_3);
                dTestSelect.setImageResource(R.drawable.test_buff_3_4);
                break;
            case 4:
                testText.setText("4 . 잠들기 전에 맡고 싶은 향은?");
                aTestSelect.setImageResource(R.drawable.test_buff_4_1);
                bTestSelect.setImageResource(R.drawable.test_buff_4_2);
                cTestSelect.setImageResource(R.drawable.test_buff_4_3);
                dTestSelect.setImageResource(R.drawable.test_buff_4_4);
                break;
            case 5:
                testText.setText("5 . 가장 마음에 드는 색은?");
                aTestSelect.setImageResource(R.drawable.test_buff_5_1);
                bTestSelect.setImageResource(R.drawable.test_buff_5_2);
                cTestSelect.setImageResource(R.drawable.test_buff_5_3);
                dTestSelect.setImageResource(R.drawable.test_buff_5_4);
                break;
        }
    }

//    void showTestNoCheck() {
//        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(TestBuffActivity.this)
//                .setTitle("선택하지 않은 항목이 있습니다.")
//                .setMessage("다시 한번 확인해주세요")
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//        AlertDialog msgDlg = msgBuilder.create();
//        msgDlg.show();
//    }

}

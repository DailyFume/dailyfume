package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserPrivacyActivity extends AppCompatActivity {

    ImageView backBtn, etc_icon;
    TextView title_change;
    Button priModifyBtn;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    EditText email_box, nickname_box, date_box, pw_box;
    // Button manBtnbox, womanBtnbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_privacy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("      개인 정보");

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


        // 수정하기 버튼 클릭 이벤트
        // ★ 사용자가 정보를 수정하기 위해 입력하거나 버튼을 클릭했다면 밑에 수정하기 버튼이나 중복확인 버튼이
        // 분홍색으로 바뀌어서 활성화 되어야 함.
        email_box = (EditText) findViewById(R.id.email_box);
        nickname_box = (EditText) findViewById(R.id.nickname_box);
        //date_box = (EditText) findViewById(R.id.date_box);
        pw_box = (EditText) findViewById(R.id.pw_box);
        //manBtnbox = (Button) findViewById(R.id.manBtnbox);
        //womanBtnbox = (Button) findViewById(R.id.womanBtnbox);

        priModifyBtn = (Button) findViewById(R.id.priModifyBtn);
        priModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModify(); // 팝업창으로 정말 수정할건지 한번 더 물어보기
            }
        });

        //womanBtnbox.setTextColor(Color.rgb(255,255,255));
        //womanBtnbox.setBackgroundColor(Color.rgb(230,182,190));

    }

    // ★ 옵션메뉴 하단 선(stoke) 넣어야 함

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_privacy_etc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.etc_logout:
                // ★ 로그아웃 클릭시 액션
                return true;
            case R.id.etc_delprivacy:
                // ★ 회원탈퇴 클릭시 액션
                return true;
        }
        return false;
    }

    // 수정하기 버튼 팝업창
    void showModify() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(UserPrivacyActivity.this)
                .setTitle("알림")
                .setMessage("회원정보를 수정하시겠습니까?")

                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "회원정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int whichButton) {
                  dialogInterface.cancel();
                }
        });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}
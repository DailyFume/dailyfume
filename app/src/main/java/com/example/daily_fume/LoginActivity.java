package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.Session;

public class LoginActivity extends AppCompatActivity {

    ImageView backBtn, joinBtn;
    TextView title_change;

    //카카오톡 로그인 버튼
    Button kakaoLogin;
    //LinearLayout linearLayout;
    private KakaoCallBack KakaoCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("LOGIN");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        // backBtn.setVisibility(View.GONE); // ★ 로그인 화면에는 뒤로가기 아이콘이 필요없어보임
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginBack();
            }
        });

        joinBtn = (ImageView) findViewById(R.id.joinPageBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        //카카오톡 로그인 구현

        viewInit();

        //linearLayout.bringToFront();
        //linearLayout.setVisibility(View.INVISIBLE);

        KakaoCallBack = new KakaoCallBack();
        Session.getCurrentSession().addCallback(KakaoCallBack);
        Session.getCurrentSession().checkAndImplicitOpen();

        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLogin.performClick();
            }
        });


    }


    void showLoginBack() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("알림")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int end = android.os.Process.myPid();
                        android.os.Process.killProcess(end);
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }


    //카카오톡 로그인 구현
    private void viewInit(){
        //linearLayout = findViewById(R.id.linearLayout);
        kakaoLogin = findViewById(R.id.kakaoLogin);
    }

    public void kakaoError(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(KakaoCallBack);
    }
}
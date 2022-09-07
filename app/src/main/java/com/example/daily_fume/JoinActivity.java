package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change, memberTerms;
    Button manBtn, womanBtn, joinBtn;
    CheckBox checkNo, checkYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("회원가입");

        backBtn = (ImageView) findViewById(R.id.back_icon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        manBtn = (Button) findViewById(R.id.manBtn);
        womanBtn = (Button) findViewById(R.id.womanBtn);

        manBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manBtn.setTextColor(Color.rgb(255,255,255));
                manBtn.setBackgroundColor(Color.rgb(230,182,190));
                womanBtn.setTextColor(Color.rgb(179,179,179));
                womanBtn.setBackgroundColor(Color.rgb(255,255,255));
            }
        });

        womanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                womanBtn.setTextColor(Color.rgb(255,255,255));
                womanBtn.setBackgroundColor(Color.rgb(230,182,190));
                manBtn.setTextColor(Color.rgb(179,179,179));
                manBtn.setBackgroundColor(Color.rgb(255,255,255));
            }
        });

        memberTerms = (TextView) findViewById(R.id.memberTerms);
        memberTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MemberTerms.class);
                startActivity(intent);
            }
        });

        joinBtn = (Button) findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, JoinYesActivity.class);
                startActivity(intent);
            }
        });

        checkNo = (CheckBox) findViewById(R.id.checkNo);
        checkYes = (CheckBox) findViewById(R.id.checkYes);

        checkNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJoinNoCheck();
            }
        });

    }

    void showJoinNoCheck() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(JoinActivity.this)
                .setTitle("알림")
                .setMessage("회원약관에 동의하지 않을 경우 가입이 불가합니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkNo.setChecked(false);
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}
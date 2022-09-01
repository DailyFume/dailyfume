package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JoinYesActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;
    Button gotoTestBtn, gotoHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_yes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("환영합니다");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setVisibility(View.INVISIBLE);

        gotoTestBtn = (Button) findViewById(R.id.gotoTestBtn);
        gotoTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinYesActivity.this, TestMainActivity.class);
                startActivity(intent);
            }
        });

        gotoHomeBtn = (Button) findViewById(R.id.gotoHomeBtn);
        gotoHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinYesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
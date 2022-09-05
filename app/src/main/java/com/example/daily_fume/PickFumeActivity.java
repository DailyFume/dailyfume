package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class PickFumeActivity extends AppCompatActivity {

    ImageView backBtn, pickFumeDel;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner spinnerPickFume;
    String[] PfItemsFume = { "  기본  ", "  최신순  ", "  이름순  "};
    GridView pickFumeGridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_fume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("기본 상품"); // ★ 나중에는 사용자가 입력한 박스이름으로 변경되게 하기

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.searchIcon);
        // loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // searchIcon.setOnClickListener();
        // loveIcon.setOnClickListener();

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // ★ 찜한 상품이 없을경우 pick_zero 레이아웃으로 변경
        // 위 경우에는 스피너와 상품삭제 클릭 안되게 하기 (FALSE)로

        // 스피너 - 폴더정렬
        spinnerPickFume = (Spinner) findViewById(R.id.spinnerPickFume);

        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, PfItemsFume);
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPickFume.setAdapter(fadapter);

        spinnerPickFume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //PfItemsBox.setText(PfItems[position]);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(199, 131, 142));
                ((TextView)parent.getChildAt(0)).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                switch(position) {
                    case 0 :
                        // ★ 임시 (기본)
                        break;
                    case 1 :
                        // ★ 임시 (최신순)
                        break;
                    case 2 :
                        // ★ 임시 (이름순)
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 찜한 상품 삭제
        pickFumeDel = (ImageView) findViewById(R.id.pickFumeDel);
        pickFumeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ★ 삭제 아이콘 클릭시 각 상품목록의 가운데에 체크할 수 있는 체크박스 뜨게 하기
                // 삭제 아이콘 있던 곳에는 (완료) 글자가 있게하기
                // 삭제할 상품 체크 후 완료를 누르면 현재 폴더에서 체크한 상품들이 사라지게 하기
            }
        });

        // 그리드뷰 - 찜한 상품 목록
        pickFumeGridview = (GridView) findViewById(R.id.pickFumeGridview);
        // ★ 해당 상품을 클릭하면 해당 상품의 상세페이지로 이동 해야 함

        // FumeGridView gAdapter = new


    }


    void pickFumeList() { //
        ArrayAdapter<String> pickFumeAdapter = new ArrayAdapter<String>(this,
                R.layout.pick_fumelist_layout);
        pickFumeGridview.setAdapter(pickFumeAdapter);

    }
}
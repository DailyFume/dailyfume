package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class PickZeroActivity extends AppCompatActivity {

    ImageView backBtn, pickFumeDel, zero_love;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner spinnerPickFume;
    String[] PfItemsFume = { "  기본  ", "  최신순  ", "  이름순  "};

    // 위 경우의 레이아웃이 적용되면 삭제아이콘과 스피너가 클릭이 불가해야 함
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_zero);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        // 새로 만든 그룹 이름
        Intent groupDataIntent = getIntent();
        GroupData groupData = (GroupData) groupDataIntent.getSerializableExtra("groupData");
        title_change.setText(groupData.getGroupTitle());
        //title_change.setText("기본 폴더"); // ★ 나중에는 사용자가 입력한 박스이름으로 변경되게 하기

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        //zero_love = (ImageView) findViewById(R.id.zero_love);
        //zero_love.setColorFilter(Color.parseColor("#FAF1F3"));

        // 하단바 액션
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.searchIcon);
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

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
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

        // 찜한 상품 삭제
        pickFumeDel = (ImageView) findViewById(R.id.pickFumeDel);
        pickFumeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ★ 삭제 아이콘 클릭시 각 상품목록의 가운데에 체크할 수 있는 체크박스 뜨게 하기
                // 삭제 아이콘 있던 곳에는 (완료) 글자가 있게하기
                // 삭제할 상품 체크 후 완료를 누르면 현재 폴더에서 체크한 상품들이 사라지게 하기
                // 우선은 임시로 찜한 상품 없을 때 레이아웃으로 변경하는 기능 넣음. (이 레이아웃은 나중에 찜한 상품 없을때 자동 적용되어야 함/ 신규일때도)
                // 위 경우의 레이아웃이 적용되면 삭제아이콘과 스피너가 클릭이 불가해야 함

            }
        });



    }


}
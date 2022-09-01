package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PickListActivity extends AppCompatActivity {

    ImageView backBtn, pickBoxNew;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner spinnerPick;
    String[] PfItems = { "  기본  ", "  최신순  ", "  이름순  "};
    ListView pickBoxList;
    List<String> TitleValues = new ArrayList<>();
    List<Integer> PickNumValue = new ArrayList<>();

    String boxTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("찜한 상품");

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


        // 스피너 - 폴더정렬
        spinnerPick = (Spinner) findViewById(R.id.spinnerPick);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, PfItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPick.setAdapter(adapter);



        spinnerPick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //PfItemsBox.setText(PfItems[position]);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(199, 131, 142));
                ((TextView)parent.getChildAt(0)).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                switch(position) {
                    case 0 :
                        // ★ 임시
                        break;
                    case 1 :
                        // ★ 임시
                        break;
                    case 2 :
                        // ★ 임시
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 새폴더 추가
        pickBoxNew = (ImageView) findViewById(R.id.pickBoxNew);
        pickBoxNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickBoxNew();
            }
        });

        // 찜폴더 관리
        pickBoxList = (ListView) findViewById(R.id.pickBoxList);

        TitleValues.add("기본 서랍");
        pickBoxLoading();

    }

    void pickBoxLoading() { // 폴더 추가 메서드 - ★ 단 새로고침하면 기존 폴더들이 사라짐 (개선하기)
        ArrayAdapter<String> pickboxadapter = new ArrayAdapter<String>(this,
                R.layout.picbox_layout, R.id.boxTitle, TitleValues);
        pickBoxList.setAdapter(pickboxadapter);
    }


    void showPickBoxNew() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("폴더명을 입력하세요");
        alert.setMessage("최대 8자까지 가능합니다.");
        final EditText boxName = new EditText(this);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(8); //글자수 제한
        boxName.setFilters(FilterArray);
        alert.setView(boxName);

        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ★ 확인 버튼 클릭시 액션
                boxTxt = boxName.getText().toString();
                TitleValues.add(boxTxt);
                pickBoxLoading();
                Toast.makeText(getApplicationContext(), boxTxt + "폴더가 추가되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ★ 취소 버튼 클릭시 액션
                dialog.cancel();
            }
        });
        alert.show();

    }
}
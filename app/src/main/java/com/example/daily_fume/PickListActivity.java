package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickListActivity extends AppCompatActivity {

    ImageView backBtn, pickBoxNew;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner spinnerPick;
    String[] PfItems = { "  기본  ", "  최신순  ", "  이름순  "};
    ListView pickBoxList;
    ArrayList<String> TitleValues = new ArrayList<String>();
    ArrayList<Integer> PickNumValue = new ArrayList<Integer>();

    String listname;
    Button BoxModifyBtn, BoxDeleteBtn;


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

        // 찜폴더 관리
        pickBoxList = (ListView) findViewById(R.id.pickBoxList);
        TitleValues.add("기본 그룹");

        for (int i = 0; i < TitleValues.size(); i++) {
            pickBoxLoading();
        }


        // ★ 리스트뷰와 버튼 조합일 시 주의사항
        // 1. 리스트뷰 포커스를 false로 해야 함
        // 2. 리스트뷰에 들어간 레이아웃에서 버튼들 xml에 clickable과 focusable은 false로 해야 함
        // 3. getView 메서드로 클릭하게 해줘야 됨.

/*
        // 어댑터 파일로 이동됨
        pickBoxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override  // ★ 각각의 폴더 선택하면 해당 찜한 상품이 저장된 폴더로 이동 나중에 수정하기
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (TitleValues.get(position)){
                    case "기본 그룹" :
                        Intent intent = new Intent(getApplicationContext(), PickFumeActivity.class);
                        startActivity(intent); // 임시 (찜한 향수들 목록이 있는 페이지로 이동)
                        break;
                }
            }
        }); */

        // 새폴더 추가
        pickBoxNew = (ImageView) findViewById(R.id.pickBoxNew);
        pickBoxNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickBoxNew();
            }
        });

    }


    // 메서드

    void pickBoxLoading() { // 폴더 추가 메서드 - ★ 단 새로고침하면 기존 폴더들이 사라짐 (개선하기)
        ButtonListAdapter pickboxadapter = new ButtonListAdapter( this, TitleValues);
        pickBoxList.setAdapter(pickboxadapter);

    }

    void pickBoxClick() {
        pickBoxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override  // ★ 각각의 폴더 선택하면 해당 찜한 상품이 저장된 폴더로 이동 나중에 수정하기
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(getApplicationContext(), PickFumeActivity.class);
                // intent.putExtra("it_listData", TitleValues.get(position));
                // startActivity(intent); // 임시
                //Toast.makeText(getApplicationContext(), " 그룹 클릭", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void showPickBoxNew() { // 새로 박스 만들기
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("그룹명을 입력하세요");
        alert.setMessage("최대 8자까지 가능합니다.");
        final EditText boxName = new EditText(this);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(8); //글자수 제한
        boxName.setFilters(FilterArray);
        alert.setView(boxName);

        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ★ 확인 버튼 클릭시 액션
                listname = boxName.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                TitleValues.add(listname);
                                pickBoxLoading();
                                Toast.makeText(getApplicationContext(), listname + "폴더가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "폴더 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                LikeRequest likeRequest = new LikeRequest(listname, responseListener);
                RequestQueue queue = Volley.newRequestQueue( PickListActivity.this );
                queue.add(likeRequest);
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

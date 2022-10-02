package com.example.daily_fume;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    Button testGoButton;
    ImageView openD, closeD, topButton;
    DrawerLayout drawerLayout;
    View dView;
    NavigationView naviView;
    ScrollView HomeScrollView;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    // 뷰페이저 변수
    private ViewPager viewPager;
    private TextViewPagerAdapter pagerAdapter;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

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

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        dView = (View) findViewById(R.id.dView);


        // 드로어 화면을 열고 닫을 객체 참조
        openD = (ImageView) findViewById(R.id.slide_icon);
        closeD = (ImageView) findViewById(R.id.backCon);

        openD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(dView);
            }
        });

        closeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(dView);
            }
        });

        naviView = (NavigationView) findViewById(R.id.nav_view);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_1:
                        Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(dView);
                        return true;

                    case R.id.nav_2:
                            intent = new Intent(getApplicationContext(), PickListActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(dView);
                        return true;

                    case R.id.nav_3:
                        intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(dView);
                        return true;
                }
                return false;
            }
        });

        testGoButton = (Button) findViewById(R.id.testGoButton);
        testGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ★ 수정할 부분
                // 네비게이션 뷰가 열려있는 상태에서는 버튼 클릭 안되게 하기

                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
            }
        });

        // 뷰페이저
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new TextViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 9) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        // top 버튼 클릭시 상단으로 이동
        topButton = (ImageView) findViewById (R.id.topButton);
        HomeScrollView = (ScrollView) findViewById(R.id.HomeScrollView);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

    }



}
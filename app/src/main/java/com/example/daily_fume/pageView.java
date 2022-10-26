package com.example.daily_fume;

import android.os.Bundle;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


// 임시
public class pageView  extends AppCompatActivity {

    // 뷰페이저 변수
    private ViewPager FumeDetailPager;
    private pageViewA pagerAdapter;
    private ArrayList<FragranceData> arrayList;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fume_floral);

        // 뷰페이저
        arrayList = new ArrayList<FragranceData>(); //
        FumeDetailPager = findViewById(R.id.FumeDetailPager);
        // pagerAdapter = new TextViewPagerAdapter(this);
        pagerAdapter = new pageViewA(this);
        FumeDetailPager.setAdapter(pagerAdapter);

        FumeDetailPager.setOffscreenPageLimit(3); // 꼭 해야 함. 초기화하다보면 쓸데없이 메모리 사용량이 늘어나게 됨.
        // 처음 한번에 다운 받아서 그대로 사용하게 하면 메모리 사용량을 줄일 수 있음.
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3) {
                    currentPage = 0;
                }
                FumeDetailPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

}

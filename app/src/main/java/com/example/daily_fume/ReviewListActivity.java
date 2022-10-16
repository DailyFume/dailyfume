package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;
    LinearLayout review_create_btn;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    // 리뷰 목록
    TextView ReviewNum; // 리뷰 총 갯수 텍스트
    int ReviewN = 0; // 리뷰 총 갯수
    TextView RBrand, RTitle, Rstr; // 브랜드, 상품명, 내용
    int IntStars; // 별점 - 안되면 빼기
    RatingBar Rstars; // 레이팅바(별점)
    ImageView RImg; // 포토리뷰

    ArrayList<ReviewListData> reviewListData;
    ListView ReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("후기 리스트");

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

        // 리뷰 작성 페이지로 이동
        review_create_btn = (LinearLayout) findViewById(R.id.review_create_btn);
        review_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewCreateActivity.class);
                startActivity(intent);
            }
        });

        // 리뷰 리스트 목록
        this.InitReviewListData();
        ReviewList = (ListView) findViewById(R.id.ReviewList);
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, reviewListData);
        ReviewList.setAdapter(reviewListAdapter);
        //Toast.makeText(getApplicationContext(), ReviewListNum.size()+"", Toast.LENGTH_SHORT).show();

        ReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 임시용 (어디로 이동해야 할지? 아님 이동이 필요없는지)
            }
        });

        // 리뷰가 3개 이상인 경우 아래로 스크롤 하라고 알려주기
        if (reviewListData.size() >= 3) {
            Toast.makeText(getApplicationContext(), "↓ 아래로 스크롤하세요", Toast.LENGTH_SHORT).show();
        }

        for (int i = 1; i <= reviewListData.size(); i++) {
            //Toast.makeText(getApplicationContext(), ReviewListNum.size()+"", Toast.LENGTH_SHORT).show();
            ReviewN = ReviewN + 1;
            //Toast.makeText(getApplicationContext(), ReviewN+"", Toast.LENGTH_SHORT).show();
            reviewNumRe();
        }

    }

    // 메서드
    void reviewNumRe() {
        ReviewNum = (TextView) findViewById(R.id.ReviewNum);
        ReviewNum.setText("("+ReviewN+")");
    }

    void InitReviewListData() {
        reviewListData = new ArrayList<ReviewListData>();
        reviewListData.add(new ReviewListData("바이레도", "모하비 고스트", "친구한테 선물받아서 써봤는데 너무 맘에 들어요! 막 전형적인 꽃향기보다 은은한데 뭔가 지나칠때 어? 뭐지? 하고 돌아보게 만드는 그런 매력적인 향이예요! 특히 잔향도 부드럽고 너무 맘에 들어서 다음에 또 구매하려구요 앞으로 제 인생템이 될 것 같아요"
                ,3,R.drawable.fume02));
        reviewListData.add(new ReviewListData("딥디크", "플레르 드 뽀", "친구한테 선물받아서 써봤는데 너무 맘에 들어요! 막 전형적인 꽃향기보다 은은한데 뭔가 지나칠때 어? 뭐지? 하고 돌아보게 만드는 그런 매력적인 향이예요! 특히 잔향도 부드럽고 너무 맘에 들어서 다음에 또 구매하려구요 앞으로 제 인생템이 될 것 같아요"
                ,5,R.drawable.fume01));

        // 임시 추가
        reviewListData.add(new ReviewListData("불가리", "불가리 골데아 더 로만 나이트", "친구한테 선물받아서 써봤는데 맘에 들지 않아요 ㅠㅠㅠㅠ! 패키지가 우아하고 고급스러워서 너무 맘에 들었는데 향이 너무 무겁게 느껴져서 제 타입은 아니라 아쉽네요. 다음에 좀 더 가벼운 향기로 다시 구매해볼 생각이예요 다른 분들 참고해주세요"
                ,2,R.drawable.fume03));

    }
}
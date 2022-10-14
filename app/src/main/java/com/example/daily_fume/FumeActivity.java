package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FumeActivity extends AppCompatActivity {

    ImageView FumeBackIcon, pickBtn, pickAfterBtn;
    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;
    ImageView FumeTopButton;
    ScrollView FumeDetailView;

    // 변경 될 데이터
    TextView DetailTitle; // 향수 상품명 (영문)
    TextView DetailKorean; // 향수 상품명 (국문)
    TextView BrandText; // 향수 브랜드
    TextView TypeText; // 향수 계열
    TextView VolText; // 향수 용량
    ImageView TitleImg; // 향수 메인 이미지
    TextView topnote, middlenote, basenote; // 향수 노트
    TextView DetailTag1, DetailTag2, DetailTag3, DetailTag4, DetailTag5, DetailTag6, DetailTag7, DetailTag8; // 향수 해시태그

    // 리뷰
    ListView review_listview;
    ArrayList<ReviewData> reviewData;
    TextView reviewCountNum;
    ImageView review_more_Btn;
    ImageView review_create_go; // 리뷰 없을때 리뷰 작성 버튼
    int ReviewHeight;
    int totalHeight;
    LinearLayout review_on, review_off;

    // 뷰페이저 변수
    private ViewPager FumeDetailPager;
    private TextViewPagerAdapter pagerAdapter;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ★ 임시로 상세페이지 이동
        // 사용자가 클릭한 상품의 이름과 계열에 맞는 상세페이지로 이동되게 변경하기
        setContentView(R.layout.fume_aldehyde);
        FumeDetailPage();

    }

    // 메서드 (상품 상세페이지 이벤트)
    void FumeDetailPage() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        FumeBackIcon = (ImageView) findViewById(R.id.FumeBackIcon);
        FumeBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        FumeTopButton = (ImageView) findViewById(R.id.FumeTopButton);
        FumeDetailView = (ScrollView) findViewById(R.id.FumeDetailView);
        FumeTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FumeDetailView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.);
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

        // 찜 pick
        pickBtn = (ImageView) findViewById(R.id.pickBtn);
        pickAfterBtn = (ImageView) findViewById(R.id.pickAfterBtn);

        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ★ 어디 그룹으로 찜할지 목록 리스트 정하기
                // 찜 완료하였거나 이미 찜한 상품의 경우 자동으로 다른 이미지로 변경 ↓
                // pickBtn.setImageResource(R.drawable.pickafterbutton);
                // 또는 Visibility()로 관리
                // 찜 클릭시 그룹 선택 후 확인을 누르면 해당 상품이 선택한 그룹에 추가되어야 함
                if (pickBtn.getVisibility() == View.VISIBLE) {
                    final String[] pickGroupTitle = {"기본 그룹", "임시 그룹", "그룹1", "그룹2", "그룹3"};  // 임시 그룹 (나중에 db랑 연결)
                    final int[] selectedIndex = {0};
                    AlertDialog.Builder GroupDialog = new AlertDialog.Builder(FumeActivity.this);
                    GroupDialog.setTitle("그룹을 선택하세요.")
                            .setSingleChoiceItems(pickGroupTitle,0,new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selectedIndex[0] = which;
                                }})
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(FumeActivity.this, pickGroupTitle[selectedIndex[0]]+"에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                    pickBtn.setVisibility(View.GONE);
                                    pickAfterBtn.setVisibility(View.VISIBLE);
                                    // ★ 사용자가 선택한 그룹에 해당 상품이 찜 추가 되어야 함
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                // ★ 취소 버튼 클릭시 액션
                                dialog.cancel();
                            }
                            })
                            .create().show();
                }
            }
        });


        pickAfterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickNoCheck(); // 찜 해제 팝업창
            }
        });

        // 리뷰 기능
        // ★ 리뷰가 존재할 때는 review_on이 Visible이어야 하고 review_off가 Gone이어야 함.
        // 리뷰가 하나도 없을 때는 review_off가 Visible이어야 하고 review_on이 Gone이어야 함.
        review_on = (LinearLayout) findViewById(R.id.review_on);
        review_off = (LinearLayout) findViewById(R.id.review_off);
        reviewCountNum = (TextView) findViewById(R.id.reviewCountNum); // 리뷰 갯수 카운트

        this.InitReviewData();
        review_listview = (ListView) findViewById(R.id.review_listview);
        final ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviewData);
        review_listview.setAdapter(reviewAdapter);
        review_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        reviewAdapter.getItem(position).getNickName(),
                        Toast.LENGTH_LONG).show(); // 임시용 (어디로 이동해야 할지? 아님 이동이 필요없는지)
            }
        });

        review_listview.setOnTouchListener(new View.OnTouchListener() { // 스크롤뷰 내에 리스트뷰가 있는경우 스크롤 가능하게 하기
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //FumeDetailView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // 리뷰 더보기 눌렀을 때
        review_more_Btn = (ImageView) findViewById(R.id.review_more_Btn);
        review_more_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewHeight = review_listview.getHeight();
                totalHeight = ReviewHeight + 600;
                ViewGroup.LayoutParams params = review_listview.getLayoutParams();
                params.height = totalHeight;
                review_listview.setLayoutParams(params);
                moreReviewData();
                reviewAdapter.notifyDataSetChanged();
            }
        });

        review_create_go = (ImageView) findViewById(R.id.review_create_go);
        review_create_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewCreateActivity.class);
                startActivity(intent);
            }
        });

        // 뷰페이저
        FumeDetailPager = findViewById(R.id.FumeDetailPager);
        pagerAdapter = new TextViewPagerAdapter(this);
        FumeDetailPager.setAdapter(pagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 9) {
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


    void showPickNoCheck() {
        pickBtn = (ImageView) findViewById(R.id.pickBtn);
        pickAfterBtn = (ImageView) findViewById(R.id.pickAfterBtn);
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(FumeActivity.this)
                .setTitle("")
                .setMessage("찜을 해제하시겠습니까?")

                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 찜목록에서 사라지기
                        Toast.makeText(getApplicationContext(), "찜이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                        pickAfterBtn.setVisibility(View.GONE);
                        pickBtn.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    void InitReviewData() { // 임시 데이터
        reviewData = new ArrayList<ReviewData>();
        reviewData.add(new ReviewData(R.drawable.review_img_01, "cutehyejin**님",
                "말해 뭐해 두말하면  입아픈 스테디셀러 제품이에요 자스민향이 진하게 올라오는게 계속 맡고싶은 냄새에요 !담번엔 같은 계열로 다른향수 사보려구요 !"));
        reviewData.add(new ReviewData(R.drawable.review_img_02, "manju01** 님",
                "알데하이드 계열 향수를 처음 구매해봤어요! 향수를  어떤걸 사야할 지 몰랐는데 델리퓸에서 비교하고 구매했더니 대만족 입니다 ㅎㅎ 향수 입문자에게 너무 좋네요 최애 앱입니다! "));
        reviewData.add(new ReviewData(R.drawable.review_img_03, "sujiniii**님",
                "향기라는게 취향을 많이타서.. 친구선물 고민하다가 델리퓸에서 향수테스트 친구한테 시켜보고 바로 주문했는데 성숙하고 깔꼼한 친구 이미지에 찰떡! 생일선물 사줄때 완전 유용하고 조아요"));
        reviewData.add(new ReviewData(R.drawable.review_img_04, "dooju98**님",
                "반오십 된 기념으로 저에게 주는 선물이에요~~! 이제 이십대 중반이니까 나이에 맞는 향을 찾아다녔는데 추천 테스트에서 맞춤향수로 뜬거보고 샀더니 찰떡같이 제 스타일 이였어요!!"));

    }

    void moreReviewData() { // 임시 리뷰 데이터 추가
        reviewData.add(new ReviewData(R.drawable.review_img_04, "cutehyejin**님",
                "말해 뭐해 두말하면  입아픈 스테디셀러 제품이에요 자스민향이 진하게 올라오는게 계속 맡고싶은 냄새에요 !담번엔 같은 계열로 다른향수 사보려구요 !"));
        reviewData.add(new ReviewData(R.drawable.review_img_03, "manju01** 님",
                "알데하이드 계열 향수를 처음 구매해봤어요! 향수를  어떤걸 사야할 지 몰랐는데 델리퓸에서 비교하고 구매했더니 대만족 입니다 ㅎㅎ 향수 입문자에게 너무 좋네요 최애 앱입니다! "));
        //reviewData.add(new ReviewData(R.drawable.review_img_02, "sujiniii**님",
        //        "향기라는게 취향을 많이타서.. 친구선물 고민하다가 델리퓸에서 향수테스트 친구한테 시켜보고 바로 주문했는데 성숙하고 깔꼼한 친구 이미지에 찰떡! 생일선물 사줄때 완전 유용하고 조아요"));
        //reviewData.add(new ReviewData(R.drawable.review_img_01, "dooju98**님",
        //        "반오십 된 기념으로 저에게 주는 선물이에요~~! 이제 이십대 중반이니까 나이에 맞는 향을 찾아다녔는데 추천 테스트에서 맞춤향수로 뜬거보고 샀더니 찰떡같이 제 스타일 이였어요!!"));
    }
}
package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    TextView reviewCountNum; // 리뷰 숫자 표시
    int fumeReviewN = 0; // 리뷰 숫자 변수
    ImageView review_more_Btn;
    ImageView review_create_go; // 리뷰 없을때 리뷰 작성 버튼
    int ReviewHeight;
    int totalHeight;
    LinearLayout review_on, review_off; // 리뷰 있을때와 없을때 레이아웃 VISIBLE로 관리

    // 뷰페이저 변수
    private pageViewA pagerAdapterA;
    private ViewPager FumeDetailPager;
    private TextViewPagerAdapter pagerAdapter;
    private ArrayList<FragranceData> farrayList;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    // 향수 데이터 불러오기
    private static String IP_ADDRESS = "43.200.245.161";
    private static String TAG = "detailphp";
    private ArrayList<FragranceData> detailList;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    private DetailAdapter detailAdapter;
    String fnamek;

     String TAG_JSON = "dailyfume";
     String TAG_FNAME = "fname";
     String TAG_FNAMEK = "fnamek";
     String TAG_FBRAND ="fbrand";
     String TAG_FTYPE = "ftype";
     String TAG_FVOL = "fvol";
     String TAG_IMAGE = "fdetail";
     String TAG_TOPNOTE = "ftop";
     String TAG_MIDDLENOTE = "fmiddle";
     String TAG_BASENOTE = "fbase";
     String TAG_FTAG = "ftag";
     Intent detailIntent;

     // 찜 그룹 불러오기
    String listname;
    ArrayList<GroupData> groupDataList;
    String serverURL = "http://43.200.245.161/getlikelist.php";
    private static String TAG1 = "getlikelistphp";
    private static final String TAG_LISTNAME = "listname";
    ButtonListAdapter buttonListAdapter;
    ArrayList<String> pickGroupTitle;

    //
    int uid;
    String uname;

    // 찜 목록에 향수 상품 저장
    //  fragrance_fid 이거랑 likelist_lid 즉 향수아이디랑 찜폴더아이디
    private static String TAGLIKEFUME = "dailyfume";
    String likefumeURL = "http://43.200.245.161/likefume.php";
    String TAG_FUMENUM = "fid";
    String TAG_LISTNAME_ID = "lid";
    int fid;
    int lid;

    // 디테일 레이아웃
    HashMap<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ★ 임시로 상세페이지 이동
        // 사용자가 클릭한 상품의 이름과 계열에 맞는 상세페이지로 이동되게 변경하기
//        String type = map.get("TAG_TYPE");
//        Log.e("type", type);
//        Toast.makeText(getApplicationContext(), type + "type", Toast.LENGTH_SHORT).show();
//
//        switch (type) {
//            case "1400" :
//                setContentView(R.layout.fume_green);
//                Toast.makeText(getApplicationContext(),"그린",Toast.LENGTH_SHORT).show();
//                FumeDetailPage();
//                break;
//        }

        setContentView(R.layout.fume_green_re);
        // 데이터 불러오기
        mRecyclerView = (RecyclerView) findViewById(R.id.detailRecycle);
        detailList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FumeActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        detailAdapter = new DetailAdapter(FumeActivity.this, detailList);
        mRecyclerView.setAdapter(detailAdapter);
        mRecyclerView.setItemViewCacheSize(1);
        mRecyclerView.hasFixedSize();
        detailIntent = getIntent();
        uid = detailIntent.getExtras().getInt("uid");
        fnamek = detailIntent.getExtras().getString("title is");
        uname = detailIntent.getStringExtra("uname");

        // 상품 정보 불러오기
        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/detail.php", fnamek);
        FumeDetailPage();

        // 사용자 찜 목록 불러오기
        GetoData getoData = new GetoData(serverURL, uid);
        GetData1 task1 = new GetData1();
        task1.execute(getoData);
        groupDataList = new ArrayList<>();

    }

    // 메서드 (상품 상세페이지 이벤트)
    void FumeDetailPage() {
            // intent로 향수
            Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
            setSupportActionBar(toolbar);

            // 그룹을 담을 리스트
            pickGroupTitle = new ArrayList<String>();

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
            searchIcon = (ImageView) findViewById(R.id.searchIcon);
            loveIcon = (ImageView) findViewById(R.id.loveIcon);
            mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

            homeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                    finish();
                }
            });

            testIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                    finish();
                }
            });

            searchIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                    finish();
                }
            });

            loveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                    finish();
                }
            });

            mypageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                    finish();
                }
            });

            // 찜 pick
        pickBtn = (ImageView) findViewById(R.id.pickBtn);
        pickAfterBtn = (ImageView) findViewById(R.id.pickAfterBtn);

        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // ★ 어디 그룹으로 찜할지 목록 리스트 정하기
             //찜 완료하였거나 이미 찜한 상품의 경우 자동으로 다른 이미지로 변경 ↓
             // pickBtn.setImageResource(R.drawable.pickafterbutton);
            // 또는 Visibility()로 관리
            // 찜 클릭시 그룹 선택 후 확인을 누르면 해당 상품이 선택한 그룹에 추가되어야 함
                if (pickBtn.getVisibility() == View.VISIBLE) {
                    final String[] groupasList = (String[]) pickGroupTitle.toArray((new String[0])); // string으로 변환
                    if (groupasList.length == 0) { // 그룹이 없다면
                        groupNopop();
                    } else { // 아니라면 그룹이 있다면
                        final int[] selectedIndex = {0}; // 선택 변수 값
                        AlertDialog.Builder GroupDialog = new AlertDialog.Builder(FumeActivity.this);
                        GroupDialog.setTitle("그룹을 선택하세요.")
                                .setSingleChoiceItems(groupasList, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedIndex[0] = which;
                                    }
                                })
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 해당 상품id가 찜목록 해당 그룹폴더에 저장되어야 함
                                        TaskParams params = new TaskParams(likefumeURL, fid, lid);
                                        //Toast.makeText(getApplicationContext(), fid + "/" + lid, Toast.LENGTH_SHORT).show();
                                        InsertData insertData = new InsertData();
                                        insertData.execute(params);
                                        pickBtn.setVisibility(View.GONE);
                                        pickAfterBtn.setVisibility(View.VISIBLE);
                                        //★ 사용자가 선택한 그룹에 해당 상품이 찜 추가 되어야 함
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
        }
    }); // pickbtn end


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
       fumeReviewNumEvent(); // ★ 리뷰 총 갯수 (총 갯수는 db에서 바로 가지고오므로 more버튼 눌러도 변함이 없어야 함) - 수정하기
            review_listview = (ListView) findViewById(R.id.review_listview);
            final ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviewData);
            review_listview.setAdapter(reviewAdapter);
            review_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Toast.makeText(getApplicationContext(), reviewAdapter.getItem(position).getNickName(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ReviewSelectActivity.class);
                    intent.putExtra("reviewImg", reviewData.get(position).getReviewImg());
                    intent.putExtra("nickName", reviewData.get(position).getNickName());
                    intent.putExtra("reviewStr", reviewData.get(position).getReviewStr());
                    intent.putExtra("reviewStars", reviewData.get(position).getReviewStars());
                    startActivity(intent); // 리뷰 상세보기 페이지로 이동
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
//                moreReviewData();
                    reviewAdapter.notifyDataSetChanged();

                    // 리뷰가 더 없을 경우 토스트메시지로 "마지막 리뷰 입니다" 알려주기
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
//            farrayList = new ArrayList<FragranceData>();
//            FumeDetailPager = findViewById(R.id.FumeDetailPager);
//            pagerAdapter = new TextViewPagerAdapter(FumeActivity.this, farrayList);
//            FumeDetailPager.setAdapter(pagerAdapter);
        FumeDetailPager = findViewById(R.id.FumeDetailPager);
//        pagerAdapter = new TextViewPagerAdapter(this, arrayList);
//        viewPager.setAdapter(pagerAdapter);
        pagerAdapterA = new pageViewA(this);
        FumeDetailPager.setAdapter(pagerAdapterA);
        pagerAdapterA.notifyDataSetChanged();

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
                "말해 뭐해 두말하면  입아픈 스테디셀러 제품이에요 자스민향이 진하게 올라오는게 계속 맡고싶은 냄새에요 !담번엔 같은 계열로 다른향수 사보려구요 !",4));
        reviewData.add(new ReviewData(R.drawable.review_img_02, "manju01** 님",
                "알데하이드 계열 향수를 처음 구매해봤어요! 향수를  어떤걸 사야할 지 몰랐는데 델리퓸에서 비교하고 구매했더니 대만족 입니다 ㅎㅎ 향수 입문자에게 너무 좋네요 최애 앱입니다! ",5));
        reviewData.add(new ReviewData(R.drawable.review_img_03, "sujiniii**님",
                "향기라는게 취향을 많이타서.. 친구선물 고민하다가 델리퓸에서 향수테스트 친구한테 시켜보고 바로 주문했는데 성숙하고 깔꼼한 친구 이미지에 찰떡! 생일선물 사줄때 완전 유용하고 조아요",3));
        reviewData.add(new ReviewData(R.drawable.review_img_04, "dooju98**님",
                "반오십 된 기념으로 저에게 주는 선물이에요~~! 이제 이십대 중반이니까 나이에 맞는 향을 찾아다녔는데 추천 테스트에서 맞춤향수로 뜬거보고 샀더니 찰떡같이 제 스타일 이였어요!!",4));

    }

    void moreReviewData() { // 임시 리뷰 데이터 추가
        reviewData.add(new ReviewData(R.drawable.review_img_04, "cutehyejin**님",
                "말해 뭐해 두말하면  입아픈 스테디셀러 제품이에요 자스민향이 진하게 올라오는게 계속 맡고싶은 냄새에요 !담번엔 같은 계열로 다른향수 사보려구요 !",4));
        reviewData.add(new ReviewData(R.drawable.review_img_03, "manju01** 님",
                "알데하이드 계열 향수를 처음 구매해봤어요! 향수를  어떤걸 사야할 지 몰랐는데 델리퓸에서 비교하고 구매했더니 대만족 입니다 ㅎㅎ 향수 입문자에게 너무 좋네요 최애 앱입니다! ",3));
        //reviewData.add(new ReviewData(R.drawable.review_img_02, "sujiniii**님",
        //        "향기라는게 취향을 많이타서.. 친구선물 고민하다가 델리퓸에서 향수테스트 친구한테 시켜보고 바로 주문했는데 성숙하고 깔꼼한 친구 이미지에 찰떡! 생일선물 사줄때 완전 유용하고 조아요",4));
        //reviewData.add(new ReviewData(R.drawable.review_img_01, "dooju98**님",
        //        "반오십 된 기념으로 저에게 주는 선물이에요~~! 이제 이십대 중반이니까 나이에 맞는 향을 찾아다녔는데 추천 테스트에서 맞춤향수로 뜬거보고 샀더니 찰떡같이 제 스타일 이였어요!!",5));
    }

    void fumeReviewNumEvent() {
        for (fumeReviewN = 0; fumeReviewN <= reviewData.size(); fumeReviewN++) {
            fumeReviewNumRe();
        }
    }

    void fumeReviewNumRe() { // 리뷰 갯수 보이기
        reviewCountNum = (TextView) findViewById(R.id.reviewCountNum);
        reviewCountNum.setText("총 리뷰 ("+fumeReviewN+")");
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(FumeActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);
            if (result == null) {
                Log.d(TAG, "response - " + errorString);
            } else {

                mJsonString = result;
                showResult();
                detailAdapter.notifyDataSetChanged(); // 갱신 여기에서 하기
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "fnamek=" + params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }

    }
    private void showResult(){

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                JSONObject item = jsonArray.getJSONObject(0);

                Bitmap image = StringToBitMap(item.getString(TAG_IMAGE));
                String name = item.getString(TAG_FNAME);
                String namek = item.getString(TAG_FNAMEK);
                String brand = item.getString(TAG_FBRAND);
                String type = item.getString(TAG_FTYPE);
                String vol = item.getString(TAG_FVOL);
                String top = item.getString(TAG_TOPNOTE);
                String middle = item.getString(TAG_MIDDLENOTE);
                String base = item.getString(TAG_BASENOTE);
                String tag = item.getString(TAG_FTAG);
                fid = item.getInt(TAG_FUMENUM);

                List<String> taglist = new ArrayList<String>();
                String[] arrays = tag.split("#");
                for (int n=0; n < arrays.length; n++) {
                    taglist.add(arrays[n]);
                }

                // 상세페이지
                map.put("TAG_TYPE", type);

                FragranceData fragranceData = new FragranceData();

                fragranceData.setFragrance_image(image);
                fragranceData.setFragrance_name(name);
                fragranceData.setFragrance_namek(namek);
                fragranceData.setFragrance_brand(brand);
                fragranceData.setFragrance_type(type);
                fragranceData.setFragrance_vol(vol);
                fragranceData.setFragrance_top(top);
                fragranceData.setFragrance_middle(middle);
                fragranceData.setFragrance_base(base);
                fragranceData.setFragrance_tag1("#"+taglist.get(1));
                fragranceData.setFragrance_tag2("#"+taglist.get(2));
                fragranceData.setFragrance_tag3("#"+taglist.get(3));
                fragranceData.setFragrance_tag4("#"+taglist.get(4));
                fragranceData.setFragrance_tag5("#"+taglist.get(5));
                fragranceData.setFragrance_tag6("#"+taglist.get(6));
                fragranceData.setFragrance_tag7("#"+taglist.get(7));
                fragranceData.setFragrance_tag8("#"+taglist.get(8));

                detailList.add(fragranceData);
//                detailAdapter.notifyDataSetChanged();
                //detailAdapter.notifyItemRangeChanged(0, 1);
//                startActivity(detailIntent);



        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

    public static Bitmap StringToBitMap(String image) {
        Log.e("StringToBitmap", "StringToBitmap");
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.e("StringToBitmap", "success");
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static class GetoData {
        int uid;
        String serverURL;

        GetoData(String serverURL, int uid) {
            this.serverURL = serverURL;
            this.uid = uid;
        }
    }

    private class GetData1 extends AsyncTask<GetoData, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(FumeActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG1, "response : " + result);
            mJsonString = result;
            showResultgroup();
        }

        @Override
        protected String doInBackground(GetoData... params) {
            String serverURL = params[0].serverURL;
            int uid = params[0].uid;

            String postParameters = "user_uid=" + uid;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG1, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG1, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    private void showResultgroup() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String listname = item.getString(TAG_LISTNAME);
                lid = item.getInt(TAG_LISTNAME_ID);

                GroupData groupData = new GroupData();
                groupData.setGroupTitle(listname);
                groupDataList.add(groupData);
            }

            int totalElements = groupDataList.size();
            //Toast.makeText(getApplicationContext(), groupDataList.size()+"", Toast.LENGTH_SHORT).show();
            for (int index = 0; index < totalElements; index++) {
                //Toast.makeText(getApplicationContext(), groupDataList.get(index).getGroupTitle() + "", Toast.LENGTH_SHORT).show();
                pickGroupTitle.add(groupDataList.get(index).getGroupTitle());
                //Toast.makeText(getApplicationContext(), pickGroupTitle.size()+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.d(TAG1, "showResult: ", e);
        } catch (NullPointerException e) {
            Log.d(TAG1, "pickGroupTitle: ", e);
        }
    }

    void groupNopop() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage("생성된 그룹이 없습니다. 찜 메뉴에서 그룹을 추가해주세요");
        alert.setPositiveButton("찜 바로가기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ★ 확인 버튼 클릭시 액션
                Intent intent  = new Intent( getApplicationContext(), PickListActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
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

    private static class TaskParams {
        String likefumeURL;
        int fumenum;
        int listnameId;;

        TaskParams(String likefumeURL, int fumenum, int listnameId) {
            this.likefumeURL = likefumeURL;
            this.fumenum = fumenum;
            this.listnameId = listnameId;
        }
    }

    class InsertData extends AsyncTask<TaskParams, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(FumeActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAGLIKEFUME, "POST response  - " + result);
            //pickBoxLoading();
            Toast.makeText(getApplicationContext(), fid + "상품을 "+lid+"에 찜하였습니다.", Toast.LENGTH_SHORT).show();
            //buttonListAdapter.notifyDataSetChanged();//

        }

        @Override
        protected String doInBackground(TaskParams... params) {
            String likefumeURL = params[0].likefumeURL;
            int fumenum = params[0].fumenum;
            int listnameId = params[0].listnameId;

            String postParameters = "fragrance_fid=" + fumenum + "&likelist_lid=" + listnameId; // db열

            try {
                URL url = new URL(likefumeURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAGLIKEFUME, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();
            } catch (Exception e) {
                Log.d(TAGLIKEFUME, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }


}

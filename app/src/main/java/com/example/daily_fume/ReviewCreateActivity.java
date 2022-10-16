package com.example.daily_fume;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ReviewCreateActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner search_spinner_brand, search_spinner_title;
    String Bnumber, Tnumber;
    RatingBar review_ratingBar;
    ImageView photo_choiceBtn;
    Button reviewCreateBtn;
    TextView brandText, titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("후기 작성");

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
            }
        });


        // 스피너
        search_spinner_brand = (Spinner) findViewById(R.id.search_spinner_brand);
        search_spinner_title = (Spinner) findViewById(R.id.search_spinner_title);
        brandText = (TextView) findViewById(R.id.brandText);
        titleText = (TextView) findViewById(R.id.titleText);
        brandText.setVisibility(View.INVISIBLE);
        titleText.setVisibility(View.INVISIBLE);
        // 기본값
        search_spinner_title.setEnabled(false); // 상품명 비활성화


        // 1) 브랜드 검색 스피너
        ArrayList<String> brandList = new ArrayList<>(); // 리스트 생성
        // 데이터 담기 (우선은 임시 나중에는 db 랑 연결해서 브랜드 부분만 가져오게)
        brandList.add("--- 선택하세요 ---");
        brandList.add("샤넬");
        brandList.add("디올");
        brandList.add("버버리");
        brandList.add("조말론");
        brandList.add("베르사체");
        brandList.add("크리드");
        brandList.add("키엘");
        brandList.add("조르지오 아르마니");
        // 스피너에 브랜드 리스트 적용
        search_spinner_brand.setAdapter(new ArrayAdapter<>(ReviewCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, brandList));
        // 브랜드 선택 이벤트
        search_spinner_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bnumber = parent.getItemAtPosition(position).toString(); // 변수에 선택한 브랜드 담기
                // ★ 나중에 이 변수를 이용해서 후기글이(DB에) 저장되어야 함
                // Log.e(Bnumber,Bnumber+"값");
                brandText.setText(Bnumber);
                search_spinner_title.setEnabled(true);
                title_spinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 별점 (레이팅바)
        review_ratingBar = (RatingBar) findViewById(R.id.review_ratingBar);
        review_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBarEvent();
            }
        });

        // 갤러리 사진 선택
        photo_choiceBtn = (ImageView) findViewById(R.id.photo_choiceBtn);
        photo_choiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("사진을 선택해주세요");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                String[] mimeTypes = {"image/jpg", "image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent);
            }
        });


    }

    // 메서드
    void title_spinner() {
        // 2) 상품명 검색 스피너
        ArrayList<String> titleList = new ArrayList<>(); // 리스트 생성
        // 데이터 담기 (우선은 임시 나중에는 db 랑 연결해서 브랜드 부분만 가져오게)

        switch (String.valueOf(Bnumber)) {
            case "" :
                //Toast.makeText(getApplicationContext(), "브랜드를 선택해주세요", Toast.LENGTH_SHORT).show();
                break;
            case "--- 선택하세요 ---" :
                Toast.makeText(getApplicationContext(), "브랜드를 선택해주세요", Toast.LENGTH_SHORT).show();
                break;

            case "샤넬":
                //Toast.makeText(getApplicationContext(), "샤넬", Toast.LENGTH_SHORT).show();
                titleList.add("--- 선택하세요 ---");
                titleList.add("넘버 파이브");
                titleList.add("넘버 나인틴");
                titleList.add("샹스 오 땅드르");
                break;

            case "디올":
                titleList.add("--- 선택하세요 ---");
                titleList.add("자도르");
                break;

            case "버버리":
                titleList.add("--- 선택하세요 ---");
                titleList.add("히어로");
                break;

            case "조말론":
                titleList.add("--- 선택하세요 ---");
                titleList.add("라임바질 앤 만다린");
                titleList.add("블랙베리 앤 베이");
                break;

            case "베르사체":
                titleList.add("--- 선택하세요 ---");
                titleList.add("크리스탈 느와르");
                break;

            case "크리드":
                titleList.add("--- 선택하세요 ---");
                titleList.add("실버마운틴 워터");
                break;

            case "키엘":
                titleList.add("--- 선택하세요 ---");
                titleList.add("오리지널 머스크");
                break;

            case "조르지오 아르마니":
                titleList.add("--- 선택하세요 ---");
                titleList.add("프리베 앙브르 이첸트리코");
                break;
        }
        // 스피너에 상품명 리스트 적용
        search_spinner_title.setAdapter(new ArrayAdapter<>(ReviewCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, titleList));
        // 브랜드 선택 이벤트
        search_spinner_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Tnumber = parent.getItemAtPosition(position).toString(); // 변수에 선택한 상품명 담기
                titleText.setText(Tnumber);
                // ★ 나중에 이 변수를 이용해서 후기글이(DB에) 저장되어야 함
                switch (String.valueOf(Tnumber)) {
                    case "":
                        //Toast.makeText(getApplicationContext(), "상품명을 선택해주세요", Toast.LENGTH_SHORT).show();
                        break;
                    case "--- 선택하세요 ---":
                        Toast.makeText(getApplicationContext(), "상품명을 선택해주세요", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void ratingBarEvent() {
        review_ratingBar = (RatingBar) findViewById(R.id.review_ratingBar);
        Toast.makeText(getApplicationContext(), String.valueOf(review_ratingBar.getRating()) + "점", Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        Bitmap bitmap = ((BitmapDrawable)photo_choiceBtn.getDrawable()).getBitmap();
                        float scale = (float) (1024/(float)bitmap.getWidth());
                        int image_w = (int) (bitmap.getWidth() * scale);
                        int image_h = (int) (bitmap.getHeight() * scale);
                        Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        Uri uri = intent.getData();
                        // intent.putExtra("uri", uri.toString());
                        Glide.with(ReviewCreateActivity.this)
                                .load(uri)
                                .into(photo_choiceBtn); // 사진 붙이기

                    }
                }
            }
    );

}
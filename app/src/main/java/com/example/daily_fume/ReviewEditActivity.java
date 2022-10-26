package com.example.daily_fume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ReviewEditActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    RatingBar review_ratingBar;
    ImageView photo_choiceBtn;
    TextView review_box;
    Button reviewCreateBtn;
    TextView brandText, titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("후기 수정");

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

        // 인텐트 값 전달받기
//        Intent intent = getIntent();
//        String  Edit_RBrand = intent.getStringExtra("RBrand");
//        String  Edit_RTitle = intent.getStringExtra("RTitle");
//        String  Edit_Rstr = intent.getStringExtra("Rstr");
//        int Edit_IntStars = intent.getIntExtra("IntStars", 0);
//        int Edit_RImg = intent.getIntExtra("RImg",0);
        //Toast.makeText(getApplicationContext(), Edit_RBrand + "", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), Edit_RTitle + "", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), Edit_Rstr + "", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), Edit_IntStars + "", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), Edit_RImg + "", Toast.LENGTH_SHORT).show();


        // 기본값 (단 수정페이지에서는 브랜드와 상품명은 수정이 안됨) - 대부분의 쇼핑몰도 그렇고, 검색스피너 사용하기 위해
        review_box = (TextView) findViewById(R.id.review_box);
        review_ratingBar = (RatingBar) findViewById(R.id.review_ratingBar);
        photo_choiceBtn = (ImageView) findViewById(R.id.photo_choiceBtn);
        brandText = (TextView) findViewById(R.id.brandText);
        titleText = (TextView) findViewById(R.id.titleText);
        brandText.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);

//        brandText.setText(Edit_RBrand);
//        titleText.setText(Edit_RTitle);
//        review_ratingBar.setRating(Edit_IntStars);
//        review_box.setText(Edit_Rstr);
//        photo_choiceBtn.setImageResource(Edit_RImg);

            // 별점 (레이팅바)
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

            // 후기 수정 버튼
            reviewCreateBtn = (Button) findViewById(R.id.reviewCreateBtn);
            reviewCreateBtn.setText("수정하기");
            reviewCreateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder RModifyBuilder = new AlertDialog.Builder(ReviewEditActivity.this);
                    RModifyBuilder.setTitle("")
                            .setMessage("해당 리뷰를 수정하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // ★ 해당 리뷰 수정하는 코드
                                    Toast.makeText(ReviewEditActivity.this, "수정 되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int whichButton) {
                                    dialogInterface.cancel();
                                }
                            })
                            .create().show();
                }
            });



    }

    // 메서드

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
                        Glide.with(ReviewEditActivity.this)
                                .load(uri)
                                .into(photo_choiceBtn); // 사진 붙이기

                    }
                }
            }
    );

}

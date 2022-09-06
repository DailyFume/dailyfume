package com.example.daily_fume;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PickFumeActivity extends AppCompatActivity {

    ImageView backBtn, pickFumeDel;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner spinnerPickFume;
    String[] PfItemsFume = { "  기본  ", "  최신순  ", "  이름순  "};
    GridView pickFumeGridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_fume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("기본 폴더"); // ★ 나중에는 사용자가 입력한 박스이름으로 변경되게 하기

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

        // ★ 찜한 상품이 없을경우 pick_zero 레이아웃으로 변경
        // 위 경우에는 스피너와 상품삭제 클릭 안되게 하기 (FALSE)로

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
                pick_zero(); // 임시로 이동

            }
        });


        // 그리드뷰 - 찜한 상품 목록
        pickFumeGridview = (GridView) findViewById(R.id.pickFumeGridview);
        // ★ 해당 상품을 클릭하면 해당 상품의 상세페이지로 이동 해야 함
        FumeGridAdapter adapter = new FumeGridAdapter();
        adapter.addItem(new Fume("딥디크","플레르 드 뽀", R.drawable.fume01));
        adapter.addItem(new Fume("바이레도","모하비 고스트", R.drawable.fume02));
        adapter.addItem(new Fume("불가리","골데아 더 로만 나이트", R.drawable.fume03));
        adapter.addItem(new Fume("딥디크","플레르 드 뽀", R.drawable.fume01));
        adapter.addItem(new Fume("바이레도","모하비 고스트", R.drawable.fume02));
        adapter.addItem(new Fume("불가리","골데아 더 로만 나이트", R.drawable.fume03));
        adapter.addItem(new Fume("딥디크","플레르 드 뽀", R.drawable.fume01));
        adapter.addItem(new Fume("바이레도","모하비 고스트", R.drawable.fume02));
        adapter.addItem(new Fume("불가리","골데아 더 로만 나이트", R.drawable.fume03));
        pickFumeGridview.setAdapter(adapter);

    }


    // 그리드뷰 생성자 클래스
    public class Fume {
        String brand;
        String fumeName;
        int iamgesResId;

        public Fume(String brand, String fumeName, int iamgesResId) {
            this.brand = brand;
            this.fumeName = fumeName;
            this.iamgesResId = iamgesResId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getFumeName() {
            return fumeName;
        }

        public void setFumeName(String fumeName) {
            this.fumeName = fumeName;
        }

        public int getIamgesResId() {
            return iamgesResId;
        }

        public void setIamgesResId(int iamgesResId) {
            this.iamgesResId = iamgesResId;
        }
    }

    public class Fumes_View extends LinearLayout {
        ImageView fumeImages;
        TextView pickFumeBrand;
        TextView pickFumeTitle;

        public Fumes_View(Context context) {
            super(context);
            init(context);
        }

        public Fumes_View(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public void init(Context context){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.pick_fumelist_layout, this, true);
            fumeImages = findViewById(R.id.fumeImages);
            pickFumeBrand = findViewById(R.id.pickFumeBrand);
            pickFumeTitle = findViewById(R.id.pickFumeTitle);
        }

        public void setImageView(int ResId){
            fumeImages.setImageResource(ResId);
        }
        public void setBrand(String brand){
            pickFumeBrand.setText(brand);
        }
        public void setFumeName(String fumeName){
            pickFumeTitle.setText(fumeName);
        }

    }


    public class FumeGridAdapter extends BaseAdapter{
        ArrayList<Fume> items = new ArrayList<>();
        @Override
        public int getCount() {
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(Fume fume){
            this.items.add(fume);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Fumes_View fumes_view = null;
            if(convertView == null){
                fumes_view = new Fumes_View(getApplicationContext());
            }else{
                fumes_view = (Fumes_View) convertView;
            }
            Fume fume = items.get(position);
            fumes_view.setBrand(fume.getBrand());
            fumes_view.setFumeName(fume.getFumeName());
            fumes_view.setImageView(fume.getIamgesResId());

            return fumes_view;
        }
    }


    // 찜한 향수가 없는 경우의 레이아웃
    void pick_zero() {
        setContentView(R.layout.activity_pick_zero);
        // 레이아웃이 변경되었을때 다시 변수들 찾아와서 코드 지정해주어야 함
        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("기본 폴더");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "눌렸어요", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
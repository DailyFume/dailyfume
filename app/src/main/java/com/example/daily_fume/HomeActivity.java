package com.example.daily_fume;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    Button testGoButton, testGoButton2;
    ImageView openD, closeD, topButton;
    DrawerLayout drawerLayout;
    View dView;
    NavigationView naviView;
    ScrollView HomeSView;
    LinearLayout fumeb01, fumeb02, fumeb03;
    TextView fume_01_brand, fume_02_brand, fume_03_brand, fume_01_title, fume_02_title, fume_03_title;
    boolean position_flag = true;

    ArrayList<FragranceData> arrayList;
    String mJsonString;

    private static String IP_ADDRESS = "43.200.245.161";
    private static String TAG = "GetData";

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    // 뷰페이저 변수
    private ViewPager viewPager;
    private TextViewPagerAdapter pagerAdapter;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_home_2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);
        LinearLayout whiteBACK = findViewById(R.id.whiteBACK);

        testIcon = (ImageView) findViewById(R.id.testIcon);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);
        searchIcon = (ImageView) findViewById(R.id.searchIcon);

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");

        Intent intent1 = getIntent();
        String uemail = intent1.getStringExtra("uemail");

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uemail", uemail);
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
                dView.setClickable(true); // 메뉴바 열었으니 뒤에 홈화면 레이아웃 등 클릭 안되게 하기
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
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        drawerLayout.closeDrawer(dView);
                        return true;

                    case R.id.nav_3:
                        intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        intent.putExtra("uid", uid);
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

        arrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/get_json.php", "");

        // 뷰페이저
        viewPager = findViewById(R.id.ViewPager);
        pagerAdapter = new TextViewPagerAdapter(this, arrayList);
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

        // ★임시 - 상세페이지 보기 위함
        fumeb01 = (LinearLayout) findViewById(R.id.fumeb01);
        fumeb02 = (LinearLayout) findViewById(R.id.fumeb02);
        fumeb03 = (LinearLayout) findViewById(R.id.fumeb03);
        fume_01_brand = (TextView) findViewById(R.id.fume_01_brand);
        fume_02_brand = (TextView) findViewById(R.id.fume_02_brand);
        fume_03_brand = (TextView) findViewById(R.id.fume_03_brand);
        fume_01_title = (TextView) findViewById(R.id.fume_01_title);
        fume_02_title = (TextView) findViewById(R.id.fume_02_title);
        fume_03_title = (TextView) findViewById(R.id.fume_03_title);

        fumeb01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FumeActivity.class);
                intent.putExtra("brand is", fume_01_brand.getText());
                intent.putExtra("title is", fume_01_title.getText());
                startActivity(intent);
            }
        });

        fumeb02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FumeActivity.class);
                intent.putExtra("brand is", fume_02_brand.getText());
                intent.putExtra("title is", fume_02_title.getText());
                startActivity(intent);
            }
        });

        fumeb03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FumeActivity.class);
                intent.putExtra("brand is", fume_03_brand.getText());
                intent.putExtra("title is", fume_03_title.getText());
                startActivity(intent);
            }
        });

        // top 버튼 클릭시 상단으로 이동
        topButton = (ImageView) findViewById (R.id.topButton);
        HomeSView = (ScrollView) findViewById(R.id.HomeSView);
        testGoButton2 = (Button) findViewById(R.id.testGoButton2);
        testGoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
            }
        });

        // 스크롤뷰 터치시 기존 버튼 사라지고 최상단에 버튼 생기기
        HomeSView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: // 스크롤뷰 움직이는 동안
                        whiteBACK.setVisibility(View.VISIBLE);
                        testGoButton.setVisibility(View.GONE);
                        testGoButton2.setVisibility(View.VISIBLE); // 테스트 버튼이 상단에 생겨남
                        break;
                }
                return false;
            }
        });

        // 스크롤이 최상단에 위치하게 되면 상단 버튼 사라지고 기존 버튼 보이기
        HomeSView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (position_flag) {
                    if ((!v.canScrollVertically(1))) {
                        //Toast.makeText(getApplicationContext(), "최하단", Toast.LENGTH_SHORT).show();
                    } else if ((!v.canScrollVertically(-1))) {
                        whiteBACK.setVisibility(View.GONE);
                        testGoButton2.setVisibility(View.GONE);
                        testGoButton.setVisibility(View.VISIBLE);
                        //Toast.makeText(getApplicationContext(),"최상단 입니다.", Toast.LENGTH_SHORT).show();
                    }
                    position_flag = false;
                }
                else position_flag = true;
            }
        });

        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeSView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(HomeActivity.this,
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
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

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
        String TAG_JSON="dailyfume";
        String TAG_IMAGE = "fimg";
        String TAG_NAME = "fnamek";
        String TAG_BRAND ="fbrand";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                Bitmap image = StringToBitMap(item.getString(TAG_IMAGE));
                String name = item.getString(TAG_NAME);
                String brand = item.getString(TAG_BRAND);

                FragranceData fragranceData = new FragranceData();

                fragranceData.setFragrance_image(image);
                fragranceData.setFragrance_brand(brand);
                fragranceData.setFragrance_name(name);

                arrayList.add(fragranceData);
                pagerAdapter.notifyDataSetChanged();
            }
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

    // 종료 메서드
    void showBackPressed() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(HomeActivity.this)
                .setTitle("알림")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int end = android.os.Process.myPid();
                        android.os.Process.killProcess(end);
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }



}

package com.example.daily_fume;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.Collections;
import java.util.Comparator;

public class PickFumeActivity extends AppCompatActivity {

    ImageView backBtn, pickFumeDel;
    TextView title_change;
    TextView pickfumnum;
    int Numpick;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    // 찜이 없을때
    RelativeLayout pickzerolatout;
    Button pickfumegoBtn;
    ImageView zero_love;
    TextView tvtv1, tvtv2;

    Spinner spinnerPickFume;
    String[] PfItemsFume = { "  기본  ", "  최신순  ", "  이름순  ", "  브랜드순  "};
    GridView pickFumeGridview;

    //
    ArrayList<PickFume> pfArrayList;
    private PickFumeAdapter pickFumeAdapter;
    private RecyclerView pickFumeRecycle;

    // 데이터 가져오기
    String serverURL = "http://43.200.245.161/get_likefume.php";
    private static final String TAG_JSON = "dailyfume";
    private static String TAG = "getlikefumephp";
    private static final String TAG_LIKEID = "like_id";
    private static final String TAG_FID = "fragrance_fid";
    private static final String TAG_LISTID = "likelist_lid";
    private static final String TAG_FIMG = "fimg";
    private static final String TAG_FBRAND = "fbrand";
    private static final String TAG_FNAMEK = "fnamek";
    String mJsonString;
    int listid;

    int uid;
    String uname;
    String uemail;
    public static Context pfCon;
    String groupname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_fume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        pfCon = this; // 어댑터와 연결

        Intent intent = getIntent();
        uid = intent.getExtras().getInt("uid");
        uname = intent.getStringExtra("uname");
        uemail = intent.getStringExtra("uemail");
        groupname = intent.getStringExtra("groupname");
        listid = intent.getExtras().getInt("picklistid");
        //Toast.makeText(getApplicationContext(), listid+"리스트숫자",Toast.LENGTH_SHORT).show();//

        GetoData getoData = new GetoData(serverURL, listid);
        GetData task1 = new GetData();
        task1.execute(getoData);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText(groupname); // 사용자가 클릭한 박스 이름으로

        pickfumnum = (TextView) findViewById(R.id.pickfumnum);

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), PickListActivity.class);
                intent1.putExtra("uid", uid);
                intent1.putExtra("uname", uname);
                intent1.putExtra("uemail", uemail);
                startActivity(intent1);
                finish();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        // loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });
        // loveIcon.setOnClickListener();

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });


        // 스피너 - 폴더정렬
        spinnerPickFume = (Spinner) findViewById(R.id.spinnerPickFume);

        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, PfItemsFume);
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPickFume.setAdapter(fadapter);

        spinnerPickFume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //PfItemsBox.setText(PfItems[position]);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(199, 131, 142));
                ((TextView)parent.getChildAt(0)).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                switch(position) {
                    case 0 :
                        // ★ (기본 - 최근등록이 아래로)
                        Collections.sort(pfArrayList);
                        pickFumeAdapter.notifyDataSetChanged();
                        break;
                    case 1 :
                        // ★ (최신순 - 최근등록이 위로)
                        Collections.sort(pfArrayList, Collections.reverseOrder());
                        pickFumeAdapter.notifyDataSetChanged();
                        break;
                    case 2 :
                        // ★ (향수 이름순)
                        pfArrayList.sort(new PickFumeNameSort());
                        pickFumeAdapter.notifyDataSetChanged();
                        break;
                    case 3 :
                        // ★ (향수 브랜드순)
                        pfArrayList.sort(new PickFumeBrandSort());
                        pickFumeAdapter.notifyDataSetChanged();
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

            }
        });


        // 리사이클러뷰
        pickFumeRecycle = (RecyclerView) findViewById(R.id.pickFumeRecycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        pickFumeRecycle.setLayoutManager(gridLayoutManager);

        pfArrayList = new ArrayList<>();

        pickFumeAdapter = new PickFumeAdapter(this, pfArrayList);
        pickFumeRecycle.setAdapter(pickFumeAdapter);

    }


    // 데이터 (찜 상품) 가져오기
    private static class GetoData {
        String serverURL;
        int lid;


        GetoData(String serverURL, int lid) {
            this.serverURL = serverURL;
            this.lid = lid;
        }
    }

    private class GetData extends AsyncTask<GetoData, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PickFumeActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response : " + result);
            mJsonString = result;
            showResult();

            Numpick = pfArrayList.size();
            pickfumnum.setText("총 "+Numpick+"개");

            if (pfArrayList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "찜한 상품이 없습니다.", Toast.LENGTH_SHORT).show();
                fumePickZero();
            }

        }

        @Override
        protected String doInBackground(GetoData... params) {
            String serverURL = params[0].serverURL;
            int lid = params[0].lid;

            String postParameters = "likelist_lid=" + lid;

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
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                Log.d(TAG, "ListFumeGetData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            // Toast.makeText(getApplicationContext(), jsonArray.length()+"", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int likeid = item.getInt(TAG_LIKEID); // 찜한 순서 정렬을 위해 필요
                int fid = item.getInt(TAG_FID);
                listid = item.getInt(TAG_LISTID);
                Bitmap fimage = StringToBitMap(item.getString(TAG_FIMG));
                String fbrand = item.getString(TAG_FBRAND);
                String fnamek = item.getString(TAG_FNAMEK);

                PickFume pickFume = new PickFume();
                pickFume.setLikeid(likeid);
                pickFume.setFid(fid);
                pickFume.setFumeName(fnamek);
                pickFume.setBrand(fbrand);
                pickFume.setIamgesResId(fimage);

                pfArrayList.add(pickFume);
                pickFumeAdapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
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

    void fumePickZero() { // 찜한 상품이 없는 경우
        pickzerolatout = (RelativeLayout) findViewById(R.id.pickzerolatout);
        pickFumeRecycle = (RecyclerView) findViewById(R.id.pickFumeRecycle);
        pickFumeRecycle.setVisibility(View.GONE);
        pickzerolatout.setVisibility(View.VISIBLE);

        zero_love = (ImageView) findViewById(R.id.zero_love);
        tvtv1 = (TextView) findViewById(R.id.tvtv1);
        tvtv2 = (TextView) findViewById(R.id.tvtv2);
        zero_love.setVisibility(View.VISIBLE);
        tvtv1.setVisibility(View.VISIBLE);
        tvtv2.setVisibility(View.VISIBLE);

        pickfumegoBtn = (Button) findViewById(R.id.pickfumegoBtn);
        pickfumegoBtn.setVisibility(View.VISIBLE);
        pickfumegoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class); // 홈으로 유도 (배너광고 많은 곳으로)
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
                finish();
            }
        });

    }

}

class PickFumeNameSort implements Comparator<PickFume> {
    @Override
    public int compare(PickFume o1, PickFume o2) {
        return o1.getFumeName().compareTo(o2.getFumeName());
    }
}

class PickFumeBrandSort implements Comparator<PickFume> {
    @Override
    public int compare(PickFume o1, PickFume o2) {
        return o1.getBrand().compareTo(o2.getBrand());
    }
}
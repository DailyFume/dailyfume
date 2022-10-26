package com.example.daily_fume;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    EditText content;

    ArrayList mArrayList, nameList;
    String mJsonString;
    float rate;
    String image;
    String serverURL = "http://43.200.245.161/review.php";
    String rcontent;

    HashMap<String, Integer> map = new HashMap<String, Integer>();

    private static String IP_ADDRESS = "43.200.245.161";
    private static String TAG = "GetData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("후기 작성");

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");
        String uname = intent.getStringExtra("uname");
        String uemail = intent.getStringExtra("uemail");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
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
                startActivity(intent);
                finish();
            }
        });

        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uid", uid);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
            }
        });

        mArrayList = new ArrayList<>();
        nameList = new ArrayList<>();

        // 스피너
        search_spinner_brand = (Spinner) findViewById(R.id.search_spinner_brand);
        search_spinner_title = (Spinner) findViewById(R.id.search_spinner_title);
        brandText = (TextView) findViewById(R.id.brandText);
        titleText = (TextView) findViewById(R.id.titleText);
        brandText.setVisibility(View.INVISIBLE);
        titleText.setVisibility(View.INVISIBLE);
        // 기본값
        search_spinner_title.setEnabled(false); // 상품명 비활성화

        content = (EditText) findViewById(R.id.review_box);

        // 1) 브랜드 검색 스피너
        ArrayList<String> brandList = new ArrayList<>(); // 리스트 생성
        // 데이터 담기 (우선은 임시 나중에는 db 랑 연결해서 브랜드 부분만 가져오게)
        brandList.add("--- 선택하세요 ---");
        brandList.add("DIOR");
        brandList.add("Dolce & Gabbana");
        brandList.add("BYREDO");
        brandList.add("CHANNEL");
        brandList.add("GUCCI");
        brandList.add("BURBURRY");
        brandList.add("Jo Malone");
        brandList.add("Diptyque");
        brandList.add("ESTEE LAUDER");
        brandList.add("GIVENCHY");
        brandList.add("Elizabeth Arden");
        brandList.add("BALMAIN");
        brandList.add("Fresh");
        brandList.add("L'Occitane");
        brandList.add("VERSACE");
        brandList.add("BVLGARI");
        brandList.add("Aigner");
        brandList.add("Lolita Lempicka");
        brandList.add("MOSCHINO");
        brandList.add("MARC JACOBS");
        brandList.add("CREED");
        brandList.add("GAP");
        brandList.add("Acqua di Parma");
        brandList.add("KENZO");
        brandList.add("Kiehle's");
        brandList.add("THE BODY SHOP");
        brandList.add("TOMFORD");
        brandList.add("LE LABO");
        brandList.add("GIORGIO ARMANI");
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
                String brand = search_spinner_brand.getSelectedItem().toString();

                GetData task = new GetData();
                task.execute("http://" + IP_ADDRESS + "/get_name.php", brand);
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
                rate = rating;
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

        reviewCreateBtn = (Button) findViewById(R.id.reviewCreateBtn);
        reviewCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rcontent = content.getText().toString();
                content.setText("");

                int fid = map.get("TAG_ID");
                // System.out.println(fid);

                PostParams params = new PostParams(serverURL, rcontent, rate, image, uid, fid);
                InsertData insertData = new InsertData();
                insertData.execute(params);

                Intent intent = new Intent(ReviewCreateActivity.this, ReviewListActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                startActivity(intent);
            }
        });


    }

    void title_spinner() {
        // 2) 상품명 검색 스피너
        ArrayList<String> titleList = new ArrayList<>(); // 리스트 생성
        // 스피너에 상품명 리스트 적용
        search_spinner_title.setAdapter(new ArrayAdapter<String>(ReviewCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, nameList));
        // 브랜드 선택 이벤트
        search_spinner_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Tnumber = parent.getItemAtPosition(position).toString(); // 변수에 선택한 상품명 담기
                titleText.setText(Tnumber);

                GetID getfid = new GetID();
                getfid.execute("http://" + IP_ADDRESS + "/get_id.php", Tnumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReviewCreateActivity.this,
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
            String postParameters = "fbrand=" + params[1];

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
        String TAG_NAME = "fnamek";
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String name = item.getString(TAG_NAME);
                nameList.add(name);

            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    private static class PostParams {
        String rcontent;
        float rate;
        String image;
        int uid;
        int fid;
        String serverURL;

        PostParams(String serverURL, String rcontent, float rate, String image, int uid, int fid) {
            this.rcontent = rcontent;
            this.rate = rate;
            this.image = image;
            this.uid = uid;
            this.fid=fid;
            this.serverURL = serverURL;
        }
    }

    class InsertData extends AsyncTask<PostParams, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReviewCreateActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
            mJsonString = result;

            if(result.equals("SUCCESS")){
                Toast.makeText(getApplicationContext(), "후기가 등록되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "후기 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(PostParams... params) {

            String serverURL = params[0].serverURL;
            String rcontent = params[0].rcontent;
            float rate = params[0].rate;
            String image = params[0].image;
            int uid = params[0].uid;
            int fid = params[0].fid;

            String postParameters = "rcontent=" + rcontent + "&rstar=" + rate + "&rimg=" + image + "&user_uid=" + uid + "&fragrance_fid=" + fid;

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
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }

    private class GetID extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReviewCreateActivity.this,
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
                getID();
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

                Log.d(TAG, "GetData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }
    private void getID(){
        String TAG_JSON="dailyfume";
        String TAG_ID = "fid";
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                Integer fid = item.getInt(TAG_ID);
                map.put("TAG_ID", fid);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
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
                                .into(photo_choiceBtn);// 사진 붙이기

                    }
                }
            }
    );

}
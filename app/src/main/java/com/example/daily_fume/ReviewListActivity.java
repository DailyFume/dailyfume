package com.example.daily_fume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import java.util.HashMap;
import java.util.List;

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

    Button deleteBtn;

    ArrayList<ReviewListData> reviewList;
    ReviewListAdapter adapter;

    String TAG = "dailyfume";
    String TAG_JSON = "review";
    String TAG_UID = "user_uid";
    String TAG_RID = "rid";
    String TAG_CONTENT = "rcontent";
    String TAG_RATE = "rstar";
    String TAG_FID = "fragrance_fid";
    String TAG_IMAGE = "fimg";
    String TAG_BRAND = "fbrand";
    String TAG_NAME = "fnamek";
    String mJsonString;
    String mJsonString1;

    String serverURL = "http://43.200.245.161/selectreview_u.php";
    String serverURL1 = "http://43.200.245.161/delete_review.php";

    private RecyclerView ReviewList;
    ScrollView ReviewScrollView;

    // 작성 리뷰 없을 때
    RelativeLayout reviewzerolatout;
    ImageView zero_review;
    TextView retv1, retv2;
    Button reviewCreategoBtn;

    HashMap<String, Integer> map = new HashMap<String, Integer>();

    int uid;
    String uname;
    String uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("후기 리스트");

        Intent intent = getIntent();
        uid = intent.getExtras().getInt("uid");
        uname = intent.getStringExtra("uname");
        uemail = intent.getStringExtra("uemail");

        GetReview getReview = new GetReview(serverURL, uid);
        GetData getData = new GetData();
        getData.execute(getReview);

        reviewList = new ArrayList<ReviewListData>();
        ReviewList = (RecyclerView) findViewById(R.id.ReviewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ReviewList.setLayoutManager(linearLayoutManager);

        adapter = new ReviewListAdapter(this, reviewList);
        ReviewList.setAdapter(adapter);
        adapter.notifyDataSetChanged();


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

        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
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
                intent.putExtra("uemail", uemail);
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
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
            }
        });


        adapter.setOnItemClickListener(new ReviewListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Toast.makeText(getApplicationContext(),String.valueOf(position)+"삭제 클릭", Toast.LENGTH_SHORT).show();
            }
        });

//        ReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 임시용 (어디로 이동해야 할지? 아님 이동이 필요없는지)
//            }
//        });

    }

    // 메서드
    void reviewNumRe() {
        ReviewNum = (TextView) findViewById(R.id.ReviewNum);
        ReviewNum.setText("("+ReviewN+")");
    }

    private static class GetReview {
        int uid;
        String serverURL;

        GetReview(String serverURL, int uid) {
            this.serverURL = serverURL;
            this.uid = uid;
        }
    }

    private class GetData extends AsyncTask<GetReview, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReviewListActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response : " + result);
            mJsonString = result;
            showResult();

            // 리뷰 갯수 확인
            ReviewNum = (TextView) findViewById(R.id.ReviewNum);
            ReviewN = reviewList.size();
            //Toast.makeText(getApplicationContext(), reviewList.size()+"", Toast.LENGTH_SHORT).show();
            ReviewNum.setText("("+ReviewN+")");

            // 리뷰 데이터가 비어있을 때
            if (reviewList.isEmpty()) {
                reviewZero();
            }
        }

        @Override
        protected String doInBackground(GetReview... params) {
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
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String content = item.getString(TAG_CONTENT);
                double rate = item.getDouble(TAG_RATE);
                Bitmap image = StringToBitMap(item.getString(TAG_IMAGE));
                String brand = item.getString(TAG_BRAND);
                String name = item.getString(TAG_NAME);
                int rid = item.getInt(TAG_RID);

                ReviewListData reviewListData = new ReviewListData();
                reviewListData.setContent(content);
                reviewListData.setRate(rate);
                reviewListData.setImage(image);
                reviewListData.setBrand(brand);
                reviewListData.setName(name);
                reviewList.add(reviewListData);

                map.put("rid", rid);
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }

    private static class DeleteReview {
        int rid;
        String serverURL1;

        DeleteReview(String serverURL1, int rid) {
            this.serverURL1 = serverURL1;
            this.rid = rid;
        }
    }

    private class DeleteData extends AsyncTask<DeleteReview, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ReviewListActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response : " + result);
            mJsonString = result;
            adapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(DeleteReview... params) {
            String serverURL = params[0].serverURL1;
            int rid = params[0].rid;

            String postParameters = "rid=" + rid;

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
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
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

    void reviewZero() { // 작성한 리뷰가 없는 경우
        ReviewScrollView = (ScrollView) findViewById(R.id.ReviewScrollView);
        ReviewScrollView.setVisibility(View.GONE);
        ReviewList.setVisibility(View.GONE);
        reviewzerolatout = (RelativeLayout) findViewById(R.id.reviewzerolatout);
        reviewzerolatout.setVisibility(View.VISIBLE);

        zero_review = (ImageView) findViewById(R.id.zero_review);
        retv1 = (TextView) findViewById(R.id.retv1);
        retv2 = (TextView) findViewById(R.id.retv2);
        reviewCreategoBtn = (Button) findViewById(R.id.reviewCreategoBtn);
        zero_review.setVisibility(View.VISIBLE);
        retv1.setVisibility(View.VISIBLE);
        retv2.setVisibility(View.VISIBLE);
        reviewCreategoBtn.setVisibility(View.VISIBLE);

        reviewCreategoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewCreateActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                startActivity(intent);
            }
        });
    }

}

package com.example.daily_fume;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SearchActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "43.200.245.161";
    private static String TAG = "phpSearch";
    private TextView mTextViewResult;
    private ArrayList<FragranceData> mArrayList;
    private GridAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    public static Context sCon;
    int uid;
    String uname;
    String uemail;

    TextView title_change;
    ImageView backBtn;
    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    // 키패드
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        sCon = this; // 어댑터와 연결

        Intent intent = getIntent();
        uid = intent.getExtras().getInt("uid");
        uname = intent.getStringExtra("uname");
        uemail = intent.getStringExtra("uemail");

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("검색");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        //searchIcon = (ImageView) findViewById(R.id.searchIcon);
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

//        searchIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(intent);
//            }
//        });

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

        // mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mEditTextSearchKeyword = (EditText) findViewById(R.id.searchEditText1);


        mArrayList = new ArrayList<>();

        mAdapter = new GridAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        Button button_search = (Button) findViewById(R.id.searchButton1);
        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imm.showSoftInput(mEditTextSearchKeyword, 0); // 키패드 보이기
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                String Keyword =  mEditTextSearchKeyword.getText().toString();
                imm.hideSoftInputFromWindow(mEditTextSearchKeyword.getWindowToken(), 0); // 키패드 숨기기
                mEditTextSearchKeyword.setText("");

                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/search.php", Keyword);
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SearchActivity.this,
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

                mArrayList.add(fragranceData);
                mAdapter.notifyDataSetChanged();

                title_change.setText("검색 결과");
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

}

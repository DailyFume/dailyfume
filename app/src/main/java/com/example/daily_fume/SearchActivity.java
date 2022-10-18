package com.example.daily_fume;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private static String IP_ADDRESS = "43.201.60.239";
    private static String TAG = "phpexample";

    private TextView mTextViewResult;
    private ArrayList<FragranceData> mArrayList;
    private GridAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEditTextSearchKeyword = (EditText) findViewById(R.id.searchEditText1);

        // mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new GridAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        Button button_search = (Button) findViewById(R.id.searchButton1);
        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                String Keyword =  mEditTextSearchKeyword.getText().toString();
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
            String postParameters = params[1]; // 향수 이름을 영어로 검색했을 때만 결과 출력 가능 수정 필요

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


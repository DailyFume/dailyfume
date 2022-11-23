package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginActivity extends AppCompatActivity {

    private static String TAG = "phplogin";
    private ArrayList<LoginData> mArrayList;

    private static final String TAG_JSON = "user";
    private static final String TAG_EMAIL = "uemail";
    private static final String TAG_PASS = "upassword";
    private static final String TAG_NAME = "uname";
    private static final String TAG_UID = "uid";

    private EditText mEditTextID, mEditTextPass;
    Button loginBtn, joinBtn;

    private String mJsonString;
    ImageView backBtn;
    Button joinBtn, loginBtn;
    TextView title_change;

    private long backKeyPressedTime = 0; // 뒤로가기 키 시간 변수

    private static String TAG = "phplogin";
    private ArrayList<LoginData> mArrayList;

    private static final String TAG_JSON = "user";
    private static final String TAG_EMAIL = "uemail";
    private static final String TAG_PASS = "upassword";
    private static final String TAG_NAME = "uname";
    private static final String TAG_UID = "uid";

    private EditText mEditTextID, mEditTextPass;

    private String mJsonString;
    String result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login2);

        mEditTextID = (EditText) findViewById(R.id.idTextBox);
        mEditTextPass = (EditText) findViewById(R.id.pwTextBox);
        mEditTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mEditTextID = (EditText) findViewById(R.id.idTextBox);
        mEditTextPass = (EditText) findViewById(R.id.pwTextBox);

        mEditTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("LOGIN");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginBack();
            }
        });

        joinBtn = (Button) findViewById(R.id.joinPageBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                GetData task = new GetData();
                task.execute(mEditTextID.getText().toString(), mEditTextPass.getText().toString());
            }
        });
        mArrayList = new ArrayList<>();



    }

    //
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response : " + result);
            String wrong = "wrong";

            if (result.equals(wrong)) {
                Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            } else {
                mJsonString = result;
                //Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String uemail = (String) params[0];
            String upassword = (String) params[1];

            String serverURL = "http://43.200.245.161/login.php";
            String postParameters = "uemail=" + uemail + "&upassword=" + upassword;

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
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String uemail = item.getString(TAG_EMAIL);
                String upassword = item.getString(TAG_PASS);
                String uname = item.getString(TAG_NAME);
                Integer uid = item.getInt(TAG_UID);

                LoginData loginData = new LoginData();

                loginData.setUid(uid);
                loginData.setUemail(uemail);
                loginData.setUpassword(upassword);
                loginData.setUname(uname);

                mArrayList.add(loginData);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uemail", uemail);
                intent.putExtra("uname", uname);
                startActivity(intent);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            showLoginBack();
        }
    }



    void showLoginBack() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(LoginActivity.this)
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

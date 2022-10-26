package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.kakao.usermgmt.response.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserPrivacyActivity extends AppCompatActivity {

    ImageView backBtn, etc_icon;
    TextView title_change;
    Button priModifyBtn;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    String uemail;

    EditText nickname_box, pw_box;
    String Name, PW;

    //logcat 에서 오류 찾을 때
    private static String TAG = "update_user";
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_privacy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("      개인 정보");

        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        // searchIcon = (ImageView) findViewById(R.id.);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);


        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                startActivity(intent);
            }
        });

        // searchIcon.setOnClickListener();

        loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickListActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String uemail = intent.getStringExtra("uemail");

        // 수정하기 버튼 클릭 이벤트
        // ★ 사용자가 정보를 수정하기 위해 입력하거나 버튼을 클릭했다면 밑에 수정하기 버튼이나 중복확인 버튼이
        // 분홍색으로 바뀌어서 활성화 되어야 함.

        nickname_box = (EditText) findViewById(R.id.nickname_box);
        pw_box = (EditText) findViewById(R.id.pw_box);

        priModifyBtn = (Button) findViewById(R.id.priModifyBtn);
        priModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = nickname_box.getText().toString();
                PW = pw_box.getText().toString();

                InsertData task = new InsertData();
                task.execute(Name, PW, uemail);

                showModify(); // 팝업창으로 수정할건지 한번 더 물어보기
            }
        });
        // 임시 기본 값
        //womanBtnbox.setTextColor(Color.rgb(255,255,255));
        //womanBtnbox.setBackgroundColor(Color.rgb(230,182,190));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_privacy_etc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.etc_logout:
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(UserPrivacyActivity.this)
                        .setTitle("알림")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "로그아웃이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();
                return true;
            case R.id.etc_delprivacy:
                // 회원탈퇴 클릭 > 탈퇴 페이지로 넘어감
                Intent intent = new Intent(getApplicationContext(), ResignActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    // 수정하기 버튼 팝업창 메서드
    void showModify() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(UserPrivacyActivity.this)
                .setTitle("알림")
                .setMessage("회원정보를 수정하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UserPrivacyActivity.this, "회원정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserPrivacyActivity.this, MyPageActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int whichButton) {
                  dialogInterface.cancel();
                }
        });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }



    //회원정보 수정 구현

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(UserPrivacyActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);

        }

        @Override
        protected String doInBackground(String... params) {


            String Name = (String)params[0];
            String PW = (String)params[1];
            String uemail = (String)params[2];

            String serverURL = "http://43.200.245.161/update_user.php";
            String postParameters = "uname=" + Name + "&upassword=" + PW + "&uemail=" + uemail;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(8000);
                httpURLConnection.setConnectTimeout(8000);
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

}

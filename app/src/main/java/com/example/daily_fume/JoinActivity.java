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

import android.text.InputType;
import android.util.Log;
import android.provider.ContactsContract;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "43.201.60.239";
    private static String TAG = "phpsignup";

    private EditText mEditTextEmail;
    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private EditText mEditTextBirth;

    ImageView backBtn;
    TextView title_change, memberTerms;
    Button joinBtn;
    // Button manBtn, womanBtn;
    ImageView membertermBtn;
    LinearLayout terms_box;
    CheckBox checkYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("회원가입");

        backBtn = (ImageView) findViewById(R.id.back_icon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mEditTextEmail = (EditText)findViewById(R.id.email_create);
        mEditTextName = (EditText)findViewById(R.id.nickname_create);
        mEditTextPassword = (EditText)findViewById(R.id.pw_create);
        mEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEditTextBirth = (EditText)findViewById(R.id.date_create);

        // manBtn = (Button) findViewById(R.id.manBtn);
        // womanBtn = (Button) findViewById(R.id.womanBtn);

        /*
        womanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                womanBtn.setTextColor(Color.rgb(255,255,255));
                womanBtn.setBackgroundColor(Color.rgb(230,182,190));
                manBtn.setTextColor(Color.rgb(179,179,179));
                manBtn.setBackgroundColor(Color.rgb(255,255,255));
            }
        });
         */

        memberTerms = (TextView) findViewById(R.id.memberTerms);
        memberTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MemberTerms.class);
                startActivity(intent);
            }
        });

        terms_box = (LinearLayout) findViewById(R.id.terms_box);
        terms_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MemberTerms.class);
                startActivity(intent);
            }
        });
/*
        membertermBtn = (ImageView) findViewById(R.id.membertermBtn);
        membertermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MemberTerms.class);
                startActivity(intent);
            }
        }); */

        checkYes = (CheckBox) findViewById(R.id.checkYes);

        joinBtn = (Button) findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkYes.isChecked()) {
                    String Email = mEditTextEmail.getText().toString();
                    String Name = mEditTextName.getText().toString();
                    String Password = mEditTextPassword.getText().toString();
                    String Birthday = mEditTextBirth.getText().toString();

                    InsertData task = new InsertData();
                    task.execute("http://" + IP_ADDRESS + "/signup.php", Email, Name, Password, Birthday);

                    mEditTextEmail.setText("");
                    mEditTextName.setText("");
                    mEditTextPassword.setText("");
                    mEditTextBirth.setText("");

                    Intent intent = new Intent(JoinActivity.this, JoinYesActivity.class);
                    startActivity(intent); // 회원완료 페이지로 (정확히는 회원가입에 성공한 경우만)
                    finish();
                } else { // 동의합니다 체크 안한 경우
                    showJoinNoCheck(); // 팝업창 뜨기
                }

            }
        });


    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(JoinActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String uemail = (String)params[1];
            String uname = (String)params[2];
            String upassword = (String)params[3];
            String ubirth = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "uemail=" + uemail + "&uname=" + uname + "&upassword=" + upassword + "&ubirth=" + ubirth;

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

    // 메서드
    void showJoinNoCheck() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(JoinActivity.this)
                .setTitle("알림")
                .setMessage("회원약관에 동의하지 않을 경우 가입이 불가합니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}

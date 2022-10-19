package com.example.daily_fume;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ResignActivity extends AppCompatActivity {

    Button joinOutBtn;
    String listname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigns);

        joinOutBtn = (Button) findViewById(R.id.joinOutBtn);
        //button3.setClickable(true);

        joinOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText user_email = new EditText(ResignActivity.this);

                // 1) 이메일 인증 창
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ResignActivity.this)
                        .setTitle("\"DailyFume\"을 탈퇴하시려면 이메일 인증이 필요합니다.")
                        .setMessage("이메일을 입력해주세요.")
                        .setView(user_email)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 입력된 이메일과 데이터베이스 대조
                                listname = user_email.getText().toString();
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        System.out.print(response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");

                                            if (success) {
                                                // 2) 이메일 일치 > 최종 확인 창 출력
                                                
                                                //TitleValues.add(listname); -- 안 쓰는 코드
                                                //pickBoxLoading();

                                                Toast.makeText(getApplicationContext(), "이메일이 일치합니다.", Toast.LENGTH_SHORT).show();

                                                AlertDialog.Builder msg_final = new AlertDialog.Builder(ResignActivity.this)
                                                        .setTitle("이 동작은 취소할 수 없습니다.")
                                                        .setMessage("정말로 \"DailyFume\"을 탈퇴하시겠습니까?")
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                // 3) 최종 확인 창에서 확인 > 데이터베이스에서 삭제 후 로그인 페이지로 이동
                                                                Toast.makeText(getApplicationContext(), "탈퇴가 정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                                                                //스택을 비우고 새로운 액티비티 띄우기
                                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                // 3) 최종 확인 창에서 취소 > 마이페이지 창으로 돌아가기
                                                                Intent intent = new Intent(getApplicationContext(), UserPrivacyActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                AlertDialog msgDlg_fin = msg_final.create();
                                                msgDlg_fin.show();

                                            } else {
                                                // 2) 이메일 불일치 > 토스트 메시지
                                                Toast.makeText(getApplicationContext(), "이메일이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };

                                DeleteRequest delete_user = new DeleteRequest(listname, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ResignActivity.this);
                                queue.add(delete_user);

                            }
                        })
                        // 1) 이메일 인증 취소
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();

            }
        });

    }


}
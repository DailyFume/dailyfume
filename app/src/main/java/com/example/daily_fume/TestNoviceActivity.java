package com.example.daily_fume;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestNoviceActivity extends AppCompatActivity {

    // 입문자용
    ImageView backBtn;
    TextView title_change;
    int progressn = 10; // 프로그래스바 변경할 값 변수 (기본값 10)
    ProgressBar testProgressBar; // 최댓값 50으로 설정
    TextView testNumber, testText;
    Integer tNum = 1;
    ImageView aTestSelect, bTestSelect, cTestSelect, dTestSelect;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    ArrayList aldehyde, citrus, floral, fruity, green, musk, oceanic, oriental, spicy, woody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("향수 테스트");

        backBtn = (ImageView) findViewById(R.id.back_icon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        testIcon = (ImageView) findViewById(R.id.testIcon);
        testIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        loveIcon = (ImageView) findViewById(R.id.loveIcon);
        mypageIcon = (ImageView) findViewById(R.id.mypageIcon);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("uid", uid);
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
                startActivity(intent);
            }
        });

        testProgressBar = (ProgressBar) findViewById(R.id.testProgressBar);
        testNumber = (TextView) findViewById(R.id.testNumber);
        testText = (TextView) findViewById(R.id.testText);

        aTestSelect = (ImageView) findViewById(R.id.aTestSelect);
        bTestSelect = (ImageView) findViewById(R.id.bTestSelect);
        cTestSelect = (ImageView) findViewById(R.id.cTestSelect);
        dTestSelect = (ImageView) findViewById(R.id.dTestSelect);

        aldehyde = new ArrayList();
        citrus = new ArrayList();
        floral = new ArrayList();
        fruity = new ArrayList();
        green = new ArrayList();
        musk = new ArrayList();
        oceanic = new ArrayList();
        oriental = new ArrayList();
        spicy = new ArrayList();
        woody = new ArrayList();

        // 초기값 설정
        testNumber.setText(tNum.toString());
        testNumPlay();
    }

    // 메서드
    void numplay() {
        if (progressn != 50) {
            progressn = progressn + 10;
            testProgressBar.setProgress(progressn);
            tNum += 1;
            testNumber.setText(tNum.toString());
            //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();
            testNumPlay();
        }
    }

    // 리스트 : aldehyde, citrus, floral, fruity, green, musk, oceanic, oriental, spicy, woody;
    // Toast.makeText(getApplicationContext(), floral.size()+"", Toast.LENGTH_SHORT).show(); // 제대로 들어가는지 확인완료!
    void testNumPlay() {
        switch (tNum.intValue()) {
            case 1:
                testText.setText("1 . 제일 좋아하는 계절은?");
                aTestSelect.setImageResource(R.drawable.test_novice_1_1);
                bTestSelect.setImageResource(R.drawable.test_novice_1_2);
                cTestSelect.setImageResource(R.drawable.test_novice_1_3);
                dTestSelect.setImageResource(R.drawable.test_novice_1_4);
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();

                aTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        floral.add(1); green.add(1);
                        numplay();
                    }
                });

                bTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        citrus.add(1); oceanic.add(1);
                        numplay();
                    }
                });

                cTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        woody.add(1);
                        numplay();
                    }
                });

                dTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        musk.add(1); aldehyde.add(1);
                        numplay();
                    }
                });

                break;
            case 2:
                testText.setText("2 . 가장 끌리는 향은?");
                aTestSelect.setImageResource(R.drawable.test_novice_2_1);
                bTestSelect.setImageResource(R.drawable.test_novice_2_2);
                cTestSelect.setImageResource(R.drawable.test_novice_2_3);
                dTestSelect.setImageResource(R.drawable.test_novice_2_4);
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();

                aTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        floral.add(1);
                        numplay();
                    }
                });

                bTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        citrus.add(1); fruity.add(1);
                        numplay();
                    }
                });

                cTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        green.add(1); woody.add(1);
                        numplay();
                    }
                });

                dTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        musk.add(1); aldehyde.add(1);
                        numplay();
                    }
                });

                break;
            case 3:
                testText.setText("3 . 가장 마음에 드는 키워드는?");
                aTestSelect.setImageResource(R.drawable.test_novice_3_1);
                bTestSelect.setImageResource(R.drawable.test_novice_3_2);
                cTestSelect.setImageResource(R.drawable.test_novice_3_3);
                dTestSelect.setImageResource(R.drawable.test_novice_3_4);
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();

                aTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        floral.add(1); aldehyde.add(1);
                        numplay();
                    }
                });

                bTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        citrus.add(1); fruity.add(1);
                        numplay();
                    }
                });

                cTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        green.add(1); woody.add(1); musk.add(1);
                        numplay();
                    }
                });

                dTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oriental.add(1); spicy.add(1);
                        numplay();
                    }
                });
                break;
            case 4:
                testText.setText("4 . 가장 가고싶은 장소는?");
                aTestSelect.setImageResource(R.drawable.test_novice_4_1);
                bTestSelect.setImageResource(R.drawable.test_novice_4_2);
                cTestSelect.setImageResource(R.drawable.test_novice_4_3);
                dTestSelect.setImageResource(R.drawable.test_novice_4_4);
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();

                aTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        floral.add(1);
                        numplay();
                    }
                });

                bTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        green.add(1); woody.add(1);
                        numplay();
                    }
                });

                cTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        citrus.add(1); fruity.add(1); oceanic.add(1);
                        numplay();
                    }
                });

                dTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        musk.add(1); aldehyde.add(1);
                        numplay();
                    }
                });
                break;
            case 5:
                testText.setText("5 . 다른 사람들에게  보여주고 싶은 내 이미지는?");
                aTestSelect.setImageResource(R.drawable.test_novice_5_1);
                bTestSelect.setImageResource(R.drawable.test_novice_5_2);
                cTestSelect.setImageResource(R.drawable.test_novice_5_3);
                dTestSelect.setImageResource(R.drawable.test_novice_5_4);
                //Toast.makeText(getApplicationContext(), tNum+"", Toast.LENGTH_SHORT).show();

                aTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oriental.add(1); spicy.add(1); aldehyde.add(1);
                        resultCheck(); // 결과 계산
                    }
                });

                bTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        citrus.add(1); oceanic.add(1); fruity.add(1);
                        resultCheck(); // 결과 계산
                    }
                });

                cTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        floral.add(1); musk.add(1);
                        resultCheck(); // 결과 계산
                    }
                });

                dTestSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        woody.add(1); green.add(1);
                        resultCheck(); // 결과 계산
                    }
                });
                break;
        }
    }


    void resultCheck() {
        // 결과값  - key value
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("aldehyde", aldehyde.size());
        map.put("citrus", citrus.size());
        map.put("floral", floral.size());
        map.put("fruity", fruity.size());
        map.put("green", green.size());
        map.put("musk", musk.size());
        map.put("oceanic", oceanic.size());
        map.put("oriental", oriental.size());
        map.put("spicy", spicy.size());
        map.put("woody", woody.size());

        Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e1.getValue().compareTo(e2.getValue());
            }
        };

        Map.Entry<String, Integer> maxEntry = Collections.max(map.entrySet(), comparator);
        //Toast.makeText(getApplicationContext(),maxEntry.getKey()+"계열"+maxEntry.getValue()+"값", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");

        // 결과값 넘기기
        Intent resultIntent = new Intent(getApplicationContext(), TestResultActivity.class);
        resultIntent.putExtra("result", maxEntry.getKey());
        resultIntent.putExtra("uid", uid);
        startActivity(resultIntent);
        finish();

    }

//    void showTestNoCheck() {
//        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(TestBuffActivity.this)
//                .setTitle("선택하지 않은 항목이 있습니다.")
//                .setMessage("다시 한번 확인해주세요")
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//        AlertDialog msgDlg = msgBuilder.create();
//        msgDlg.show();
//    }

}

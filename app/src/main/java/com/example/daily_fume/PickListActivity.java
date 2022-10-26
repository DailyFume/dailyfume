package com.example.daily_fume;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;

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

public class PickListActivity extends AppCompatActivity {

    ImageView backBtn, pickBoxNew;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;

    Spinner spinnerPick;
    String[] PfItems = { "  기본  ", "  최신순  ", "  이름순  "};
    ListView pickBoxList;
    ArrayList<GroupData> groupDataList;
    ButtonListAdapter pickboxadapter;

    // 데이터 가져오기 및 데이터 보내기
    String listname;
    String serverURL = "http://43.200.245.161/getlikelist.php";
    String serverURL2 = "http://43.200.245.161/likelist.php";
    Button BoxModifyBtn, BoxDeleteBtn;
    private static String TAG1 = "getlikelistphp";
    private static String TAG2 = "likelistphp";
    private static final String TAG_JSON = "dailyfume";
    private static final String TAG_LISTNAME = "listname";
    private static final String TAG_LISTID = "lid";
    String mJsonString;
    ButtonListAdapter buttonListAdapter;
    int listid; // 폴더로 넘길때

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int uid = intent.getExtras().getInt("uid");
        String uname = intent.getStringExtra("uname");

        groupDataList = new ArrayList<>();
        buttonListAdapter = new ButtonListAdapter(PickListActivity.this, groupDataList);
        pickBoxList = (ListView) findViewById(R.id.pickBoxList);
        pickBoxList.setAdapter(buttonListAdapter);

        GetoData getoData = new GetoData(serverURL, uid);
        GetData task1 = new GetData();
        task1.execute(getoData);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText("찜한 상품");

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
        // loveIcon = (ImageView) findViewById(R.id.loveIcon);
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
        // loveIcon.setOnClickListener();

        mypageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                startActivity(intent);
                finish();
                finish();
            }
        });


        // 스피너 - 폴더정렬
        spinnerPick = (Spinner) findViewById(R.id.spinnerPick);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, PfItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPick.setAdapter(adapter);



        spinnerPick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //PfItemsBox.setText(PfItems[position]);
                ((TextView)parent.getChildAt(0)).setTextColor(Color.rgb(199, 131, 142));
                ((TextView)parent.getChildAt(0)).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                switch(position) {
                    case 0 :
                        // ★ 임시 (기본)
                        break;
                    case 1 :
                        // ★ 임시 (최신순)
                        break;
                    case 2 :
                        // ★ 임시 (이름순)
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ★ 리스트뷰와 버튼 조합일 시 주의사항
        // 1. 리스트뷰 포커스를 false로 해야 함
        // 2. 리스트뷰에 들어간 레이아웃에서 버튼들 xml에 clickable과 focusable은 false로 해야 함
        // 3. getView 메서드로 클릭하게 해줘야 됨.


        // 각 그룹(폴더) 클릭시
        pickBoxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override  // ★ 각각의 폴더 선택하면 해당 찜한 상품이 저장된 폴더로 이동 나중에 수정하기
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupName = groupDataList.get(position).getGroupTitle();
                //Toast.makeText(getApplicationContext(), groupName+"", Toast.LENGTH_SHORT).show();

                Intent groupIntent = new Intent(getApplicationContext(), PickFumeActivity.class);
                groupIntent.putExtra("groupname", groupName);
                groupIntent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("listid", listid);
                startActivity(groupIntent);
            }
        });


        //groupDataList.add(new GroupData("기본 그룹")); // 이렇게 하지말고 하나의 그룹도 없을때 추가해보세요 라는 팝업창 뜨기

        // 새폴더 추가
        pickBoxNew = (ImageView) findViewById(R.id.pickBoxNew);
        pickBoxNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickBoxNew();
            }
        });

    }

    // 메서드

    void groupZero() {
        android.app.AlertDialog.Builder GroupZeroBuilder = new android.app.AlertDialog.Builder(PickListActivity.this);
        GroupZeroBuilder.setTitle("")
                .setMessage("현재 그룹이 없습니다. 그룹을 생성해주세요")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // ★ 해당 리뷰 수정하는 코드
                        Toast.makeText(PickListActivity.this, "위 그룹추가를 눌러주세요", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }

    void showPickBoxNew() { // 새로 박스 만들기
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("그룹명을 입력하세요");
        alert.setMessage("최대 8자까지 가능합니다.");
        final EditText boxName = new EditText(this);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(8); //글자수 제한
        boxName.setFilters(FilterArray);
        alert.setView(boxName);


        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ★ 확인 버튼 클릭시 액션
                listname = boxName.getText().toString();
                Intent intent = getIntent();
                int uid = intent.getExtras().getInt("uid");
                TaskParams params = new TaskParams(serverURL2, listname, uid);
                InsertData insertData = new InsertData();
                insertData.execute(params);

                GetoData getoData = new GetoData(serverURL, uid);
                GetData task1 = new GetData();
                task1.execute(getoData);

            }
        });

        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // ★ 취소 버튼 클릭시 액션
                dialog.cancel();
            }
        });
        alert.show();

    }

    private static class TaskParams {
        int uid;
        String listname;
        String serverURL2;

        TaskParams(String serverURL2, String listname, int uid) {
            this.serverURL2 = serverURL2;
            this.listname = listname;
            this.uid = uid;
        }
    }

    class InsertData extends AsyncTask<TaskParams, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PickListActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG2, "POST response  - " + result);
            //pickBoxLoading();
            Toast.makeText(getApplicationContext(), listname + "폴더가 추가되었습니다.", Toast.LENGTH_SHORT).show();
            buttonListAdapter.notifyDataSetChanged();

        }

        @Override
        protected String doInBackground(TaskParams... params) {

            String serverURL2 = params[0].serverURL2;
            String listname = params[0].listname;
            int uid = params[0].uid;

            String postParameters = "listname=" + listname + "&user_uid=" + uid;

            try {
                URL url = new URL(serverURL2);
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
                Log.d(TAG2, "POST response code - " + responseStatusCode);

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
                Log.d(TAG2, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }

    private static class GetoData {
        int uid;
        String serverURL;

        GetoData(String serverURL, int uid) {
            this.serverURL = serverURL;
            this.uid = uid;
        }
    }

    private class GetData extends AsyncTask<GetoData, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PickListActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG1, "response : " + result);
            mJsonString = result;
            showResult();

            if (groupDataList.size() == 0) {
                groupZero();
            }
        }

        @Override
        protected String doInBackground(GetoData... params) {
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
                Log.d(TAG1, "POST response code - " + responseStatusCode);

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
                Log.d(TAG1, "PickListGetData: Error ", e);
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

                String listname = item.getString(TAG_LISTNAME);
                listid = item.getInt(TAG_LISTID);

                GroupData groupData = new GroupData();
                groupData.setGroupTitle(listname);
                groupDataList.add(groupData);
                buttonListAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d(TAG1, "showResult: ", e);
        }
    }

}

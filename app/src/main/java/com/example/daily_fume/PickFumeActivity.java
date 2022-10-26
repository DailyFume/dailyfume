package com.example.daily_fume;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class PickFumeActivity extends AppCompatActivity {

    ImageView backBtn, pickFumeDel;
    TextView title_change;

    ImageView homeIcon, testIcon, searchIcon, loveIcon, mypageIcon;
    RelativeLayout pickzerolatout;
    ScrollView FumeScrollView;
    Button pickfumegoBtn;

    Spinner spinnerPickFume;
    String[] PfItemsFume = { "  기본  ", "  최신순  ", "  이름순  "};
    GridView pickFumeGridview;
    FumeGridAdapter adapter;

    // 데이터 가져오기
    String serverURL = "http://43.200.245.161/get_likefume.php";
    private static final String TAG_JSON = "dailyfume";
    private static String TAG = "getlikefumephp";
    private static final String TAG_FID = "fragrance_fid";
    private static final String TAG_LISTID = "likelist_lid";
    private static final String TAG_FIMG = "fimg";
    private static final String TAG_FBRAND = "fbrand";
    private static final String TAG_FNAMEK = "fnamek";
    String mJsonString;
    int listid;

    int uid;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_fume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        uid = intent.getExtras().getInt("uid");
        uname = intent.getStringExtra("uname");
        String groupname = intent.getStringExtra("groupname");
        listid = intent.getExtras().getInt("listid");

        GetoData getoData = new GetoData(serverURL, listid);
        GetData task1 = new GetData();
        task1.execute(getoData);

        title_change = (TextView) findViewById(R.id.title_change);
        title_change.setText(groupname); // ★ 나중에는 사용자가 입력한 박스이름으로 변경되게 하기

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
            }
        });

        // ★ 찜한 상품이 없을경우 pick_zero 레이아웃으로 변경
        // 위 경우에는 스피너와 상품삭제 클릭 안되게 하기 (FALSE)로

        // 스피너 - 폴더정렬
        spinnerPickFume = (Spinner) findViewById(R.id.spinnerPickFume);

        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, PfItemsFume);
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPickFume.setAdapter(fadapter);

        spinnerPickFume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        // 찜한 상품 삭제
        pickFumeDel = (ImageView) findViewById(R.id.pickFumeDel);
        pickFumeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ★ 삭제 아이콘 클릭시 각 상품목록의 가운데에 체크할 수 있는 체크박스 뜨게 하기
                // 삭제 아이콘 있던 곳에는 (완료) 글자가 있게하기
                // 삭제할 상품 체크 후 완료를 누르면 현재 폴더에서 체크한 상품들이 사라지게 하기

            }
        });


        // 그리드뷰 - 찜한 상품 목록
        pickFumeGridview = (GridView) findViewById(R.id.pickFumeGridview);
        // ★ 해당 상품을 클릭하면 해당 상품의 상세페이지로 이동 해야 함
        FumeGridAdapter adapter = new FumeGridAdapter();
        Drawable drawable = getResources().getDrawable(R.drawable.fume14);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        adapter.addItem(new Fume("구찌","구찌 블룸", bitmap));
//        adapter.addItem(new Fume("바이레도","모하비 고스트", R.drawable.fume02));
//        adapter.addItem(new Fume("불가리","골데아 더 로만 나이트", R.drawable.fume03));
//        adapter.addItem(new Fume("딥디크","플레르 드 뽀", R.drawable.fume01));
//        adapter.addItem(new Fume("바이레도","모하비 고스트", R.drawable.fume02));
//        adapter.addItem(new Fume("불가리","골데아 더 로만 나이트", R.drawable.fume03));
//        adapter.addItem(new Fume("딥디크","플레르 드 뽀", R.drawable.fume01));
//        adapter.addItem(new Fume("바이레도","모하비 고스트", R.drawable.fume02));
//        adapter.addItem(new Fume("불가리","골데아 더 로만 나이트", R.drawable.fume03));
        pickFumeGridview.setAdapter(adapter);

    }

    // 그리드뷰 생성자 클래스
    public class Fume {
        String brand;
        String fumeName;
        Bitmap iamgesResId;

        public Fume(String brand, String fumeName, Bitmap iamgesResId) {
            this.brand = brand;
            this.fumeName = fumeName;
            this.iamgesResId = iamgesResId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getFumeName() {
            return fumeName;
        }

        public void setFumeName(String fumeName) {
            this.fumeName = fumeName;
        }

        public Bitmap getIamgesResId() {
            return iamgesResId;
        }

        public void setIamgesResId(Bitmap iamgesResId) {
            this.iamgesResId = iamgesResId;
        }
    }

    public class Fumes_View extends LinearLayout {
        ImageView fumeImages;
        TextView pickFumeBrand;
        TextView pickFumeTitle;

        public Fumes_View(Context context) {
            super(context);
            init(context);
        }

        public Fumes_View(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public void init(Context context){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.pick_fumelist_layout, this, true);
            fumeImages = findViewById(R.id.fumeImages);
            pickFumeBrand = findViewById(R.id.pickFumeBrand);
            pickFumeTitle = findViewById(R.id.pickFumeTitle);
        }

        public void setImageView(Bitmap ResId){
            fumeImages.setImageBitmap(ResId);
        }
        public void setBrand(String brand){
            pickFumeBrand.setText(brand);
        }
        public void setFumeName(String fumeName){
            pickFumeTitle.setText(fumeName);
        }

    }


    public class FumeGridAdapter extends BaseAdapter{
        ArrayList<Fume> items = new ArrayList<>();
        @Override
        public int getCount() {
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(Fume fume){
            this.items.add(fume);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Fumes_View fumes_view = null;
            if(convertView == null){
                fumes_view = new Fumes_View(getApplicationContext());
            }else{
                fumes_view = (Fumes_View) convertView;
            }
            Fume fume = items.get(position);
            fumes_view.setBrand(fume.getBrand());
            fumes_view.setFumeName(fume.getFumeName());
            fumes_view.setImageView(fume.getIamgesResId());

            return fumes_view;
        }
    }

    // 데이터 (찜 상품) 가져오기
    private static class GetoData {
        String serverURL;
        int lid;


        GetoData(String serverURL, int lid) {
            this.serverURL = serverURL;
            this.lid = lid;
        }
    }

    private class GetData extends AsyncTask<GetoData, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PickFumeActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response : " + result);
            mJsonString = result;
            showResult();

//            if (adapter.getCount() == 0) {
//                fumePickZero();
//            }
        }

        @Override
        protected String doInBackground(GetoData... params) {
            String serverURL = params[0].serverURL;
            int lid = params[0].lid;

            String postParameters = "likelist_lid=" + lid;

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
                Log.d(TAG, "ListFumeGetData: Error ", e);
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

                String fid = item.getString(TAG_FID);
                listid = item.getInt(TAG_LISTID);
                Bitmap image = StringToBitMap(item.getString(TAG_FIMG));
                String fbrand = item.getString(TAG_FBRAND);
                String fnamek = item.getString(TAG_FNAMEK);
                Toast.makeText(getApplicationContext(), fid+"",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), listid+"",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), fbrand+"",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), fnamek+"",Toast.LENGTH_SHORT).show();

                adapter = new FumeGridAdapter();
                for (int a=0; a < item.length(); a++) {
                    adapter.addItem(new Fume(fbrand, fnamek, image));
                    Toast.makeText(getApplicationContext(), item.length()+"",Toast.LENGTH_SHORT).show();
                }
                pickFumeGridview.setAdapter(adapter);

//                GroupData groupData = new GroupData();
//                groupData.setGroupTitle(listname);
//                groupDataList.add(groupData);
//                buttonListAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
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

    void fumePickZero() {
        pickzerolatout = (RelativeLayout) findViewById(R.id.pickzerolatout);
        FumeScrollView = (ScrollView) findViewById(R.id.FumeScrollView);
        FumeScrollView.setVisibility(View.GONE);
        pickzerolatout.setVisibility(View.VISIBLE);

        pickfumegoBtn = (Button) findViewById(R.id.pickfumegoBtn);
        pickfumegoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                startActivity(intent);
                finish();
            }
        });

    }

}
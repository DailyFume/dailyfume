package com.example.daily_fume;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ButtonListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<com.example.daily_fume.GroupData> groupData;

    int uid;
    String uname;
    String uemail;
    int picklistid;

    String uplistname;
    String serverURL = "http://43.200.245.161/update_likelist.php";
    private static String TAG = "update_likelistphp";

    String dserverURL = "http://43.200.245.161/delete_likelist.php";
    private static String TAGd = "delete_likelistphp";

    public ButtonListAdapter(Context context, ArrayList<GroupData> groupData){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.groupData = groupData;
    }

    @Override
    public int getCount() {
        return groupData.size();
    }

    @Override
    public Object getItem(int position) {
        return groupData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.picbox_layout, null);

        TextView textView = view.findViewById(R.id.boxTitle);
        textView.setText(groupData.get(position).getGroupTitle());

//        TextView pickNum = view.findViewById(R.id.pickNum);
        //View pickBoxLayout = view.findViewById(R.id.pickBoxLayout2);
        ImageView pickBoxLayout = view.findViewById(R.id.pickBoxLayout);
        Button BoxDeleteBtn = view.findViewById(R.id.BoxDeleteBtn);
        Button BoxModifyBtn = view.findViewById(R.id.BoxModifyBtn);

        pickBoxLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String groupName = groupData.get(position).getGroupTitle();
                //Toast.makeText(getApplicationContext(), groupName+"", Toast.LENGTH_SHORT).show();
                int picklistid = groupData.get(position).getListid();

                uid = ((PickListActivity)PickListActivity.bLon).uid;
                uname = ((PickListActivity)PickListActivity.bLon).uname;
                uemail = ((PickListActivity)PickListActivity.bLon).uemail;

                Intent groupIntent = new Intent(context, PickFumeActivity.class);
                groupIntent.putExtra("groupname", groupName);
                groupIntent.putExtra("uid", uid);
                groupIntent.putExtra("uname", uname);
                groupIntent.putExtra("picklistid", picklistid);//
                groupIntent.putExtra("uemail", uemail);
                (context).startActivity(groupIntent);
            }
        });

        BoxModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "["+groupData.get(position).getListid()+"/"+groupData.get(position).getGroupTitle()+"]" + " 수정 클릭", Toast.LENGTH_SHORT).show();
                //
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("새로운 그룹명을 입력하세요");
                alert.setMessage("최대 8자까지 가능합니다.");
                final EditText boxName = new EditText(context);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(8); //글자수 제한
                boxName.setFilters(FilterArray);
                alert.setView(boxName);

                alert.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // ★ 확인 버튼 클릭시 액션
                        uplistname = boxName.getText().toString();
                        picklistid = groupData.get(position).getListid();

                        TaskParams params = new TaskParams(serverURL, picklistid, uplistname);
                        //Toast.makeText(context.getApplicationContext(), picklistid+"/"+uplistname+"", Toast.LENGTH_SHORT).show();
                        InsertData insertData = new InsertData();
                        insertData.execute(params);
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
        });

        BoxDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "["+groupData.get(position).getListid()+"/"+groupData.get(position).getGroupTitle()+"]" + " 삭제 클릭", Toast.LENGTH_SHORT).show();
                //
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(groupData.get(position).getGroupTitle()+"를(을) 정말 삭제하시겠습니까?");
                alert.setMessage(" 해당 그룹안에 찜한 상품이 있는 경우 삭제가 불가합니다. 먼저 찜한 상품을 해제해주세요");

                alert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // ★ 확인 버튼 클릭시 액션
                        uplistname = groupData.get(position).getGroupTitle();
                        picklistid = groupData.get(position).getListid();

                        TaskParams1 params1 = new TaskParams1(dserverURL, picklistid);
                        //Toast.makeText(context.getApplicationContext(), picklistid+"/"+uplistname+"", Toast.LENGTH_SHORT).show();
                        DeleteData deleteData = new DeleteData();
                        deleteData.execute(params1);
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
        });

        return view;

    }

    // 메서드

    // 수정
    private static class TaskParams {
        String serverURL;
        int picklistid;
        String uplistname;

        TaskParams(String serverURL, int picklistid, String uplistname) {
            this.serverURL = serverURL;
            this.picklistid = picklistid;
            this.uplistname = uplistname;
        }
    }

    class InsertData extends AsyncTask<TaskParams, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
            Toast.makeText(context, uplistname + "로 수정되었습니다.", Toast.LENGTH_SHORT).show();
            //notifyDataSetChanged();/// ★ 여기서 갱신이 안됨.
        }

        @Override
        protected String doInBackground(TaskParams... params) {

            String serverURL = params[0].serverURL;
            int picklistid = params[0].picklistid;
            String uplistname = params[0].uplistname;

            String postParameters = "lid=" + picklistid + "&listname=" + uplistname;

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
                Log.d(TAG, "UpdateData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }

    // 삭제
    private static class TaskParams1 {
        String dserverURL;
        int picklistid;

        TaskParams1(String dserverURL, int picklistid) {
            this.dserverURL = dserverURL;
            this.picklistid = picklistid;
        }
    }

    class DeleteData extends AsyncTask<TaskParams1, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAGd, "POST response  - " + result);
            Toast.makeText(context, uplistname + "가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            // notifyDataSetChanged();/// ★ 여기서 갱신이 안됨.
        }

        @Override
        protected String doInBackground(TaskParams1... params1) {

            String dserverURL = params1[0].dserverURL;
            int picklistid = params1[0].picklistid;

            String postParameters = "lid=" + picklistid;

            try {
                URL url = new URL(dserverURL);
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
                Log.d(TAGd, "POST response code - " + responseStatusCode);

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
                Log.d(TAGd, "DeleteData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }

}
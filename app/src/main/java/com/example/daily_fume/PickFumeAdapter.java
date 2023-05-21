package com.example.daily_fume;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PickFumeAdapter extends RecyclerView.Adapter<PickFumeAdapter.CustomViewHolder> {

    private ArrayList<PickFume> pfitems  = null;
    private Activity pfcontext = null;

    // intent
    int uid;
    String uname;
    String uemail;
    int listfumeid;

    // delete
    String serverDelURL = "http://43.200.245.161/delete_likefume.php";
    private static String delTAG = "delete_likelistphp";

    public PickFumeAdapter(Activity pfcontext, ArrayList<PickFume> flist) {
        this.pfcontext = pfcontext;
        this.pfitems = flist;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView brand;
        protected TextView name;
        protected LinearLayout lovepickfume;

        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.fumeImages);
            this.brand = (TextView) view.findViewById(R.id.pickFumeBrand);
            this.name = (TextView) view.findViewById(R.id.pickFumeTitle);
            this.lovepickfume = (LinearLayout) view.findViewById(R.id.lovepickfume);

        }
    }

    @Override
    public PickFumeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pick_fumelist_layout, null);
        PickFumeAdapter.CustomViewHolder viewHolder = new PickFumeAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickFumeAdapter.CustomViewHolder viewholder, @SuppressLint("RecyclerView") int position) {

        viewholder.image.setImageBitmap(pfitems.get(position).getIamgesResId());
        viewholder.brand.setText(pfitems.get(position).getBrand());
        viewholder.name.setText(pfitems.get(position).getFumeName());
        viewholder.lovepickfume.setOnClickListener(new View.OnClickListener() { // 각 아이템의 하트 클릭시
            @Override
            public void onClick(View v) {
                // Toast.makeText(pfcontext.getApplicationContext(),pfitems.get(position).getLikeid()+"",Toast.LENGTH_SHORT).show();
                // 삭제
                AlertDialog.Builder alert = new AlertDialog.Builder(pfcontext);
                alert.setTitle(pfitems.get(position).getFumeName()+"를(을) 정말 삭제하시겠습니까?");
                alert.setMessage(" ");

                alert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // ★ 확인 버튼 클릭시 액션
                        listfumeid = pfitems.get(position).getLikeid();
                        TaskParams1 params1 = new TaskParams1(serverDelURL, listfumeid);
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

        uid = ((PickFumeActivity)PickFumeActivity.pfCon).uid;
        uname = ((PickFumeActivity)PickFumeActivity.pfCon).uname;
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), name.getText()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(pfcontext,FumeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                intent.putExtra("title is", pfitems.get(position).getFumeName());
                ((PickFumeActivity)pfcontext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != pfitems ? pfitems.size() : 0);
    }


    // 삭제
    private static class TaskParams1 {
        String serverDelURL;
        int listfumeid;

        TaskParams1(String serverDelURL, int listfumeid) {
            this.serverDelURL = serverDelURL;
            this.listfumeid = listfumeid;
        }
    }

    class DeleteData extends AsyncTask<TaskParams1, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(pfcontext,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(delTAG, "POST response  - " + result);
            Toast.makeText(pfcontext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            // notifyDataSetChanged();///
        }

        @Override
        protected String doInBackground(TaskParams1... params1) {

            String serverDelURL = params1[0].serverDelURL;
            int listfumeid = params1[0].listfumeid;

            String postParameters = "like_id=" + listfumeid;

            try {
                URL url = new URL(serverDelURL);
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
                Log.d(delTAG, "POST response code - " + responseStatusCode);

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
                Log.d(delTAG, "DeleteData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }


}

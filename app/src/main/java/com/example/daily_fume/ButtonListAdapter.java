package com.example.daily_fume;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ButtonListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<GroupData> groupData;

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

        View pickBoxLayout = view.findViewById(R.id.pickBoxLayout);
        Button BoxDeleteBtn = view.findViewById(R.id.BoxDeleteBtn);
        Button BoxModifyBtn = view.findViewById(R.id.BoxModifyBtn);

//        pickBoxLayout.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//
//            }
//        });

        BoxModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "["+groupData.get(position).getGroupTitle()+"]" + " 수정 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        BoxDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "["+groupData.get(position).getGroupTitle()+"]" + " 삭제 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    // 메서드




}
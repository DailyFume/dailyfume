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
    ArrayList<String> TitleValues;

    public ButtonListAdapter(Context context, ArrayList<String> TitleValues){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.TitleValues = TitleValues;
    }

    @Override
    public int getCount() {
        return TitleValues.size();
    }

    @Override
    public Object getItem(int position) {
        return TitleValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.picbox_layout, null);

        TextView textView = view.findViewById(R.id.boxTitle);
        textView.setText(TitleValues.get(position));

        View pickBoxLayout = view.findViewById(R.id.pickBoxLayout);
        Button BoxDeleteBtn = view.findViewById(R.id.BoxDeleteBtn);
        Button BoxModifyBtn = view.findViewById(R.id.BoxModifyBtn);

        pickBoxLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "["+TitleValues.get(position)+"]" + " 폴더 클릭", Toast.LENGTH_SHORT).show();
                switch (TitleValues.get(position)) {
                    case "기본 그룹" :
                        Intent intent = new Intent(context.getApplicationContext(), PickFumeActivity.class);
                        context.startActivity(intent); // 임시 (찜한 향수들 목록이 있는 페이지로 이동)
                        break;
                    case "임시 그룹" :
                        Intent intent1 = new Intent(context.getApplicationContext(), PickZeroActivity.class);
                        context.startActivity(intent1); // 임시 (찜한 향수들 목록이 있는 페이지로 이동)
                        break;
                }

            }
        });

        BoxModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "["+TitleValues.get(position)+"]" + " 수정 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        BoxDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "["+TitleValues.get(position)+"]" + " 삭제 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    // 메서드




}

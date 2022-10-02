package com.example.daily_fume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = layoutInflater.inflate(R.layout.picbox_layout2, null);

        TextView textView = view.findViewById(R.id.boxTitle);
        textView.setText(TitleValues.get(position));

        View pickListLayout = view.findViewById(R.id.pickListLayout);
        Button BoxModifyBtn = view.findViewById(R.id.BoxModifyBtn);
        Button BoxDeleteBtn = view.findViewById(R.id.BoxDeleteBtn);

        pickListLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, TitleValues.get(position) + "폴더 클릭", Toast.LENGTH_SHORT).show();

            }
        });

        BoxModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "수정 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        BoxDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "삭제 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
}

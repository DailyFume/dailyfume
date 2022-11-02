package com.example.daily_fume;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.CustomViewHolder> {

    private ArrayList<FragranceData> DetailList = null;
    private Activity context = null;

    String a1; // 받아올 변수
    RelativeLayout mainTitle; // 상세 배경
    TextView top_tv, middle_tv, base_tv; // 각 노트들
    LinearLayout tagBox; // 태그박스


    public DetailAdapter(Activity context, ArrayList<FragranceData> list) {
        this.context = context;
        this.DetailList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView brand;
        protected TextView name;
        protected TextView namek;
        protected TextView vol;
        protected TextView type;
        protected TextView top;
        protected TextView middle;
        protected TextView base;
        protected TextView tag1;
        protected TextView tag2;
        protected TextView tag3;
        protected TextView tag4;
        protected TextView tag5;
        protected TextView tag6;
        protected TextView tag7;
        protected TextView tag8;

        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.TitleImg);
            this.brand = (TextView) view.findViewById(R.id.BrandText);
            this.name = (TextView) view.findViewById(R.id.DetailTitle);
            this.namek = (TextView) view.findViewById(R.id.DetailKorean);
            this.type = (TextView) view.findViewById(R.id.TypeText);
            this.vol = (TextView) view.findViewById(R.id.VolText);
            this.top = (TextView) view.findViewById(R.id.topnote);
            this.middle = (TextView) view.findViewById(R.id.middlenote);
            this.base = (TextView) view.findViewById(R.id.basenote);
            this.tag1 = (TextView) view.findViewById(R.id.DetailTag1);
            this.tag2 = (TextView) view.findViewById(R.id.DetailTag2);
            this.tag3 = (TextView) view.findViewById(R.id.DetailTag3);
            this.tag4 = (TextView) view.findViewById(R.id.DetailTag4);
            this.tag5 = (TextView) view.findViewById(R.id.DetailTag5);
            this.tag6 = (TextView) view.findViewById(R.id.DetailTag6);
            this.tag7 = (TextView) view.findViewById(R.id.DetailTag7);
            this.tag8 = (TextView) view.findViewById(R.id.DetailTag8);

        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_green, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.image.setImageBitmap(DetailList.get(position).getFragrance_image());
        viewholder.brand.setText(DetailList.get(position).getFragrance_brand());
        viewholder.name.setText(DetailList.get(position).getFragrance_name());
        viewholder.namek.setText(DetailList.get(position).getFragrance_namek());
        viewholder.type.setText(DetailList.get(position).getFragrance_type());
        viewholder.vol.setText(DetailList.get(position).getFragrance_vol());
        viewholder.top.setText(DetailList.get(position).getFragrance_top());
        viewholder.middle.setText(DetailList.get(position).getFragrance_middle());
        viewholder.base.setText(DetailList.get(position).getFragrance_base());
        viewholder.tag1.setText(DetailList.get(position).getFragrance_tag1());
        viewholder.tag2.setText(DetailList.get(position).getFragrance_tag2());
        viewholder.tag3.setText(DetailList.get(position).getFragrance_tag3());
        viewholder.tag4.setText(DetailList.get(position).getFragrance_tag4());
        viewholder.tag5.setText(DetailList.get(position).getFragrance_tag5());
        viewholder.tag6.setText(DetailList.get(position).getFragrance_tag6());
        viewholder.tag7.setText(DetailList.get(position).getFragrance_tag7());
        viewholder.tag8.setText(DetailList.get(position).getFragrance_tag8());

        // 어댑터에서 어댑터 레이아웃 접근하면 가능!! ( 액티비티에서 type 불러와서 switch문으로 각 레이아웃 컬러 및 리소스 지정)
        a1 = ((FumeActivity)FumeActivity.fCon).type;
        this.mainTitle = viewholder.itemView.findViewById(R.id.mainTitle);
        this.top_tv = viewholder.itemView.findViewById(R.id.top_tv);
        this.middle_tv = viewholder.itemView.findViewById(R.id.middle_tv);
        this.base_tv = viewholder.itemView.findViewById(R.id.base_tv);
        this.tagBox = viewholder.itemView.findViewById(R.id.tagBox);

        switch (a1) {
            case "알데하이드" :
                //Toast.makeText(context, a1+"찾음", Toast.LENGTH_SHORT).show(); // 제대로 찾아오는지 확인 완료!
                mainTitle.setBackgroundResource(R.drawable.detail_aldehyde);
                top_tv.setBackgroundColor(Color.parseColor("#EFF5FE"));
                middle_tv.setBackgroundColor(Color.parseColor("#B4CCDC"));
                base_tv.setBackgroundColor(Color.parseColor("#6899B9"));
                tagBox.setBackgroundResource(R.drawable.detail_aldehyde_tagbox);
                break;
            case "시트러스" :
                mainTitle.setBackgroundResource(R.drawable.detail_citrus);
                top_tv.setBackgroundColor(Color.parseColor("#F0BFB2"));
                middle_tv.setBackgroundColor(Color.parseColor("#E5937C"));
                base_tv.setBackgroundColor(Color.parseColor("#DA6D3F"));
                tagBox.setBackgroundResource(R.drawable.detail_citrus_tagbox);
                break;
            case "플로럴" :
                mainTitle.setBackgroundResource(R.drawable.detail_floral);
                top_tv.setBackgroundColor(Color.parseColor("#F3DCE8"));
                middle_tv.setBackgroundColor(Color.parseColor("#E0A8C4"));
                base_tv.setBackgroundColor(Color.parseColor("#D282AA"));
                tagBox.setBackgroundResource(R.drawable.detail_floral_tagbox);
                break;
            case "프루티" :
                mainTitle.setBackgroundResource(R.drawable.detail_fruity);
                top_tv.setBackgroundColor(Color.parseColor("#FFEDC0"));
                middle_tv.setBackgroundColor(Color.parseColor("#FEDA7D"));
                base_tv.setBackgroundColor(Color.parseColor("#FCB80A"));
                tagBox.setBackgroundResource(R.drawable.detail_fruity_tagbox);
                break;
            case "그린" :
                mainTitle.setBackgroundResource(R.drawable.detail_green);
                top_tv.setBackgroundColor(Color.parseColor("#E8F2E9"));
                middle_tv.setBackgroundColor(Color.parseColor("#A0CAA4"));
                base_tv.setBackgroundColor(Color.parseColor("#5AA261"));
                tagBox.setBackgroundResource(R.drawable.detail_green_tagbox);
                break;
            case "머스크" :
                mainTitle.setBackgroundResource(R.drawable.detail_musk);
                top_tv.setBackgroundColor(Color.parseColor("#E8E2ED"));
                middle_tv.setBackgroundColor(Color.parseColor("#B09BC0"));
                base_tv.setBackgroundColor(Color.parseColor("#8A6BA2"));
                tagBox.setBackgroundResource(R.drawable.detail_musk_tagbox);
                break;
            case "오셔닉" :
                mainTitle.setBackgroundResource(R.drawable.detail_oceanic);
                top_tv.setBackgroundColor(Color.parseColor("#DAEEF2"));
                middle_tv.setBackgroundColor(Color.parseColor("#81C6D5"));
                base_tv.setBackgroundColor(Color.parseColor("#1997B2"));
                tagBox.setBackgroundResource(R.drawable.detail_oceanic_tagbox);
                break;
            case "오리엔탈" :
                mainTitle.setBackgroundResource(R.drawable.detail_oriental);
                top_tv.setBackgroundColor(Color.parseColor("#E1C1D2"));
                middle_tv.setBackgroundColor(Color.parseColor("#C486A6"));
                base_tv.setBackgroundColor(Color.parseColor("#A23F72"));
                tagBox.setBackgroundResource(R.drawable.detail_oriental_tagbox);
                break;
            case "스파이시" :
                mainTitle.setBackgroundResource(R.drawable.detail_spicy);
                top_tv.setBackgroundColor(Color.parseColor("#EDDBDB"));
                middle_tv.setBackgroundColor(Color.parseColor("#C88D8D"));
                base_tv.setBackgroundColor(Color.parseColor("#AD5555"));
                tagBox.setBackgroundResource(R.drawable.detail_spicy_tagbox);
                break;
            case "우디" :
                mainTitle.setBackgroundResource(R.drawable.detail_woody);
                top_tv.setBackgroundColor(Color.parseColor("#F0EAE6"));
                middle_tv.setBackgroundColor(Color.parseColor("#D0BFB3"));
                base_tv.setBackgroundColor(Color.parseColor("#A17E66"));
                tagBox.setBackgroundResource(R.drawable.detail_woody_tagbox);
                break;
        }

    }

    @Override
    public int getItemCount() {
        //return DetailList.size();
        return (null != DetailList ? DetailList.size() : 0);
    }

}
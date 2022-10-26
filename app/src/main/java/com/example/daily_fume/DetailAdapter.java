package com.example.daily_fume;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.CustomViewHolder> {

    private ArrayList<FragranceData> DetailList = null;
    private Activity context = null;

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

//        View view1;
//        View view2;
//        CustomViewHolder viewHolder;
//
//        switch ((DetailList.get(viewType).getFragrance_type())) {
//            case "aldehyde" :
//                view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_aldehyde, null);
//                viewHolder = new CustomViewHolder(view1);
//                break;
//            case "green" :
//                view2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_green, null);
//                viewHolder = new CustomViewHolder(view2);
//                break;
//        }
//
////        CustomViewHolder viewHolder = new CustomViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        //return DetailList.size();
        return (null != DetailList ? DetailList.size() : 0);
    }

}
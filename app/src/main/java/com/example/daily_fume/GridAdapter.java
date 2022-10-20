package com.example.daily_fume;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CustomViewHolder> {

    private ArrayList<FragranceData> mList = null;
    private Activity context = null;

    public GridAdapter(Activity context, ArrayList<FragranceData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView brand;
        protected TextView name;

        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.FragranceImageView);
            this.brand = (TextView) view.findViewById(R.id.BrandTextView);
            this.name = (TextView) view.findViewById(R.id.NameTextView);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.image.setImageBitmap(mList.get(position).getFragrance_image());
        viewholder.brand.setText(mList.get(position).getFragrance_brand());
        viewholder.name.setText(mList.get(position).getFragrance_name());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
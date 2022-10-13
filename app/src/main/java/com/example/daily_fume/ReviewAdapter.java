package com.example.daily_fume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ReviewData> reviewData;

    public ReviewAdapter(Context context, ArrayList<ReviewData> data) {
        mContext = context;
        reviewData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return reviewData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ReviewData getItem(int position) {
        return reviewData.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.review_layout, null);

        ImageView reviewImg = (ImageView) view.findViewById(R.id.reviewImg);
        TextView nickName = (TextView) view.findViewById(R.id.nickName);
        TextView reviewStr = (TextView) view.findViewById(R.id.reviewStr);

        reviewImg.setImageResource(reviewData.get(position).getReviewImg());
        nickName.setText(reviewData.get(position).getNickName());
        reviewStr.setText(reviewData.get(position).getReviewStr());

        return view;
    }

}

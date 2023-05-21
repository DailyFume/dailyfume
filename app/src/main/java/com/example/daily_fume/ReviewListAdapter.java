package com.example.daily_fume;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.CustomViewHolder> {

    Activity context;
    //LayoutInflater layoutInflater;
    ArrayList<ReviewListData> reviewListData;

    // 클릭 이벤트 구현을 위해 추가한 코드
    public interface OnItemClickListener {
        void onDeleteClicked(int position);
        void onModifyClicked(int position);
    }

    private OnItemClickListener itemClickListener = null; // 참조 변수 선언

//    public void setOnItemClickListener (OnItemClickListener listener) {
//        this.itemClickListener = listener;
//    }

    public ReviewListAdapter(ArrayList<ReviewListData> reviewListData, OnItemClickListener itemClickListener) {
        //this.context = context;
        // this.layoutInflater = LayoutInflater.from(context);
        this.reviewListData = reviewListData;
        this.itemClickListener = itemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView image;
        protected TextView brand;
        protected TextView name;
        protected TextView content;
        protected RatingBar rate;
        protected Button deleteBtn, modifyBtn;
        protected WeakReference<OnItemClickListener> listener;

        public CustomViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            listener = new WeakReference<>(onItemClickListener);
            this.brand = view.findViewById(R.id.RBrand);
            this.name = view.findViewById(R.id.RTitle);
            this.content = view.findViewById(R.id.Rstr);
            this.rate = view.findViewById(R.id.Rstars);
            this.image = view.findViewById(R.id.RImg);
            this.deleteBtn = view.findViewById(R.id.Review_delete_Btn);
            this.modifyBtn = view.findViewById(R.id.Review_modify_Btn);

            deleteBtn.setOnClickListener(this);
            modifyBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.get().onDeleteClicked(getAdapterPosition());
            listener.get().onModifyClicked(getAdapterPosition());
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_layout, null);
//        CustomViewHolder viewHolder = new CustomViewHolder(view);
//
//        return viewHolder;
        return new CustomViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_layout,null), itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.image.setImageBitmap(reviewListData.get(position).getImage());
        viewholder.brand.setText(reviewListData.get(position).getBrand());
        viewholder.name.setText(reviewListData.get(position).getName());
        viewholder.content.setText(reviewListData.get(position).getContent());
        viewholder.rate.setRating((float) reviewListData.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return (null != reviewListData ? reviewListData.size() : 0);
    }

}



package com.example.daily_fume;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog; // appcompat과 관련없는 AlertDialog import

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.CustomViewHolder> {

    Activity context;
    //LayoutInflater layoutInflater;
    ArrayList<ReviewListData> reviewListData;

    public ReviewListAdapter(Activity context, ArrayList<ReviewListData> reviewListData) {
        this.context = context;
        // this.layoutInflater = LayoutInflater.from(context);
        this.reviewListData = reviewListData;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView brand;
        protected TextView name;
        protected TextView content;
        protected RatingBar rate;

        public CustomViewHolder(View view) {
            super(view);
            this.brand = view.findViewById(R.id.RBrand);
            this.name = view.findViewById(R.id.RTitle);
            this.content = view.findViewById(R.id.Rstr);
            this.rate = view.findViewById(R.id.Rstars);
            this.image = view.findViewById(R.id.RImg);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
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


//
//    @Override
//    public int getCount() {
//        //return reviewListData.size(); // 기존
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return reviewListData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = layoutInflater.inflate(R.layout.review_list_layout, null);
//
//        TextView brand = view.findViewById(R.id.RBrand);
//        brand.setText(reviewListData.get(position).getBrand());
//        TextView name = view.findViewById(R.id.RTitle);
//        name.setText(reviewListData.get(position).getName());
//        TextView content = view.findViewById(R.id.Rstr);
//        content.setText(reviewListData.get(position).getContent());
//        RatingBar rate = view.findViewById(R.id.Rstars);
//        // rate.setRating(reviewListData.get(position).getRate());
//        ImageView image = view.findViewById(R.id.RImg);
//        image.setImageBitmap(reviewListData.get(position).getImage());

//        Button Review_delete_Btn = view.findViewById(R.id.Review_delete_Btn);
//
//        View reviewBoxClick = view.findViewById(R.id.reviewBoxClick); // 박스를 누르면 수정할 수 있게 페이지가 떠야 함
//        reviewBoxClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "["+reviewListData.get(position)+"]" + "번째 리뷰 수정", Toast.LENGTH_SHORT).show();
//                // intent로 클릭한 상품의 정보 전달
//                Intent intent = new Intent(context,ReviewEditActivity.class);
//                intent.putExtra("RBrand",reviewListData.get(position).getBrand());
//                intent.putExtra("RTitle",reviewListData.get(position).getName());
//                intent.putExtra("Rstr",reviewListData.get(position).getContent());
//                intent.putExtra("IntStars",reviewListData.get(position).getRate());
//                intent.putExtra("RImg",reviewListData.get(position).getImage());
//
//                ((ReviewListActivity)context).startActivity(intent);
//                // ★ 리뷰박스 클릭시 해당 리뷰의 내용이 적혀있는 리뷰 작성(수정) 페이지로 이동되어야 함
//            }
//        });
//
//        Review_delete_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "["+reviewListData.get(position)+"]" + "번째 리뷰 삭제", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder RdelBuilder = new AlertDialog.Builder(context);
//                RdelBuilder.setTitle("("+reviewListData.get(position).getBrand() + "/" + reviewListData.get(position).getName()+")")
//                        .setMessage("해당 리뷰를 삭제하시겠습니까?")
//                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                // ★ 해당 리뷰 삭제하는 코드
//                                Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialogInterface, int whichButton) {
//                                dialogInterface.cancel();
//                            }
//                        })
//                        .create().show();
//            }
//        });
//
//        return view;
//    }
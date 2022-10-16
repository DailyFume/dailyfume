package com.example.daily_fume;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog; // appcompat과 관련없는 AlertDialog import
import java.util.ArrayList;

public class ReviewListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ReviewListData> reviewListData;

    public ReviewListAdapter(Context context, ArrayList<ReviewListData> reviewListData){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.reviewListData = reviewListData;
    }

    @Override
    public int getCount() {
        return reviewListData.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.review_list_layout, null);


        TextView RBrand = view.findViewById(R.id.RBrand);
        RBrand.setText(reviewListData.get(position).getRBrand());
        TextView RTitle = view.findViewById(R.id.RTitle);
        RTitle.setText(reviewListData.get(position).getRTitle());
        TextView Rstr = view.findViewById(R.id.Rstr);
        Rstr.setText(reviewListData.get(position).getRstr());
        RatingBar Rstars = view.findViewById(R.id.Rstars);
        Rstars.setRating(reviewListData.get(position).getIntStars());
        ImageView RImg = view.findViewById(R.id.RImg);
        RImg.setImageResource(reviewListData.get(position).getRImg());

        Button Review_delete_Btn = view.findViewById(R.id.Review_delete_Btn);
        //Button Review_modify_Btn = view.findViewById(R.id.Review_modify_Btn);
        /*
        Review_modify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "["+ReviewListNum.get(position)+"]" + "번째 리뷰 수정", Toast.LENGTH_SHORT).show();
            }
        }); */

        View reviewBoxClick = view.findViewById(R.id.reviewBoxClick); // 박스를 누르면 수정할 수 있게 페이지가 떠야 함
        reviewBoxClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "["+reviewListData.get(position)+"]" + "번째 리뷰 수정", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,ReviewEditActivity.class);
                intent.putExtra("RBrand",reviewListData.get(position).getRBrand());
                intent.putExtra("RTitle",reviewListData.get(position).getRTitle());
                intent.putExtra("Rstr",reviewListData.get(position).getRstr());
                intent.putExtra("IntStars",reviewListData.get(position).getIntStars());
                intent.putExtra("RImg",reviewListData.get(position).getRImg());
//                Toast.makeText(context, reviewListData.get(position).getRBrand() + "", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, reviewListData.get(position).getRTitle() + "", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, reviewListData.get(position).getRstr() + "", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, reviewListData.get(position).getIntStars() + "", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, reviewListData.get(position).getRImg() + "", Toast.LENGTH_SHORT).show();
                ((ReviewListActivity)context).startActivity(intent);
                // ★ 리뷰박스 클릭시 해당 리뷰의 내용이 적혀있는 리뷰 작성(수정) 페이지로 이동되어야 함
            }
        });

        Review_delete_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "["+reviewListData.get(position)+"]" + "번째 리뷰 삭제", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder RdelBuilder = new AlertDialog.Builder(context);
                RdelBuilder.setTitle("")
                        .setMessage("해당 리뷰를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // ★ 해당 리뷰 삭제하는 코드
                                Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                dialogInterface.cancel();
                            }
                        })
                        .create().show();
            }
        });

        return view;
    }
}

package com.example.daily_fume;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CustomViewHolder> {

    private ArrayList<FragranceData> mList = null;
    private Activity context = null;
    protected TextView name;

    // intent
    int uid;
    String uname;
    String uemail;

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

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Toast.makeText(context.getApplicationContext(), name.getText()+"",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context,FumeActivity.class);
//                    intent.putExtra("title is", String.valueOf(name.getText()));
//                    ((SearchActivity)context).startActivity(intent);
//                }
//            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, @SuppressLint("RecyclerView") int position) {

        viewholder.image.setImageBitmap(mList.get(position).getFragrance_image());
        viewholder.brand.setText(mList.get(position).getFragrance_brand());
        viewholder.name.setText(mList.get(position).getFragrance_name());

        uid = ((SearchActivity)SearchActivity.sCon).uid;
        uname = ((SearchActivity)SearchActivity.sCon).uname;
        uemail = ((SearchActivity)SearchActivity.sCon).uemail;

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), name.getText()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,FumeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("uemail", uemail);
                intent.putExtra("title is", mList.get(position).getFragrance_name());
                ((SearchActivity)context).startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
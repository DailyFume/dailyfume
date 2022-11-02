package com.example.daily_fume;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PickFumeAdapter extends RecyclerView.Adapter<PickFumeAdapter.CustomViewHolder> {

    private ArrayList<PickFume> pfitems  = null;
    private Activity pfcontext = null;

    // intent
    int uid;
    String uname;

    public PickFumeAdapter(Activity pfcontext, ArrayList<PickFume> flist) {
        this.pfcontext = pfcontext;
        this.pfitems = flist;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView brand;
        protected TextView name;

        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.fumeImages);
            this.brand = (TextView) view.findViewById(R.id.pickFumeBrand);
            this.name = (TextView) view.findViewById(R.id.pickFumeTitle);
        }
    }

    @Override
    public PickFumeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pick_fumelist_layout, null);
        PickFumeAdapter.CustomViewHolder viewHolder = new PickFumeAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickFumeAdapter.CustomViewHolder viewholder, @SuppressLint("RecyclerView") int position) {

        viewholder.image.setImageBitmap(pfitems.get(position).getIamgesResId());
        viewholder.brand.setText(pfitems.get(position).getBrand());
        viewholder.name.setText(pfitems.get(position).getFumeName());

        uid = ((PickFumeActivity)PickFumeActivity.pfCon).uid;
        uname = ((PickFumeActivity)PickFumeActivity.pfCon).uname;
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), name.getText()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(pfcontext,FumeActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("uname", uname);
                intent.putExtra("title is", pfitems.get(position).getFumeName());
                ((PickFumeActivity)pfcontext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != pfitems ? pfitems.size() : 0);
    }


}

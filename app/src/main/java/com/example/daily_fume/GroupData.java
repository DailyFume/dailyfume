package com.example.daily_fume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class GroupData implements Serializable, Comparable<GroupData> {

    private String groupTitle;

    private int listid;

    public String getGroupTitle() {
        return groupTitle;
    }

    public int getListid() { return listid; }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public void setListid(int listid) { this.listid = listid; }

    @Override
    public int compareTo(GroupData o) {
        return this.listid - o.listid;
    }
}
package com.example.daily_fume;

import java.io.Serializable;

public class GroupData implements Serializable {

    private String groupTitle;

    public GroupData(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

}
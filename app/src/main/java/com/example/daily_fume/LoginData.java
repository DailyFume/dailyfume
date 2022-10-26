package com.example.daily_fume;

import android.graphics.Bitmap;

public class LoginData {

    private int uid;
    private String uemail;
    private String upassword;
    private String uname;

    public Integer getUid() {
        return uid;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUpassword() {
        return upassword;
    }

    public String getUname() {
        return uname;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

}

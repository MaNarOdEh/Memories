package com.example.memories;

public class AppInfo {
    String mApp_name;
    String mUser_name;
    String mApp_pass;
    public AppInfo(){

    }
    public AppInfo(String app_name, String user_name, String app_pass){
        this.mApp_name=app_name;
        this.mUser_name=user_name;
        this.mApp_pass=app_pass;

    }

    public String getmApp_name() {
        return mApp_name;
    }

    public String getmApp_pass() {
        return mApp_pass;
    }

    public String getmUser_name() {
        return mUser_name;
    }

    public void setmApp_name(String mApp_name) {
        this.mApp_name = mApp_name;
    }

    public void setmApp_pass(String mApp_pass) {
        this.mApp_pass = mApp_pass;
    }

    public void setmUser_name(String mUser_name) {
        this.mUser_name = mUser_name;
    }
}

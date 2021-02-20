package com.bauet.bauet;

import android.content.SharedPreferences;

public class InformationMVC {

    private  String mID;
    private  String mYS;
    private  String mDept;
    private  String mBatch;


    public InformationMVC(String ID, String YS, String dept, String batch) {
        mID = ID;
        mYS = YS;
        mDept = dept;
        mBatch = batch;
    }

    public String getID() {
        return mID;
    }

    public String getYS() {
        return mYS;
    }

    public String getDept() {
        return mDept;
    }



    public void setID(String ID) {
        mID = ID;
    }

    public void setYS(String YS) {
        mYS = YS;
    }

    public void setDept(String dept) {
        mDept = dept;
    }

    public String getBatch() {
        return mBatch;
    }

    public void setBatch(String batch) {
        mBatch = batch;
    }






}

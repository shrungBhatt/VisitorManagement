package com.example.android.visitormanagement;

/**
 * Created by Shrung on 29-Jul-17.
 */

//Model class for monuments having getter and setter to store the data.

public class MonumentData {
    private String mMonumentName;
    private int mRfidCnt;
    private int mPirCnt;
    private int mQrCnt;
    private String mDateAndTime;
    private float mRating;

    public String getMonumentName() {
        return mMonumentName;
    }

    public void setMonumentName(String monumentName) {
        mMonumentName = monumentName;
    }

    public int getRfidCnt() {
        return mRfidCnt;
    }

    public void setRfidCnt(int rfidCnt) {
        mRfidCnt = rfidCnt;
    }

    public int getPirCnt() {
        return mPirCnt;
    }

    public void setPirCnt(int pirCnt) {
        mPirCnt = pirCnt;
    }

    public int getQrCnt() {
        return mQrCnt;
    }

    public void setQrCnt(int qrCnt) {
        mQrCnt = qrCnt;
    }

    public String getDateAndTime() {
        return mDateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        mDateAndTime = dateAndTime;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }
}

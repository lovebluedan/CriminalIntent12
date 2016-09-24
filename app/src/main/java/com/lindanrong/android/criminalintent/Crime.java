package com.lindanrong.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by 林丹荣 on 2016/9/10.
 */
public class Crime {
    //mId是个只读变量，用于直接读取
    private UUID mId;
    private String mTitle;

    private Date mDate;
    private boolean mSolved;


    public Crime(){
        //生成惟一的标识符
        mId=UUID.randomUUID();
        mDate=new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

}

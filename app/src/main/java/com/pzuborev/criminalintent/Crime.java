package com.pzuborev.criminalintent;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Crime {
    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private DateFormat mDateFormat;

    public Crime() {
        mUUID = UUID.randomUUID();
        mDate = new Date();
        mDateFormat = DateFormat.getDateInstance(java.text.DateFormat.FULL, Locale.getDefault());
    }

    public Crime(String title, boolean solved) {
        this();
        mTitle = title;
        mSolved = solved;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDateString() {
        if (mDate != null)
            return mDateFormat.format(mDate);
        else
            return "";

    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}

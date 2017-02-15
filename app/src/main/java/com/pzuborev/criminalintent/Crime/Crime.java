package com.pzuborev.criminalintent.Crime;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Crime {
    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private static final String JSON_ID     = "id";
    private static final String JSON_TITLE  = "title";
    private static final String JSON_DATE   = "date";
    private static final String JSON_SOLVED = "solved";

    public Crime() {
        mUUID = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        setUUID(UUID.fromString((String) json.getString(JSON_ID)));
        setTitle(json.getString(JSON_TITLE));
        setDate(new Date(json.getLong(JSON_DATE)));
        setSolved(json.getBoolean(JSON_SOLVED));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID, getUUID().toString());
        jsonObject.put(JSON_TITLE, getTitle());
        jsonObject.put(JSON_DATE, getDate().getTime());
        jsonObject.put(JSON_SOLVED, isSolved());
        return jsonObject;
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

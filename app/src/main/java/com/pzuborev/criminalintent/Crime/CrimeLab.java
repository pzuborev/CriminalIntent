package com.pzuborev.criminalintent.Crime;


import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    // const
    private static final String CRIMES_FILENAME = "crimes.json";
    private static final String TAG = "CrimeLab";
    //
    private static CrimeLab crimeLab;
    // class fields
    private CrimeIntentJSONSerializer mSerializer;
    private ArrayList<Crime> mCrimes;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;

        mSerializer = new CrimeIntentJSONSerializer(appContext, CRIMES_FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            Log.e(TAG, "Error on loading crimes " + e.getMessage());
            mCrimes = new ArrayList<>();
        }

    }
    public static CrimeLab get(Context context){
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context.getApplicationContext());
        }
        return crimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime c: mCrimes) {
            if (c.getUUID().equals(id))
                return c;
        }

        return null;
    }


    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    public boolean saveCrimes(){
        try {
            mSerializer.saveCrimes(mCrimes);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error on saving crimes " + e.getMessage(), e);
            return false;
        }
    }

}

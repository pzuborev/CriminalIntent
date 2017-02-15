package com.pzuborev.criminalintent.Singleton;


import android.content.Context;

import com.pzuborev.criminalintent.Crime;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab crimeLab;

    private ArrayList<Crime> mCrimes;
    private Context mAppContext;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            mCrimes.add(new Crime("Crime #"+i, i % 2 == 0));
//        }
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
}

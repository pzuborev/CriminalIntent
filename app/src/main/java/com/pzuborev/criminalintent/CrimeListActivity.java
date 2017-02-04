package com.pzuborev.criminalintent;


import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment CreateFragment() {
        return new CrimeListFragment();
    }
}

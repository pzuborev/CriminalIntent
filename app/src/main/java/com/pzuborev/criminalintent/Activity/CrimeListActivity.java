package com.pzuborev.criminalintent.Activity;


import android.support.v4.app.Fragment;

import com.pzuborev.criminalintent.Activity.SingleFragmentActivity;
import com.pzuborev.criminalintent.Fragment.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment CreateFragment() {
        return new CrimeListFragment();
    }
}

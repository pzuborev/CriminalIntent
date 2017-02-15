package com.pzuborev.criminalintent.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.pzuborev.criminalintent.Crime;
import com.pzuborev.criminalintent.Singleton.CrimeLab;
import com.pzuborev.criminalintent.Fragment.CrimeFragment;
import com.pzuborev.criminalintent.R;

import java.util.ArrayList;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity{

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime c = mCrimes.get(position);
                Fragment fragment = CrimeFragment.newInstance(c.getUUID());

                return fragment;
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++) {
            Crime c = mCrimes.get(i);
            if (c.getUUID().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                updateTitle(c);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Crime c = mCrimes.get(position);
                updateTitle(c);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTitle(Crime c) {
        if (c.getTitle() != null){
            setTitle(c.getTitle());
        }
        else setTitle("");
    }
}

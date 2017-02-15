package com.pzuborev.criminalintent.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.pzuborev.criminalintent.Crime.Crime;
import com.pzuborev.criminalintent.Crime.CrimeLab;
import com.pzuborev.criminalintent.Activity.CrimePagerActivity;
import com.pzuborev.criminalintent.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CrimeListFragment extends ListFragment {
    public static final String TAG = "CrimeListFragment";
    private static final String SUBTITLE_VISIBLE = "com.pzuborev.criminalintent.Fragment.CrimeListFragment.SUBTITLE_VISIBLE";

    private ArrayList<Crime> mCrimes;
    private boolean mSubtitlesVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        mSubtitlesVisible = false;
        if (savedInstanceState != null){
            Log.d(TAG, "exists saved instance");
            mSubtitlesVisible = (boolean) savedInstanceState.get(SUBTITLE_VISIBLE);
        } else
            Log.d(TAG, "saved instance is empty");

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);

        CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimes);
        setListAdapter(crimeAdapter);

    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "OnDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttache");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putSerializable(SUBTITLE_VISIBLE, mSubtitlesVisible);
        Log.d(TAG, "onSavedInstanceState");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        if (savedInstanceState != null){
            Log.d(TAG, "exists saved instance");
            mSubtitlesVisible = (boolean) savedInstanceState.get(SUBTITLE_VISIBLE);
        } else
            Log.d(TAG, "saved instance is empty");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            if(mSubtitlesVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitles);
            }
        View listView = inflater.inflate(R.layout.list_view, container, false);

        Button addCrime = (Button) listView.findViewById(R.id.list_empty_new_crime);
        addCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCrime();
            }
        });


        return listView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, "onListItemClick " + crime.getTitle());
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.CRIME_ID, crime.getUUID());
        startActivity(i);

    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        private DateFormat mDateFormat = null;

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        private DateFormat getDateFormat() {
            if (mDateFormat == null)
                mDateFormat = DateFormat.getDateInstance(java.text.DateFormat.FULL, Locale.getDefault());
            return mDateFormat;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime c = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            TextView dateTextView =  (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            CheckBox solvedCheckBox =  (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);

            titleTextView.setText(c.getTitle());
            dateTextView.setText(getDateFormat().format(c.getDate()));
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
        Log.d(TAG, "onResume !");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem itemShowSubTitles = menu.findItem(R.id.menu_item_show_subtitle);
        if (itemShowSubTitles != null && mSubtitlesVisible)
            getActivity().getActionBar().setSubtitle(R.string.subtitles);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime :
                newCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    Log.d(TAG, "menu click ^^ switch on");
                    mSubtitlesVisible = true;
                    getActivity().getActionBar().setSubtitle(R.string.subtitles);
                    item.setTitle(R.string.menu_item_hide_subtitle);

                } else {
                    Log.d(TAG, "menu click ^^ switch off");
                    mSubtitlesVisible = false;
                    getActivity().getActionBar().setSubtitle(null);
                    item.setTitle(R.string.menu_item_show_subtitle);

                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void newCrime() {
        Log.d(TAG, "newCrime");
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);

        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.CRIME_ID, crime.getUUID());
        startActivity(i);
    }
}

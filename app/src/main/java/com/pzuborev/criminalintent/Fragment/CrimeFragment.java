package com.pzuborev.criminalintent.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.pzuborev.criminalintent.Crime.Crime;
import com.pzuborev.criminalintent.R;
import com.pzuborev.criminalintent.Crime.CrimeLab;

import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.pzuborev.criminalintent.Fragment.DatePickerFragment.EXTRA_DATE;
import static com.pzuborev.criminalintent.Fragment.TimePickerFragment.EXTRA_TIME;


public class CrimeFragment extends Fragment {
    public static final String CRIME_ID = "com.pzuborev.criminalintent.CRIME_ID";
    private static final String TAG = "CrimeFragment";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    //class fields
    private Crime mCrime;
    //view fields
    private EditText mTitleField;
    private Button mCrimeDateField;
    private Button mCrimeTimeField;
    private CheckBox mCrimeSolvedField;

    public static Fragment newInstance(UUID crimeId) {
        CrimeFragment crimeFragment = new CrimeFragment();
        Bundle arg = new Bundle();
        arg.putSerializable(CRIME_ID, crimeId);
        crimeFragment.setArguments(arg);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");

        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_ID);
        if (crimeId != null) {
            mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
            Assert.assertNotNull(mCrime);
            Log.d(TAG, " !! crime = " + mCrime + " crimeId = " + crimeId);
        } else {
            Log.d(TAG, " **** new crime");
            mCrime = new Crime();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putSerializable(CRIME_ID, mCrime.getUUID());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.criminal_fragment, container, false);

        setHasOptionsMenu(true);
        if (getActivity().getActionBar() != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        Assert.assertNotNull("onCreateView  mCrime is null", mCrime);
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });


        mCrimeDateField = (Button) v.findViewById(R.id.crime_date);
        if (mCrimeDateField == null)
            Log.d(TAG, "mCrimeDateField == null");
        mCrimeDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Set date of crime - click");
                DatePickerFragment datePicker = DatePickerFragment.newInstance(mCrime.getDate());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                datePicker.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                datePicker.show(fm, DIALOG_DATE);
            }
        });
        updateDateButtonText();

        mCrimeTimeField = (Button) v.findViewById(R.id.crime_time);
        Log.d(TAG, "mCrimeTimeField == null");

        mCrimeTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Set time of crime - click");
                TimePickerFragment timePicker = TimePickerFragment.newInstance(mCrime.getDate());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                timePicker.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timePicker.show(fm, DIALOG_TIME);
            }
        });

        updateTimeButtonText();


        mCrimeSolvedField = (CheckBox) v.findViewById(R.id.crime_solved);
        mCrimeSolvedField.setChecked(mCrime.isSolved());
        mCrimeSolvedField.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    private void updateDateButtonText() {
        java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL, Locale.getDefault());
        mCrimeDateField.setText(df.format(mCrime.getDate()));
    }

    private void updateTimeButtonText() {
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        mCrimeTimeField.setText(tf.format(mCrime.getDate()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult resultCode = " + resultCode + " requestCode = " + requestCode);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_DATE:
                Date crimeDate = (Date) data.getSerializableExtra(EXTRA_DATE);
                mCrime.setDate(crimeDate);
                updateDateButtonText();
                return;
            case REQUEST_TIME:
                Date crimeTime = (Date) data.getSerializableExtra(EXTRA_TIME);
                Log.d(TAG, "got crimeTime = " + crimeTime);
                mCrime.setDate(crimeTime);
                updateTimeButtonText();
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }
}

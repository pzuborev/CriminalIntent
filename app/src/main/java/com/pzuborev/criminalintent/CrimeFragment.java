package com.pzuborev.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Locale;
import java.util.UUID;


public class CrimeFragment extends Fragment {
    public static final String CRIME_ID = "com.pzuborev.criminalintent.CRIME_ID";
    private static final String TAG = "CrimeFragment";

    //class fields
    private Crime mCrime;
    //view fields
    private EditText mTitleField;
    private Button mCrimeDateField;
    private CheckBox mCrimeSolvedField;

    public static Fragment newInstance(UUID crimeId) {
        CrimeFragment crimeFragment = new CrimeFragment();
        Bundle arg = new Bundle();
        arg.putSerializable(CRIME_ID, crimeId);
        crimeFragment.setArguments(arg);
        return  crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_ID);
        if (crimeId != null) {
            mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
            Log.d(TAG, " **** crime" + mCrime);
        } else {
            Log.d(TAG, " **** new crime");
            mCrime = new Crime();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.criminal_fragment, container, false);

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

        java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL, Locale.getDefault());
        mCrimeDateField = (Button) v.findViewById(R.id.crime_date);
        mCrimeDateField.setText(df.format(mCrime.getDate()));


        mCrimeDateField.setEnabled(false);

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
}

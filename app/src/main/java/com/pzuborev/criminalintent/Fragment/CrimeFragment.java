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

import com.pzuborev.criminalintent.Crime;
import com.pzuborev.criminalintent.R;
import com.pzuborev.criminalintent.Singleton.CrimeLab;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.pzuborev.criminalintent.Fragment.DatePickerFragment.EXTRA_DATE;


public class CrimeFragment extends Fragment {
    public static final String CRIME_ID = "com.pzuborev.criminalintent.CRIME_ID";
    private static final String TAG = "CrimeFragment";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

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
        return crimeFragment;
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

        setHasOptionsMenu(true);
        if (getActivity().getActionBar() != null)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

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
        Log.d(TAG, "onActivityResult resultCode = " + resultCode);
        if (resultCode != RESULT_OK) return;
        Log.d(TAG, "onActivityResult processing .. requestCode = "+ requestCode);
        switch (requestCode){
            case REQUEST_DATE:
                Log.d(TAG, "onActivityResult processing REQUEST_DATE ");
                Date crimeDate = (Date) data.getSerializableExtra(EXTRA_DATE);
                mCrime.setDate(crimeDate);
                updateDateButtonText();
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

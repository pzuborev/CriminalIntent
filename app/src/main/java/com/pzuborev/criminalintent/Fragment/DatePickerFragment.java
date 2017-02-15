package com.pzuborev.criminalintent.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;

import com.pzuborev.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    private static final String EXTRA_DATE = "com.pzuborev.criminalintent.DatePickerFragment.date";
    private Date mCrimeDate;

    public static final DatePickerFragment newInstance(Date crimeDate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, crimeDate);
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setArguments(bundle);
        return datePicker;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mCrimeDate = (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCrimeDate);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
        datePicker.init(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        getArguments().putSerializable(EXTRA_DATE, date);
                    }
                });

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}

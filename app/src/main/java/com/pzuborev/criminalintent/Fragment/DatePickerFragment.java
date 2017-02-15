package com.pzuborev.criminalintent.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.pzuborev.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.pzuborev.criminalintent.DatePickerFragment.date";
    private static final String TAG = "DatePickerFragment";

    public static final DatePickerFragment newInstance(Date crimeDate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, crimeDate);
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setArguments(bundle);
        return datePicker;
    }

    public void sendResult(int resultCode) {
        Log.d(TAG, "sendResult " + resultCode);
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, (Date) getArguments().getSerializable(EXTRA_DATE));
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date crimeDate = (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(crimeDate);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
        int day = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        Log.d(TAG, "init " + day + " " + month + " " + year);

        datePicker.init(
                year, month, day,
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
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(RESULT_OK);
                    }
                })
                .create();
    }
}

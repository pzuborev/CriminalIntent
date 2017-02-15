package com.pzuborev.criminalintent.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.pzuborev.criminalintent.R;

import junit.framework.Assert;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    public static final String TAG = "TimePickerFragment";
    public static final String EXTRA_TIME = "com.pzuborev.criminalintent.time";

    public static final TimePickerFragment newInstance(Date crimeDate) {
        Assert.assertNotNull(crimeDate);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TIME, crimeDate);
        TimePickerFragment dialog = new TimePickerFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    private void sendResult(int resultCode) {
        Log.d(TAG, "sendResult " + resultCode);
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, getArguments().getSerializable(EXTRA_TIME));
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date crimeDate = (Date) getArguments().getSerializable(EXTRA_TIME);
        Assert.assertNotNull(crimeDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(crimeDate);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.crime_time);
        timePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
        } else {
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Date date = (Date) getArguments().getSerializable(EXTRA_TIME);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);

                getArguments().putSerializable(EXTRA_TIME, c.getTime());
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.time_crime)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "TimeDialog click");
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}

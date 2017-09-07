package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.ui.callback.DatePickerCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/20/16.
 */

public class FragDatePicker extends BottomSheetDialogFragment {

    @InjectView(R.id.dpPickerDialog)
    DatePicker dpPickerDialog;

    private DatePickerCallback callback;
    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;

    private Calendar mcurrentTime = Calendar.getInstance();

    private int year = mcurrentTime.get(Calendar.YEAR);
    private int month = mcurrentTime.get(Calendar.MONTH);
    private int dateMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
    private String ab_date_start;
    private String ab_date_end;

    private SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                setAlwaysExpanded();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    public FragDatePicker() {

    }

    @SuppressLint("ValidFragment")
    public FragDatePicker(DatePickerCallback listener, String ab_date_start, String ab_date_end) {
        this.callback = listener;
        this.ab_date_start = ab_date_start;
        this.ab_date_end = ab_date_end;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.dialog_datepicker, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        try {
            if (!ab_date_start.equals(""))
                dpPickerDialog.setMinDate(sdr.parse(ab_date_start).getTime());
            else
                dpPickerDialog.setMinDate(mcurrentTime.getTimeInMillis());
            if (!ab_date_end.equals(""))
                dpPickerDialog.setMaxDate(sdr.parse(ab_date_end).getTime());
            dpPickerDialog.init(year, month, dateMonth, new ObtainDateFromPicker());
        } catch (ParseException e) {
            Log.d("TAG", "exception " + e.getMessage());
        }

    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.tvDonePickerDialog)
    public void onDismissDatePickerDialog() {
        callback.ObtainDate(sdr.format(mcurrentTime.getTime()));
        dismiss();
    }

    private class ObtainDateFromPicker implements DatePicker.OnDateChangedListener {

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mcurrentTime.set(Calendar.YEAR, year);
                mcurrentTime.set(Calendar.MONTH, monthOfYear);
                mcurrentTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    }
}

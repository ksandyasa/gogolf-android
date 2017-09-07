package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.adapters.FlightNumberListAdapter;
import co.id.GoGolf.ui.callback.FlightNumberCallback;
import co.id.GoGolf.ui.callback.FlightNumberListAdapterCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/20/16.
 */

public class FragFlightNumber extends BottomSheetDialogFragment implements FlightNumberListAdapterCallback {

    private String flightNumber = "";
    private List<String> stringList = new ArrayList<>();
    private FlightNumberCallback callback;
    private int selectedPosition = 0;
    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;

    @InjectView(R.id.tvDoneFlightDialog)
    CustomTextView tvDoneFlightDialog;

    @InjectView(R.id.rvFlightDialog)
    RecyclerView rvFlightDialog;

    LinearLayoutManager linearLayoutManager;
    FlightNumberListAdapter flightNumberListAdapter;

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

    public FragFlightNumber() {

    }

    @SuppressLint("ValidFragment")
    public FragFlightNumber(int position, FlightNumberCallback listener) {
        this.selectedPosition = position;
        this.callback = listener;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.dialog_flight, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        String[] strings = getContext().getResources().getStringArray(R.array.flight_number);
        stringList = new ArrayList<>(Arrays.asList(strings));

        flightNumber = stringList.get(selectedPosition);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFlightDialog.setHasFixedSize(true);
        rvFlightDialog.setLayoutManager(linearLayoutManager);

        flightNumberListAdapter = new FlightNumberListAdapter(getContext(), stringList, selectedPosition, this);
        rvFlightDialog.setAdapter(flightNumberListAdapter);
    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.tvDoneFlightDialog)
    public void onDismissFlightDialog() {
        callback.ObtainFlightNumber(flightNumber);
        dismiss();
    }

    @Override
    public void onSelectedFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}

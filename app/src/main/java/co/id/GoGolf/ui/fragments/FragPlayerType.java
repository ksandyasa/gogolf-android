package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.models.response.prebooking.Price_list;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.adapters.PlayerAdapter;
import co.id.GoGolf.ui.callback.PlayerAdapterCallback;
import co.id.GoGolf.ui.callback.PlayerTypeCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/27/16.
 */

public class FragPlayerType extends BottomSheetDialogFragment implements PlayerAdapterCallback {

    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;
    private PlayerTypeCallback callback;

    @InjectView(R.id.tvDonePlayerType)
    CustomTextView tvDonePlayerType;

    @InjectView(R.id.rvDefault)
    RecyclerView rvCities;

    private PlayerAdapter playerAdapter;
    private ArrayList<Price_list> price_lists;
    private int pos = 0;
    private int posParent = 0;
    private boolean needCartOpt = false;
    private int selectedPosition = 0;

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

    public FragPlayerType() {

    }

    @SuppressLint("ValidFragment")
    public FragPlayerType(ArrayList<Price_list> price_lists, int pos, int posParent, boolean needCartOpt, int selectedPosition, PlayerTypeCallback listener) {
        this.price_lists = price_lists;
        this.pos = pos;
        this.posParent = posParent;
        this.needCartOpt = needCartOpt;
        this.selectedPosition = selectedPosition;
        this.callback = listener;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.act_dialog_tee_time, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        initRecyclerView();
    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void initRecyclerView() {
        rvCities.setHasFixedSize(true);
        rvCities.setLayoutManager(new LinearLayoutManager(rvCities.getContext()));
        rvCities.setItemAnimator(new DefaultItemAnimator());
//        rvSerp.addItemDecoration(new DividerItemDecoration(rvSerp.getContext(),
//                DividerItemDecoration.VERTICAL_LIST));
        playerAdapter = new PlayerAdapter(getActivity(), pos, posParent, needCartOpt, selectedPosition, this);
        playerAdapter.addPlayer(price_lists);
        rvCities.setAdapter(playerAdapter);
    }

    @OnClick(R.id.tvDonePlayerType)
    public void onDismissPlayerTypeDialog() {
        dismiss();
    }

    @Override
    public void SelectPlayerType(int position) {
        callback.ObtainPlayerType(position);
        dismiss();
    }
}

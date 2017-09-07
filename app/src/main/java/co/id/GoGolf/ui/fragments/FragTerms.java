package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.ui.activities.BaseAct;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 11/22/16.
 */

public class FragTerms extends BottomSheetDialogFragment {

    @InjectView(R.id.wvTerms)
    WebView wvTerms;

    private String urlTerms;
    private Context context;
    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;

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

    public FragTerms() {

    }

    @SuppressLint("ValidFragment")
    public FragTerms(Context context, String urlTerms) {
        this.context = context;
        this.urlTerms = urlTerms;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.dialog_terms, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        wvTerms.getSettings().setUseWideViewPort(true);
        wvTerms.getSettings().setLoadWithOverviewMode(false);
        wvTerms.getSettings().setSupportZoom(true);
        wvTerms.getSettings().setDisplayZoomControls(true);
        wvTerms.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        wvTerms.loadUrl(urlTerms);
        ((BaseAct)context).dismissLoadingDialog();
    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.tvDoneTerms)
    public void onDismissTerms() {
        dismiss();
    }

}

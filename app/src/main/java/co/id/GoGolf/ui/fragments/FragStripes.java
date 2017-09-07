package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import co.id.GoGolf.Config;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.ui.callback.StripeCallback;
import co.id.GoGolf.util.PreferenceUtility;

import butterknife.ButterKnife;

/**
 * Created by apridosandyasa on 12/13/16.
 */

public class FragStripes extends BottomSheetDialogFragment {

    private Context context;
    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;
    private StripeCallback callback;

    private int priceBook;
    private String bcode;
    private String bid;

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

    public FragStripes() {

    }

    @SuppressLint("ValidFragment")
    public FragStripes(Context context, int priceBook, String bcode, String bid, StripeCallback listener) {
        this.context = context;
        this.priceBook = priceBook;
        this.bcode = bcode;
        this.bid = bid;
        this.callback = listener;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.dialog_of_stripe, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        String stripeUrl = Config.SERVER_NAME + Config.API_STRIPE_CHARGE + "?id="+bcode+"&access_token="+ PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN);
        Log.d("TAG", "stripeUrl " + stripeUrl);
        Log.d("TAG", "useragent " + System.getProperty( "http.agent" ));

        //((BaseAct)context).dismissLoadingDialog();
//        wvStripe.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//                WebView view1 = new WebView(context);
//                view.addView(view1);
//                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
//                transport.setWebView(view1);
//                resultMsg.sendToTarget();
//
//                view1.setWebViewClient(new WebViewClient() {
//
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        Log.d("TAG", "stripeUrl " + url);
//                        if (url.equals(Config.SERVER_NAME + Config.API_STRIPE_CHARGE.replace("/view", "")))
//                            callback.ShowSuccessfulPaymentView();
//                    }
//                });
//
//                return true;
//            }
//        });
//        wvStripe.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.d("TAG", "stripeUrl " + url);
//                //view.loadUrl(url);
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // do write your code here
//                Log.d("TAG", "stripeUrl " + url);
//                if (url.equals(Config.SERVER_NAME + Config.API_STRIPE_CHARGE.replace("/view", "")))
//                    callback.ShowSuccessfulPaymentView();
//            }
//        });
//        wvStripe.getSettings().setJavaScriptEnabled(true);
//        wvStripe.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        //wvStripe.getSettings().setSupportMultipleWindows(true);
//        wvStripe.getSettings().setPluginState(WebSettings.PluginState.ON);
//        wvStripe.getSettings().setAllowContentAccess(true);
//        wvStripe.getSettings().setAllowFileAccess(true);
//        wvStripe.getSettings().setDomStorageEnabled(true);
//        wvStripe.getSettings().setUserAgentString(System.getProperty("http.agent"));//("Chrome/18.0.1025.166 Mobile");
//        wvStripe.getSettings().setUseWideViewPort(true);
//        wvStripe.getSettings().setLoadWithOverviewMode(true);
//        wvStripe.getSettings().setSupportZoom(true);
//        wvStripe.getSettings().setDisplayZoomControls(true);
//        wvStripe.loadUrl(stripeUrl);
    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }


//    public class WebAppInterface {
//        Context mContext;
//
//        /** Instantiate the interface and set the context */
//        WebAppInterface(Context c) {
//            mContext = c;
//        }
//
//        /** Show a toast from the web page */
//        @JavascriptInterface
//        public void showToast(String toast) {
//            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
//        }
//    }


}

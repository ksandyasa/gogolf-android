package co.id.GoGolf.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.PaymentTokenEvent;
import co.id.GoGolf.events.VeritransEvent;
import co.id.GoGolf.models.response.DataPaymentToken;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.ConfirmPaymentCallback;
import co.id.GoGolf.ui.fragments.FragConfirmPayment;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import id.co.veritrans.sdk.coreflow.core.SdkCoreFlowBuilder;
import id.co.veritrans.sdk.coreflow.core.VeritransSDK;
import id.co.veritrans.sdk.coreflow.eventbus.bus.VeritransBusProvider;
import id.co.veritrans.sdk.coreflow.eventbus.callback.TokenBusCallback;
import id.co.veritrans.sdk.coreflow.eventbus.events.GeneralErrorEvent;
import id.co.veritrans.sdk.coreflow.eventbus.events.GetTokenFailedEvent;
import id.co.veritrans.sdk.coreflow.eventbus.events.GetTokenSuccessEvent;
import id.co.veritrans.sdk.coreflow.eventbus.events.NetworkUnavailableEvent;
import id.co.veritrans.sdk.coreflow.models.CardTokenRequest;
import butterknife.ButterKnife;
import id.co.veritrans.sdk.coreflow.models.SaveCardRequest;

/**
 * Created by dedepradana on 7/15/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActCreditCard extends BaseAct implements TokenBusCallback, ConfirmPaymentCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.svCreditCardContainer)
    ScrollView svCreditCardContainer;

    @InjectView(R.id.card_name_container)
    TextInputLayout card_name_container;

    @InjectView(R.id.card_number_container)
    TextInputLayout card_number_container;

    @InjectView(R.id.exp_date_container)
    TextInputLayout exp_date_container;

    @InjectView(R.id.cvv_number_container)
    TextInputLayout cvv_number_container;

    @InjectView(R.id.mask_card_number_container)
    TextInputLayout mask_card_number_container;

    @InjectView(R.id.ll_date_container)
    LinearLayout ll_date_container;

    @InjectView(R.id.ll_saved_container)
    LinearLayout ll_saved_container;

    @InjectView(R.id.card_name)
    CustomEditText card_name;

    @InjectView(R.id.card_number)
    CustomEditText card_number;

    @InjectView(R.id.exp_date)
    CustomEditText exp_date;

    @InjectView(R.id.cvv_number)
    CustomEditText cvv_number;

    @InjectView(R.id.mask_card_number)
    CustomEditText mask_card_number;

    @InjectView(R.id.saved_cvv_number)
    CustomEditText saved_cvv_number;

    @InjectView(R.id.tv_new_card)
    CustomTextView tv_new_card;

    @InjectView(R.id.tv_saved_card)
    CustomTextView tv_saved_card;

    @InjectView(R.id.tvPrice)
    CustomTextView tvPrice;

    @InjectView(R.id.btnCreditCardPayment)
    CustomButton btnCreditCardPayment;

    FragConfirmPayment fragConfirmPayment;
    VeritransSDK veritransSDK;
    CardTokenRequest cardTokenRequest;
    SaveCardRequest saveCardRequest;
    User user;
    DataPaymentToken paymentToken;
    String totalPrice = "0";
    String bcode, bid;
    String tokenId;
    String savedTokenId = "";
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;
    private int modeCard = 0;
    private int editCard = 0;
    private int editExp = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);
        totalPrice = getIntent().getStringExtra("price");
        bcode = getIntent().getStringExtra("bcode");
        bid = getIntent().getStringExtra("bid");

        veritransSDK = new SdkCoreFlowBuilder(this, "VT-client-G6fMEXO_V3esGwGg", "http://golf-api.yocto-international.com/veritrans/vtdirect")
                .enableLog(true)
                .setDefaultText("open_sans_regular.ttf")
                .setSemiBoldText("open_sans_semibold.ttf")
                .setBoldText("open_sans_bold.ttf")
                .setMerchantName("mechantName")
                .buildSDK();

        initToolbar("Payment", null);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        VeritransBusProvider.getInstance().register(this);

        tvPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(totalPrice)));
        card_number.addTextChangedListener(new CreditCardWatcher());
        exp_date.addTextChangedListener(new ExpireDateWatcher());
        card_number.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace
                    editCard = 1;
                }else {
                    editCard = 0;
                }
                return false;
            }
        });
        exp_date.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace
                    editExp = 1;
                }else {
                    editExp = 0;
                }
                return false;
            }
        });

        cvv_number.setOnEditorActionListener(new HideSoftKeyboard());
        saved_cvv_number.setOnEditorActionListener(new HideSoftKeyboard());
        cvv_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                svCreditCardContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        svCreditCardContainer.scrollTo(0, cvv_number.getBottom());
                    }
                }, 250);
            }
        });
        saved_cvv_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                svCreditCardContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        svCreditCardContainer.scrollTo(0, saved_cvv_number.getBottom());
                    }
                }, 250);
            }
        });

        showLoadingDialog();
        mainPresenter.getPaymentToken(PreferenceUtility.getInstance().loadDataString(ActCreditCard.this, PreferenceUtility.ACCESS_TOKEN));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25 && data != null) {
            if (data.getStringExtra("success_paid").equals("success_paid")) {
                setResult(RESULT_OK, data);
                ActCreditCard.this.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister this class into event bus
        VeritransBusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    private boolean inputValidator() {
        return !card_number.getText().toString().isEmpty()
                && card_number.getText().toString().length() == 19
                && !cvv_number.getText().toString().isEmpty()
                && cvv_number.getText().toString().length() == 3
                && !exp_date.getText().toString().isEmpty()
                && exp_date.getText().toString().split("/").length == 2;
    }

    private void refreshView() {
        // Check card number
        if (card_number.getText().toString().isEmpty()) {
            card_number_container.setError("Must not be empty.");
        } else {
            if (card_number.getText().length() == 19) {
                card_number_container.setError(null);
            } else {
                card_number_container.setError("Must be 16 digits.");
            }
        }

        // Check cvv number
        if (cvv_number.getText().toString().isEmpty()) {
            cvv_number_container.setError("Must not be empty.");
        } else {
            if (cvv_number.getText().toString().length() == 3) {
                cvv_number_container.setError(null);
            } else {
                cvv_number_container.setError("Must be 3 digits.");
            }
        }

        // Check exp date
        if (exp_date.getText().toString().isEmpty()) {
            exp_date_container.setError("Must not be empty.");
        } else {
            if (exp_date.getText().toString().split("/").length == 2) {
                exp_date_container.setError(null);
            } else {
                exp_date_container.setError("Must be (mm/yy) formatted.");
            }
        }
    }

    @OnClick(R.id.btnCreditCardPayment)
    public void onCreditCardPayment() {
        if (modeCard == 0) {
            refreshView();
            if (inputValidator()) {
                showPointDialog(card_number.getText().toString(), totalPrice);
            }
        }else{
            showPointDialog(mask_card_number.getText().toString(), totalPrice);
        }
    }

    @OnClick(R.id.tv_new_card)
    public void onShowFillCreditCard() {
        modeCard = 0;
        ll_saved_container.setVisibility(View.GONE);
        tv_new_card.setVisibility(View.GONE);
        card_name_container.setVisibility(View.VISIBLE);
        card_number_container.setVisibility(View.VISIBLE);
        ll_date_container.setVisibility(View.VISIBLE);
        tv_saved_card.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_saved_card)
    public void onShowSavedCreditCard() {
        modeCard = 1;
        ll_saved_container.setVisibility(View.VISIBLE);
        tv_new_card.setVisibility(View.VISIBLE);
        card_name_container.setVisibility(View.GONE);
        card_number_container.setVisibility(View.GONE);
        ll_date_container.setVisibility(View.GONE);
        tv_saved_card.setVisibility(View.GONE);
    }

    @Subscribe
    @Override
    public void onEvent(NetworkUnavailableEvent networkUnavailableEvent) {
        // Handle network not available condition
        dismissLoadingDialog();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Network is unavailable")
                .create();
        dialog.show();
    }

    @Subscribe
    @Override
    public void onEvent(GeneralErrorEvent generalErrorEvent) {
        // Handle generic error condition
        dismissLoadingDialog();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Unknown error: " + generalErrorEvent.getMessage())
                .create();
        dialog.show();
    }

    @Subscribe
    @Override
    public void onEvent(GetTokenSuccessEvent getTokenSuccessEvent) {
        tokenId = getTokenSuccessEvent.getResponse().getTokenId();
        dismissLoadingDialog();
        showLoadingDialog();
        mainPresenter.postCharge(getTokenSuccessEvent.getResponse().getTokenId(), bid, (savedTokenId.equals("")) ? "1" : "");
    }

    @Subscribe
    @Override
    public void onEvent(GetTokenFailedEvent getTokenFailedEvent) {
        dismissLoadingDialog();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getTokenFailedEvent.getMessage())
                .create();
        dialog.show();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setMessage(event.getMessage())
//                .create();
//        dialog.show();
    }

    @Subscribe
    public void onEventThread(VeritransEvent event) {
        dismissLoadingDialog();
        PreferenceUtility.getInstance().saveData(ActCreditCard.this, PreferenceUtility.CARD_NUMBER, card_number.getText().toString());
        PreferenceUtility.getInstance().saveData(ActCreditCard.this, PreferenceUtility.EXP_DATE, exp_date.getText().toString());
        startActivityForResult(new Intent(getApplicationContext(), ActSuccessPaid.class).putExtra("totalprice",totalPrice).putExtra("bcode", bcode), 25);
    }

    @Subscribe
    public void onEventThread(PaymentTokenEvent event) {
        dismissLoadingDialog();
        savedTokenId = event.getData().getSaved_token_id();
        paymentToken = event.getData();
        if (!savedTokenId.equals("")) {
            modeCard = 1;
            card_name_container.setVisibility(View.GONE);
            card_number_container.setVisibility(View.GONE);
            ll_date_container.setVisibility(View.GONE);
            tv_saved_card.setVisibility(View.GONE);
            ll_saved_container.setVisibility(View.VISIBLE);
            tv_new_card.setVisibility(View.VISIBLE);
            mask_card_number.setText(paymentToken.getMasked_card());
            mask_card_number_container.setHint(getResources().getString(R.string.cc_card_number));
        }else{
            modeCard = 0;
            ll_saved_container.setVisibility(View.GONE);
            tv_new_card.setVisibility(View.GONE);
            card_name_container.setVisibility(View.VISIBLE);
            card_number_container.setVisibility(View.VISIBLE);
            ll_date_container.setVisibility(View.VISIBLE);
            tv_saved_card.setVisibility(View.GONE);
        }
    }

    private void showPointDialog(String cardNumber, final String totalPrice) {
        fragConfirmPayment = new FragConfirmPayment(ActCreditCard.this, cardNumber, totalPrice, this);
        fragConfirmPayment.show(getSupportFragmentManager(), "fragConfirmPayment");
//        LayoutInflater li = getLayoutInflater();
//        View promptsView = li.inflate(R.layout.dialog_confirm_payment, null);
//
//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                ActCreditCard.this);
//
//        alertDialogBuilder.setView(promptsView);
//
//        final TextView tvCreditCard = (TextView) promptsView
//                .findViewById(R.id.tvCreditCard);
//        final TextView tvPriceAmount = (TextView) promptsView
//                .findViewById(R.id.tvPriceAmount);
//
//        tvCreditCard.setText(cardNumber);
//        tvPriceAmount.setText("Rp. " + totalPrice);
//
//        alertDialogBuilder
//                .setTitle("Confirm Payment")
//                .setCancelable(false)
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//
//                    }
//                })
//                .setPositiveButton("Confirm",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                                showLoadingDialog();
//                                String date = exp_date.getText().toString();
//                                CardTokenRequest cardTokenRequest = new CardTokenRequest(
//                                        // Card number
//                                        card_number.getText().toString(),
//                                        cvv_number.getText().toString(),
//                                        date.split("/")[0],
//                                        date.split("/")[1],
//                                        veritransSDK.getClientKey());
//                                cardTokenRequest.setGrossAmount(Double.parseDouble(totalPrice));
//                                veritransSDK.getToken(cardTokenRequest);
//                            }
//                        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        alertDialog.show();
    }

    @Override
    public void ConfirmPayment() {
        showLoadingDialog();
        String date = exp_date.getText().toString();
        if (modeCard == 0) {
            cardTokenRequest = new CardTokenRequest(
                    // Card number
                    card_number.getText().toString().replace("-", ""),
                    cvv_number.getText().toString(),
                    date.split("/")[0],
                    date.split("/")[1],
                    veritransSDK.getClientKey());
            cardTokenRequest.setGrossAmount(Double.parseDouble(totalPrice));
            veritransSDK.getToken(cardTokenRequest);
        }else{
            cardTokenRequest = new CardTokenRequest();
            cardTokenRequest.setSavedTokenId(savedTokenId);
            cardTokenRequest.setCardCVV(saved_cvv_number.getText().toString());
            cardTokenRequest.setClientKey(veritransSDK.getClientKey());
            cardTokenRequest.setGrossAmount(Double.parseDouble(totalPrice));
            cardTokenRequest.setTwoClick(true);
            veritransSDK.getToken(cardTokenRequest);
        }
    }

    @Override
    public void CancelPayment() {

    }

    private class CreditCardWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG", "start " + start);
            Log.d("TAG", "before " + before);
            Log.d("TAG", "count " + count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (card_number.getText().toString().length() == 4 || card_number.getText().toString().length() == 9 || card_number.getText().toString().length() == 14) {
                if (editCard == 0) {
                    card_number.setText(card_number.getText().toString() + "-");
                    card_number.setSelection(card_number.getText().toString().length());
                }
            }
        }
    }

    private class ExpireDateWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (exp_date.getText().toString().length() == 2) {
                if (editExp == 0) {
                    exp_date.setText(exp_date.getText().toString() + "/");
                    exp_date.setSelection(exp_date.getText().length());
                }
            }
        }
    }

    private class HideSoftKeyboard implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return true;
            }

            return false;
        }
    }

}

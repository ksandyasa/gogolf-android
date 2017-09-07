package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.PaymentTokenEvent;
import co.id.GoGolf.events.StripeEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.fragments.FragStripes;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

/**
 * Created by apridosandyasa on 12/23/16.
 */

public class ActPayment extends BaseAct {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.rlPaymentOtherCC)
    RelativeLayout rlPaymentOtherCC;

    @InjectView(R.id.llPaymentNow)
    RelativeLayout llPaymentNow;

    @InjectView(R.id.edtNameOtherCC)
    CustomEditText edtNameOtherCC;

    @InjectView(R.id.edtCardOtherCC)
    CustomEditText edtCardOtherCC;

    @InjectView(R.id.edtExpOtherCC)
    CustomEditText edtExpOtherCC;

    @InjectView(R.id.edtCVVOtherCC)
    CustomEditText edtCVVOtherCC;

    @InjectView(R.id.tvMaskPaymentNow)
    CustomTextView tvMaskPaymentNow;

    @InjectView(R.id.tvExpPaymentNow)
    CustomTextView tvExpPaymentNow;

    @InjectView(R.id.tvDpPaymentNow)
    CustomTextView tvDpPaymentNow;

    private User user;
    private Card stripeCard;
    private Stripe stripeToken;
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;
    private int priceBook;
    private String bcode;
    private int modePayment = 0;
    private int editCard = 0;
    private int editExp = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar("Payment", null);

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);
        priceBook = getIntent().getIntExtra("price", 0);
        bcode = getIntent().getStringExtra("bcode");

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        edtNameOtherCC.setText(user.getEmail());
        edtCardOtherCC.addTextChangedListener(new CreditCardWatcher());
        edtExpOtherCC.addTextChangedListener(new ExpireDateWatcher());
        edtCardOtherCC.setOnKeyListener(new View.OnKeyListener() {
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
        edtExpOtherCC.setOnKeyListener(new View.OnKeyListener() {
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

        edtCVVOtherCC.setOnEditorActionListener(new HideSoftKeyboard());

        showLoadingDialog();
        mainPresenter.getPaymentToken(PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.ACCESS_TOKEN));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25 && data != null) {
            if (data.getStringExtra("success_paid").equals("success_paid")) {
                setResult(RESULT_OK, data);
                ActPayment.this.finish();
            }
        }
    }

    @Subscribe
    public void onEventThread(PaymentTokenEvent event) {
        dismissLoadingDialog();
        Log.d("TAG", "savedToken " + event.getData().getSaved_token_id());
        Log.d("TAG", "maskedCard " + event.getData().getMasked_card());
        Log.d("TAG", "uid " + event.getData().getUid());
        if (!event.getData().getMasked_card().equals("")) {
            if (PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.CARD_NUMBER).equals("")) {
                modePayment = 0;
                rlPaymentOtherCC.setVisibility(View.VISIBLE);
                llPaymentNow.setVisibility(View.GONE);
            }else{
                modePayment = 1;
                rlPaymentOtherCC.setVisibility(View.GONE);
                llPaymentNow.setVisibility(View.VISIBLE);
                tvDpPaymentNow.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(priceBook)));
                tvMaskPaymentNow.setText(event.getData().getMasked_card());
                tvExpPaymentNow.setText(event.getData().getExpiry_date());
            }
        }else {
            modePayment = 0;
            rlPaymentOtherCC.setVisibility(View.VISIBLE);
            llPaymentNow.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onEventThread(StripeEvent event) {
        dismissLoadingDialog();
        if (modePayment == 0) {
            PreferenceUtility.getInstance().saveData(ActPayment.this, PreferenceUtility.CARD_NAME, edtNameOtherCC.getText().toString());
            PreferenceUtility.getInstance().saveData(ActPayment.this, PreferenceUtility.CARD_NUMBER, edtCardOtherCC.getText().toString());
            PreferenceUtility.getInstance().saveData(ActPayment.this, PreferenceUtility.EXP_DATE, edtExpOtherCC.getText().toString());
            PreferenceUtility.getInstance().saveData(ActPayment.this, PreferenceUtility.CVV_NUMBER, edtCVVOtherCC.getText().toString());
        }
//        callback.ShowSuccessfulPaymentView();
        startActivityForResult(new Intent(ActPayment.this, ActSuccessPaid.class).putExtra("totalprice",priceBook).putExtra("bcode", bcode), 25);
    }

    private boolean inputValidator() {
        return !edtCardOtherCC.getText().toString().isEmpty()
                && edtCardOtherCC.getText().toString().length() == 19
                && !edtCVVOtherCC.getText().toString().isEmpty()
                && edtCVVOtherCC.getText().toString().length() == 3
                && !edtExpOtherCC.getText().toString().isEmpty()
                && edtExpOtherCC.getText().toString().split("/").length == 2
                && !edtNameOtherCC.getText().toString().isEmpty();
    }

    private void refreshView() {
        // Check card number
        if (edtCardOtherCC.getText().toString().isEmpty()) {
            Toast.makeText(ActPayment.this, "Must not be empty.", Toast.LENGTH_SHORT).show();
        } else {
            if (edtCardOtherCC.getText().length() == 19) {
                //card_number_container.setError(null);
            } else {
                Toast.makeText(ActPayment.this, "Must be 16 digits.", Toast.LENGTH_SHORT).show();
            }
        }

        // Check cvv number
        if (edtCVVOtherCC.getText().toString().isEmpty()) {
            Toast.makeText(ActPayment.this, "Must not be empty.", Toast.LENGTH_SHORT).show();
        } else {
            if (edtCVVOtherCC.getText().toString().length() == 3) {
                //cvv_number_container.setError(null);
            } else {
                Toast.makeText(ActPayment.this, "Must be 3 digits.", Toast.LENGTH_SHORT).show();
            }
        }

        // Check exp date
        if (edtExpOtherCC.getText().toString().isEmpty()) {
            Toast.makeText(ActPayment.this, "Must not be empty.", Toast.LENGTH_SHORT).show();
        } else {
            if (edtExpOtherCC.getText().toString().split("/").length == 2) {
                //exp_date_container.setError(null);
            } else {
                Toast.makeText(ActPayment.this, "Must be (mm/yy) formatted.", Toast.LENGTH_SHORT).show();
            }
        }

        // Check card name
        if (edtNameOtherCC.getText().toString().isEmpty()) {
            Toast.makeText(ActPayment.this, "Must not be empty.", Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
    }

    @OnClick(R.id.btnPayNowOtherCC)
    public void onPayWithOtherCC() {
        refreshView();
        if (inputValidator()) {
            String[] expDates = edtExpOtherCC.getText().toString().split("/");
            stripeCard = new Card(edtCardOtherCC.getText().toString().replace(" ", "-"), Integer.valueOf(expDates[0]), Integer.valueOf("20" + expDates[1]), edtCVVOtherCC.getText().toString());

            if (stripeCard.validateCard()) {
                if (stripeCard.validateExpMonth()) {
                    if (stripeCard.validateExpYear()) {
                        if (stripeCard.validateCVC()) {
                            try {
                                showLoadingDialog();
                                stripeToken = new Stripe("pk_test_3jV7G9yCWLxmu8eGHgPIdVCT");
                                stripeToken.createToken(stripeCard, new TokenCallback() {
                                    @Override
                                    public void onError(Exception error) {

                                    }

                                    @Override
                                    public void onSuccess(Token token) {
                                        mainPresenter.postStripeChargePayment(PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.ACCESS_TOKEN), bcode, token.getId(), "false");
                                    }
                                });
                            } catch (AuthenticationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.btnPaymentNow)
    public void onPayWithSavedToken() {
        String[] expDates = PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.EXP_DATE).split("/");
        stripeCard = new Card(PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.CARD_NUMBER).replace(" ", "-"),
                Integer.valueOf(expDates[0]),
                Integer.valueOf("20" + expDates[1]),
                PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.CVV_NUMBER));

        if (stripeCard.validateCard()) {
            if (stripeCard.validateExpMonth()) {
                if (stripeCard.validateExpYear()) {
                    if (stripeCard.validateCVC()) {
                        try {
                            showLoadingDialog();
                            stripeToken = new Stripe("pk_test_3jV7G9yCWLxmu8eGHgPIdVCT");
                            stripeToken.createToken(stripeCard, new TokenCallback() {
                                @Override
                                public void onError(Exception error) {
                                    Log.d("TAG", error.getMessage());
                                }

                                @Override
                                public void onSuccess(Token token) {
                                    mainPresenter.postStripeChargePayment(PreferenceUtility.getInstance().loadDataString(ActPayment.this, PreferenceUtility.ACCESS_TOKEN), bcode, token.getId(), "false");
                                }
                            });
                        } catch (AuthenticationException e) {
                            Log.d("TAG", e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.btnOtherPaymentNow)
    public void onShowPayWithOtherCC() {
        modePayment = 0;
        rlPaymentOtherCC.setVisibility(View.VISIBLE);
        llPaymentNow.setVisibility(View.GONE);
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
            if (edtCardOtherCC.getText().toString().length() == 4 || edtCardOtherCC.getText().toString().length() == 9 || edtCardOtherCC.getText().toString().length() == 14) {
                if (editCard == 0) {
                    edtCardOtherCC.setText(edtCardOtherCC.getText().toString() + " ");
                    edtCardOtherCC.setSelection(edtCardOtherCC.getText().toString().length());
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
            if (edtExpOtherCC.getText().toString().length() == 2) {
                if (editExp == 0) {
                    edtExpOtherCC.setText(edtExpOtherCC.getText().toString() + "/");
                    edtExpOtherCC.setSelection(edtExpOtherCC.getText().length());
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

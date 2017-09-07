package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.LoginEvent;
import co.id.GoGolf.models.response.FacebookMeResponse;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.presenters.OAuthPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 5/16/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActLogin extends BaseAct implements GoogleApiClient.OnConnectionFailedListener {

    @Inject
    OAuthPresenter oAuthPresenter;

    @InjectView(R.id.svContainerLogin)
    ScrollView svContainerLogin;

    @InjectView(R.id.tvTitleGlobal)
    CustomTextView tvTitleGlobal;

    @InjectView(R.id.tvTitleJp)
    CustomTextView tvTitleJp;

    @InjectView(R.id.etEmail)
    CustomEditText etEmail;

    @InjectView(R.id.etPassword)
    CustomEditText etPassword;

    private Context context;
    protected CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        context = getApplicationContext();

        if (PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN).length() > 0) {
            goToMainAct();
        }

        PreferenceUtility.getInstance().saveData(getApplicationContext(), PreferenceUtility.GG_DEVICE_ID, FirebaseInstanceId.getInstance().getToken());

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        doGetFBDataAndLogin(getApplicationContext(), loginResult);
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });

        if (PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.DEVICE_TOKEN).trim().length() == 0){
            PreferenceUtility.getInstance().saveData(getApplicationContext(), PreferenceUtility.DEVICE_TOKEN, FirebaseInstanceId.getInstance().getToken());
        }

        checkLocaleLogin();

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("TAG", "etEmail has focus");
                    svContainerLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerLogin.scrollTo(0, etPassword.getBottom());
                        }
                    }, 250);
                }

            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("TAG", "etPassword has focus");
                    svContainerLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerLogin.scrollTo(0, etPassword.getBottom());
                        }
                    }, 250);
                }
            }
        });
        etEmail.setOnEditorActionListener(new LoginFromInput());
        etPassword.setOnEditorActionListener(new LoginFromInput());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d("TAG", "back button pressed");
            getWindow().getDecorView().clearFocus();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkLocaleLogin() {
        if (PreferenceUtility.getInstance().loadDataString(ActLogin.this, PreferenceUtility.LANG_PREF).equals("jp")) {
            tvTitleGlobal.setVisibility(View.GONE);
            tvTitleJp.setVisibility(View.VISIBLE);
        }else{
            tvTitleGlobal.setVisibility(View.VISIBLE);
            tvTitleJp.setVisibility(View.GONE);
        }
    }

    private void goToMainAct() {
        startActivity(new Intent(context, MainActivity.class));
        ActLogin.this.finish();
    }

    @OnClick(R.id.llFb)
    public void onSignFb() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    @OnClick(R.id.btSignin)
    public void onSignIn() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        if (UIUtils.validateEmail(ActLogin.this, etEmail)) {
            if (UIUtils.validatePassword(ActLogin.this, etPassword)) {
                showLoadingDialog();
                oAuthPresenter.postLogin(etEmail.getText().toString(), etPassword.getText().toString(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.DEVICE_TOKEN));
            }
        }
    }

    @OnClick(R.id.tvForgotLogin)
    public void onForgot(){
        startActivity(new Intent(getApplicationContext(), ActForgot.class));
    }

    @OnClick(R.id.tvSignup)
    public void onSignUp() {
        startActivityForResult(new Intent(getApplicationContext(), ActRegister.class),21);
    }

    private void doGetFBDataAndLogin(final Context context, final LoginResult loginResult) {
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.FACEBOOK_TOKEN, loginResult.getAccessToken().getToken());
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject graphObject, GraphResponse response) {
                if (null == graphObject) {
//                    showQuickToast(getString(R.string.facebook_account_not_support));
                    return;
                }
//                Clog.e("====== GraphResponse : " + graphObject.toString());
//                Clog.e("====== GraphResponse : " + response.toString());
                String graphString = graphObject.toString();
                PreferenceUtility.getInstance().saveData(context, PreferenceUtility.FACEBOOK_ME_RESPONSE, graphString);
                FacebookMeResponse facebookMeResponse = new Gson().fromJson(graphString, FacebookMeResponse.class);
                if (null != facebookMeResponse) {
                    //Clog.e("====== FacebookMeResponse : " + facebookMeResponse.toString());
                    if (null == facebookMeResponse.getEmail()) {
//                        showQuickToast(getString(R.string.facebook_account_not_support));
                        LoginManager.getInstance().logOut();
                    } else {
//                        FbLoginParams params = new FbLoginParams();
//                        params.setFbId(facebookMeResponse.getId());
//                        params.setFirstName(facebookMeResponse.getFirstName());
//                        params.setLastName(facebookMeResponse.getLastName());
//                        params.setBirthdate(Utility.getInstance().getFormattedDateFromUS("dd/MM/yyyy", facebookMeResponse.getBirthday()));
//                        params.setGrantType(FbLoginParams.PASSWORD_GRAND_TYPE);
//                        params.setPassword(FbLoginParams.DEFAULT_PASSWORD);
//                        params.setSex(facebookMeResponse.getGender());
//                        params.setUsername(facebookMeResponse.getEmail());
//                        params.setUdid(Utility.getDeviceUID(BaseActivity.this));
//                        params.setAlertSubscribeStatus(FbLoginParams.DEFAULT_ALERT);
//
//                        UserController.getInstance(activity).postLoginViaFacebook(params);
                        showLoadingDialog();
                        Log.d(ActLogin.class.getSimpleName(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.FACEBOOK_TOKEN) + ", " +
                                "Facebook" + ", " +
                                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.GG_DEVICE_ID) + ", " +
                                "id");
                        oAuthPresenter.postSocialLogin(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.FACEBOOK_TOKEN),
                                "Facebook",
                                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.GG_DEVICE_ID),
                                "id");

                        //Toast.makeText(getApplicationContext(), "my email : " + facebookMeResponse.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
        } else if (requestCode == 21 && data != null){
            if (!data.getStringExtra("mainAct").equals(""))
                goToMainAct();
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Subscribe
    public void onEventThread(LoginEvent event) {
        dismissLoadingDialog();
        Log.d(ActLogin.class.getSimpleName(), event.getData().toString());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, new Gson().toJson(event.getData().getUser()));
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.ACCESS_TOKEN, event.getData().getAccess_token());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.PASSWORD, etPassword.getText().toString());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.COUNTRY_ID, event.getData().getUser().getCountry_id());
        goToMainAct();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private class LoginFromInput implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Your piece of code on keyboard search click
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                if (UIUtils.validateEmail(ActLogin.this, etEmail)) {
//                    if (UIUtils.validatePassword(ActLogin.this, etPassword)) {
//                        showLoadingDialog();
//                        oAuthPresenter.postLogin(etEmail.getText().toString(), etPassword.getText().toString(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.DEVICE_TOKEN));
//                    }
//                }
                oAuthPresenter.postLogin(etEmail.getText().toString(), etPassword.getText().toString(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.DEVICE_TOKEN));
                getWindow().getDecorView().clearFocus();

                return true;
            }else if (actionId == EditorInfo.IME_ACTION_NEXT) {
                svContainerLogin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        svContainerLogin.scrollTo(0, etPassword.getBottom());
                    }
                }, 250);

                return true;
            }

            return false;
        }
    }

}

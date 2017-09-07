package co.id.GoGolf.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.CountryEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.LanguageEvent;
import co.id.GoGolf.events.RegisterEvent;
import co.id.GoGolf.models.response.DataLanguage;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomRadioButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.fragments.FragTerms;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.ui.presenters.OAuthPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static co.id.GoGolf.BuildConfig.TERM_OF_USE;

/**
 * Created by dedepradana on 5/16/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActRegister extends BaseAct {

    @Inject
    MainPresenter mainPresenter;

    @Inject
    OAuthPresenter oAuthPresenter;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private ArrayList<DataLanguage> languageArrayList = new ArrayList<>();
    private ArrayList<String> languageList = new ArrayList<>();

    private Uri fileUri; // file url to store image/video

    private ImageView imgPreview;

    private Context context;

    private String userEmail, userPassword;

    private FragTerms fragTerms;

    @InjectView(R.id.svContainerReg)
    ScrollView svContainerReg;

    @InjectView(R.id.llContainerReg)
    LinearLayout llContainerReg;

    @InjectView(R.id.ivPhoto)
    ImageView ivPhoto;

    @InjectView(R.id.etCountry)
    CustomEditText etCountry;

    @InjectView(R.id.etFirstName)
    CustomEditText etFirstName;

    @InjectView(R.id.etLastName)
    CustomEditText etLastName;

    @InjectView(R.id.etEmail)
    CustomEditText etEmail;

    @InjectView(R.id.etPassword)
    CustomEditText etPassword;

    @InjectView(R.id.etConfirm)
    CustomEditText etConfirm;

    @InjectView(R.id.etCode)
    CustomEditText etCode;

    @InjectView(R.id.etPhone)
    CustomEditText etPhone;

    @InjectView(R.id.rgSex)
    RadioGroup rgSex;

    @InjectView(R.id.rb1)
    CustomRadioButton rb1;

    @InjectView(R.id.rb2)
    CustomRadioButton rb2;

    @InjectView(R.id.spLanguage)
    Spinner spLanguage;

    @InjectView(R.id.tvTermsOfUse)
    CustomTextView tvTermsOfUse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        setupUI(findViewById(R.id.llContainerReg));

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar(getResources().getString(R.string.rg_sign_up), null);

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerReg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerReg.scrollTo(0, etCountry.getBottom());
                        }
                    }, 250);
                }
            }
        });
        etConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerReg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerReg.scrollTo(0, etCountry.getBottom());
                        }
                    }, 250);
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerReg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerReg.scrollTo(0, etCountry.getBottom());
                        }
                    }, 250);
                }
            }
        });
        etFirstName.setOnEditorActionListener(new HideSoftKeyboard());
        etLastName.setOnEditorActionListener(new HideSoftKeyboard());
        etEmail.setOnEditorActionListener(new HideSoftKeyboard());
        etPassword.setOnEditorActionListener(new HideSoftKeyboard());
        etConfirm.setOnEditorActionListener(new HideSoftKeyboard());
        etPhone.setOnEditorActionListener(new HideSoftKeyboard());

        mainPresenter.getCountry();

        mainPresenter.getLanguage();

        tvTermsOfUse.setText(UIUtils.obtainTermsOfUseString(getResources().getString(R.string.rg_term1), getResources().getString(R.string.rg_term2), getResources().getString(R.string.rg_term3)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            getWindow().getDecorView().clearFocus();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.ivPhoto)
    public void onAddPhoto() {
        captureImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
//        else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // video successfully recorded
//                // preview the recorded video
//                previewVideo();
//            } else if (resultCode == RESULT_CANCELED) {
//                // user cancelled recording
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled video recording", Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                // failed to record video
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }

    }

    /*
 * Capturing Camera Image will lauch camera app requrest image capture
 */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
 * returning image / video
 */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
//        else if (type == MEDIA_TYPE_VIDEO) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + "VID_" + timeStamp + ".mp4");
//        }
        else {
            return null;
        }

        return mediaFile;
    }

    private void previewCapturedImage() {
        try {
            // hide video preview

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEventThread(LanguageEvent event) {

        languageArrayList = event.getData();

        for (int i = 0; i < languageArrayList.size(); i++) {
            languageList.add(languageArrayList.get(i).getLang_name());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languageList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguage.setAdapter(spinnerArrayAdapter);
        spLanguage.setSelection(0);
    }

    @Subscribe
    public void onEventThread(CountryEvent event) {
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.GG_COUNTRY, new Gson().toJson(event));
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        Toast.makeText(getApplicationContext(), "Failed! " + event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(RegisterEvent event) {
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, new Gson().toJson(event.getData().getUser()));
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.ACCESS_TOKEN, event.getData().getAccess_token());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.COUNTRY_ID, event.getData().getUser().getCountry_id());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.LANG_PREF, languageArrayList.get(spLanguage.getSelectedItemPosition()).getId());
//        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.LOGIN_OAUTH_TOKEN, event.getData().getToken().getToken());
//        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.LOGIN_OAUTH_TOKEN_SECRET, event.getData().getToken().getToken_secret());
        Intent mainIntent = new Intent();
        mainIntent.putExtra("mainAct", "mainAct");
        setResult(RESULT_OK, mainIntent);
        UIUtils.setLocale(ActRegister.this);
        ActRegister.this.finish();
    }

    private void goToMainAct() {
        startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @OnClick(R.id.etCountry)
    public void onCountryClicked() {
        dialog();
    }

    private void dialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActRegister.this);
        builderSingle.setTitle("Select country");

        final CountryEvent countryEvent = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.GG_COUNTRY), CountryEvent.class);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                ActRegister.this,
                android.R.layout.simple_list_item_1);

        for (int i = 0; i < countryEvent.getData().size(); i++) {
            arrayAdapter.add(countryEvent.getData().get(i).getCountry_name());
        }

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etCountry.setText(countryEvent.getData().get(which).getCountry_name());
                        etCountry.setTag(countryEvent.getData().get(which).getCountry_id());
                    }
                });
        builderSingle.show();
    }

    @OnClick(R.id.rlSignup)
    public void onSignup() {
        int selectedId = rgSex.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        String gender = (selectedId == rb1.getId()) ? rb1.getTag().toString() : rb2.getTag().toString();

        userEmail = etEmail.getText().toString();
        userPassword = etPassword.getText().toString();
        oAuthPresenter.postRegister(etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etCountry.getTag().toString(),
                gender,
                etPhone.getText().toString(),
                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.GG_DEVICE_ID),
                languageArrayList.get(spLanguage.getSelectedItemPosition()).getId(),
                etCode.getText().toString().replace("+", "")
        );

//        if (UIUtils.validateFirstName(ActRegister.this, etFirstName)) {
//            if (UIUtils.validateLastName(ActRegister.this, etLastName)) {
//                if (UIUtils.validateEmail(ActRegister.this, etEmail)) {
//                    if (UIUtils.validateInputPassword(ActRegister.this, etPassword)) {
//                        if (UIUtils.validateConfirmPassword(ActRegister.this, etConfirm, etPassword.getText().toString())) {
//                            if (UIUtils.validatePhoneNumber(ActRegister.this, etPhone)) {
//                                if (UIUtils.validatePhoneCountry(ActRegister.this, etCode)) {
//                                    if (UIUtils.validateCountry(ActRegister.this, etCountry)) {
//                                        userEmail = etEmail.getText().toString();
//                                        userPassword = etPassword.getText().toString();
//                                        oAuthPresenter.postRegister(etFirstName.getText().toString(),
//                                                etLastName.getText().toString(),
//                                                etEmail.getText().toString(),
//                                                etPassword.getText().toString(),
//                                                etCountry.getTag().toString(),
//                                                gender,
//                                                etPhone.getText().toString(),
//                                                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.GG_DEVICE_ID),
//                                                languageArrayList.get(spLanguage.getSelectedItemPosition()).getId(),
//                                                etCode.getText().toString().replace("+", "")
//                                        );
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    @OnClick(R.id.tvTermsOfUse)
    public void onShowTermsOfUse() {
        showLoadingDialog();
        fragTerms = new FragTerms(ActRegister.this, TERM_OF_USE);
        fragTerms.show(getSupportFragmentManager(), "fragTerms");
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TERM_OF_USE)));
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof CustomEditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    HideSoftKeyboardForActivity(ActRegister.this);
                    getWindow().getDecorView().clearFocus();

                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public void HideSoftKeyboardForActivity(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private class HideSoftKeyboard implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                getWindow().getDecorView().clearFocus();

                return true;
            }else if (actionId == EditorInfo.IME_ACTION_NEXT) {
                svContainerReg.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        svContainerReg.scrollTo(0, etCountry.getBottom());
                    }
                }, 250);

                return true;
            }

            return false;
        }
    }
}

package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.LanguageEvent;
import co.id.GoGolf.events.ProfileUpdateEvent;
import co.id.GoGolf.models.response.DataLanguage;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/13/16.
 */

public class ActLangDetail extends BaseAct {

    private String TAG = ActLangDetail.class.getSimpleName();
    private ArrayList<DataLanguage> languageArrayList = new ArrayList<>();
    private Context context;
    private User user;
    private ArrayList<String> languageList = new ArrayList<>();
    private int selectedLanguage = -1;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.tvLanguage)
    CustomTextView tvLanguage;
    @InjectView(R.id.spLanguage)
    Spinner spLanguage;
    @InjectView(R.id.btnSubmitLang)
    CustomButton btnSubmitLang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lang_detail);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();
        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);

        initToolbar("Update Profile", null);
        Log.d(TAG, "user lang = " + user.toString());

        initView();
    }

    private void initView() {
        showLoadingDialog();

        mainPresenter.getLanguage();
    }

    private void updateApplicationLocale() {
        UIUtils.setLocale(ActLangDetail.this);
        Intent intent = new Intent();
        intent.putExtra("lang_change", "change");
        setResult(RESULT_OK, intent);
        ActLangDetail.this.finish();
    }

    @OnClick(R.id.btnSubmitLang)
    public void onLanguageUpdate() {
        showLoadingDialog();
        Log.d(TAG, "language selected = " + languageArrayList.get(spLanguage.getSelectedItemPosition()).getId());
        selectedLanguage = spLanguage.getSelectedItemPosition();
        mainPresenter.postProfileUpdate(PreferenceUtility.getInstance().loadDataString(context,
                PreferenceUtility.ACCESS_TOKEN),
                user.getFname(),
                user.getLname(),
                user.getPhone(),
                user.getGender(),
                user.getBirthdate(),
                languageArrayList.get(spLanguage.getSelectedItemPosition()).getId());
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(LanguageEvent event) {
        dismissLoadingDialog();

        languageArrayList = event.getData();
        Log.d(TAG, "size = " + languageArrayList.size());

        for (int i = 0; i < languageArrayList.size(); i++) {
            if (languageArrayList.get(i).getId().equals(user.getLang())) {
                selectedLanguage = i;
            }
            languageList.add(languageArrayList.get(i).getLang_name());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languageList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguage.setAdapter(spinnerArrayAdapter);
        spLanguage.setSelection(selectedLanguage);
        Log.d(TAG, "selectedLanguage = " + selectedLanguage);
    }

    @Subscribe
    public void onEventThread(ProfileUpdateEvent event) {
        dismissLoadingDialog();
        user.setLang(languageArrayList.get(selectedLanguage).getId());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.LANG_PREF, languageArrayList.get(selectedLanguage).getId());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, new Gson().toJson(user));
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        updateApplicationLocale();
    }

}

package co.id.GoGolf.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import co.id.GoGolf.Config;
import co.id.GoGolf.R;
import co.id.GoGolf.events.ErrorEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 8/21/16.
 */
public class ActMyProfile extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_profile);

        ButterKnife.inject(this);

        initToolbar(getResources().getString(R.string.pf_nav1), null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.LANG_DETAIL_REQUEST && data != null) {
            setResult(RESULT_OK, data);
            startActivityForResult(new Intent(ActMyProfile.this, ActMyProfile.class), Config.MY_PROFILE_REQUEST);
            ActMyProfile.this.finish();
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        Toast.makeText(getApplicationContext(), "Failed! " + event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.llName)
    public void onNameClick(){
        startActivity(new Intent(getApplicationContext(), ActProfileDetail.class));
    }

    @OnClick(R.id.llEmail)
    public void onEmailClick() {
        startActivity(new Intent(getApplicationContext(), ActEmailDetail.class));
    }

    @OnClick(R.id.llPhone)
    public void onPhoneClick() {
        startActivity(new Intent(getApplicationContext(), ActPhoneDetail.class));
    }

    @OnClick(R.id.llAddress)
    public void onAddressClick() {
        startActivity(new Intent(getApplicationContext(), ActAddressDetail.class));
    }

    @OnClick(R.id.llLanguage)
    public void onLanguageClick() {
        startActivityForResult(new Intent(getApplicationContext(), ActLangDetail.class), Config.LANG_DETAIL_REQUEST);
    }

    @OnClick(R.id.llPassword)
    public void onPasswordClick() {
        startActivity(new Intent(getApplicationContext(), ActPasswordDetail.class));
    }

    @OnClick(R.id.llPushNotif)
    public void onPushNotifClick() {
        startActivity(new Intent(getApplicationContext(), ActPushNotifDetail.class));
    }

}

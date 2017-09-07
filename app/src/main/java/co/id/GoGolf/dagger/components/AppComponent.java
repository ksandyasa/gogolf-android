package co.id.GoGolf.dagger.components;

import co.id.GoGolf.dagger.modules.AppModule;
import co.id.GoGolf.ui.activities.ActAddressDetail;
import co.id.GoGolf.ui.activities.ActBookHis;
import co.id.GoGolf.ui.activities.ActBookStat;
import co.id.GoGolf.ui.activities.ActBookingDetail;
import co.id.GoGolf.ui.activities.ActCreditCard;
import co.id.GoGolf.ui.activities.ActEmailDetail;
import co.id.GoGolf.ui.activities.ActForgot;
import co.id.GoGolf.ui.activities.ActGolfDetail;
import co.id.GoGolf.ui.activities.ActLangDetail;
import co.id.GoGolf.ui.activities.ActLogin;
import co.id.GoGolf.ui.activities.ActNotification;
import co.id.GoGolf.ui.activities.ActPasswordDetail;
import co.id.GoGolf.ui.activities.ActPayment;
import co.id.GoGolf.ui.activities.ActPhoneDetail;
import co.id.GoGolf.ui.activities.ActPointHistory;
import co.id.GoGolf.ui.activities.ActPreBookingV3;
import co.id.GoGolf.ui.activities.ActPreview;
import co.id.GoGolf.ui.activities.ActProfileDetail;
import co.id.GoGolf.ui.activities.ActPushNotifDetail;
import co.id.GoGolf.ui.activities.ActRegister;
import co.id.GoGolf.ui.activities.ActSearch;
import co.id.GoGolf.ui.activities.ActSearchPromo;
import co.id.GoGolf.ui.activities.ActSplash;
import co.id.GoGolf.ui.activities.ActWeekend;
import co.id.GoGolf.ui.activities.MainActivity;
import co.id.GoGolf.ui.fragments.FragBookHis;
import co.id.GoGolf.ui.fragments.FragBookStat;
import co.id.GoGolf.ui.fragments.FragCancelBooking;
import co.id.GoGolf.ui.fragments.FragConfirmPayment;
import co.id.GoGolf.ui.fragments.FragDatePicker;
import co.id.GoGolf.ui.fragments.FragFlightNumber;
import co.id.GoGolf.ui.fragments.FragGolf;
import co.id.GoGolf.ui.fragments.FragInputDialog;
import co.id.GoGolf.ui.fragments.FragMap;
import co.id.GoGolf.ui.fragments.FragOne;
import co.id.GoGolf.ui.fragments.FragPlayerType;
import co.id.GoGolf.ui.fragments.FragPoint;
import co.id.GoGolf.ui.fragments.FragPointUse;
import co.id.GoGolf.ui.fragments.FragPro;
import co.id.GoGolf.ui.fragments.FragStripes;
import co.id.GoGolf.ui.fragments.FragSuccessDialog;
import co.id.GoGolf.ui.fragments.FragTerms;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dedepradana on 2/28/16.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(ActSplash activity);
    void inject(ActSearch activity);
    void inject(ActWeekend activity);
    void inject(ActNotification activity);
    void inject(ActProfileDetail activity);
    void inject(ActEmailDetail activity);
    void inject(ActPhoneDetail activity);
    void inject(ActAddressDetail activity);
    void inject(ActLangDetail activity);
    void inject(ActPasswordDetail activity);
    void inject(ActPushNotifDetail activity);
    void inject(ActBookingDetail activity);
    void inject(ActGolfDetail activity);
    void inject(ActLogin activity);
    void inject(ActForgot activity);
    void inject(ActBookStat activity);
    void inject(ActPreview activity);
    void inject(ActPointHistory activity);
    void inject(MainActivity activity);
    void inject(ActPreBookingV3 activity);
    void inject(ActRegister activity);
    void inject(ActBookHis activity);
    void inject(ActCreditCard activity);
    void inject(ActSearchPromo activity);
    void inject(ActPayment activity);
    void inject(FragPro fragment);
    void inject(FragGolf fragment);
    void inject(FragMap fragment);
    void inject(FragBookHis fragment);
    void inject(FragBookStat fragment);
    void inject(FragPoint fragment);
    void inject(FragOne fragment);
    void inject(FragInputDialog fragment);
    void inject(FragSuccessDialog fragment);
    void inject(FragDatePicker fragment);
    void inject(FragFlightNumber fragment);
    void inject(FragPointUse fragment);
    void inject(FragConfirmPayment fragment);
    void inject(FragCancelBooking fragment);
    void inject(FragPlayerType fragment);
    void inject(FragTerms fragTerms);
    void inject(FragStripes fragStripes);
}

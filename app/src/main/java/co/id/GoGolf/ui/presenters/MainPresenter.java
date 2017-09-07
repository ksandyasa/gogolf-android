package co.id.GoGolf.ui.presenters;

import android.util.Log;

import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.BaseEvent;
import co.id.GoGolf.events.BookHisEvent;
import co.id.GoGolf.events.BookingEvent;
import co.id.GoGolf.events.CancelEvent;
import co.id.GoGolf.events.ChangePasswordEvent;
import co.id.GoGolf.events.CountryEvent;
import co.id.GoGolf.events.EmailUpdateEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.GolfDetailEvent;
import co.id.GoGolf.events.HomeEvent;
import co.id.GoGolf.events.LanguageEvent;
import co.id.GoGolf.events.MapEvent;
import co.id.GoGolf.events.NotifEvent;
import co.id.GoGolf.events.PaymentTokenEvent;
import co.id.GoGolf.events.PointEvent;
import co.id.GoGolf.events.PointHistoryEvent;
import co.id.GoGolf.events.PreBookingEvent;
import co.id.GoGolf.events.PreBookingEventV2;
import co.id.GoGolf.events.ProfileUpdateEvent;
import co.id.GoGolf.events.PromotionEvent;
import co.id.GoGolf.events.SearchEvent;
import co.id.GoGolf.events.StripeEvent;
import co.id.GoGolf.events.TokenInvalidEvent;
import co.id.GoGolf.events.UserDetailEvent;
import co.id.GoGolf.events.VerifyEvent;
import co.id.GoGolf.events.VeritransEvent;
import co.id.GoGolf.events.VersionEvent;
import co.id.GoGolf.models.RestAPI;
import co.id.GoGolf.models.request.BookReq;
import co.id.GoGolf.util.Clog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dedepradana on 6/8/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class MainPresenter {

    private String TAG = MainPresenter.class.getSimpleName();
    RestAPI restAPI;

    @Inject
    public MainPresenter(RestAPI restAPI) {
        this.restAPI = restAPI;
    }


    public void getCountry() {
        restAPI.getCountry().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CountryEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(CountryEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }


    public void postBooking(String access_token, BookReq bookReq, String priceDp) {
        Clog.e("FLIGHT : " + new Gson().toJson(bookReq.getFlightarr()));
        restAPI.postBooking(access_token, bookReq.getGid(), bookReq.getDate(), bookReq.getUse_point(), new Gson().toJson(bookReq.getFlightarr()), priceDp).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookingEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(BookingEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postEmailUpdate(String access_token, String emailAddr) {
        restAPI.postEmailUpdate(access_token, emailAddr).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EmailUpdateEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(EmailUpdateEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postProfileUpdate(String access_token, String fname, String lname, String phone, String gender, String birthdate, String lang) {
        restAPI.postProfileUpdate(access_token, fname, lname, phone, gender, birthdate, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfileUpdateEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(ProfileUpdateEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postProfileUpdate1(String access_token, String fname, String lname, String phone, String gender, String birthdate, String lang, String phone_country) {
        restAPI.postProfileUpdate1(access_token, fname, lname, phone, gender, birthdate, lang, phone_country).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfileUpdateEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(ProfileUpdateEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postAddressUpdate(String access_token, String address) {
        restAPI.postAddressUpdate(access_token, address).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfileUpdateEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(ProfileUpdateEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postChangePassword(String access_token, String oldpassword, String newpassword, String confirmpassword) {
        restAPI.postChangePassword(access_token, oldpassword, newpassword, confirmpassword).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangePasswordEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(ChangePasswordEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getBookHis(String access_token, String sdate, String edate, String status, String lang) {
        restAPI.getHistory(access_token, sdate, edate, status, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookHisEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(BookHisEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getNotif(String access_token, String lang) {
        restAPI.postNotif(access_token, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NotifEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(NotifEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getPromotion(String pricemin, String pricemax, String date, String stime, String etime, String area_id, String gid, String lang) {
        restAPI.postPromotion(pricemin, pricemax, date, stime, etime, area_id, gid, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PromotionEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PromotionEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getCourseList() {
        restAPI.getCourseList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(SearchEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });

    }

    public void getGolf(String pricemin, String pricemax, String area_id, String gname) {
        restAPI.postGolf(pricemin, pricemax, area_id, gname).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(SearchEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }


    public void getGolfWeekend(String date) {
        restAPI.postGolfWeekend(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(SearchEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getMap(String access_token, String areaId, String language) {
        restAPI.getMap(access_token, areaId, language).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MapEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(MapEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postArea(String country_id, String lang) {
        restAPI.postArea(country_id, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AreaEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(AreaEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postGolfDetail(String gid, String lang) {
        restAPI.postGolfDetail(gid, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GolfDetailEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(GolfDetailEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postCancelBooking(String access_token, String bid, String lang) {
        restAPI.postCancelBooking(access_token, bid, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CancelEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(CancelEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postCharge(String token_id, String bid, String saved_token) {
        restAPI.postCharge(token_id, bid, saved_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VeritransEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(VeritransEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }


    public void postPreBooking(String gid, String gdate) {
        restAPI.postPreBooking(gid, gdate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PreBookingEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PreBookingEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }


    public void postPreBookingV2(String gid, String gdate) {
        restAPI.postPreBookingV2(gid, gdate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PreBookingEventV2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PreBookingEventV2 event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postVerify(String access_token, String code, String lang) {
        restAPI.postVerifyCode(access_token, code, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VerifyEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(VerifyEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postGetPoint(String access_token, String gid, String date, String tprice) {
        restAPI.postGetPoint(access_token, gid, date, tprice).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PointEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PointEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postGetPointHistory(String access_token, String lang) {
        restAPI.postGetPointHistory(access_token, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PointHistoryEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PointHistoryEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }


    public void postGetUserDetail(String access_token, String lang, String user_id) {
        restAPI.postGetUserDetail(access_token, lang, user_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserDetailEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(UserDetailEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else if (event.getCode() == 400){
                            EventBus.getDefault().post(new TokenInvalidEvent(event.getMessage()));
                        }else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postGetPhotoHome(String lang) {
        restAPI.postGetPhotoHome(lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(HomeEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getLanguage() {
        restAPI.getLanguage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LanguageEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(LanguageEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getVersionApps(String operating_system) {
        restAPI.getVersionApps(operating_system).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(VersionEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void getPaymentToken(String access_token) {
        restAPI.getPaymentToken(access_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PaymentTokenEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PaymentTokenEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        }
                    }
                });
    }

    public void postStripeChargePayment(String access_token, String bcode, String stripeToken, String use_saved_token) {
        restAPI.postStripeCharge(access_token, bcode, stripeToken, use_saved_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StripeEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(StripeEvent event) {
                        Log.d("TAG", "code " + event.getCode());
                        Log.d("TAG", "message " + event.getMessage());
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

}

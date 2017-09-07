package co.id.GoGolf.models;

import co.id.GoGolf.Config;
import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.BaseEvent;
import co.id.GoGolf.events.BookHisEvent;
import co.id.GoGolf.events.BookingEvent;
import co.id.GoGolf.events.CancelEvent;
import co.id.GoGolf.events.ChangePasswordEvent;
import co.id.GoGolf.events.CountryEvent;
import co.id.GoGolf.events.EmailUpdateEvent;
import co.id.GoGolf.events.ForgotPasswordEvent;
import co.id.GoGolf.events.GolfDetailEvent;
import co.id.GoGolf.events.HomeEvent;
import co.id.GoGolf.events.LanguageEvent;
import co.id.GoGolf.events.LoginEvent;
import co.id.GoGolf.events.MapEvent;
import co.id.GoGolf.events.NotifEvent;
import co.id.GoGolf.events.PaymentTokenEvent;
import co.id.GoGolf.events.PointEvent;
import co.id.GoGolf.events.PointHistoryEvent;
import co.id.GoGolf.events.PreBookingEvent;
import co.id.GoGolf.events.PreBookingEventV2;
import co.id.GoGolf.events.ProfileUpdateEvent;
import co.id.GoGolf.events.PromotionEvent;
import co.id.GoGolf.events.RegisterEvent;
import co.id.GoGolf.events.SearchEvent;
import co.id.GoGolf.events.StripeEvent;
import co.id.GoGolf.events.UserDetailEvent;
import co.id.GoGolf.events.VerifyEvent;
import co.id.GoGolf.events.VeritransEvent;
import co.id.GoGolf.events.VersionEvent;
import co.id.GoGolf.models.response.DataPaymentToken;
import co.id.GoGolf.models.response.point.DataPoint;
import co.id.GoGolf.util.Clog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by dedepradana on 5/30/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class RestAPI {

    private interface RestService {

        String AUTHORIZATION_KEY = "a175bac26ba2bd3e0a2eafb88e3a418c28be6cf0";

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @GET(Config.API_GET_COUNTRY)
        Observable<CountryEvent> getCountry();

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @GET(Config.API_GET_GOLF)
        Observable<SearchEvent> getCourseList();

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_PROMOTION)
        Observable<PromotionEvent> postPromotion(@Field("pricemin") String pricemin, @Field("pricemax") String pricemax, @Field("date") String date, @Field("stime") String stime, @Field("etime") String etime, @Field("area_id") String area_id, @Query("gid") String gid, @Query("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_GOLF_DETAIL)
        Observable<GolfDetailEvent> postGolfDetail(@Field("gid") String gid, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_GOLF)
        Observable<SearchEvent> postGolf(@Field("pricemin") String pricemin, @Field("pricemax") String pricemax, @Field("area_id") String area_id, @Field("gname") String gname);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_GOLF)
        Observable<SearchEvent> postGolfWeekend(@Field("date") String date);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @POST(Config.API_GET_MAP)
        @FormUrlEncoded
        Observable<MapEvent> getMap(@Header("ACCESS-TOKEN") String access_token, @Field("area_id") String areaId, @Field("lang") String language);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_BOOK_HIS)
        Observable<BookHisEvent> getHistory(@Header("ACCESS-TOKEN") String access_token, @Field("sdate") String sdate, @Field("edate") String edate, @Field("status") String status);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_LOGIN)
        Observable<LoginEvent> postLogin(@Field("email") String email, @Field("password") String password, @Field("device_id") String device_id);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_AREA)
        Observable<AreaEvent> postArea(@Field("country_id") String country_id, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_UPDATE_PROFILE)
        Observable<ProfileUpdateEvent> postProfileUpdate(@Header("ACCESS-TOKEN") String access_token, @Field("fname") String fname, @Field("lname") String lname, @Field("phone") String phone, @Field("gender") String gender, @Field("birthdate") String birthdate, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_UPDATE_PROFILE)
        Observable<ProfileUpdateEvent> postProfileUpdate1(@Header("ACCESS-TOKEN") String access_token, @Field("fname") String fname, @Field("lname") String lname, @Field("phone") String phone, @Field("gender") String gender, @Field("birthdate") String birthdate, @Field("lang") String lang, @Field("phone_country") String phone_country);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_NOTIFICATION)
        Observable<NotifEvent> postNotif(@Header("ACCESS-TOKEN") String access_token, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_SOCIAL_LOGIN)
        Observable<LoginEvent> postSocialLogin(@Field("access_token") String access_token, @Field("provider") String social_provider, @Field("device_id") String device_id, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_REGISTER)
        Observable<RegisterEvent> postRegister(@Field("fname") String fname, @Field("lname") String lname, @Field("email") String email, @Field("password") String password, @Field("country_id") String country_id, @Field("gender") String gender, @Field("phone") String phone, @Field("device_id") String device_id, @Field("lang") String lang, @Field("phone_country") String phone_country);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_PRE_BOOKING)
        Observable<PreBookingEvent> postPreBooking(@Field("gid") String gid, @Field("gdate") String gdate);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_PRE_BOOKINGv2)
        Observable<PreBookingEventV2> postPreBookingV2(@Field("gid") String gid, @Field("gdate") String gdate);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_BOOKING)
        Observable<BookingEvent> postBooking(@Header("ACCESS-TOKEN") String access_token, @Field("gid") String gid, @Field("date") String date, @Field("use_point") String use_point, @Field("flightarr") String flightarr);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_BOOKINGv2)
        Observable<BookingEvent> postBookingV2(@Header("ACCESS-TOKEN") String access_token, @Field("referral") String referral, @Field("gid") String gid, @Field("date") String date, @Field("use_point") String use_point, @Field("flightarr") String flightarr, @Field("deposit_price") String deposit_price);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_CANCEL)
        Observable<CancelEvent> postCancelBooking(@Header("ACCESS-TOKEN") String access_token, @Field("bid") String bid, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_VERIFY)
        Observable<VerifyEvent> postVerifyCode(@Header("ACCESS-TOKEN") String access_token, @Field("code") String code, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_POINT_CONFIRMATION)
        Observable<PointEvent> postGetPoint(@Header("ACCESS-TOKEN") String access_token, @Field("gid") String gid, @Field("date") String date, @Field("tprice") String tprice);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_POINT_HISTORY)
        Observable<PointHistoryEvent> postGetPointHistory(@Header("ACCESS-TOKEN") String access_token, @Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_USET_DETAIL)
        Observable<UserDetailEvent> postGetUserDetail(@Header("ACCESS-TOKEN") String access_token, @Field("lang") String lang, @Field("user_id") String user_id);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_POST_CHANGE_PASSWORD)
        Observable<ChangePasswordEvent> postChangePassword(@Header("ACCESS-TOKEN") String access_token, @Field("old_password") String oldpassword, @Field("new_password") String newpassword, @Field("confirm_new_password") String confirmpassword);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_UPDATE_PROFILE)
        Observable<ProfileUpdateEvent> postAddressUpdate(@Header("ACCESS-TOKEN") String access_token, @Field("address") String address);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_UPDATE_EMAIL)
        Observable<EmailUpdateEvent> postEmailUpdate(@Header("ACCESS-TOKEN") String access_token, @Field("email") String emailAddress);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @GET(Config.API_GET_LANG)
        Observable<LanguageEvent> postGetLanguage();

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_GET_PHOTO_HOME)
        Observable<HomeEvent> postGetHome(@Field("lang") String lang);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_VERITRANS)
        Observable<VeritransEvent> postCharge(@Field("token_id") String token_id, @Field("bid") String bid, @Field("save_token") String saved_token);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_FORGOT_PASSWORD)
        Observable<ForgotPasswordEvent> postForgotPassword(@Field("email") String emailAddr);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_VERSION)
        Observable<VersionEvent> getVersionApps(@Field("operating_system") String operating_system);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @POST(Config.API_PAYMENT_TOKEN)
        Observable<PaymentTokenEvent> getPaymentToken(@Header("ACCESS-TOKEN") String access_token);

        @Headers("AUTHORIZATION-KEY: " + AUTHORIZATION_KEY)
        @FormUrlEncoded
        @POST(Config.API_STRIPE_CHARGE)
        Observable<StripeEvent> postStripeCharge(@Field("access_token") String access_token, @Field("bcode") String bcode, @Field("stripeToken") String stripeToken, @Field("use_saved_token") String use_saved_token);
    }

    private RestAdapter restAdapter;

    public RestAPI() {
        setupRestClient();
    }

    private RestAdapter get() {
        return restAdapter;
    }

    public void setupRestClient() {
        Clog.e("CALL SETUP CONSTRUCTOR");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PaymentTokenEvent.class, new DataPaymentTokenDeserializer())
                .registerTypeAdapter(PointEvent.class, new DataPointTokenDeserializer())
                .create();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(50000, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(50000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(50000, TimeUnit.MILLISECONDS);
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Config.SERVER_NAME)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        restAdapter = builder.build();

    }

    public Observable<PromotionEvent> postPromotion(String pricemin, String pricemax, String date, String stime, String etime, String area_id, String gid, String lang) {
        return get().create(RestService.class).postPromotion(pricemin, pricemax, date, stime, etime, area_id, gid, lang).cache();
    }

    public Observable<SearchEvent> postGolf(String pricemin, String pricemax, String area_id, String gname) {
        return get().create(RestService.class).postGolf(pricemin, pricemax, area_id, gname).cache();
    }

    public Observable<SearchEvent> getCourseList() {
        return get().create(RestService.class).getCourseList().cache();
    }

    public Observable<SearchEvent> postGolfWeekend(String date) {
        return get().create(RestService.class).postGolfWeekend(date).cache();
    }

    public Observable<MapEvent> getMap(String access_token, String areaId, String language) {
        return get().create(RestService.class).getMap(access_token, areaId, language).cache();
    }

    public Observable<BookHisEvent> getHistory(String access_token, String sdate, String edate, String status, String lang) {
        return get().create(RestService.class).getHistory(access_token, sdate, edate, status).cache();
    }

    public Observable<LoginEvent> postLogin(String email, String password, String device_id) {
        return get().create(RestService.class).postLogin(email, password, device_id).cache();
    }

    public Observable<LoginEvent> postSocialLogin(String access_token, String social_provider, String device_id, String lang) {
        return get().create(RestService.class).postSocialLogin(access_token, social_provider, device_id, lang).cache();
    }

    public Observable<BookingEvent> postBooking(String access_token, String gid, String date, String use_point, String flightarr, String deposit_price) {
        return get().create(RestService.class).postBookingV2(access_token, "gogolf_android", gid, date, use_point, flightarr, deposit_price).cache();
    }

    public Observable<CountryEvent> getCountry() {
        return get().create(RestService.class).getCountry().cache();
    }

    public Observable<RegisterEvent> postRegister(String fname, String lname, String email, String password, String country_id, String gender, String phone, String device_id, String lang, String phone_country) {
        return get().create(RestService.class).postRegister(fname, lname, email, password, country_id, gender, phone, device_id, lang, phone_country).cache();
    }

    public Observable<AreaEvent> postArea(String country_id, String lang) {
        return get().create(RestService.class).postArea(country_id, lang).cache();
    }

    public Observable<PreBookingEvent> postPreBooking(String gid, String gdate) {
        return get().create(RestService.class).postPreBooking(gid, gdate).cache();
    }

    public Observable<PreBookingEventV2> postPreBookingV2(String gid, String gdate) {
        return get().create(RestService.class).postPreBookingV2(gid, gdate).cache();
    }

    public Observable<VerifyEvent> postVerifyCode(String access_token, String code, String lang) {
        return get().create(RestService.class).postVerifyCode(access_token, code, lang).cache();
    }

    public Observable<PointEvent> postGetPoint(String access_token, String gid, String date, String tprice) {
        return get().create(RestService.class).postGetPoint(access_token, gid, date, tprice).cache();
    }

    public Observable<PointHistoryEvent> postGetPointHistory(String access_token, String lang) {
        return get().create(RestService.class).postGetPointHistory(access_token, lang).cache();
    }

    public Observable<UserDetailEvent> postGetUserDetail(String access_token, String lang, String user_id) {
        return get().create(RestService.class).postGetUserDetail(access_token, lang, user_id).cache();
    }

    public Observable<HomeEvent> postGetPhotoHome(String lang) {
        return get().create(RestService.class).postGetHome(lang).cache();
    }

    public Observable<GolfDetailEvent> postGolfDetail(String gid, String lang) {
        return get().create(RestService.class).postGolfDetail(gid, lang).cache();
    }

    public Observable<NotifEvent> postNotif(String access_token, String lang) {
        return get().create(RestService.class).postNotif(access_token, lang).cache();
    }

    public Observable<CancelEvent> postCancelBooking(String access_token, String bid, String lang) {
        return get().create(RestService.class).postCancelBooking(access_token, bid, lang).cache();
    }

    public Observable<ProfileUpdateEvent> postProfileUpdate(String access_token, String fname, String lname, String phone, String gender, String birthdate, String lang) {
        return get().create(RestService.class).postProfileUpdate(access_token, fname, lname, phone, gender, birthdate, lang).cache();
    }

    public Observable<ProfileUpdateEvent> postProfileUpdate1(String access_token, String fname, String lname, String phone, String gender, String birthdate, String lang, String phone_country) {
        return get().create(RestService.class).postProfileUpdate1(access_token, fname, lname, phone, gender, birthdate, lang, phone_country).cache();
    }

    public Observable<ProfileUpdateEvent> postAddressUpdate(String access_token, String address) {
        return get().create(RestService.class).postAddressUpdate(access_token, address).cache();
    }

    public Observable<EmailUpdateEvent> postEmailUpdate(String access_token, String emailAddr) {
        return get().create(RestService.class).postEmailUpdate(access_token, emailAddr).cache();
    }

    public Observable<ChangePasswordEvent> postChangePassword(String access_token, String oldpassword, String newpassword, String confirmpassword) {
        return get().create(RestService.class).postChangePassword(access_token, oldpassword, newpassword, confirmpassword).cache();
    }

    public Observable<LanguageEvent> getLanguage() {
        return get().create(RestService.class).postGetLanguage().cache();
    }

    public Observable<ForgotPasswordEvent> postForgotPassword(String emaiAddr) {
        return get().create(RestService.class).postForgotPassword(emaiAddr).cache();
    }

    public Observable<VeritransEvent> postCharge(String token_id, String bid, String saved_token) {
        return get().create(RestService.class).postCharge(token_id, bid, saved_token).cache();
    }

    public Observable<VersionEvent> getVersionApps(String operating_system) {
        return get().create(RestService.class).getVersionApps(operating_system).cache();
    }

    public Observable<PaymentTokenEvent> getPaymentToken(String access_token) {
        return get().create(RestService.class).getPaymentToken(access_token).cache();
    }

    public Observable<StripeEvent> postStripeCharge(String access_token, String bcode, String stripeTokem, String use_saved_token) {
        return get().create(RestService.class).postStripeCharge(access_token, bcode, stripeTokem, use_saved_token).cache();
    }

    public class DataPointTokenDeserializer implements JsonDeserializer<PointEvent> {

        @Override
        public PointEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PointEvent pointEvent = new PointEvent();
            Gson gson = new Gson();

            JsonElement element = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                if (entry.getKey().equals("data")) {
                    if (entry.getValue().isJsonObject())
                        pointEvent.setData(gson.fromJson(entry.getValue(), DataPoint.class));
                    else
                        pointEvent.setData(new DataPoint());
                }
                if (entry.getKey().equals("code"))
                    pointEvent.setCode(Integer.parseInt(entry.getValue().toString()));
                if (entry.getKey().equals("message"))
                    pointEvent.setMessage(entry.getValue().toString());
            }

            return pointEvent;
        }
    }

    public class DataPaymentTokenDeserializer implements JsonDeserializer<PaymentTokenEvent> {

        @Override
        public PaymentTokenEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PaymentTokenEvent value = new PaymentTokenEvent();
            Gson gson = new Gson();

            JsonElement element = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                if (entry.getKey().equals("data")) {
                    if (entry.getValue().isJsonObject())
                        value.setData(gson.fromJson(entry.getValue(), DataPaymentToken.class));
                    else
                        value.setData(new DataPaymentToken());
                }
                if (entry.getKey().equals("code"))
                    value.setCode(Integer.parseInt(entry.getValue().toString()));
                if (entry.getKey().equals("message"))
                    value.setMessage(entry.getValue().toString());
            }

            return value;
        }
    }
}

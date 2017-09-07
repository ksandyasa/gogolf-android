package co.id.GoGolf;

import static co.id.GoGolf.BuildConfig.BASE_URL;

/**
 * Created by Dede Pradana on 1/15/2015.
 **/
public interface Config {
    String SERVER_NAME = BASE_URL;
    String API_GET_COUNTRY = "/v1/country/get";
    String API_POST_LOGIN = "/v1/auth/login";
    String API_POST_SOCIAL_LOGIN = "/v1/auth/socialLogin";
    String API_POST_REGISTER = "/v1/user/register";
    String API_GET_PROMOTION = "/v1/promotion/lists";
    String API_GET_PRE_BOOKING = "/v1/golf/getPreBookingData";
    String API_GET_PRE_BOOKINGv2 = "/v1/golf/getPreBookingData";
    String API_POST_BOOKING = "/v1/booking/add";
    String API_POST_CANCEL = "/v1/booking/delete";
    String API_POST_BOOKINGv2 = "/v1/booking/add";
    String API_POST_VERIFY = "/v1/point/verifyPromotionCode";
    String API_GET_POINT_CONFIRMATION = "/v1/point/getPointConfirmation";
    String API_GET_POINT_HISTORY = "/v1/point/getPointLog";
    String API_GET_USET_DETAIL = "/v1/user/detail";
    String API_GET_PHOTO_HOME = "/v1/photo/home";
    String API_GET_GOLF = "/v1/golf/lists";
    String API_GET_GOLF_DETAIL = "/v1/golf/detail";
    String API_GET_MAP = "/v1/golf/map";
    String API_GET_AREA = "/v1/area/get";
    String API_GET_LANG = "/v1/lang/get";
    String API_UPDATE_PROFILE = "/v1/user/update";
    String API_UPDATE_EMAIL = "/v1/user/email_update";
    String API_POST_CHANGE_PASSWORD = "/v1/user/change_password";
    String API_GET_NOTIFICATION = "/v1/notification/log";
    String API_GET_BOOK_HIS = "/v1/booking/history";
    String API_FORGOT_PASSWORD = "/v1/user/forgot_password";
    String API_VERITRANS = "/veritrans/vtdirect/charge_payment";
    String API_VERSION = "/v1/version/check";
    String API_TERM_OF_USE = "/v1/legal/terms";
    String API_STRIPE_CHARGE = "/stripe/charge";
    String API_PAYMENT_TOKEN = "/v1/user/getPaymentToken";

    int MY_PROFILE_REQUEST = 19;
    int LANG_DETAIL_REQUEST = 20;
    int REGISTER_PAGE = 21;
    int PREBOOKING = 22;
    int PREVIEW = 23;
    int CREDIT_CARD = 24;
    int SUCESS_PAYMENT = 25;
    int POINT = 26;
    int BOOK_STATUS = 27;
    int BOOK_HISTORY = 28;
    int BOOK_DETAIL = 29;
    int PREBOOKING_SUCCESS = 30;

}


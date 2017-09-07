package co.id.GoGolf.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dedepradana on 1/21/15.
 * at time 16:21.
 */
@SuppressWarnings({"FieldCanBeLocal", "JavaDoc", "unused"})
public class PreferenceUtility {

    public static final String DEFAULT_TOKEN = "default_token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String PASSWORD = "password";
    public static final String DEVICE_TOKEN = "device_token";
//    public static final String OAUTH_TOKEN = "oauth_token";
//    public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
//    public static final String OAUTH_TOKEN_ACCESS = "oauth_token_access";
    public static final String OAUTH_TOKEN_SECRET_ACCESS = "oauth_token_secret_access";
    public static final String GG_COUNTRY = "gg_country";
    public static final String GG_DEVICE_ID = "gg_device_id";
//    public static final String LOGIN_OAUTH_TOKEN = "login_oauth_token";
//    public static final String LOGIN_OAUTH_TOKEN_SECRET = "login_oauth_token_secret";
    public static final String COUNTRY_ID = "country_id";
    public static final String SF_USER_DATA = "sf_user_data";
    public static final String SF_AREA_DATA = "sf_area_data";
    public static final String SF_BOOKING = "sf_booking";
    public static final String SF_TEA_TIME = "sf_tea_time";

    public static final String RB_SERVICES = "rb_services";
    public static final String RB_VEHICLES = "rb_vehicles";
    public static final String RB_UPDATE_SCHEDULER = "rb_update_scheduler";
    public static final String RB_GCM_TOKEN = "rb_gcm_token";
    public static final String FACEBOOK_TOKEN = "facebook_token";
    public static final String FACEBOOK_ME_RESPONSE = "facebook_me_response";
    public static final String LANG_PREF = "lang_pref";
    public static final String VERSION_CODE_KEY = "version_code";
    public static final String CARD_NAME = "card_name";
    public static final String CARD_NUMBER = "card_number";
    public static final String CVV_NUMBER = "cvv_number";
    public static final String EXP_DATE = "exp_date";
    public static final String DESTINATION = "destination";
    public static final String EVENT = "event";

    private final String SP_COMMON = "go_golf_db";

    /**
     * Singleton instance
     */
    private static PreferenceUtility instance = null;

    /**
     * Private constructor to avoid instantiation outside class
     */
    protected PreferenceUtility() {
    }

    /**
     * Get the singleton instance
     *
     * @return the PreferenceUtility instance
     */
    public static PreferenceUtility getInstance() {
        if (instance == null) {
            return new PreferenceUtility();
        }
        return instance;
    }

    /**
     * Set the singleton instance
     *
     * @param instance the PreferenceUtility instance
     */
    public static synchronized void setInstance(PreferenceUtility instance) {
        PreferenceUtility.instance = instance;
    }

    /**
     * private constructor, not to be instantiated with new keyword
     *
     * @param context application context
     * @return SharedPreference object of current application
     */
    public SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences(SP_COMMON, Context.MODE_PRIVATE);
    }

    /**
     * Save data base on key given, and the value is on integer format
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveData(Context context, String key, int value) {
        saveData(context, key, String.valueOf(value));
    }

    /**
     * Save data base on key given, and the value is on string format
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveData(Context context, String key, String value) {
        sharedPreferences(context).edit().putString(key, value).commit();
    }

    public void saveData(Context context, String key, Set<String> value) {
        sharedPreferences(context).edit().putStringSet(key, value).commit();
    }

    /**
     * Save data base on key given, and the value is on string format
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveData(Context context, String key, Boolean value) {
        sharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    /**
     * Save data base on key given, and the value is on long format
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveData(Context context, String key, long value) {
        sharedPreferences(context).edit().putLong(key, value).commit();
    }

    /**
     * get data base on key given, and the value is on integer format
     *
     * @param context
     * @param key
     * @return
     */
    public int loadDataInt(Context context, String key) {
        return Integer.parseInt(sharedPreferences(context).getString(key, "0"));
    }

    /**
     * get data base on key given, and the value is on String format
     *
     * @param context
     * @param key
     * @return
     */
    public String loadDataString(Context context, String key) {
        return sharedPreferences(context).getString(key, "");
    }

    public Set<String> loadDataStringSet(Context context, String key) {
        Set<String> stringSet = new HashSet<>();
        return sharedPreferences(context).getStringSet(key, stringSet);
    }

    /**
     * get data base on key given, and the value is on String format
     *
     * @param context
     * @param key
     * @return
     */
    public Boolean loadDataBoolean(Context context, String key) {
        return loadDataBoolean(context, key, true);
    }

    /**
     * get data base on key given, and the value is on String format
     *
     * @param context
     * @param key
     * @return
     */
    public Boolean loadDataBoolean(Context context, String key, boolean defaultValue) {
        return sharedPreferences(context).getBoolean(key, defaultValue);
    }

    /**
     * get data base on key given, and the value is on long format
     *
     * @param context
     * @param key
     * @return
     */
    public long loadDataOfLong(Context context, String key) {
        return sharedPreferences(context).getLong(key, 0);
    }

    /**
     * Save data base on key given, and the value is on string format
     *
     * @param context
     * @param key
     */
    public void delete(Context context, String key) {
        sharedPreferences(context).edit().remove(key).commit();
    }

    /**
     * Delete data base for all existing key, and based on context
     *
     * @param context
     */
    public void deleteAll(Context context) {
        sharedPreferences(context).edit().clear().commit();
    }

}

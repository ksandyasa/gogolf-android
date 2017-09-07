package co.id.GoGolf.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.util.PreferenceUtility;

import java.util.Locale;

/**
 * Created by prumacadmin on 8/21/16.
 */
public class UIUtils {
    private static final int WIDTH_INDEX = 0;
    private static final int HEIGHT_INDEX = 1;

    public static int[] getScreenSize(Context context) {
        int[] widthHeight = new int[2];
        widthHeight[WIDTH_INDEX] = 0;
        widthHeight[HEIGHT_INDEX] = 0;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        widthHeight[WIDTH_INDEX] = size.x;
        widthHeight[HEIGHT_INDEX] = size.y;

        if (!isScreenSizeRetrieved(widthHeight)) {
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            widthHeight[0] = metrics.widthPixels;
            widthHeight[1] = metrics.heightPixels;
        }

        // Last defense. Use deprecated API that was introduced in lower than API 13
        if (!isScreenSizeRetrieved(widthHeight)) {
            widthHeight[0] = display.getWidth(); // deprecated
            widthHeight[1] = display.getHeight(); // deprecated
        }

        return widthHeight;
    }

    private static boolean isScreenSizeRetrieved(int[] widthHeight) {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0;
    }

    public static void setLocale(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getLocale(context);
        configuration.locale = locale;
        resources.updateConfiguration(configuration, null);
    }

    private static Locale getLocale(Context context){
        return new Locale(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.LANG_PREF));
    }

    public static boolean validateEmail(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert email", Toast.LENGTH_SHORT).show();

            return false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()){
            Toast.makeText(context, "Please insert valid email", Toast.LENGTH_SHORT).show();

            return false;
        }else if  (editText.getText().toString().length() > 245){
            Toast.makeText(context, "The length of email cannot be more than 254 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else{

            return true;
        }
    }

    public static boolean validatePassword(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert password", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }

    }

    public static boolean validateFirstName(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert first name", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() > 35) {
            Toast.makeText(context, "The length of first name cannot be more than 35 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validateLastName(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert last name", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() > 35) {
            Toast.makeText(context, "The length of last name cannot be more than 35 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validateInputPassword(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert password", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() > 20) {
            Toast.makeText(context, "The length of password cannot be more than 20 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else  if (editText.getText().toString().length() < 8) {
            Toast.makeText(context, "The length of password cannot be less than 8 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validatePhoneNumber(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert phone number", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() < 9) {
            Toast.makeText(context, "The length of phone number cannot be less than 9 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() > 12) {
            Toast.makeText(context, "The length of phone number cannot be more than 12 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else if (!PhoneNumberUtils.isGlobalPhoneNumber(editText.getText().toString())) {

            return false;
        }else {

            return true;
        }
    }

    public static boolean validateAddress(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert address", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() > 300) {
            Toast.makeText(context, "The length of address cannot be more than 300 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validateConfirmPassword(Context context, CustomEditText editText, String password) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert password", Toast.LENGTH_SHORT).show();

            return false;
        }else if (editText.getText().toString().length() > 20) {
            Toast.makeText(context, "The length of password cannot be more than 20 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else  if (editText.getText().toString().length() < 8) {
            Toast.makeText(context, "The length of password cannot be less than 8 characters", Toast.LENGTH_SHORT).show();

            return false;
        }else if (!editText.getText().toString().equals(password)){
            Toast.makeText(context, "Pasword Confirm doesn't match with Password", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validateCountry(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please select a country", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validateLanguage(Context context, CustomEditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "Please select Language", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validatePromotionCode(Context context, String promoCode) {
        if (promoCode.equals("")) {
            Toast.makeText(context, "Please select Language", Toast.LENGTH_SHORT).show();

            return false;
        }else if (promoCode.length() > 20) {
            Toast.makeText(context, "Please select Language", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static boolean validatePhoneCountry(Context context, CustomEditText customEditText) {
        if (customEditText.getText().toString().equals("")) {
            Toast.makeText(context, "Please insert phone country", Toast.LENGTH_SHORT).show();

            return false;
        }else {

            return true;
        }
    }

    public static Spanned obtainTermsOfUseString(String term1, String term2, String term3) {
        return Html.fromHtml(term1 + "  <font color='#377AAD'>" + term2 +"</font>  " + term3);
    }

    public static SpannableStringBuilder getStrikeThroughSpannable(Context context, String textSpannable) {
        ForegroundColorSpan redForegroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.green));

        // Use a SpannableStringBuilder so that both the text and the spans are mutable
        SpannableStringBuilder ssb = new SpannableStringBuilder(textSpannable);

        // Apply the color span
        ssb.setSpan(
                redForegroundColorSpan,            // the span to add
                0,                                 // the start of the span (inclusive)
                ssb.length(),                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later

        // Create a span that will strikethrough the text
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

        ssb.setSpan(
                strikethroughSpan,
                0,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssb;

    }

}

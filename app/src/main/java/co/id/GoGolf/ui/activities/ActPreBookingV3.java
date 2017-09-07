package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.PointEvent;
import co.id.GoGolf.events.PreBookingEventV2;
import co.id.GoGolf.models.request.BookReq;
import co.id.GoGolf.models.request.Flightarr;
import co.id.GoGolf.models.request.FlightarrNew;
import co.id.GoGolf.models.request.Price_list;
import co.id.GoGolf.models.request.TriggerEvent;
import co.id.GoGolf.models.response.prebooking.Conditionarr;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.CustomVP;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.adapters.PagerAdapter;
import co.id.GoGolf.ui.callback.DatePickerCallback;
import co.id.GoGolf.ui.callback.FlightNumberCallback;
import co.id.GoGolf.ui.fragments.FragDatePicker;
import co.id.GoGolf.ui.fragments.FragFlightNumber;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.Clog;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 8/17/16.
 */
public class ActPreBookingV3 extends BaseAct implements DatePickerCallback, FlightNumberCallback {
    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.ivHeaderPreBook)
    ImageView ivHeaderPreBook;

    @InjectView(R.id.tvPromoDate)
    CustomTextView tvPromoDate;

    @InjectView(R.id.tvName)
    CustomTextView tvName;

    @InjectView(R.id.tvTime)
    CustomTextView tvTime;

    @InjectView(R.id.tvPrate)
    CustomTextView tvPrate;

    @InjectView(R.id.tvPprice)
    CustomTextView tvPprice;

    @InjectView(R.id.tvDiscount)
    CustomTextView tvDiscount;

    @InjectView(R.id.tvLimit)
    CustomTextView tvLimit;

    @InjectView(R.id.etDatePreBook)
    CustomEditText etDatePreBook;

    @InjectView(R.id.ivDatePreBook)
    ImageView ivDatePreBook;

    @InjectView(R.id.llPopbox)
    LinearLayout llPopbox;

    @InjectView(R.id.llOldPrice)
    LinearLayout llOldPrice;

    @InjectView(R.id.llPayment)
    LinearLayout llPayment;

    @InjectView(R.id.tvTotalOldPrice)
    CustomTextView tvTotalOldPrice;

    @InjectView(R.id.llForm)
    CustomVP vpForm;

    @InjectView(R.id.svContent)
    ScrollView svContent;

    @InjectView(R.id.llItem)
    LinearLayout llItem;

    @InjectView(R.id.llPromotionContent)
    LinearLayout llPromotionContent;

    @InjectView(R.id.tvFlightNumber)
    CustomTextView tvFlightNumber;

    @InjectView(R.id.ivFlightNumberDropDown)
    ImageView ivFlightNumberDropDown;

    BottomSheetDialogFragment datePickerSheet;
    BottomSheetDialogFragment flightNumberSheet;

    private int isBookingAvailable = 0;
    private String gid = "";
    private String date = "", gname = "", cancelLimit = "";
    private String ab_date_start = "", ab_date_end = "";
    private int currentPage = 0;

    private int selectedPosition = 0;
    private String sTotal = "";
    private ArrayList<Flightarr> classFlightarrs = new ArrayList<>();
    private ArrayList<FlightarrNew> classFlightarrsReq = new ArrayList<>();

    private Context context;
    Calendar mcurrentTime = Calendar.getInstance();

    private ArrayList<Timearr> timearrs = new ArrayList<>();
    private ArrayList<Conditionarr> conditionarrs = new ArrayList<>();

    int totalPrice = 0, deposite_rate = 0;

    private PagerAdapter pagerAdapter;

    BookReq bookReq;

    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");

    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pre_book);

        gid = getIntent().getStringExtra("gid");
        date = getIntent().getStringExtra("date");

        if (date.equals("")) {
            date = sdr.format(new Date());
            Log.d(ActPreBookingV3.class.getSimpleName(), "date " + date);
        }

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);
        context = getApplicationContext();
        llPopbox.setVisibility(View.GONE);
        llPayment.setVisibility(View.GONE);

        initToolbar(getResources().getString(R.string.pb_nav), null);

        showLoadingDialog();

        tvFlightNumber.setText("" + (selectedPosition + 1));

        conditionarrs.clear();
        timearrs.clear();

        vpForm.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mainPresenter.postPreBookingV2(gid, date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 23 && data != null) {
            if (data.getStringExtra("success_paid").equals("success_paid")) {
                setResult(RESULT_OK, data);
                ActPreBookingV3.this.finish();
            }
        }
    }

    @OnClick(R.id.ivDatePreBook)
    public void onDateClick() {
        datePickerSheet = new FragDatePicker(this, ab_date_start, ab_date_end);
        datePickerSheet.show(getSupportFragmentManager(), datePickerSheet.getTag());
    }

    @OnClick(R.id.ivFlightNumberDropDown)
    public void onSelectFlightNumber() {
        flightNumberSheet = new FragFlightNumber(selectedPosition, this);
        flightNumberSheet.show(getSupportFragmentManager(), flightNumberSheet.getTag());
    }

    @Subscribe
    public void onEventThread(PreBookingEventV2 event) {
        dismissLoadingDialog();
        isBookingAvailable = 0;
        svContent.setVisibility(View.VISIBLE);

        tvName.setText(event.getData().getGname());
        etDatePreBook.setText(event.getData().getDate());
        ab_date_start = event.getData().getAb_date_start();
        ab_date_end = event.getData().getAb_date_end();
        gname = event.getData().getGname();

        if (event.getData().getPromotion().size() > 0) {
            llPromotionContent.setVisibility(View.VISIBLE);
            tvPrate.setVisibility(View.VISIBLE);
            tvPprice.setVisibility(View.VISIBLE);
            tvDiscount.setVisibility(View.VISIBLE);

            String prateString = "";
            prateString = "Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(event.getData().getPromotion().get(0).getPrate()));
            String ppriceString = "";
            ppriceString = "" + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(event.getData().getPromotion().get(0).getPprice()));

            tvPrate.setText(UIUtils.getStrikeThroughSpannable(ActPreBookingV3.this, prateString), TextView.BufferType.EDITABLE);
            tvPprice.setText("Rp. " + ppriceString);
            tvDiscount.setText("Disc " + event.getData().getPromotion().get(0).getDiscount() + "%");
            tvPromoDate.setText(event.getData().getDate());
            tvLimit.setText("Rest of limit : " + event.getData().getPromotion().get(0).getLimit_num() + " Flight");
            tvTime.setText(event.getData().getPromotion().get(0).getStime() + " - " + event.getData().getPromotion().get(0).getEtime());
        } else {
            llPromotionContent.setVisibility(View.GONE);
            tvPrate.setVisibility(View.GONE);
            tvPprice.setVisibility(View.GONE);
            tvDiscount.setVisibility(View.GONE);
        }

        timearrs = event.getData().getTimearr();
        conditionarrs = event.getData().getConditionarr();
        Log.d(ActPreBookingV3.class.getSimpleName(), event.getData().getTimearr().toString());
        Log.d(ActPreBookingV3.class.getSimpleName(), "timearr " + event.getData().getTimearr().size());
        Log.d(ActPreBookingV3.class.getSimpleName(), event.getData().getConditionarr().toString());
        Log.d(ActPreBookingV3.class.getSimpleName(), "conditionarr " + event.getData().getConditionarr().size());

        if (timearrs.size() > 0) {
            Glide.with(context).load(event.getData().getImage()).centerCrop().into(ivHeaderPreBook);

            PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_TEA_TIME, new HashSet<String>());

            pagerAdapter = new PagerAdapter(getSupportFragmentManager(), selectedPosition + 1, conditionarrs, timearrs);
            vpForm.setAdapter(pagerAdapter);
            vpForm.setOffscreenPageLimit(selectedPosition + 1);

            tvTotalOldPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(conditionarrs.get(timearrs.get(0).getIntCondition()).getPrice_list().get(0).getPrice())));
            cancelLimit = conditionarrs.get(timearrs.get(0).getIntCondition()).getCancel_limit_hours();
            deposite_rate = (conditionarrs.get(timearrs.get(0).getIntCondition()).getDeposit_rate() == null) ? 0 : Integer.valueOf(conditionarrs.get(timearrs.get(0).getIntCondition()).getDeposit_rate());
        }else {
            Toast.makeText(ActPreBookingV3.this, "No flight data for this course", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btBookNow)
    public void onBookNow() {
        if (isBookingAvailable != -1) {
            showLoadingDialog();
            mainPresenter.postGetPoint(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), gid, date, String.valueOf(totalPrice));
        }else
            Toast.makeText(ActPreBookingV3.this, "No flight data for this course", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(PointEvent event) {
        dismissLoadingDialog();
        if (!event.getMessage().equals("tprice is wrong")) {
            bookReq = new BookReq("0", gid, date, "id", classFlightarrsReq);
            startActivityForResult(new Intent(getApplicationContext(), ActPreview.class).putExtra("gdate", date).putExtra("gid", gid).putExtra("gname", gname).putExtra("sTotal", sTotal).putExtra("flightarrs", classFlightarrsReq).putExtra("data_point", event.getData()).putExtra("fromHistory", false).putExtra("bookreq", bookReq).putExtra("deposite_rate", deposite_rate).putExtra("cancel", cancelLimit), 23);
        }else {
            Toast.makeText(ActPreBookingV3.this, "Teetime already past", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        isBookingAvailable = -1;
    }

    @Subscribe
    public void onEventThread(TriggerEvent event) {
        totalPrice = 0;
        classFlightarrsReq.clear();
        for (int x = 0; x < pagerAdapter.getCount(); x++) {
            Flightarr flightarr = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.SF_BOOKING + x), Flightarr.class);

            ArrayList<Price_list> price_lists = new ArrayList<>();

            boolean needCart = event.isNeedCart().get(x);
            Log.d("TAG", "index : " + x + " , needCart : " + String.valueOf(needCart));
            Log.d("TAG", "index : " + x + " , playerType : " + flightarr.getPlayer());
            Log.d("TAG", "index : " + x + " , teetime : " + flightarr.getTtime());

            for (co.id.GoGolf.models.response.prebooking.Price_list o : flightarr.getPlayerarr()) {
                Log.d("TAG", "index : " + x + " , cart_mandatory : " + o.getCart_mandatory());
                if (o.getCart_mandatory().equals("1")) {
                    needCart = true;
                    break;
                }
            }

            if (needCart){
                for (co.id.GoGolf.models.response.prebooking.Price_list o : flightarr.getPlayerarr()) {
                    price_lists.add(new Price_list(o.getPrice_cart(), o.getType()));
                }
            }else {
                for (co.id.GoGolf.models.response.prebooking.Price_list o : flightarr.getPlayerarr()) {
                    price_lists.add(new Price_list(o.getPrice(), o.getType()));
                }
            }

            FlightarrNew flightarrNew = new FlightarrNew(flightarr.getPlayer(),flightarr.getCart(), price_lists, flightarr.getTtime());
            classFlightarrsReq.add(flightarrNew);
            totalPrice = totalPrice + flightarrNew.getTotalPrice();
            Log.d("TAG", "index : " + x + " ,total price : " + totalPrice);
            Clog.e("index : " + x + " ,total price : " + totalPrice);
        }

        bookReq = new BookReq("0", gid, date, "id", classFlightarrsReq);
        sTotal = String.valueOf(totalPrice);
        tvTotalOldPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(totalPrice));
        tvTotalOldPrice.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void ObtainDate(String stringDate) {
        showLoadingDialog();
        this.date = stringDate;
        if (pagerAdapter != null) {
            vpForm.setAdapter(null);
            pagerAdapter = null;
        }
        timearrs.clear();
        conditionarrs.clear();
        tvTotalOldPrice.setText("Rp. 0");
        tvTotalOldPrice.setTextColor(getResources().getColor(R.color.black));
        mainPresenter.postPreBookingV2(gid, date);
    }

    @Override
    public void ObtainFlightNumber(String flightNumber) {
        selectedPosition = Integer.valueOf(flightNumber) - 1;
        tvFlightNumber.setText(flightNumber);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), Integer.valueOf(flightNumber), conditionarrs, timearrs);
        vpForm.setAdapter(pagerAdapter);
        vpForm.setOffscreenPageLimit(Integer.valueOf(flightNumber));
    }
}

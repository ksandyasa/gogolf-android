package co.id.GoGolf.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.MapEvent;
import co.id.GoGolf.events.PromotionEvent;
import co.id.GoGolf.events.SearchFilterPromoEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.adapters.AreaListAdapter;
import co.id.GoGolf.ui.adapters.GolfCourseNameListAdapter;
import co.id.GoGolf.ui.callback.AreaListAdapterCallback;
import co.id.GoGolf.ui.callback.DatePickerCallback;
import co.id.GoGolf.ui.callback.GolfCourseNameListAdapterCallback;
import co.id.GoGolf.ui.fragments.FragDatePicker;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 6/26/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActSearchPromo extends BaseAct implements AreaListAdapterCallback, GolfCourseNameListAdapterCallback,
        DatePickerCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.nsvContainerSearchPromo)
    NestedScrollView nsvContainerSearchPromo;

    @InjectView(R.id.etDatePromo)
    CustomEditText etDatePromo;

    @InjectView(R.id.ivDatePromo)
    ImageView ivDatePromo;

    Calendar mcurrentTime = Calendar.getInstance();
    Calendar mOriTime = Calendar.getInstance();
    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");

    int year = mcurrentTime.get(Calendar.YEAR);
    int month = mcurrentTime.get(Calendar.MONTH);
    int dateMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);

    @InjectView(R.id.rbPrice)
    RangeBar rbPrice;

    @InjectView(R.id.tvStart)
    CustomTextView tvStart;

    @InjectView(R.id.tvEnd)
    CustomTextView tvEnd;

    @InjectView(R.id.rbTeeTime)
    RangeBar rbTeeTime;

    @InjectView(R.id.tvStartTime)
    CustomTextView tvStartTime;

    @InjectView(R.id.tvEndTime)
    CustomTextView tvEndTime;

    @InjectView(R.id.etGolfCourseName)
    CustomEditText etGolfCourseName;

    @InjectView(R.id.rvAreaSearch)
    RecyclerView rvAreaSearch;

    LinearLayoutManager linearLayoutManager;

    AreaListAdapter areaListAdapter;

    @InjectView(R.id.rvGolfCourse)
    RecyclerView rvGolfCourse;

    @InjectView(R.id.rvBottomSearchPromo)
    RelativeLayout rvBottomSearchPromo;

    LinearLayoutManager linearLayoutManager1;

    GolfCourseNameListAdapter golfCourseNameListAdapter;

    private FragDatePicker fragDatePicker;
    private int pricemin = 0;
    private int pricemax = 10000;

    private String startTime = "00:00";
    private String endTime = "24:00";

    private String areaId = "";
    private AreaEvent areaEvent;
    private MapEvent mapEvent;
    private User user;
    private int selectedArea = 0;
    private int selectedGolfCourse = 0;
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_promo);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        initToolbar("Search Promotion", null);

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(ActSearchPromo.this, PreferenceUtility.SF_USER_DATA), User.class);

        etDatePromo.setText("");

        pricemin = pricemin * 1000;
        pricemax = pricemax * 1000;

        rbPrice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                pricemin = Integer.valueOf(leftPinValue) * 1000;
                pricemax = Integer.valueOf(rightPinValue) * 1000;

                tvStart.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(pricemin));
                tvEnd.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(pricemax));
            }
        });


        rbTeeTime.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                startTime = leftPinValue + ":00";
                endTime = rightPinValue + ":00";

                tvStartTime.setText(startTime);
                tvEndTime.setText(endTime);
            }
        });

        mainPresenter.postArea("1", "en");

        etGolfCourseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    nsvContainerSearchPromo.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nsvContainerSearchPromo.scrollTo(0, rvBottomSearchPromo.getBottom());
                        }
                    }, 250);
                }
            }
        });
    }

    @OnClick(R.id.ivDatePromo)
    public void onDateClicked() {
        fragDatePicker = new FragDatePicker(this, "", "");
        fragDatePicker.show(getSupportFragmentManager(), "fragDatePicker");
//        DatePickerDialog mDatePickerDialog;
//        mDatePickerDialog = new DatePickerDialog(ActSearchPromo.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                mcurrentTime.set(Calendar.YEAR, year);
//                mcurrentTime.set(Calendar.MONTH, monthOfYear);
//                mcurrentTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                etDatePromo.setText(sdr.format(mcurrentTime.getTime()));
//            }
//        }, year, month, dateMonth);
//        mDatePickerDialog.getDatePicker().setMinDate(mOriTime.getTimeInMillis() - 1000);
//        mDatePickerDialog.setTitle("");
//        mDatePickerDialog.show();
    }

    @OnClick(R.id.btSearch)
    public void onSearch() {
        showLoadingDialog();
        mainPresenter.getPromotion(String.valueOf(pricemin), String.valueOf(pricemax), etDatePromo.getText().toString(), URLEncoder.encode(startTime).replace("%3A", ":"), URLEncoder.encode(endTime).replace("%3A", ":"), areaId, "", "en");
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        dismissLoadingDialog();
        Toast.makeText(ActSearchPromo.this, "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(PromotionEvent event) {
        dismissLoadingDialog();
        EventBus.getDefault().post(new SearchFilterPromoEvent(areaId, String.valueOf(pricemax), URLEncoder.encode(startTime).replace("%3A", ":"), URLEncoder.encode(endTime).replace("%3A", ":"), "", etDatePromo.getText().toString(), "en", String.valueOf(pricemin)));
        ActSearchPromo.this.finish();
    }

    @Subscribe
    public void onEventThread(AreaEvent event) {
        Log.d("TAG", "size area " + event.getData().size());
        areaEvent = event;

        linearLayoutManager = new LinearLayoutManager(ActSearchPromo.this, LinearLayoutManager.HORIZONTAL, false);
        rvAreaSearch.setHasFixedSize(true);
        rvAreaSearch.setLayoutManager(linearLayoutManager);

        areaListAdapter = new AreaListAdapter(ActSearchPromo.this, areaEvent.getData(), selectedArea, this);
        rvAreaSearch.setAdapter(areaListAdapter);

        mainPresenter.getMap(PreferenceUtility.getInstance().loadDataString(ActSearchPromo.this, PreferenceUtility.ACCESS_TOKEN),
                areaEvent.getData().get(selectedArea).getArea_id(),
                "en");
    }

    @Subscribe
    public void onEventThread(MapEvent event) {
        mapEvent = event;
        linearLayoutManager1 = new LinearLayoutManager(ActSearchPromo.this, LinearLayoutManager.HORIZONTAL, false);
        rvGolfCourse.setHasFixedSize(true);
        rvGolfCourse.setLayoutManager(linearLayoutManager1);

        golfCourseNameListAdapter = new GolfCourseNameListAdapter(ActSearchPromo.this, mapEvent.getData(), selectedGolfCourse, this);
        rvGolfCourse.setAdapter(golfCourseNameListAdapter);

        if (event.getData().size() > 0) {
            etGolfCourseName.setText(mapEvent.getData().get(selectedGolfCourse).getGname());
        }
    }

    @Override
    public void ShowSelectedAreaOnMap(int position) {
        selectedArea = position;

        mainPresenter.getMap(PreferenceUtility.getInstance().loadDataString(ActSearchPromo.this, PreferenceUtility.ACCESS_TOKEN),
                areaEvent.getData().get(selectedArea).getArea_id(),
                "en");
    }

    @Override
    public void onSelectedGolfCourseName(int position) {
        selectedGolfCourse = position;
        etGolfCourseName.setText(mapEvent.getData().get(selectedGolfCourse).getGname());
    }

    @Override
    public void ObtainDate(String stringDate) {
        etDatePromo.setText(stringDate);
    }
}

package co.id.GoGolf.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.MapEvent;
import co.id.GoGolf.events.SearchEvent;
import co.id.GoGolf.events.SearchFilterEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.adapters.AreaListAdapter;
import co.id.GoGolf.ui.adapters.GolfCourseNameListAdapter;
import co.id.GoGolf.ui.callback.AreaListAdapterCallback;
import co.id.GoGolf.ui.callback.GolfCourseNameListAdapterCallback;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 6/26/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActSearch extends BaseAct implements AreaListAdapterCallback, GolfCourseNameListAdapterCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.svContainerSearch)
    ScrollView svContainerSearch;

    @InjectView(R.id.rbPrice)
    RangeBar rbPrice;

    @InjectView(R.id.rvAreaSearch)
    RecyclerView rvAreaSearch;

    LinearLayoutManager linearLayoutManager;

    AreaListAdapter areaListAdapter;

    @InjectView(R.id.rvGolfCourse)
    RecyclerView rvGolfCourse;

    LinearLayoutManager linearLayoutManager1;

    GolfCourseNameListAdapter golfCourseNameListAdapter;

    @InjectView(R.id.etGolfCourseName)
    CustomEditText etGolfCourseName;

    @InjectView(R.id.tvStart)
    CustomTextView tvStart;

    @InjectView(R.id.tvEnd)
    CustomTextView tvEnd;

    @InjectView(R.id.rlBottomSearch)
    RelativeLayout rlBottomSearch;

    private int pricemin = 0;
    private int pricemax = 0;
    private String areaId = "";
    private AreaEvent areaEvent;
    private MapEvent mapEvent;
    private User user;
    private boolean fromhome = false;
    private int firstAreaLoaded = 0;
    private int selectedArea = 0;
    private int selectedGolfCourse = 0;
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar(getResources().getString(R.string.sgc_nav), null);

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(ActSearch.this, PreferenceUtility.SF_USER_DATA), User.class);

        fromhome = getIntent().getBooleanExtra("from_home", false);

        mainPresenter.postArea("1", "en");

        pricemin = 0;
        pricemax = 0;

        tvStart.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(pricemin));
        tvEnd.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(pricemax));

        rbPrice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                pricemin = Integer.valueOf(leftPinValue) * 1000;
                pricemax = Integer.valueOf(rightPinValue) * 1000;

                tvStart.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(pricemin));
                tvEnd.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(pricemax));
            }
        });

        etGolfCourseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerSearch.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerSearch.scrollTo(0, rlBottomSearch.getBottom());
                        }
                    }, 250);
                }
            }
        });
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        Toast.makeText(ActSearch.this, "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(AreaEvent event) {
        Log.d("TAG", "size area " + event.getData().size());
        areaEvent = event;
        if (firstAreaLoaded == 0) {
            firstAreaLoaded = 1;
            areaId = "";
        }else{
            areaId = areaEvent.getData().get(selectedArea).getArea_id();
        }

        linearLayoutManager = new LinearLayoutManager(ActSearch.this, LinearLayoutManager.HORIZONTAL, false);
        rvAreaSearch.setHasFixedSize(true);
        rvAreaSearch.setLayoutManager(linearLayoutManager);

        areaListAdapter = new AreaListAdapter(ActSearch.this, areaEvent.getData(), selectedArea, this);
        rvAreaSearch.setAdapter(areaListAdapter);

        mainPresenter.getMap(PreferenceUtility.getInstance().loadDataString(ActSearch.this, PreferenceUtility.ACCESS_TOKEN),
                areaEvent.getData().get(selectedArea).getArea_id(),
                "en");
    }

    @Subscribe
    public void onEventThread(MapEvent event) {
        mapEvent = event;
        linearLayoutManager1 = new LinearLayoutManager(ActSearch.this, LinearLayoutManager.HORIZONTAL, false);
        rvGolfCourse.setHasFixedSize(true);
        rvGolfCourse.setLayoutManager(linearLayoutManager1);

        golfCourseNameListAdapter = new GolfCourseNameListAdapter(ActSearch.this, mapEvent.getData(), selectedGolfCourse, this);
        rvGolfCourse.setAdapter(golfCourseNameListAdapter);

        if (event.getData().size() > 0) {
            etGolfCourseName.setText(mapEvent.getData().get(selectedGolfCourse).getGname());
        }
    }

    @Subscribe
    public void onEventThread(SearchEvent event) {
        dismissLoadingDialog();
        EventBus.getDefault().post(new SearchFilterEvent(pricemin, pricemax, areaId, etGolfCourseName.getText().toString(), "en"));
        ActSearch.this.finish();
    }

    @OnClick(R.id.btSearch)
    public void onSearch() {
        Log.d("retro", "pricemin " + pricemin);
        showLoadingDialog();
        mainPresenter.getGolf((pricemin == 0) ? "" : String.valueOf(pricemin), (pricemax == 0) ? "" : String.valueOf(pricemax), areaId, etGolfCourseName.getText().toString());
    }

    @Override
    public void ShowSelectedAreaOnMap(int position) {
        selectedArea = position;
        areaId = areaEvent.getData().get(selectedArea).getArea_id();

        mainPresenter.getMap(PreferenceUtility.getInstance().loadDataString(ActSearch.this, PreferenceUtility.ACCESS_TOKEN),
                areaEvent.getData().get(selectedArea).getArea_id(),
                "en");
    }

    @Override
    public void onSelectedGolfCourseName(int position) {
        selectedGolfCourse = position;
        etGolfCourseName.setText(mapEvent.getData().get(selectedGolfCourse).getGname());
    }
}

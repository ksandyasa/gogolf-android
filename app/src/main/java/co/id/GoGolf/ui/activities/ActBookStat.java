package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.BookHisEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.DividerItemDecoration;
import co.id.GoGolf.ui.adapters.BookStatAdapter;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 8/8/16.
 */
public class ActBookStat extends BaseAct {

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tvEmptyBookStat)
    CustomTextView tvEmptyBookStat;

    private BookStatAdapter historyAdapter;

    @Inject
    MainPresenter mainPresenter;

    private Context context;

    Calendar mcurrentTime = Calendar.getInstance();
    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
    private Date today;
    private Date nextYear;
    private String todayString;
    private String nextYearString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_stat);

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar("Booking Status", null);
        initRecyclerView();

        showLoadingDialog();

        Date today = mcurrentTime.getTime();
        todayString = sdr.format(today);
        mcurrentTime.add(Calendar.YEAR, 1);
        Date nextYear = mcurrentTime.getTime();
        nextYearString = sdr.format(nextYear);

        mainPresenter.getBookHis(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), todayString, nextYearString, URLEncoder.encode("1,2,3,5").replace("%2C", ","), "en");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 29 && data != null) {
            if (data.getStringExtra("success_paid") != null) {
                if (data.getStringExtra("success_paid").equals("success_paid")) {
                    setResult(RESULT_OK, data);
                    ActBookStat.this.finish();
                }
            }else if (data.getStringExtra("page_refresh") != null) {
                if (data.getStringExtra("page_refresh").equals("refresh")) {
                    rvHistory.setAdapter(null);
                    historyAdapter = null;
                    mainPresenter.getBookHis(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), todayString, nextYearString, URLEncoder.encode("1,2,3,5").replace("%2C", ","), "en");
                }
            }
        }
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(rvHistory.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Subscribe
    public void onEventThread(BookHisEvent event) {
        dismissLoadingDialog();
        historyAdapter = new BookStatAdapter(ActBookStat.this);
        rvHistory.setAdapter(historyAdapter);
        historyAdapter.addData(event.getData());
        if (event.getData().size() == 0) {
            tvEmptyBookStat.setText(event.getMessage());
            tvEmptyBookStat.setVisibility(View.VISIBLE);
//            Toast.makeText(context, "There is no data in this page.", Toast.LENGTH_SHORT).show();
        }else{
            tvEmptyBookStat.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        dismissLoadingDialog();
        Toast.makeText(context, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }


}

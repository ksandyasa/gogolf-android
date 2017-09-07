package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.BookHisEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.DividerItemDecoration;
import co.id.GoGolf.ui.adapters.BookHisAdapter;
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
public class ActBookHis extends BaseAct {

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tvEmptyBookHis)
    CustomTextView tvEmptyBookHis;

    private BookHisAdapter historyAdapter;

    @Inject
    MainPresenter mainPresenter;

    private Context context;

    Calendar mcurrentTime = Calendar.getInstance();
    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_his);

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar("Booking History", null);

        showLoadingDialog();

        Date today = mcurrentTime.getTime();
        mcurrentTime.add(Calendar.YEAR, -1);
        Date lastYear = mcurrentTime.getTime();
        Log.d("TAG", "today date " + sdr.format(today));
        Log.d("TAG", "last year date " + sdr.format(lastYear));

        mainPresenter.getBookHis(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), sdr.format(lastYear), sdr.format(today), URLEncoder.encode("4,6,7,8").replace("%2C", ","), "en");
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(rvHistory.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        historyAdapter = new BookHisAdapter(ActBookHis.this);
        rvHistory.setAdapter(historyAdapter);
    }

    @Subscribe
    public void onEventThread(BookHisEvent event) {
        dismissLoadingDialog();
        initRecyclerView();
        historyAdapter.addData(event.getData());
        if (event.getData().size() == 0) {
            tvEmptyBookHis.setText(event.getMessage());
            tvEmptyBookHis.setVisibility(View.VISIBLE);
//            Toast.makeText(context, "There is no data in this page.", Toast.LENGTH_SHORT).show();
        }else{
            tvEmptyBookHis.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        dismissLoadingDialog();
        Toast.makeText(context, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

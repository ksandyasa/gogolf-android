package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.PointHistoryEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.DividerItemDecoration;
import co.id.GoGolf.ui.adapters.PointHisAdapter;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 7/27/16.
 */
public class ActPointHistory extends BaseAct {

    @Inject
    MainPresenter mainPresenter;
    private Context context;

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tvEmptyPointHistory)
    CustomTextView tvEmptyPointHistory;

    private PointHisAdapter historyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_point_history);

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar("Point History", null);
        initRecyclerView();

        mainPresenter.postGetPointHistory(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), "en");

        showLoadingDialog();
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(rvHistory.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        historyAdapter = new PointHisAdapter(context);
        rvHistory.setAdapter(historyAdapter);

    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), "Failed! " + event.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Subscribe
    public void onEventThread(PointHistoryEvent event) {
        dismissLoadingDialog();
        historyAdapter.addData(event.getData());
        if (event.getData().size() == 0) {
            tvEmptyPointHistory.setText(event.getMessage());
            tvEmptyPointHistory.setVisibility(View.VISIBLE);
        }else {
            tvEmptyPointHistory.setVisibility(View.INVISIBLE);
        }
    }
}

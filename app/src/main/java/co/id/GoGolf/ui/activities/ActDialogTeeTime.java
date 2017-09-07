package co.id.GoGolf.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.ui.adapters.TeeTimeAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 6/21/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActDialogTeeTime extends AppCompatActivity {
    @InjectView(R.id.rvDefault)
    RecyclerView rvCities;

    private TeeTimeAdapter teeTimeAdapter;
    private ArrayList<Timearr> timearrs;
    private int pos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialog_tee_time);
        ButterKnife.inject(this);

        pos = getIntent().getIntExtra("pos", 0);
        timearrs = (ArrayList<Timearr>) getIntent().getSerializableExtra("timearr");

        initRecyclerView();

    }

    public void initRecyclerView() {
        rvCities.setHasFixedSize(true);
        rvCities.setLayoutManager(new LinearLayoutManager(rvCities.getContext()));
        rvCities.setItemAnimator(new DefaultItemAnimator());
//        rvSerp.addItemDecoration(new DividerItemDecoration(rvSerp.getContext(),
//                DividerItemDecoration.VERTICAL_LIST));
        teeTimeAdapter = new TeeTimeAdapter(ActDialogTeeTime.this, pos);
        teeTimeAdapter.addTimearr(timearrs);
        rvCities.setAdapter(teeTimeAdapter);
    }
}

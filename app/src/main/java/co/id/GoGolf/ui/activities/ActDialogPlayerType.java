package co.id.GoGolf.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.prebooking.Price_list;
import co.id.GoGolf.ui.adapters.PlayerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 6/21/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActDialogPlayerType extends AppCompatActivity {

    @InjectView(R.id.rvDefault)
    RecyclerView rvCities;

    private PlayerAdapter playerAdapter;
    private ArrayList<Price_list> price_lists;
    private int pos = 0;
    private int posParent = 0;
    private boolean needCartOpt = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialog_tee_time);
        ButterKnife.inject(this);

        pos = getIntent().getIntExtra("pos", 0);
        posParent = getIntent().getIntExtra("posParent", 0);
        needCartOpt = getIntent().getBooleanExtra("needCartOpt", false);

        price_lists = (ArrayList<Price_list>) getIntent().getSerializableExtra("price_list");

        initRecyclerView();

    }

    public void initRecyclerView() {
//        rvCities.setHasFixedSize(true);
//        rvCities.setLayoutManager(new LinearLayoutManager(rvCities.getContext()));
//        rvCities.setItemAnimator(new DefaultItemAnimator());
//        rvSerp.addItemDecoration(new DividerItemDecoration(rvSerp.getContext(),
//                DividerItemDecoration.VERTICAL_LIST));
//        playerAdapter = new PlayerAdapter(ActDialogPlayerType.this, pos, posParent, needCartOpt);
//        playerAdapter.addPlayer(price_lists);
//        rvCities.setAdapter(playerAdapter);
    }
}

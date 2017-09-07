package co.id.GoGolf.ui.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.util.PreferenceUtility;
//import Timearr;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 5/11/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class TeeTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Timearr> timearrs;
    private Activity activity;
    private Bundle bundle;

    private int pos;
    private Set<String> hashTime = new HashSet<>();

    public TeeTimeAdapter(Activity activity, int pos) {
        this.hashTime = PreferenceUtility.getInstance().loadDataStringSet(activity.getApplicationContext(), PreferenceUtility.SF_TEA_TIME);
        this.timearrs = new ArrayList<>();
        this.activity = activity;
        this.pos = pos;
    }

    public void addTimearr(List<Timearr> newTimearr) {
        this.timearrs.addAll(newTimearr);
        notifyDataSetChanged();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_timearr, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvTimeArr.setText(timearrs.get(position).getTtime());
        if (timearrs.get(position).getPromo().equals("0")) {
            ((ViewHolder) holder).tvPromoStatus.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).tvPromoStatus.setVisibility(View.VISIBLE);
        }
        ((ViewHolder) holder).rlTimeArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timearr timearr = timearrs.get(position);
                if (hashTime.contains(timearr.getTtime())) {
                    Toast.makeText(activity.getApplicationContext(), "You have already chosen same tee time in different flight. Please choose other tee time.", Toast.LENGTH_LONG).show();
                } else {
                    timearr.setPos(pos);
                    EventBus.getDefault().post(timearr);
                    activity.finish();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return timearrs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rlTimeArr)
        RelativeLayout rlTimeArr;
        @InjectView(R.id.tvTimeArr)
        TextView tvTimeArr;
        @InjectView(R.id.tvPromoStatus)
        TextView tvPromoStatus;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
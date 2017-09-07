package co.id.GoGolf.ui.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.prebooking.Price_list;
import co.id.GoGolf.ui.callback.PlayerAdapterCallback;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import Timearr;

/**
 * Created by dedepradana on 5/11/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Price_list> price_lists;
    private Activity activity;
    private Bundle bundle;
    private int pos, posParent;
    private boolean needCartOpt;
    private PlayerAdapterCallback callback;
    private int selectedPosition;
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public PlayerAdapter(Activity activity, int pos, int posParent, boolean needCartOptParent, int selectedPosition, PlayerAdapterCallback listener) {
        this.price_lists = new ArrayList<>();
        this.activity = activity;
        this.pos = pos;
        this.posParent = posParent;
        this.needCartOpt = needCartOptParent;
        this.selectedPosition = selectedPosition;
        this.callback = listener;
        this.otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        this.otherSymbols.setDecimalSeparator(',');
        this.otherSymbols.setGroupingSeparator('.');

    }

    public void addPlayer(List<Price_list> newPrice_lists) {
        this.price_lists.addAll(newPrice_lists);
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
        if (needCartOpt == false)
            ((ViewHolder) holder).tvTimeArr.setText(price_lists.get(position).getType() + " - Rp." + new DecimalFormat(this.formatString, this.otherSymbols).format(Integer.valueOf(price_lists.get(position).getPrice())));
        else
            ((ViewHolder) holder).tvTimeArr.setText(price_lists.get(position).getType() + " - Rp." + new DecimalFormat(this.formatString, this.otherSymbols).format(Integer.valueOf(price_lists.get(position).getPrice_cart())));
        ((ViewHolder) holder).rlTimeArr.setOnClickListener(new SelectPlayerType(position));
    }

    @Override
    public int getItemCount() {
        return price_lists.size();
    }

    private class SelectPlayerType implements View.OnClickListener {

        private int mPosition;

        public SelectPlayerType(int pos) {
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            Price_list price_list = price_lists.get(mPosition);
            price_list.setPos(pos);
            price_list.setPosParent(posParent);
            EventBus.getDefault().post(price_list);
            callback.SelectPlayerType(mPosition);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rlTimeArr)
        RelativeLayout rlTimeArr;
        @InjectView(R.id.tvTimeArr)
        TextView tvTimeArr;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
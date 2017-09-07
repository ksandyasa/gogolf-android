package co.id.GoGolf.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import co.id.GoGolf.Config;
import co.id.GoGolf.R;
//import Flightarr;
import co.id.GoGolf.models.response.history.DataHistory;
import co.id.GoGolf.models.response.history.Flightarr;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActBookingDetail;
import co.id.GoGolf.ui.activities.BaseAct;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 5/11/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class BookStatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataHistory> histories;
    private Activity context;
    private Bundle bundle;
    private ArrayList<Flightarr> classFlightarrs = new ArrayList<>();
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public BookStatAdapter(Activity context) {
        this.histories = new ArrayList<>();
        this.context = context;
        this.otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        this.otherSymbols.setDecimalSeparator(',');
        this.otherSymbols.setGroupingSeparator('.');
    }

    public void addData(List<DataHistory> newHistory) {
        this.histories.addAll(newHistory);
        notifyDataSetChanged();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_stat, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Glide.with(context).load(histories.get(position).getImage()).centerCrop().into(((ViewHolder) holder).ivImage);
        ((ViewHolder) holder).tvName.setText(histories.get(position).getGname());
        ((ViewHolder) holder).tvDate.setText(histories.get(position).getDate());
        ((ViewHolder) holder).tvFlight.setText("Flight : " + histories.get(position).getFlightarr().size());
        int players = 0;
        for (int i = 0; i < histories.get(position).getFlightarr().size(); i++) {
            players = players + Integer.valueOf(histories.get(position).getFlightarr().get(i).getNumber());
        }
        ((ViewHolder) holder).tvPLayer.setText("Player : " + players);
        ((ViewHolder) holder).tvBookingCode.setText("Booking code : " + histories.get(position).getBcode());

        String totalString = "";
        totalString = new DecimalFormat(this.formatString, this.otherSymbols).format(Integer.valueOf(histories.get(position).getTprice()));
        ((ViewHolder) holder).tvPrice.setText("Rp " + totalString);
        ((ViewHolder) holder).tvStatusPaid.setText(histories.get(position).getStatus());

        final int finalPlayers = players;

        ((ViewHolder) holder).btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < histories.get(position).getFlightarr().size(); i++) {
                    Flightarr flightarr = new Flightarr(String.valueOf(finalPlayers), histories.get(position).getFlightarr().get(i).getMembership(), histories.get(position).getFlightarr().get(i).getCart(), histories.get(position).getFlightarr().get(i).getTtime(), histories.get(position).getFlightarr().get(i).getTprice(), histories.get(position).getFlightarr().get(i).getPlayerarr());
                    classFlightarrs.add(flightarr);
                }

                ((BaseAct)context).startActivityForResult(new Intent(context, ActBookingDetail.class).putExtra("data", histories.get(position)).putExtra("fromHis", false), Config.BOOK_DETAIL);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.rlParent)
        RelativeLayout rlParent;

        @InjectView(R.id.ivImage)
        ImageView ivImage;

        @InjectView(R.id.tvName)
        CustomTextView tvName;

        @InjectView(R.id.tvDate)
        CustomTextView tvDate;

        @InjectView(R.id.tvPLayer)
        CustomTextView tvPLayer;

        @InjectView(R.id.tvFlight)
        CustomTextView tvFlight;

        @InjectView(R.id.tvPrice)
        CustomTextView tvPrice;

        @InjectView(R.id.tvStatusPaid)
        CustomTextView tvStatusPaid;

        @InjectView(R.id.tvBookingCode)
        CustomTextView tvBookingCode;

        @InjectView(R.id.btnStatus)
        CustomButton btnStatus;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
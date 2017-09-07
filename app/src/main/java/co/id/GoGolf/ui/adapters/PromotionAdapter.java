package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.models.response.DataPromotion;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActGolfDetail;
import co.id.GoGolf.ui.callback.PromotionAdapterCallback;

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
public class PromotionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataPromotion> promotions;
    private Context context;
    private Bundle bundle;
    private PromotionAdapterCallback callback;
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public PromotionAdapter(Context context, PromotionAdapterCallback listener) {
        this.promotions = new ArrayList<>();
        this.context = context;
        this.callback = listener;
        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
    }

    public void addData(List<DataPromotion> newPromotions) {
        this.promotions.addAll(newPromotions);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.promotions.clear();
        notifyDataSetChanged();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promotion, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvName.setText(promotions.get(position).getGname());
        String totalString = "";
        totalString = new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(promotions.get(position).getPprice()));
        ((ViewHolder) holder).tvPrice.setText("Rp." + totalString);
        ((ViewHolder) holder).tvArea.setText(promotions.get(position).getArea_name());
        ((ViewHolder) holder).tvDiscount.setText("Disc "+promotions.get(position).getDiscount() + "%");
        ((ViewHolder) holder).tvDate.setText(promotions.get(position).getDate());
        ((ViewHolder) holder).tvTime.setText(promotions.get(position).getStime() + "-" + promotions.get(position).getEtime());
        ((ViewHolder) holder).tvLimit.setText("Rest of limit : " + promotions.get(position).getLimit_num() + " Flight");
        Glide.with(context).load(promotions.get(position).getImage()).centerCrop().into(((ViewHolder) holder).ivImage);
        ((ViewHolder) holder).ivBookNow.setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
        ((ViewHolder) holder).btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.ShowPreBookingView(promotions.get(position).getGid(), promotions.get(position).getDate());
                //context.startActivity(new Intent(context, ActPreBookingV3.class).putExtra("gid", promotions.get(position).getGid()).putExtra("date", promotions.get(position).getDate()));
            }
        });

        ((ViewHolder) holder).llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ActGolfDetail.class).putExtra("gid", promotions.get(position).getGid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tvName)
        CustomTextView tvName;

        @InjectView(R.id.tvPrice)
        CustomTextView tvPrice;

        @InjectView(R.id.tvArea)
        CustomTextView tvArea;

        @InjectView(R.id.tvDiscount)
        CustomTextView tvDiscount;

        @InjectView(R.id.tvDate)
        CustomTextView tvDate;

        @InjectView(R.id.tvTime)
        CustomTextView tvTime;

        @InjectView(R.id.tvLimit)
        CustomTextView tvLimit;

        @InjectView(R.id.llItem)
        LinearLayout llItem;

        @InjectView(R.id.btBookNow)
        LinearLayout btBookNow;

        @InjectView(R.id.ivBookNow)
        ImageView ivBookNow;

        @InjectView(R.id.ivImage)
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
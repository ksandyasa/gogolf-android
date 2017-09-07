package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.models.response.DataGolf;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.GolfWeekendAdapterCallback;
import co.id.GoGolf.util.Clog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 5/11/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class GolfWeekendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataGolf> golfs;
    private Context context;
    private Bundle bundle;
    private GolfWeekendAdapterCallback callback;

    public GolfWeekendAdapter(Context context, GolfWeekendAdapterCallback listener) {
        this.golfs = new ArrayList<>();
        this.context = context;
        this.callback = listener;
    }

    public void addData(List<DataGolf> newGolfs) {
        this.golfs.addAll(newGolfs);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.golfs.clear();
        notifyDataSetChanged();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_golf_weekend, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvName.setText(golfs.get(position).getGname());
        ((ViewHolder) holder).tvArea.setText(golfs.get(position).getArea_name());
        ((ViewHolder) holder).tvDate.setText(golfs.get(position).getDate());
        ((ViewHolder) holder).tvLimit.setText("Limit : "+golfs.get(position).getLimit_num()+ " Flight");

        Glide.with(context).load(golfs.get(position).getImage()).centerCrop().into(((ViewHolder) holder).ivImage);

//        ((ViewHolder) holder).tvStatus.setText(histories.get(position).getPayment_status());
//        ((ViewHolder) holder).tvStatus.setBackgroundColor(Color.parseColor(histories.get(position).getPayment_status_color()));
//
//        ((ViewHolder) holder).tvPrice.setText(histories.get(position).getPrice());
//        ((ViewHolder) holder).tvDateTime.setText(histories.get(position).getDate() + " | "+histories.get(position).getTime());
        ((ViewHolder) holder).btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, DetailActivity.class).putExtras(bundle));
                Clog.e("GID : "+golfs.get(position).getGid());
                callback.ShowPreBookingView(golfs.get(position).getGid(), golfs.get(position).getDate());
                //context.startActivity(new Intent(context, ActPreBookingV3.class).putExtra("gid", golfs.get(position).getGid()).putExtra("date", ""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return golfs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tvName)
        CustomTextView tvName;

        @InjectView(R.id.tvArea)
        CustomTextView tvArea;

        @InjectView(R.id.tvDate)
        CustomTextView tvDate;

        @InjectView(R.id.tvLimit)
        CustomTextView tvLimit;

        @InjectView(R.id.btBookNow)
        LinearLayout btBookNow;

        @InjectView(R.id.llItem)
        LinearLayout llItem;

        @InjectView(R.id.ivImage)
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
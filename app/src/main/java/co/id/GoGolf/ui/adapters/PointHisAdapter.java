package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.DataPointHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 7/27/16.
 */
public class PointHisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<DataPointHistory> histories;
    private Context context;
    private Bundle bundle;

    public PointHisAdapter (Context context){
        this.histories = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<DataPointHistory> newHistory) {
        this.histories.addAll(newHistory);
        notifyDataSetChanged();
    }


    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_point_his, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tvDateTime.setText(histories.get(position).getDate());
        ((ViewHolder) holder).tvTransactionType.setText(histories.get(position).getPo_type());
        ((ViewHolder) holder).tvTotalPoint.setText(histories.get(position).getHistorical_point());
        ((ViewHolder) holder).tvPoint.setText(histories.get(position).getTransaction());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tvDateTime)
        TextView tvDateTime;

        @InjectView(R.id.tvTransactionType)
        TextView tvTransactionType;

        @InjectView(R.id.tvTotalPoint)
        TextView tvTotalPoint;

        @InjectView(R.id.tvPoint)
        TextView tvPoint;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

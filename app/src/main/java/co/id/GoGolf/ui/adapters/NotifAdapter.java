package co.id.GoGolf.ui.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.DataNotification;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by prumacadmin on 9/4/16.
 */
public class NotifAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<DataNotification> dataNotifications;
    private Activity context;
    private Bundle bundle;

    public NotifAdapter(Activity context) {
        this.dataNotifications = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<DataNotification> newNotifications) {
        this.dataNotifications.addAll(newNotifications);
        notifyDataSetChanged();
    }


    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notif, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tvTitle.setText(dataNotifications.get(position).getTitle());
        ((ViewHolder) holder).tvDate.setText(dataNotifications.get(position).getPushed_date());
        ((ViewHolder) holder).tvBody.setText(dataNotifications.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return dataNotifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tvDate)
        TextView tvDate;

        @InjectView(R.id.tvTitle)
        TextView tvTitle;

        @InjectView(R.id.tvBody)
        TextView tvBody;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

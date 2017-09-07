package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.SelectTeeTimeListAdapterCallback;
import co.id.GoGolf.util.PreferenceUtility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 10/20/16.
 */

public class SelectTeeTimeListAdapter extends RecyclerView.Adapter<SelectTeeTimeListAdapter.SelectTeeTimeListViewHolder> {

    private Context context;
    private List<Timearr> timearrList;
    private int selectedPosition = 0;
    private SelectTeeTimeListAdapterCallback callback;
    private Set<String> hashTime = new HashSet<>();

    public SelectTeeTimeListAdapter(Context context, List<Timearr> objects, SelectTeeTimeListAdapterCallback listener, int selection) {
        this.context = context;
        this.timearrList = objects;
        this.callback = listener;
        this.selectedPosition = selection;
    }

    @Override
    public SelectTeeTimeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_select_tee_time, parent, false);
        SelectTeeTimeListViewHolder holder = new SelectTeeTimeListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SelectTeeTimeListViewHolder holder, int position) {
//        Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);

//        String[] teeTimeArray = timearrList.get(position).getTtime().split(":");

//        if (Integer.parseInt(teeTimeArray[0]) >= hour) {
//            if (Integer.parseInt(teeTimeArray[1]) >= minute) {
//                if (selectedPosition == position) {
//                    holder.row_title_tee_time.setTextColor(context.getResources().getColor(R.color.green));
//                }else {
//                    holder.row_title_tee_time.setTextColor(context.getResources().getColor(R.color.black));
//                }
//                holder.row_container_tee_time.setOnClickListener(new SelectedTeeTime(position));
//            }else{
//                holder.row_title_tee_time.setTextColor(context.getResources().getColor(R.color.gray3));
//            }
//        }else{
//            holder.row_title_tee_time.setTextColor(context.getResources().getColor(R.color.gray3));
//        }

        if (selectedPosition == position) {
            holder.row_title_tee_time.setTextColor(context.getResources().getColor(R.color.green));
        }else {
            holder.row_title_tee_time.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.row_container_tee_time.setOnClickListener(new SelectedTeeTime(position));
        holder.row_title_tee_time.setText(timearrList.get(position).getTtime());
        holder.row_promo_tee_time.setText("Promo");
        Log.d("TAG", "" + timearrList.get(position).getPromo());
        if (timearrList.get(position).getPromo().equals("1")) {
            Log.d("TAG", "" + timearrList.get(position).getPromo());
            holder.row_promo_tee_time.setVisibility(View.VISIBLE);
        }else {
            Log.d("TAG", "" + timearrList.get(position).getPromo());
            holder.row_promo_tee_time.setVisibility(View.INVISIBLE);
        }
        holder.row_container_tee_time.setOnClickListener(new SelectedTeeTime(position));
    }

    @Override
    public int getItemCount() {
        return timearrList.size();
    }

    private class SelectedTeeTime implements View.OnClickListener {

        private int mPosition;

        public SelectedTeeTime(int pos) {
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            hashTime = PreferenceUtility.getInstance().loadDataStringSet(context, PreferenceUtility.SF_TEA_TIME);
            if (hashTime.contains(timearrList.get(mPosition).getTtime())) {
                Toast.makeText(context, "You have already chosen same tee time in different flight. Please choose other tee time.", Toast.LENGTH_LONG).show();
            } else {
                notifyItemChanged(selectedPosition);
                selectedPosition = mPosition;
                notifyItemChanged(selectedPosition);
                callback.SelectedTeeTime(mPosition);
            }

        }
    }

    static class SelectTeeTimeListViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.row_container_tee_time)
        RelativeLayout row_container_tee_time;

        @InjectView(R.id.row_title_tee_time)
        CustomTextView row_title_tee_time;

        @InjectView(R.id.row_promo_tee_time)
        CustomTextView row_promo_tee_time;

        SelectTeeTimeListViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

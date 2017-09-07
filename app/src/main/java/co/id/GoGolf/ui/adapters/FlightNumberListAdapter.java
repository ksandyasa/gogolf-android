package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import co.id.GoGolf.R;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.FlightNumberListAdapterCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 10/20/16.
 */

public class FlightNumberListAdapter extends RecyclerView.Adapter<FlightNumberListAdapter.FlightNumberViewHolder> {

    private Context context;
    private List<String> flightNumberList;
    private int selectedPosition = 0;
    private FlightNumberListAdapterCallback callback;

    public FlightNumberListAdapter(Context context, List<String> objects, int selectedPosition, FlightNumberListAdapterCallback listener) {
        this.context = context;
        this.flightNumberList = objects;
        this.selectedPosition = selectedPosition;
        this.callback = listener;
    }

    @Override
    public FlightNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_flight_number, parent, false);
        FlightNumberViewHolder holder = new FlightNumberViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FlightNumberViewHolder holder, int position) {
        if (selectedPosition == position) {
            holder.rowTitleFlightNumber.setTextColor(context.getResources().getColor(R.color.black));
        }else {
            holder.rowTitleFlightNumber.setTextColor(context.getResources().getColor(R.color.gray2));
        }
        holder.rowTitleFlightNumber.setText(flightNumberList.get(position));
        holder.rowContainerFlightNumber.setOnClickListener(new SelectedFlightNumber(position));
    }

    @Override
    public int getItemCount() {
        return flightNumberList.size();
    }

    private class SelectedFlightNumber implements View.OnClickListener {

        int mPosition;

        public SelectedFlightNumber(int pos) {
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedPosition);
            selectedPosition = mPosition;
            notifyItemChanged(selectedPosition);
            callback.onSelectedFlightNumber(flightNumberList.get(mPosition));
        }
    }

    static class FlightNumberViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.rowContainerFlightNumber)
        LinearLayout rowContainerFlightNumber;

        @InjectView(R.id.rowTitleFlightNumber)
        CustomTextView rowTitleFlightNumber;

        FlightNumberViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

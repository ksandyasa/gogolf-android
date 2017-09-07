package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import co.id.GoGolf.R;

import co.id.GoGolf.models.response.DataAmenities;
import co.id.GoGolf.ui.CustomTextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 11/15/16.
 */

public class AmenitiesListAdapter extends RecyclerView.Adapter<AmenitiesListAdapter.AmenitiesListViewHolder> {

    private Context context;
    private List<DataAmenities> amenitiesList;

    public AmenitiesListAdapter(Context context, List<DataAmenities> objects) {
        this.context = context;
        this.amenitiesList = objects;
    }

    @Override
    public AmenitiesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_amenities, parent, false);
        AmenitiesListViewHolder holder = new AmenitiesListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AmenitiesListViewHolder holder, int position) {
        holder.ivIconAmenities.setImageResource(this.amenitiesList.get(position).getIcon());
        holder.tvTitleAmenities.setText(this.amenitiesList.get(position).getTitle());
        holder.ivCheckAmenities.setImageResource(this.amenitiesList.get(position).getChecklist());
        if (this.amenitiesList.get(position).getIsAvailable() == 1)
            holder.ivCheckAmenities.setVisibility(View.VISIBLE);
        else
            holder.ivCheckAmenities.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.amenitiesList.size();
    }

    static class AmenitiesListViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ivIconAmenities)
        ImageView ivIconAmenities;

        @InjectView(R.id.tvTitleAmenities)
        CustomTextView tvTitleAmenities;

        @InjectView(R.id.ivCheckAmenities)
        ImageView ivCheckAmenities;

        AmenitiesListViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }
    }
}

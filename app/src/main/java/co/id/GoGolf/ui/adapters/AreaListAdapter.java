package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.DataArea;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.AreaListAdapterCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 10/16/16.
 */

public class AreaListAdapter extends RecyclerView.Adapter<AreaListAdapter.AreaListViewHolder> {

    private Context context;
    private List<DataArea> dataAreaList;
    private int selectedPosition = 0;
    private AreaListAdapterCallback callback;

    public AreaListAdapter(Context context, List<DataArea> objects, int selectedPosition, AreaListAdapterCallback listener) {
        this.context = context;
        this.dataAreaList = objects;
        this.selectedPosition = selectedPosition;
        this.callback = listener;
    }

    @Override
    public AreaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_area_map, parent, false);
        AreaListViewHolder holder = new AreaListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AreaListViewHolder holder, int position) {
        if (selectedPosition == position) {
            holder.row_title_area_map.setTextColor(context.getResources().getColor(R.color.selected_area_map));
        }else {
            holder.row_title_area_map.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.row_title_area_map.setText(dataAreaList.get(position).getArea_name());
        holder.row_container_area_map.setOnClickListener(new ShowAreaMapFromAdapter(position));
    }

    @Override
    public int getItemCount() {
        return dataAreaList.size();
    }

    private class ShowAreaMapFromAdapter implements View.OnClickListener {

        private int pos;

        public ShowAreaMapFromAdapter(int position) {
            this.pos = position;
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedPosition);
            selectedPosition = pos;
            notifyItemChanged(selectedPosition);
            callback.ShowSelectedAreaOnMap(pos);
        }
    }

    static class AreaListViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.row_container_area_map)
        RelativeLayout row_container_area_map;

        @InjectView(R.id.row_title_area_map)
        CustomTextView row_title_area_map;

        AreaListViewHolder(View view){
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.DataMap;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.GolfCourseNameListAdapterCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 10/18/16.
 */

public class GolfCourseNameListAdapter extends RecyclerView.Adapter<GolfCourseNameListAdapter.GolfCourseNameViewHolder> {

    private Context context;
    private List<DataMap> dataMapList;
    private int selectedPosition = 0;
    private GolfCourseNameListAdapterCallback callback;

    public GolfCourseNameListAdapter(Context context, List<DataMap> objects, int selectedPosition, GolfCourseNameListAdapterCallback listener) {
        this.context = context;
        this.dataMapList = objects;
        this.selectedPosition = selectedPosition;
        this.callback = listener;
    }

    @Override
    public GolfCourseNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_area_map, parent, false);
        GolfCourseNameViewHolder holder = new GolfCourseNameViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GolfCourseNameViewHolder holder, int position) {
        if (selectedPosition == position) {
            holder.row_title_area_map.setTextColor(context.getResources().getColor(R.color.selected_area_map));
        }else {
            holder.row_title_area_map.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.row_title_area_map.setText(dataMapList.get(position).getGname());
        holder.row_container_area_map.setOnClickListener(new SelectGolfCourseNameFromAdapter(position));
    }

    @Override
    public int getItemCount() {
        return dataMapList.size();
    }

    private class SelectGolfCourseNameFromAdapter implements View.OnClickListener {

        private int pos;

        public SelectGolfCourseNameFromAdapter(int position) {
            this.pos = position;
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedPosition);
            selectedPosition = pos;
            notifyItemChanged(selectedPosition);
            callback.onSelectedGolfCourseName(pos);
        }
    }

    static class GolfCourseNameViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.row_container_area_map)
        RelativeLayout row_container_area_map;

        @InjectView(R.id.row_title_area_map)
        CustomTextView row_title_area_map;

        GolfCourseNameViewHolder(View view){
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}

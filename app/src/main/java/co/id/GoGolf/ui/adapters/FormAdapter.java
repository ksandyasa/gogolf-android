package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import co.id.GoGolf.R;

import co.id.GoGolf.models.response.prebooking.Conditionarr;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.ui.activities.ActDialogTeeTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dedepradana on 7/29/16.
 */
public class FormAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Conditionarr> conditionarrs;
    private ArrayList<Timearr> timearrs;
    private int size = 0;

    public FormAdapter(Context context, ArrayList<Conditionarr> conditionarrs, ArrayList<Timearr> timearrs, int size) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conditionarrs = conditionarrs;
        this.timearrs = timearrs;
        this.size = size;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.order_formv2, container, false);

        TextView tvFlight = (TextView) itemView.findViewById(R.id.tvFlight);
        TextView tvSelectTeeTime = (TextView) itemView.findViewById(R.id.tvSelectTeeTime);
        final TextView tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        RadioGroup rgCart = (RadioGroup) itemView.findViewById(R.id.rgCart);
        final Spinner spPlayerNumber = (Spinner) itemView.findViewById(R.id.spPlayerNumber);

        tvFlight.setText("Flight " + (position + 1));
        tvSelectTeeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ActDialogTeeTime.class).putExtra("timearr", timearrs).putExtra("pos", position).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        List<String> playerNumber = new ArrayList<>();
        for (int a = Integer.valueOf(conditionarrs.get(Integer.valueOf(timearrs.get(0).getCondition())).getMin_number()); a <= Integer.valueOf(conditionarrs.get(Integer.valueOf(timearrs.get(0).getCondition())).getMax_number()); a++) {
            playerNumber.add(String.valueOf(a));
        }
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(mContext, R.layout.spinner_item, playerNumber);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_drop_down);
        spPlayerNumber.setAdapter(dataAdapter2);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}
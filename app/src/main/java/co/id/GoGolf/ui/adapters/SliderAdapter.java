package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.models.response.home.Toparr;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActPreBookingV3;
import co.id.GoGolf.ui.activities.ActWebView;

import java.util.ArrayList;

/**
 * Created by dedepradana on 7/30/16.
 */
public class SliderAdapter extends PagerAdapter {

    private String TAG = SliderAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Toparr> toparr;

    public SliderAdapter(Context context, ArrayList<Toparr> toparr) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.toparr = toparr;
    }

    @Override
    public int getCount() {
        return toparr.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        Log.d(TAG, toparr.get(position).getTitle());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        CustomTextView textView = (CustomTextView) itemView.findViewById(R.id.textView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toparr.get(position).getEvent().equals("webview")){
                    mContext.startActivity(new Intent(mContext, ActWebView.class).putExtra("url",toparr.get(position).getDestination()));
                } else if (toparr.get(position).getEvent().equals("page")){
                    mContext.startActivity(new Intent(mContext, ActPreBookingV3.class).putExtra("gid", toparr.get(position).getRequest().getData().getGid()).putExtra("date", toparr.get(position).getRequest().getData().getGdate()));
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toparr.get(position).getEvent().equals("webview")){
                    mContext.startActivity(new Intent(mContext, ActWebView.class).putExtra("url",toparr.get(position).getDestination()));
                } else if (toparr.get(position).getEvent().equals("page")){
                    mContext.startActivity(new Intent(mContext, ActPreBookingV3.class).putExtra("gid", toparr.get(position).getRequest().getData().getGid()).putExtra("date", toparr.get(position).getRequest().getData().getGdate()));
                }
            }
        });

        Glide.with(mContext).load(toparr.get(position).getImage()).centerCrop().into(imageView);
        textView.setText(toparr.get(position).getTitle());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

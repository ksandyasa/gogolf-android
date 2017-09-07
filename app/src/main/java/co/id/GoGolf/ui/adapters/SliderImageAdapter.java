package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import co.id.GoGolf.R;

import java.util.ArrayList;

/**
 * Created by prumacadmin on 9/6/16.
 */
public class SliderImageAdapter extends android.support.v4.view.PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Integer> toparr;

    public SliderImageAdapter(Context context, ArrayList<Integer> toparr) {
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
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.tutor_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

//        Glide.with(mContext).load(toparr.get(position).getImage()).into(imageView);

        imageView.setImageResource(toparr.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}

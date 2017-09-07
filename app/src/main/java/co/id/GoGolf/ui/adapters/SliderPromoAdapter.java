package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.models.response.golf.Promotion;
import co.id.GoGolf.ui.CustomTextView;

import java.util.ArrayList;

/**
 * Created by dedepradana on 7/30/16.
 */
public class SliderPromoAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Promotion> promotions;

    public SliderPromoAdapter(Context context, ArrayList<Promotion> promotions) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.promotions = promotions;
    }

    @Override
    public int getCount() {
        return promotions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.promo_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivGolf);
        CustomTextView tvPrice = (CustomTextView) itemView.findViewById(R.id.tvPrice);
        CustomTextView tvPriceDiscount = (CustomTextView) itemView.findViewById(R.id.tvPriceDiscount);
        CustomTextView tvLimit = (CustomTextView) itemView.findViewById(R.id.tvLimit);

        Glide.with(mContext).load(promotions.get(position).getImage()).into(imageView);
        tvPrice.setText("Rp. " + String.format("%,d", Integer.valueOf(promotions.get(position).getPrate())));
        tvPriceDiscount.setText("Rp. " + String.format("%,d", Integer.valueOf(promotions.get(position).getPprice())));
        tvLimit.setText("Rest of limit : "+promotions.get(position).getLimit_num()+ " Flight");
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

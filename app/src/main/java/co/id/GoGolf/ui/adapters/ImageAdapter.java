package co.id.GoGolf.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.models.response.home.Bottomarr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dedepradana on 7/29/16.
 */
public class ImageAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private ArrayList<Bottomarr> bottomarrs;
    private Context mContext;

    public ImageAdapter(Context context, ArrayList<Bottomarr> bottomarrs) {
        mInflater = LayoutInflater.from(context);
        this.bottomarrs = bottomarrs;
        this.mContext = context;

        mItems.add(new Item("Home 1", R.drawable.home_1));
        mItems.add(new Item("Home 2", R.drawable.home_2));
        mItems.add(new Item("Home 3", R.drawable.home_3));
        mItems.add(new Item("Home 4", R.drawable.home_4));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = mInflater.inflate(R.layout.grid_item, viewGroup, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.picture);
//        TextView name;

//        if (v == null) {
//            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
//            v.setTag(R.id.picture, v.findViewById(R.id.picture));
//            v.setTag(R.id.text, v.findViewById(R.id.text));
//        }

//        picture = (ImageView) v.getTag(R.id.picture);
//        name = (TextView) v.getTag(R.id.text);

        Glide.with(mContext).load(bottomarrs.get(i).getImage()).into(imageView);


        Item item = getItem(i);
//
//        imageView.setImageResource(item.drawableId);
//        name.setText(item.name);

        return itemView;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}
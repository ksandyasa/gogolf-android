package co.id.GoGolf.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.id.GoGolf.models.request.TriggerEvent;
import co.id.GoGolf.models.response.prebooking.Conditionarr;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.ui.fragments.FragForm;

import java.util.ArrayList;

/**
 * Created by prumacadmin on 8/18/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int size = 0;
    ArrayList<Conditionarr> conditionarrs;
    ArrayList<Timearr> timearrs;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    TriggerEvent triggerEvent = new TriggerEvent();

    public PagerAdapter(FragmentManager fm, int size, ArrayList<Conditionarr> conditionarrs, ArrayList<Timearr> timearrs) {
        super(fm);
        this.size = size;
        this.conditionarrs = conditionarrs;
        this.timearrs = timearrs;
        triggerEvent.initializeNeedCart(this.size);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Fragment getItem(int position) {
        fragmentArrayList.add(new FragForm(position, conditionarrs, timearrs, triggerEvent));
//        return ArrayListFragment.newInstance(position);
        return fragmentArrayList.get(position);
    }
}

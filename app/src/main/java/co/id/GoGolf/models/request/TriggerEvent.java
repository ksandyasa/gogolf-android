package co.id.GoGolf.models.request;

import java.util.ArrayList;

/**
 * Created by prumacadmin on 8/23/16.
 */
public class TriggerEvent {

    private ArrayList<Boolean> needCart = new ArrayList<>();

    public ArrayList<Boolean> isNeedCart() {
        return needCart;
    }

    public void setNeedCart(boolean value, int posParent) {
        needCart.set(posParent, value);
    }

    public void initializeNeedCart(int size) {
        for (int i = 0; i < size; i++) {
            needCart.add(true);
        }
    }

}

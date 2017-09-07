package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import co.id.GoGolf.R;
import co.id.GoGolf.models.request.Flightarr;
import co.id.GoGolf.models.request.TriggerEvent;
import co.id.GoGolf.models.response.prebooking.Conditionarr;
import co.id.GoGolf.models.response.prebooking.Price_list;
import co.id.GoGolf.models.response.prebooking.Timearr;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActDialogTeeTime;
import co.id.GoGolf.ui.adapters.SelectTeeTimeListAdapter;
import co.id.GoGolf.ui.callback.PlayerTypeCallback;
import co.id.GoGolf.ui.callback.SelectTeeTimeListAdapterCallback;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by prumacadmin on 8/18/16.
 */
public class FragForm extends Fragment implements SelectTeeTimeListAdapterCallback, PlayerTypeCallback {

    RecyclerView rvSelectTeeTime;
    LinearLayoutManager linearLayoutManager;
    SelectTeeTimeListAdapter rvSelectTeeTimeAdapter;

    CustomTextView tvSelectTeeTime;
    CustomTextView tvFlight;
    CustomTextView tvPrice;
    CustomTextView tvCart;
    RadioGroup rgCart;
    Spinner spPlayerNumber;
    LinearLayout llPlayersType;
    LinearLayout llPlayer;
    FragPlayerType fragPlayerType;
    int totalPrice = 0;
    int totalPriceTemp = 0;
    static int realTotalPrice = 0;

    boolean needCart = false;
    boolean needCartOpt = true;

    TriggerEvent triggerEvent;
    int mNum;
    int selectedTeeTime = 0;
    int selectedPlayerType = 0;
    ArrayList<Conditionarr> conditionarrs = new ArrayList<>();
    ArrayList<Timearr> timearrs = new ArrayList<>();
    ArrayList<Timearr> timearrsNew = new ArrayList<>();
    ArrayAdapter<String> playerNumberAdapter;
    ArrayList<String> playerNumber = new ArrayList<>();
    Conditionarr conditionarr;
    HashMap<Integer, Price_list> price_lists = new HashMap<>();
    ArrayList<Price_list> price_values = new ArrayList<>();
    static Set<String> hashSet = new HashSet<>();

    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public FragForm() {

    }

    @SuppressLint("ValidFragment")
    public FragForm(int num, ArrayList<Conditionarr> conditionarrs, ArrayList<Timearr> timearrs, TriggerEvent triggerEvent) {
        this.mNum = num;
        this.conditionarrs = conditionarrs;
        this.timearrs = timearrs;
        this.triggerEvent = triggerEvent;
    }

    public static FragForm newInstance(int num, ArrayList<Conditionarr> conditionarrs, ArrayList<Timearr> timearrs) {
        FragForm f = new FragForm();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putSerializable("cond", conditionarrs);
        args.putSerializable("time", timearrs);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//        conditionarrs = (ArrayList<Conditionarr>) getArguments().getSerializable("cond");
//        timearrs = (ArrayList<Timearr>) getArguments().getSerializable("time");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.order_formv2, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        tvFlight = (CustomTextView) view.findViewById(R.id.tvFlight);
        tvSelectTeeTime = (CustomTextView) view.findViewById(R.id.tvSelectTeeTime);
        rvSelectTeeTime = (RecyclerView) view.findViewById(R.id.rvSelectTeeTime);
        tvPrice = (CustomTextView) view.findViewById(R.id.tvPrice);
        rgCart = (RadioGroup) view.findViewById(R.id.rgCart);
        spPlayerNumber = (Spinner) view.findViewById(R.id.spPlayerNumber);
        tvCart = (CustomTextView) view.findViewById(R.id.tvCart);
        llPlayersType = (LinearLayout) view.findViewById(R.id.llPlayersType);
        llPlayer = (LinearLayout) view.findViewById(R.id.llPlayer);

        Log.d("TAG", "size timeArr " + timearrs.size());

        rgCart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbYes:
                        needCartOpt = true;
                        triggerEvent.setNeedCart(needCartOpt, mNum);
                        calculate();
                        EventBus.getDefault().post(triggerEvent);
                        break;
                    case R.id.rbNo:
                        needCartOpt = false;
                        triggerEvent.setNeedCart(needCartOpt, mNum);
                        calculate();
                        EventBus.getDefault().post(triggerEvent);
                        break;
                }
            }
        });

        tvSelectTeeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActDialogTeeTime.class).putExtra("timearr", timearrs).putExtra("pos", mNum));
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSelectTeeTime.setHasFixedSize(true);
        rvSelectTeeTime.setLayoutManager(linearLayoutManager);

        rvSelectTeeTimeAdapter = new SelectTeeTimeListAdapter(getContext(), timearrs, this, mNum);
        rvSelectTeeTime.setAdapter(rvSelectTeeTimeAdapter);

        playerNumber.clear();

        if (conditionarrs.size() > 0) {
            conditionarr = conditionarrs.get(timearrs.get(selectedTeeTime).getIntCondition());
            if (conditionarr.getPrice_list().size() > 0) {
                totalPrice = conditionarr.getPrice_list().get(selectedPlayerType).getIntPriceCart();

//                map = new HashMap<>();
//                map.put(1, conditionarr.getPrice_list().get(0).getPrice());
//                map.put(2, conditionarr.getPrice_list().get(0).getPrice_cart());
//                map.put(3, conditionarr.getPrice_list().get(0).getCart_mandatory());

            }
            for (int a = Integer.valueOf(conditionarr.getMin_number()); a <= Integer.valueOf(conditionarr.getMax_number()); a++) {
                playerNumber.add(String.valueOf(a));
            }
            resetCart();
            saveTime(timearrs.get(selectedTeeTime).getTtime());
            tvSelectTeeTime.setText(timearrs.get(selectedTeeTime).getTtime());

        }

        playerNumberAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, playerNumber);
        playerNumberAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        spPlayerNumber.setAdapter(playerNumberAdapter);
        spPlayerNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                setupLLPlayer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvFlight.setText("Flight " + (mNum + 1));
//        tvPrice.setText("Rp. " + totalPrice);
        realTotalPrice = totalPrice;

        Log.d(FragForm.class.getSimpleName(), "timearr " + timearrs.size());
        resetllPlayer(timearrs.get(selectedTeeTime));

        price_lists.put(0, conditionarr.getPrice_list().get(selectedPlayerType));

        price_values.clear();
        price_values.addAll(price_lists.values());

        PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_BOOKING + mNum, new Gson().toJson(new Flightarr(spPlayerNumber.getSelectedItem().toString(), useCart(), price_values, timearrs.get(selectedTeeTime).getTtime())));

        EventBus.getDefault().post(triggerEvent);
    }

    private void resetCart() {
        Log.d(FragForm.class.getSimpleName(), "cart_mandatory " + conditionarr.getCart_mandatory());
        if (conditionarr.getCart_mandatory().equals("1")) {
            tvCart.setVisibility(View.VISIBLE);
            rgCart.setVisibility(View.GONE);
        } else {
            if (conditionarr.getPrice_list().get(selectedPlayerType).getCart_mandatory().equals("1")) {
                tvCart.setVisibility(View.VISIBLE);
                rgCart.setVisibility(View.GONE);
            }else{
                tvCart.setVisibility(View.GONE);
                rgCart.setVisibility(View.VISIBLE);
            }
        }
    }

    private String useCart() {
        if (conditionarr.getCart_mandatory().equals("1")) {
            return "1";
        } else {
            if (conditionarr.getPrice_list().get(selectedPlayerType).getCart_mandatory().equals("1")) {
                return "1";
            }else{
                int radioButtonID = rgCart.getCheckedRadioButtonId();
                View radioButton = rgCart.findViewById(radioButtonID);
                int idx = rgCart.indexOfChild(radioButton);

                if (idx == 0) {
                    return "1";
                } else {
                    return "0";
                }
            }
        }
    }

    private void resetllPlayer(Timearr timearr) {
//        if (timearr.getIntPromo() == 1) {
//            llPlayer.setVisibility(View.GONE);
//        } else {
        llPlayer.setVisibility(View.VISIBLE);
//        }
    }

    private void setupLLPlayer() {
        llPlayersType.removeAllViews();
//        int pos = position + 1;

        price_lists.clear();

        for (int o = 0; o < Integer.valueOf(spPlayerNumber.getSelectedItem().toString()); o++) {
            View playerForm = getActivity().getLayoutInflater().inflate(R.layout.item_player_type, null);
            CustomTextView tvPlayerType = (CustomTextView) playerForm.findViewById(R.id.tvPlayerType);
            CustomTextView tvPlayerNo = (CustomTextView) playerForm.findViewById(R.id.tvPlayerNo);
            tvPlayerType.setText(conditionarr.getPrice_list().get(0).getType());
            tvPlayerType.setTag(conditionarr.getPrice_list().get(0).getPrice_cart());
            tvPlayerNo.setText("Player " + (o + 1) + " - Rp. " + new DecimalFormat(formatString, otherSymbols).format(conditionarr.getPrice_list().get(0).getIntPriceCart()));

            final int finalO = o;
            tvPlayerType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragPlayerType = new FragPlayerType(conditionarr.getPrice_list(), finalO, mNum, needCartOpt, selectedPlayerType, FragForm.this);
                    fragPlayerType.show(getChildFragmentManager(), "fragPlayerType");
                    //startActivity(new Intent(getContext(), ActDialogPlayerType.class).putExtra("price_list", conditionarr.getPrice_list()).putExtra("pos", finalO).putExtra("posParent", mNum).putExtra("needCartOpt", needCartOpt));
                }
            });

            price_lists.put(o, conditionarr.getPrice_list().get(0));
//                    price_lists.add(new Price_list(conditionarr.getPrice_list().get(0).getPrice() ,conditionarr.getPrice_list().get(0).getType()));

            llPlayersType.addView(playerForm);
            llPlayersType.requestLayout();
        }

        llPlayer.requestLayout();

        price_values.clear();
        price_values.addAll(price_lists.values());

        totalPriceTemp = 0;

        for (Price_list price_list : price_values){
            totalPriceTemp = totalPriceTemp + price_list.getIntPriceCart();
        }

//                totalPriceTemp = (totalPrice * Integer.valueOf(spPlayerNumber.getSelectedItem().toString()));
        realTotalPrice = totalPriceTemp;

        tvPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(totalPriceTemp));

        needCartOpt = true;
        rgCart.check(R.id.rbYes);

        PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_BOOKING + mNum, new Gson().toJson(new Flightarr(spPlayerNumber.getSelectedItem().toString(), useCart(), price_values, tvSelectTeeTime.getText().toString())));

        EventBus.getDefault().post(triggerEvent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventThread(final Timearr event) {
        Log.d("TAG", "teetime " + tvSelectTeeTime.getText().toString());
        removeTime(tvSelectTeeTime.getText().toString());
        saveTime(event.getTtime());
        tvSelectTeeTime.setText(event.getTtime());
        Log.d("TAG", "teetime " + tvSelectTeeTime.getText().toString());

//        conditionarr = conditionarrs.get(event.getIntCondition());
//
//        playerNumber.clear();
//        for (int a = Integer.valueOf(conditionarr.getMin_number()); a <= Integer.valueOf(conditionarr.getMax_number()); a++) {
//            playerNumber.add(String.valueOf(a));
//        }
//
//        playerNumberAdapter.notifyDataSetChanged();
//
//        if (conditionarr.getPrice_list().size() > 0) {
//            totalPrice = conditionarr.getPrice_list().get(0).getIntPriceCart();
//        }
//
//        int a = Integer.valueOf(spPlayerNumber.getSelectedItem().toString());
//
//        price_lists.clear();
//
//        for (int o = 0; o < a; o++) {
//            View view = llPlayersType.getChildAt(o);
//            CustomTextView tvPlayerType = (CustomTextView) view.findViewById(R.id.tvPlayerType);
//            tvPlayerType.setText(conditionarr.getPrice_list().get(0).getType());
//            tvPlayerType.setTag(conditionarr.getPrice_list().get(0).getPrice_cart());
//            ((CustomTextView) view.findViewById(R.id.tvPlayerNo)).setText("Player " + (o + 1) + " - Rp. " + String.format("%,d", conditionarr.getPrice_list().get(0).getIntPriceCart()));
//            price_lists.put(o, conditionarr.getPrice_list().get(0));
////                price_lists.add(new Price_list(conditionarr.getPrice_list().get(0).getPrice(), conditionarr.getPrice_list().get(0).getType()));
//        }
//
//        totalPriceTemp = 0;
//        totalPriceTemp = (totalPrice * Integer.valueOf(spPlayerNumber.getSelectedItem().toString()));
//        realTotalPrice = totalPriceTemp;
//        tvPrice.setText("Rp. " + String.format("%,d", totalPriceTemp));
//
//        resetllPlayer(event);
//
//        price_values.clear();
//        price_values.addAll(price_lists.values());
//
//        PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_BOOKING + mNum, new Gson().toJson(new Flightarr(spPlayerNumber.getSelectedItem().toString(), useCart(), price_values, event.getTtime())));
//
        calculate();

        EventBus.getDefault().post(triggerEvent);
    }

    private synchronized void saveTime(String teaTime) {
        hashSet.add(teaTime);
        PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_TEA_TIME, hashSet);
    }

    private synchronized void removeTime(String teaTime) {
        hashSet.remove(teaTime);
        PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_TEA_TIME, hashSet);
    }

    @Subscribe
    public void onEventThread(Price_list event) {
        if (event.getPosParent() == selectedTeeTime) {
            Log.d("TAG", "pos parent " + event.getPosParent());
            Log.d("TAG", "pos " + event.getPos());
            View view = llPlayersType.getChildAt(event.getPos());
            needCart = false;
//            ((TextView) view.findViewById(R.id.tvPlayerType)).setText(event.getType());
            CustomTextView tvPlayerType = (CustomTextView) view.findViewById(R.id.tvPlayerType);
            tvPlayerType.setText(event.getType());
//            map = new HashMap<>();
//            map.put(1, event.getPrice());
//            map.put(2, event.getPrice_cart());
//            map.put(3, event.getCart_mandatory());
//            tvPlayerType.setTag(1, event.getPrice());
//            tvPlayerType.setTag(2, event.getPrice_cart());
//            tvPlayerType.setTag(3, event.getCart_mandatory());
            ((CustomTextView) view.findViewById(R.id.tvPlayerNo)).setText("Player " + (event.getPos() + 1) + " - Rp. " + new DecimalFormat(formatString, otherSymbols).format(event.getIntPriceCart()));

//            price_lists.clear();

//            for (int x = 0; x < llPlayersType.getChildCount(); x++) {
//                View view2 = llPlayersType.getChildAt(x);
//                TextView tvpt = (TextView) view2.findViewById(R.id.tvPlayerType);
//
//            }

            price_lists.put(event.getPos(), event);

            for (int d=0; d<price_lists.size(); d++){
                if (price_lists.get(d).getCart_mandatory().equals("1")) {
                    needCart = true;
                    break;
                }
            }

//            for (Price_list a : price_lists) {
//                if (a.getCart_mandatory().equals("1")) {
//                    needCart = true;
//                    break;
//                }
//            }

            calculate();

            EventBus.getDefault().post(triggerEvent);
        }
    }

    private void compareSelectedTeeTimeWithSystem(String selectedTeeTime) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String[] teeTimeArray = selectedTeeTime.split(":");
    }

    private void calculate() {
        totalPriceTemp = 0;

        price_values.clear();
        price_values.addAll(price_lists.values());

        if (needCart) {

            for (int x = 0; x < llPlayersType.getChildCount(); x++) {
                totalPriceTemp = totalPriceTemp + price_lists.get(x).getIntPriceCart();
            }

            tvCart.setVisibility(View.VISIBLE);
            rgCart.setVisibility(View.GONE);
            PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_BOOKING + mNum, new Gson().toJson(new Flightarr(spPlayerNumber.getSelectedItem().toString(), "1", price_values, tvSelectTeeTime.getText().toString())));
        } else {

            if (needCartOpt) {
                for (int x = 0; x < llPlayersType.getChildCount(); x++) {
                    View view = llPlayersType.getChildAt(x);
                    CustomTextView tvPlayerType = (CustomTextView) view.findViewById(R.id.tvPlayerType);
                    tvPlayerType.setText(price_lists.get(x).getType());
                    ((CustomTextView) view.findViewById(R.id.tvPlayerNo)).setText("Player " + (x + 1) + " - Rp. " + new DecimalFormat(formatString, otherSymbols).format(price_lists.get(x).getIntPriceCart()));
                    totalPriceTemp = totalPriceTemp + price_lists.get(x).getIntPriceCart();
                    final int finalO = x;
                    tvPlayerType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragPlayerType = new FragPlayerType(conditionarr.getPrice_list(), finalO, mNum, needCartOpt, selectedPlayerType, FragForm.this);
                            fragPlayerType.show(getChildFragmentManager(), "fragPlayerType");
                            //startActivity(new Intent(getContext(), ActDialogPlayerType.class).putExtra("price_list", conditionarr.getPrice_list()).putExtra("pos", finalO).putExtra("posParent", mNum).putExtra("needCartOpt", needCartOpt));
                        }
                    });

                }
            } else {
                for (int x = 0; x < llPlayersType.getChildCount(); x++) {
                    View view = llPlayersType.getChildAt(x);
                    TextView tvPlayerType = (TextView) view.findViewById(R.id.tvPlayerType);
                    tvPlayerType.setText(price_lists.get(x).getType());
                    ((TextView) view.findViewById(R.id.tvPlayerNo)).setText("Player " + (x + 1) + " - Rp. " + new DecimalFormat(formatString, otherSymbols).format(price_lists.get(x).getIntPrice()));
                    totalPriceTemp = totalPriceTemp + price_lists.get(x).getIntPrice();
                    final int finalO = x;
                    tvPlayerType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragPlayerType = new FragPlayerType(conditionarr.getPrice_list(), finalO, mNum, needCartOpt, selectedPlayerType, FragForm.this);
                            fragPlayerType.show(getChildFragmentManager(), "fragPlayerType");
                            //startActivity(new Intent(getContext(), ActDialogPlayerType.class).putExtra("price_list", conditionarr.getPrice_list()).putExtra("pos", finalO).putExtra("posParent", mNum).putExtra("needCartOpt", needCartOpt));
                        }
                    });
                }
            }

            Log.d("TAG", "totalPriceTemp " + mNum + " " + totalPriceTemp);

            tvCart.setVisibility(View.GONE);
            rgCart.setVisibility(View.VISIBLE);
            PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_BOOKING + mNum, new Gson().toJson(new Flightarr(spPlayerNumber.getSelectedItem().toString(), useCart(), price_values, tvSelectTeeTime.getText().toString())));
        }

        realTotalPrice = totalPriceTemp;
        tvPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(totalPriceTemp));
    }

    @Override
    public void SelectedTeeTime(int position) {
        EventBus.getDefault().post(timearrs.get(position));
    }

    @Override
    public void ObtainPlayerType(int position) {
        selectedPlayerType = position;
    }
}

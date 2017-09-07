package co.id.GoGolf.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.MapEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.activities.ActGolfDetail;
import co.id.GoGolf.ui.adapters.AreaListAdapter;
import co.id.GoGolf.ui.callback.AreaListAdapterCallback;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by dedepradana on 6/2/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragMap extends Fragment implements GoogleMap.OnInfoWindowClickListener, AreaListAdapterCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.map)
    MapView mapView;

    @InjectView(R.id.rvFooterListMap)
    RecyclerView rvFooterListMap;

    @InjectView(R.id.fabSearchMap)
    FloatingActionButton fabSearchMap;

    LinearLayoutManager linearLayoutManager;

    AreaListAdapter areaListAdapter;

    GoogleMap map;

    CameraUpdate cameraUpdate;

    User user;

    AreaEvent areaEvent;

    private HashMap<String, String> hashMap = new HashMap<>();

    private int selectedArea = 8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_map, container, false);

        ButterKnife.inject(this, rootView);
        DaggerInjection.get().inject(this);

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getActivity(), PreferenceUtility.SF_USER_DATA), User.class);

        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }else{
            map.setMyLocationEnabled(true);
        }
        map.setOnInfoWindowClickListener(this);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        mainPresenter.postArea("1", "en");

        return rootView;
    }

    @OnClick(R.id.fabSearchMap)
    public void onSearchMap() {

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Subscribe
    public void onEventThread(AreaEvent event) {
        Log.d("TAG", "size area " + event.getData().size());
        areaEvent = event;
        for (int i = 0; i < areaEvent.getData().size(); i++) {
            if (areaEvent.getData().get(i).getArea_id().equals("1")) {
                selectedArea = i;
            }
        }

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvFooterListMap.setHasFixedSize(true);
        rvFooterListMap.setLayoutManager(linearLayoutManager);

        areaListAdapter = new AreaListAdapter(getActivity(), areaEvent.getData(), selectedArea, this);
        rvFooterListMap.setAdapter(areaListAdapter);
        linearLayoutManager.scrollToPositionWithOffset(selectedArea, 240);

        // Updates the location and zoom of the MapView
        mainPresenter.getMap(PreferenceUtility.getInstance().loadDataString(getActivity(), PreferenceUtility.ACCESS_TOKEN),
                areaEvent.getData().get(selectedArea).getArea_id(),
                "en");

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(areaEvent.getData().get(selectedArea).getLat()),
                Double.parseDouble(areaEvent.getData().get(selectedArea).getLang())), 9);
        map.animateCamera(cameraUpdate);
    }

    @Subscribe
    public void onEventThread(MapEvent event) {
        hashMap.clear();
        if (event.getData().size() > 0) {
            for (int i = 0; i < event.getData().size(); i++) {
                LatLng latLng = new LatLng(Double.parseDouble(event.getData().get(i).getLat()),
                        Double.parseDouble(event.getData().get(i).getIng()));
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(event.getData().get(i).getGname()))
                        .setIcon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.ic_marker)));
                hashMap.put(event.getData().get(i).getGname(), event.getData().get(i).getGid());
            }
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        Toast.makeText(getActivity(), "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        startActivity(new Intent(getContext(), ActGolfDetail.class).putExtra("gid", hashMap.get(marker.getTitle())).putExtra("date", ""));
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void ShowSelectedAreaOnMap(int position) {
        selectedArea = position;
        mainPresenter.getMap(PreferenceUtility.getInstance().loadDataString(getActivity(), PreferenceUtility.ACCESS_TOKEN),
                areaEvent.getData().get(selectedArea).getArea_id(),
                "en");
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(areaEvent.getData().get(selectedArea).getLat()),
                Double.parseDouble(areaEvent.getData().get(selectedArea).getLang())), 10);
        map.animateCamera(cameraUpdate);
    }
}

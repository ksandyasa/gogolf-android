package co.id.GoGolf.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.GolfDetailEvent;
import co.id.GoGolf.models.response.DataAmenities;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.adapters.AmenitiesListAdapter;
import co.id.GoGolf.ui.adapters.SliderPromoAdapter;
import co.id.GoGolf.ui.presenters.MainPresenter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 8/12/16.
 */
public class ActGolfDetail extends BaseAct implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private boolean mIsTheTitleVisible          = false;

    @Inject
    MainPresenter mainPresenter;
    private Context context;

    @InjectView(R.id.ablGolfDetail)
    AppBarLayout ablGolfDetail;

    @InjectView(R.id.map)
    MapView mapView;

    @InjectView(R.id.tvGolfName)
    CustomTextView tvGolfName;

    @InjectView(R.id.tvAddress)
    CustomTextView tvAddress;

    @InjectView(R.id.tvTextAbout)
    CustomTextView tvTextAbout;

    @InjectView(R.id.sl_image)
    SliderLayout mSliderLayout;

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.tvTitleGolfDetail)
    CustomTextView tvTitleGolfDetail;

    @InjectView(R.id.rlBook)
    LinearLayout rlBook;

    @InjectView(R.id.tvVOpenTime)
    CustomTextView tvVOpenTime;

    @InjectView(R.id.tvVCloseTime)
    CustomTextView tvVCloseTime;

    @InjectView(R.id.tvVCloseDate)
    CustomTextView tvVCloseDate;

    @InjectView(R.id.tvVHoles)
    CustomTextView tvVHoles;

    @InjectView(R.id.tvVPar)
    CustomTextView tvVPar;

    @InjectView(R.id.tvVLength)
    CustomTextView tvVLength;

    @InjectView(R.id.tvVEstin)
    CustomTextView tvVEstin;

    @InjectView(R.id.tvVDesigner)
    CustomTextView tvVDesigner;

    @InjectView(R.id.rvAmenities)
    RecyclerView rvAmenities;

    GridLayoutManager gridLayoutManager;
    AmenitiesListAdapter amenitiesListAdapter;

    GoogleMap map;
    List<DataAmenities> amenitiesList = new ArrayList<>();
    private String gid = "";
    private String mTitle = "";
    private SliderPromoAdapter sliderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_golf_detail);

        initToolbar("", null);

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        gid = getIntent().getStringExtra("gid");

        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-6.316282, 106.787142), 10);
        map.animateCamera(cameraUpdate);

        showLoadingDialog();

        mainPresenter.postGolfDetail(gid, "en");

        ablGolfDetail.addOnOffsetChangedListener(this);

        startAlphaAnimation(this.tvTitleGolfDetail, 0, View.INVISIBLE);

    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
    }


    @Subscribe
    public void onEventThread(GolfDetailEvent event) {
        dismissLoadingDialog();
        sliderAdapter = new SliderPromoAdapter(getApplicationContext(), event.getData().getPromotion());
        pager.setAdapter(sliderAdapter);

        initUI(event);
        initSlider(event);
        initAmenitiesUI(event);
    }

    private void initUI(GolfDetailEvent event) {
        String[] openTimeArray = event.getData().getOpen().split(":");
        String[] closeTimeArray = event.getData().getClose().split(":");
        String openTime = openTimeArray[0] + ":" + openTimeArray[1];
        String closeTime = closeTimeArray[1] + ":" + closeTimeArray[1];

        mTitle = event.getData().getGname();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(event.getData().getLat()), Double.valueOf(event.getData().getIng())), 10);
        map.animateCamera(cameraUpdate);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(event.getData().getLat()), Double.valueOf(event.getData().getIng()))))
                .setIcon(getMarkerIconFromDrawable(getResources().getDrawable(R.drawable.ic_marker)));

        tvGolfName.setText(event.getData().getGname());
        tvAddress.setText(event.getData().getAddress());
        tvTextAbout.setText(event.getData().getInfo());
        tvTitleGolfDetail.setText(event.getData().getGname());
        tvVOpenTime.setText(openTime);
        tvVCloseTime.setText(closeTime);
        tvVCloseDate.setText(event.getData().getClosedate());
        tvVHoles.setText(event.getData().getHoles() + " Holes");
        tvVPar.setTag(event.getData().getPar() + " Yards");
        tvVLength.setText(event.getData().getLength() + " Yards");
        tvVEstin.setText(event.getData().getEstablish());
        tvVDesigner.setText(event.getData().getDesigner());
    }

    private void initSlider(GolfDetailEvent event) {

//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
//
//        for(String name : url_maps.keySet()){
//            TextSliderView textSliderView = new TextSliderView(this);
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//
//            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//
//            mSliderLayout.addSlider(textSliderView);
//        }

        for (int i = 0; i < event.getData().getImagearr().size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getApplicationContext());
            // initialize a SliderLayout
            textSliderView
//                    .description(name)
                    .image(event.getData().getImagearr().get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", "");

            mSliderLayout.addSlider(textSliderView);
        }

        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setBackgroundColor(getResources().getColor(R.color.white));
//        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(3000);
    }

    private void initAmenitiesUI(GolfDetailEvent event) {
        if (event.getData().getCarts().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.golfcart, "Golf Cart", R.drawable.checklist, (event.getData().getCarts().equals("1")) ? 1 : 0));
        if (event.getData().getShoe_rental().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.shoerental, "Shoe Rental", R.drawable.checklist, (event.getData().getShoe_rental().equals("1")) ? 1 : 0));
        if (event.getData().getCaddy().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.caddy, "Caddy", R.drawable.checklist, (event.getData().getCaddy().equals("1")) ? 1 : 0));
        if (event.getData().getLocker_room().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.lockerroom, "Locker Room", R.drawable.checklist, (event.getData().getLocker_room().equals("1")) ? 1 : 0));
        if (event.getData().getPractice_facility().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.practicefacility, "Practice Facility", R.drawable.checklist, (event.getData().getPractice_facility().equals("1")) ? 1 : 0));
        if (event.getData().getNight_golf().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.nightgolf, "Night Golf", R.drawable.checklist, (event.getData().getNight_golf().equals("1")) ? 1 : 0));
        if (event.getData().getLesson().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.lesson, "Lesson", R.drawable.checklist, (event.getData().getLesson().equals("1")) ? 1 : 0));
        if (event.getData().getGolf_shop().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.golfshop, "Golf Shop", R.drawable.checklist, (event.getData().getGolf_shop().equals("1")) ? 1 : 0));
        if (event.getData().getRestaurant().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.restaurant, "Restaurant", R.drawable.checklist, (event.getData().getRestaurant().equals("1")) ? 1 : 0));
        if (event.getData().getAccomodation().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.accomodation, "Accomodation", R.drawable.checklist, (event.getData().getAccomodation().equals("1")) ? 1 : 0));
        if (event.getData().getSpa().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.spa, "Spa", R.drawable.checklist, (event.getData().getSpa().equals("1")) ? 1 : 0));
        if (event.getData().getHealth_club().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.healthclub, "Health Club", R.drawable.checklist, (event.getData().getHealth_club().equals("1")) ? 1 : 0));
        if (event.getData().getClub_rental().equals("1"))
            amenitiesList.add(new DataAmenities(R.drawable.clubrental, "Club Rental", R.drawable.checklist, (event.getData().getClub_rental().equals("1")) ? 1 : 0));

        gridLayoutManager = new GridLayoutManager(ActGolfDetail.this, 2);
        rvAmenities.setHasFixedSize(true);
        rvAmenities.setLayoutManager(gridLayoutManager);

        amenitiesListAdapter = new AmenitiesListAdapter(ActGolfDetail.this, amenitiesList);
        rvAmenities.setAdapter(amenitiesListAdapter);
    }

    @Override
    public void onResume() {
        mapView.onResume();
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
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleToolbarTitleVisibility(percentage);

    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(this.tvTitleGolfDetail, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(tvTitleGolfDetail, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.rlBook)
    public void onPreBookingPage() {
        startActivityForResult(new Intent(context, ActPreBookingV3.class).putExtra("gid", gid).putExtra("date", ""), 22);
    }

}

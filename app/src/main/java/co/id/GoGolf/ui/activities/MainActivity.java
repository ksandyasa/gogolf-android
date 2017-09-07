package co.id.GoGolf.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.id.GoGolf.Config;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.VerifyEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.fragments.FragHome;
import co.id.GoGolf.ui.fragments.FragInputDialog;
import co.id.GoGolf.ui.fragments.FragSuccessDialog;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;
import com.makeramen.RoundedImageView;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseAct
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tvName)
    TextView tvName;

    @InjectView(R.id.tvCountry)
    TextView tvCountry;

    @InjectView(R.id.llBookStatus)
    LinearLayout llBookStatus;

    @InjectView(R.id.llBookHistory)
    LinearLayout llBookHistory;

    @InjectView(R.id.llPromotionCode)
    LinearLayout llPromotionCode;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    private Fragment defaultFragment;

    @InjectView(R.id.imageView)
    RoundedImageView imageView;

    private Context context;
    private User user;
    private FragInputDialog fragInputDialog;
    private ImageView notifBadge;
    private CustomTextView notifCount;
    private int mNotifCount = 0;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if (intent != null) {
                Log.d("TAG", intentToString(getIntent()));
                mNotifCount++;
                setNotifCount(mNotifCount);

//                if (intent.getStringExtra("title") != null) {
//                    if (intent.getStringExtra("title").equals("Point Rewarded")) {
//                    }
//                }
//
//                if (intent.getStringExtra("destination") != null && intent.getStringExtra("event") != null) {
//                    if (intent.getStringExtra("destination").equals("weekend") && intent.getStringExtra("event").equals("page")) {
//                    }
//                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("push_notifications"));

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);
        context = getApplicationContext();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initToolbar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        defaultFragment = new FragHome();

        //tvName.setText(user.getFname()+" "+user.getLname());
        //tvCountry.setText(user.getCountry_name());

        mainPresenter.postArea(user.getCountry_id(), "en");

        //Glide.with(this).load("http://zblogged.com/wp-content/uploads/2015/11/17.jpg").into(imageView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, defaultFragment).commit();

        Log.d("TAG", PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.DESTINATION));
        Log.d("TAG", PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.EVENT));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Log.d("TAG", intentToString(intent));
        }

        if (intent.getExtras() != null) {

            if (intent.getExtras().get("destination") != null && intent.getExtras().get("event") != null) {
                if (intent.getExtras().get("destination").toString().equals("weekend") && intent.getExtras().get("event").toString().equals("page")) {
                    if (defaultFragment != null) {
                        ((FragHome)defaultFragment).openWeekendFromMain();
                    }
                }

                if (intent.getExtras().get("destination").toString().equals("point") && intent.getExtras().get("event").toString().equals("tab")) {
                    ((FragHome)defaultFragment).showPointPageFromHome();
                }

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA).equals("")) {
            user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);
            //tvName.setText(user.getFname()+" "+user.getLname());
            //tvCountry.setText(user.getCountry_name());
        }
    }

    private void initToolbar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.MY_PROFILE_REQUEST && data != null) {
            MainActivity.this.finish();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_notification);
        View count = MenuItemCompat.getActionView(item);
        notifBadge = (ImageView) count.findViewById(R.id.ivNotifBadge);
        notifCount = (CustomTextView) count.findViewById(R.id.tvNotifCount);

        notifBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotifCount = 0;
                notifCount.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(), ActNotification.class));
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button      , so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            startActivity(new Intent(getApplicationContext(), ActNotification.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeFragment(Fragment fragment) {
        defaultFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, defaultFragment).commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        dismissLoadingDialog();
    }

    @Subscribe
    public void onEventThread(AreaEvent event) {
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_AREA_DATA, new Gson().toJson(event));
    }

    @Subscribe
    public void onEventThread(VerifyEvent event) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), "Verification success! You Got "+ event.getData().getPo_transaction() +" points in your account", Toast.LENGTH_LONG).show();
        fragInputDialog.dismiss();
        ((FragHome)defaultFragment).refreshPointPageFromHome();
    }

    @OnClick(R.id.llBookHistory)
    public void onClickBookHis() {
//        defaultFragment = new FragBookHis();
//        changeFragment(defaultFragment);
        startActivity(new Intent(getApplicationContext(), ActBookHis.class));
    }

    @OnClick(R.id.llPromotionCode)
    public void onClickPromotionCode() {
        showInputDialog();
    }

    public void showSuccessDialog(String messageSuccess) {
        FragSuccessDialog fragSuccessDialog = new FragSuccessDialog(MainActivity.this, messageSuccess);
        fragSuccessDialog.show(getSupportFragmentManager(), "fragSuccessDialog");
    }

    private void showInputDialog() {
        fragInputDialog = new FragInputDialog(MainActivity.this);
        fragInputDialog.show(getSupportFragmentManager(), "fragInputDialog");
//        LayoutInflater li = getLayoutInflater();
//        View promptsView = li.inflate(R.layout.dialog_input, null);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                MainActivity.this);
//
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.etInput);
//
//        alertDialogBuilder
//                .setTitle("Please insert your Promotion Code")
//                .setCancelable(true)
//                .setPositiveButton("Verify",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                String code = userInput.getText().toString();
//                                if (code.trim().length() < 1) {
//                                    quickToast("Please input your email address");
//                                } else {
////                                    oAuthPresenter.postForgotApi(email);
//                                    mainPresenter.postVerify(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), code, "id");
//                                    showLoadingDialog();
//                                }
//                            }
//                        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        alertDialog.show();
    }

    @OnClick(R.id.llLogout)
    public void onClickLogout() {
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, "");
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.ACCESS_TOKEN, "");
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.PASSWORD, "");
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.COUNTRY_ID, "");
        MainActivity.this.finish();
        startActivity(new Intent(MainActivity.this, ActLogin.class));
    }

    @OnClick(R.id.llBookStatus)
    public void onClickBookStat() {
//        defaultFragment = new FragBookStat();
//        changeFragment(defaultFragment);
        startActivityForResult(new Intent(getApplicationContext(), ActBookStat.class), Config.BOOK_STATUS);
    }

    @OnClick(R.id.llMyProfile)
    public void onClickProfile(){
        startActivityForResult(new Intent(getApplicationContext(), ActMyProfile.class), Config.MY_PROFILE_REQUEST);
    }

    @OnClick(R.id.llTutorial)
    public void onClickTutor(){
        startActivity(new Intent(getApplicationContext(), ActTutorial.class));
    }

    private void setNotifCount(int count) {
        Log.d("TAG", "notifCount " + count);
        if (count > 0)
            notifCount.setVisibility(View.VISIBLE);
        notifCount.setText(String.valueOf(count));
        //invalidateOptionsMenu();
    }

    public static String intentToString(Intent intent) {
        if (intent == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder("action: ")
                .append(intent.getAction())
                .append(" data: ")
                .append(intent.getDataString())
                .append(" extras: ")
                ;

        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet())
                stringBuilder.append(key).append("=").append(intent.getExtras().get(key)).append(" ");
        }

        return stringBuilder.toString();

    }

}

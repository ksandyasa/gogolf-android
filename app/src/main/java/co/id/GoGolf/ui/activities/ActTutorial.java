package co.id.GoGolf.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import co.id.GoGolf.R;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.CustomVP;
import co.id.GoGolf.ui.adapters.SliderImageAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by prumacadmin on 9/5/16.
 */
public class ActTutorial extends BaseAct {


    @InjectView(R.id.pager)
    CustomVP pager;
    @InjectView(R.id.indicator)
    CircleIndicator indicator;
    @InjectView(R.id.ivNext)
    ImageView ivNext;
    @InjectView(R.id.ivIcon)
    ImageView ivIcon;
    @InjectView(R.id.tvTutorDesc)
    CustomTextView tvTutorDesc;
    @InjectView(R.id.tvTitle)
    CustomTextView tvTitle;
    @InjectView(R.id.tvSkip)
    CustomTextView tvSkip;
    @InjectView(R.id.llAll)
    LinearLayout llAll;

    SliderImageAdapter sliderImageAdapter;

    int position = 0;

    ArrayList<Integer> list = new ArrayList<>();

    private String first_install = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tutor);

        if (getIntent().getStringExtra("first_install") != null) {
            first_install = getIntent().getStringExtra("first_install");
        }

        Log.d("TAG", "first_install " + first_install);

        ButterKnife.inject(this);

        list.add(R.drawable.golf1);
        list.add(R.drawable.golf2);
        list.add(R.drawable.golf3);
        list.add(R.drawable.golf4);

        sliderImageAdapter = new SliderImageAdapter(getApplicationContext(), list);
        pager.setAdapter(sliderImageAdapter);
        indicator.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                position = pos;
                if (position == 3) {
                    ivNext.setVisibility(View.GONE);
                    tvSkip.setText(getResources().getString(R.string.tr_done));
                } else {
                    tvSkip.setText(getResources().getString(R.string.tr_skip));
                    ivNext.setVisibility(View.VISIBLE);
                }

                switch (pos) {
                    case 0:
                        tvTutorDesc.setText(getResources().getString(R.string.tr_desc1));
                        ivIcon.setImageResource(R.drawable.golf1ic);
                        llAll.setBackgroundColor(Color.parseColor("#7ED321"));
                        tvTitle.setText(getResources().getString(R.string.tr_title1));
                        break;
                    case 1:
                        tvTutorDesc.setText(getResources().getString(R.string.tr_desc2));
                        ivIcon.setImageResource(R.drawable.golf2ic);
                        llAll.setBackgroundColor(Color.parseColor("#F5A623"));
                        tvTitle.setText(getResources().getString(R.string.tr_title2));
                        break;
                    case 2:
                        tvTutorDesc.setText(getResources().getString(R.string.tr_desc3));
                        ivIcon.setImageResource(R.drawable.golf3ic);
                        llAll.setBackgroundColor(Color.parseColor("#FF5252"));
                        tvTitle.setText(getResources().getString(R.string.tr_title3));
                        break;
                    case 3:
                        tvTutorDesc.setText(getResources().getString(R.string.tr_desc4));
                        ivIcon.setImageResource(R.drawable.golf4ic);
                        llAll.setBackgroundColor(Color.parseColor("#06BEBD"));
                        tvTitle.setText(getResources().getString(R.string.tr_title4));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {

    }

    @OnClick(R.id.ivNext)
    public void onClickNext() {
        pager.setCurrentItem(position + 1);
    }

    @OnClick(R.id.tvSkip)
    public void onClickSkip() {
        if (first_install.equals("")) {
            startActivity(new Intent(ActTutorial.this, ActLogin.class));
        }
        ActTutorial.this.finish();
    }

}

package co.id.GoGolf.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import co.id.GoGolf.R;
//import com.felipecsl.gifimageview.library.GifImageView;

/**
 * Created by prumacadmin on 8/29/16.
 */
public class LoadingImage extends AppCompatActivity {

//    GifImageView gifImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

//        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
//
//        gifImageView.startAnimation();
    }
}

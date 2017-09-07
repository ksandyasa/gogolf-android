package co.id.GoGolf.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import co.id.GoGolf.R;
import co.id.GoGolf.events.ErrorEvent;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by prumacadmin on 9/27/16.
 */
public class ActWebView extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);

        initToolbar("", null);

        WebView webView = (WebView) findViewById(R.id.webview);

        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
    }

}

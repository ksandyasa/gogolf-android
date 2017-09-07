package co.id.GoGolf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import co.id.GoGolf.R;

/**
 * Created by dedepradana on 6/6/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
    }
}

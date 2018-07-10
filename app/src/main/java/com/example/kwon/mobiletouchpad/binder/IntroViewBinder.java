package com.example.kwon.mobiletouchpad.binder;

import android.view.View;

import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.view.IntroView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class IntroViewBinder {
    private View introView;
    private final MainActivity MAIN_ACTIVITY;

    public IntroViewBinder(MainActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        bind();
    }

    public View bindedView() {
        return introView;
    }

    private void bind() {
        introView = new IntroView(MAIN_ACTIVITY);
        final View PORT_SETTING_VIEW = new PortSettingViewBinder(MAIN_ACTIVITY).bindedView();
        ((IntroView)introView).setWifiButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.changeView(PORT_SETTING_VIEW, true);}
        });
        ((IntroView)introView).setBluetoothButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ((IntroView)introView).setQuitButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.onBackPressed();}
        });
    }
}

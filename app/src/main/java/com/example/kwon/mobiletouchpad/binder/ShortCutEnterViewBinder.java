package com.example.kwon.mobiletouchpad.binder;

import android.view.View;

import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.network.Sender;
import com.example.kwon.mobiletouchpad.view.ShortCutEnterView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class ShortCutEnterViewBinder {
    private final MainActivity MAIN_ACTIVITY;
    private View shortCutEnterView;
    private WindowShortCutViewBinder windowShortCutViewBinder;
    private PptShortCutViewBinder pptShortCutViewBinder;

    public ShortCutEnterViewBinder(MainActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        bind();
    }

    public void setSender(Sender sender) {
        windowShortCutViewBinder.setSender(sender);
        pptShortCutViewBinder.setSender(sender);
    }

    public View bindedView() {
        return shortCutEnterView;
    }

    private void bind() {
        shortCutEnterView = new ShortCutEnterView(MAIN_ACTIVITY);
        windowShortCutViewBinder = new WindowShortCutViewBinder(MAIN_ACTIVITY);
        pptShortCutViewBinder = new PptShortCutViewBinder(MAIN_ACTIVITY);
        ((ShortCutEnterView)shortCutEnterView).setWindowButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.changeView(windowShortCutViewBinder.bindedView(), false);}
        });
        ((ShortCutEnterView)shortCutEnterView).setPptButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.changeView(pptShortCutViewBinder.bindedView(), false);}
        });
        ((ShortCutEnterView)shortCutEnterView).setCustomButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

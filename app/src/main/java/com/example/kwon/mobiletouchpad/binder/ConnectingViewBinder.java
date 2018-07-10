package com.example.kwon.mobiletouchpad.binder;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.kwon.mobiletouchpad.LoadingViewAnimator;
import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.connect.Connector;
import com.example.kwon.mobiletouchpad.view.ConnectingView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class ConnectingViewBinder {
    private final MainActivity MAIN_ACTIVITY;
    private Connector connector;
    private final MousePadViewBinder MOUSE_PAD_VIEW_BINDER;

    private View connectingView;
    private LoadingViewAnimator loadingViewAnimator;
    private boolean isConnecting;

    public ConnectingViewBinder(MainActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        MOUSE_PAD_VIEW_BINDER = new MousePadViewBinder(mainActivity);
        bind();
    }

    public ConnectingViewBinder setConnector(Connector connector) {
        this.connector = connector;
        return this;
    }

    public View bindedView() {
        return connectingView;
    }

    private void bind() {
        connectingView = new ConnectingView(MAIN_ACTIVITY);
        loadingViewAnimator = new LoadingViewAnimator(((ConnectingView)connectingView).getLoadingView());
        MOUSE_PAD_VIEW_BINDER.setReconnectingView(connectingView);

        connectingView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {startConnecting();}
            @Override
            public void onViewDetachedFromWindow(View v) {finishConnecting();}
        });
        ((ConnectingView)connectingView).setCancelButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.onBackPressed();}
        });
    }

    private AlertDialog alertDialog;
    private void alertConnectionFailed() {
        if(alertDialog==null) {
            alertDialog = new AlertDialog.Builder(MAIN_ACTIVITY).setTitle("오류")
                    .setMessage("연결에 실패하였습니다. 연결 상태를 확인해주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {dialog.dismiss();}
                    }).create();
        }
        MAIN_ACTIVITY.onBackPressed();
        alertDialog.show();
    }

    private void startConnecting() {
        isConnecting = true;
        loadingViewAnimator.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    MOUSE_PAD_VIEW_BINDER.setSender(connector.connect());
                    isConnecting = false;
                    new Handler(MAIN_ACTIVITY.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {MAIN_ACTIVITY.changeView(MOUSE_PAD_VIEW_BINDER.bindedView(), false);}
                    });
                } catch(Exception e) {
                    if(isConnecting) {
                        isConnecting = false;
                        new Handler(MAIN_ACTIVITY.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {alertConnectionFailed();}
                        });
                    }
                }
            }
        }.start();
    }

    private void finishConnecting() {
        loadingViewAnimator.finish();
        if(isConnecting) {
            isConnecting = false;
            connector.disconnect();
        }
    }
}

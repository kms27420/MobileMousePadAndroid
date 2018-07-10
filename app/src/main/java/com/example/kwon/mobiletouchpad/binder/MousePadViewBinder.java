package com.example.kwon.mobiletouchpad.binder;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.example.kwon.mobiletouchpad.MotionEventConverter;
import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.network.Sender;
import com.example.kwon.mobiletouchpad.view.MousePadView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class MousePadViewBinder {
    private final MainActivity MAIN_ACTIVITY;

    private View mousePadView, reconnectingView;
    private Sender sender;
    private final MotionEventConverter motionEventConverter = new MotionEventConverter();
    private boolean isDisconnectedByMyself;

    private ShortCutEnterViewBinder shortCutEnterViewBinder;
    private boolean isShortCutViewing;

    public MousePadViewBinder(MainActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        bind();
    }

    public void setReconnectingView(View reconnectingView) {
        this.reconnectingView = reconnectingView;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public View bindedView() {
        return mousePadView;
    }

    private void bind() {
        mousePadView = new MousePadView(MAIN_ACTIVITY);
        shortCutEnterViewBinder = new ShortCutEnterViewBinder(MAIN_ACTIVITY);
        mousePadView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {listenDisconnected();}
            @Override
            public void onViewDetachedFromWindow(View v) {disconnectSender();}
        });
        ((MousePadView)mousePadView).setLeftButtonListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        sender.sendData(MotionEventConverter.LEFT_DOWN_PREFIX);
                        break;
                    case MotionEvent.ACTION_UP :
                        sender.sendData(MotionEventConverter.LEFT_UP_PREFIX);
                        break;
                        default: break;
                }
                return true;
            }
        });
        ((MousePadView)mousePadView).setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {sender.sendData(MotionEventConverter.RIGHT_CLICK_PREFIX);}
        });
        ((MousePadView)mousePadView).setShortCutButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortCutEnterViewBinder.setSender(sender);
                isShortCutViewing = true;
                MAIN_ACTIVITY.changeView(shortCutEnterViewBinder.bindedView(), true);
            }
        });
        ((MousePadView)mousePadView).setMousePadListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sender.sendData(motionEventConverter.convertToString(event));
                return true;
            }
        });
        ((MousePadView)mousePadView).setQuitButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.onBackPressed();}
        });
    }

    private void disconnectSender() {
        if(isDisconnectedByMyself || isShortCutViewing)  return;
        isDisconnectedByMyself = true;
        sender.disconnect();
    }

    private void listenDisconnected() {
        if(isShortCutViewing) {
            isShortCutViewing = false;
            return;
        }
        isDisconnectedByMyself = false;
        new Thread(){
            @Override
            public void run() {
                if(!sender.checkNormallyDisconnected()) {    // 비정상적으로 연결 끊겼을때
                    if(isDisconnectedByMyself) return; // 비정상 종료이고 내가 종료한거일때는 종료
                    disconnectSender();
                    new Handler(MAIN_ACTIVITY.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {MAIN_ACTIVITY.changeView(reconnectingView, false);}
                    });
                } else if(!isDisconnectedByMyself) {    // 정상 종료인데 내가 종료한게 아닐때
                    disconnectSender();
                    new Handler(MAIN_ACTIVITY.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {MAIN_ACTIVITY.onBackPressed();}
                    });
                }
            }
        }.start();
    }
}

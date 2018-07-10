package com.example.kwon.mobiletouchpad.binder;

import android.view.View;

import com.example.kwon.mobiletouchpad.MotionEventConverter;
import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.network.Sender;
import com.example.kwon.mobiletouchpad.view.ShortCutView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class WindowShortCutViewBinder {
    private final MainActivity MAIN_ACTIVITY;
    private View shortCutView;
    private Sender sender;

    private final String[][] btNames = new String[3][3];
    private final String[][] btData = new String[3][3];

    public WindowShortCutViewBinder(MainActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        bind();
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public View bindedView() {
        return shortCutView;
    }

    private void bind() {
        btNames[0][0] = "Alt + f4";
        btNames[0][1] = "Alt + Tab";
        btNames[0][2] = "windows";
        btNames[1][0] = "Ctrl + z";
        btNames[1][1] = "Ctrl + x";
        btNames[1][2] = "Ctrl + c";
        btNames[2][0] = "Ctrl + v";
        btNames[2][1] = "Ctrl + s";
        btNames[2][2] = "Ctrl + a";

        btData[0][0] = MotionEventConverter.KEYBOARD_PREFIX + "alt+f4+";
        btData[0][1] = MotionEventConverter.KEYBOARD_PREFIX + "alt+tab+";
        btData[0][2] = MotionEventConverter.KEYBOARD_PREFIX + "windows+";
        btData[1][0] = MotionEventConverter.KEYBOARD_PREFIX + "ctrl+z+";
        btData[1][1] = MotionEventConverter.KEYBOARD_PREFIX + "ctrl+x+";
        btData[1][2] = MotionEventConverter.KEYBOARD_PREFIX + "ctrl+c+";
        btData[2][0] = MotionEventConverter.KEYBOARD_PREFIX + "ctrl+v+";
        btData[2][1] = MotionEventConverter.KEYBOARD_PREFIX + "ctrl+s+";
        btData[2][2] = MotionEventConverter.KEYBOARD_PREFIX + "ctrl+a+";

        shortCutView = new ShortCutView(MAIN_ACTIVITY);
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++) {
                final String data = btData[i][j];
                ((ShortCutView)shortCutView).setButton(i, j, btNames[i][j], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {sender.sendData(data);}
                });
            }
    }
}

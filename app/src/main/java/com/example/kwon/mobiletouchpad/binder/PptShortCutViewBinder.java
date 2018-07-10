package com.example.kwon.mobiletouchpad.binder;

import android.view.View;

import com.example.kwon.mobiletouchpad.MotionEventConverter;
import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.network.Sender;
import com.example.kwon.mobiletouchpad.view.ShortCutView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class PptShortCutViewBinder {
    private final MainActivity MAIN_ACTIVITY;
    private View shortCutView;
    private Sender sender;

    private final String[][] btNames = new String[3][3];
    private final String[][] btData = new String[3][3];

    public PptShortCutViewBinder(MainActivity mainActivity) {
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
        btNames[0][0] = "->";
        btNames[0][1] = "<-";
        btNames[0][2] = "ESC";
        btNames[1][0] = "f5";
        btNames[1][1] = "Shift + f5";
        btNames[1][2] = "추가하기";
        btNames[2][0] = "추가하기";
        btNames[2][1] = "추가하기";
        btNames[2][2] = "추가하기";

        btData[0][0] = MotionEventConverter.KEYBOARD_PREFIX + "→+";
        btData[0][1] = MotionEventConverter.KEYBOARD_PREFIX + "←+";
        btData[0][2] = MotionEventConverter.KEYBOARD_PREFIX + "esc+";
        btData[1][0] = MotionEventConverter.KEYBOARD_PREFIX + "f5+";
        btData[1][1] = MotionEventConverter.KEYBOARD_PREFIX + "shift+f5+";

        shortCutView = new ShortCutView(MAIN_ACTIVITY);
        for(int i=0; i<2; i++)
            for(int j=0; j<3; j++) {
                final String data = btData[i][j];
                if(i==1 && j==2)    break;
                ((ShortCutView)shortCutView).setButton(i, j, btNames[i][j], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {sender.sendData(data);}
                });
            }
    }
}

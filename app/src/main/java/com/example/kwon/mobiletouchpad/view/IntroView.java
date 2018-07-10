package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kwon.mobiletouchpad.custom_view.RippleButton;

/**
 * Created by Kwon on 2018-03-27.
 */

public class IntroView extends LinearLayout {
    private String[] btNames = new String[]{"와이파이 연결", "블루투스 연결", "종료"};
    private int[] btColors = new int[]{Color.LTGRAY, Color.LTGRAY, Color.LTGRAY};

    public IntroView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
        setOrientation(VERTICAL);
        setWeightSum(btNames.length);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        params.setMargins(100,100,100,100);
        for(int i=0; i<btNames.length; i++) addView(createCommonBt(i), params);
    }

    private View createCommonBt(int idx) {
        RippleButton commonBt = new RippleButton(getContext(),
                new ShapeDrawable(new RoundRectShape(new float[]{100f,100f,100f,100f,100f,100f,100f,100f}, null, null)));
        commonBt.setButtonColor(btColors[idx]);
        commonBt.setText(btNames[idx]);
        commonBt.setTextColor(Color.WHITE);
        return commonBt;
    }

    public void setWifiButtonListener(OnClickListener wifiButtonListener) {
        ((Button)getChildAt(0)).setOnClickListener(wifiButtonListener);
    }

    public void setBluetoothButtonListener(OnClickListener bluetoothButtonListener) {
        ((Button)getChildAt(1)).setOnClickListener(bluetoothButtonListener);
    }

    public void setQuitButtonListener(OnClickListener quitButtonListener) {
        ((Button)getChildAt(2)).setOnClickListener(quitButtonListener);
    }
}

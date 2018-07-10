package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kwon.mobiletouchpad.custom_view.RippleButton;

/**
 * Created by Kwon on 2018-05-31.
 */

public class ShortCutEnterView extends LinearLayout {
    private String[] btNames = new String[]{"윈도우 단축키", "PPT 단축키", "커스텀 단축키"};
    private int[] btColors = new int[]{Color.LTGRAY, Color.LTGRAY, Color.LTGRAY};

    public ShortCutEnterView(Context context) {
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

    public void setWindowButtonListener(OnClickListener windowButtonListener) {
        ((Button)getChildAt(0)).setOnClickListener(windowButtonListener);
    }

    public void setPptButtonListener(OnClickListener pptButtonListener) {
        ((Button)getChildAt(1)).setOnClickListener(pptButtonListener);
    }

    public void setCustomButtonListener(OnClickListener customButtonListener) {
        ((Button)getChildAt(2)).setOnClickListener(customButtonListener);
    }
}

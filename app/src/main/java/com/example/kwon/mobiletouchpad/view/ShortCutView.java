package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kwon.mobiletouchpad.custom_view.RippleButton;

/**
 * Created by Kwon on 2018-05-31.
 */

public class ShortCutView extends LinearLayout {
    public ShortCutView(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setWeightSum(3);
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(10,10,10,10);
        for(int i=0; i<3; i++)  addView(createVerticalView(), params);
    }

    public void setButton(int row, int col, String name, OnClickListener buttonListener) {
        ((RippleButton)((LinearLayout)getChildAt(row)).getChildAt(col)).setText(name);
        ((LinearLayout)getChildAt(row)).getChildAt(col).setOnClickListener(buttonListener);
        ((RippleButton)((LinearLayout)getChildAt(row)).getChildAt(col)).setButtonColor(Color.LTGRAY);
        ((RippleButton)((LinearLayout)getChildAt(row)).getChildAt(col)).setTextColor(Color.WHITE);
    }

    private View createVerticalView() {
        LinearLayout verticalView = new LinearLayout(getContext());
        verticalView.setOrientation(VERTICAL);
        verticalView.setWeightSum(3);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        params.setMargins(10,10,10,10);
        for(int i=0; i<3; i++)  verticalView.addView(new RippleButton(getContext(),
                new ShapeDrawable(new RoundRectShape(new float[]{100,100,100,100,100,100,100,100}, null, null))), params);
        return verticalView;
    }
}

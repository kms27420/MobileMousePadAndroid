package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kwon.mobiletouchpad.custom_view.RippleButton;

/**
 * Created by Kwon on 2018-04-15.
 */

public class ConnectingView extends LinearLayout {
    public ConnectingView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setWeightSum(3);
        addView(new LoadingView(context), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));
        RippleButton button = new RippleButton(context, new ShapeDrawable(
                        new RoundRectShape(new float[]{100f,100f,100f,100f,100f,100f,100f,100f},null,null)));
        button.setText("CANCEL");
        button.setButtonColor(Color.rgb(255,175,175));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        params.setMargins(100,100,100,100);
        addView(button, params);
    }

    public LoadingView getLoadingView() {
        return (LoadingView) getChildAt(0);
    }

    public void setCancelButtonListener(OnClickListener cancelButtonListener) {
        ((Button)getChildAt(1)).setOnClickListener(cancelButtonListener);
    }
}

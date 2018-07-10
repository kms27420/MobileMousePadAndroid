package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kwon.mobiletouchpad.custom_view.RippleButton;

/**
 * Created by Kwon on 2018-04-16.
 */

public class MousePadView extends LinearLayout {
    public MousePadView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setWeightSum(10);
        addView(createMouseButtonView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));
        addView(createMousePadView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 6));
        addView(createCommonButton("종료"), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));
    }

    public void setMousePadListener(OnTouchListener mousePadListener) {
        getChildAt(1).setOnTouchListener(mousePadListener);
    }

    public void setLeftButtonListener(OnTouchListener leftButtonListener) {
        ((ViewGroup)getChildAt(0)).getChildAt(0).setOnTouchListener(leftButtonListener);
    }

    public void setShortCutButtonListener(OnClickListener shortCutButtonListener) {
        ((ViewGroup)getChildAt(0)).getChildAt(1).setOnClickListener(shortCutButtonListener);
    }

    public void setRightButtonListener(OnClickListener rightButtonListener) {
        ((ViewGroup)getChildAt(0)).getChildAt(2).setOnClickListener(rightButtonListener);
    }

    public void setQuitButtonListener(OnClickListener quitButtonListener) {
        getChildAt(2).setOnClickListener(quitButtonListener);
    }

    private Button createCommonButton(String name) {
        RippleButton commonButton = new RippleButton(getContext(), new ShapeDrawable(
                new RoundRectShape(new float[]{100f,100f,100f,100f,100f,100f,100f,100f}, null, null)));
        commonButton.setButtonColor(Color.GREEN);
        commonButton.setTextColor(Color.WHITE);
        commonButton.setText(name);

        return commonButton;
    }

    private LinearLayout createMouseButtonView() {
        LinearLayout mouseButtonView = new LinearLayout(getContext());
        mouseButtonView.setOrientation(HORIZONTAL);
        mouseButtonView.setWeightSum(3);
        mouseButtonView.addView(createCommonButton("left"), new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        mouseButtonView.addView(createCommonButton("Short Cut"), new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        mouseButtonView.addView(createCommonButton("right"), new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        return mouseButtonView;
    }

    private View createMousePadView() {
        LinearLayout view = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        view.setOrientation(VERTICAL);
        view.setWeightSum(10);
        for(int i=0; i<10; i++) view.addView(new TextView(getContext()), params);
        return view;
    }

    private int order = 0;
    public void addText(String text) {
        ((TextView)((ViewGroup)getChildAt(1)).getChildAt(order)).setText(text);
        order = order+1==10 ? 0 : order+1;
    }
}

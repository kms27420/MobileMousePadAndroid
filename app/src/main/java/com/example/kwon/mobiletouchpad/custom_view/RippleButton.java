package com.example.kwon.mobiletouchpad.custom_view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Kwon on 2018-03-28.
 */

public class RippleButton extends AppCompatButton {
    private final ColorStateList PRESSED_COLOR_SELECTOR = createPressedColorSelector(Color.WHITE);

    private Drawable contentDrawable;

    public RippleButton(Context context) {
        this(context, Color.LTGRAY);
    }

    public RippleButton(Context context, int buttonColor) {
        super(context);
        setButtonColor(buttonColor);
    }

    public RippleButton(Context context, Bitmap buttonImage) {
        super(context);
        setButtonImage(buttonImage);
    }

    public RippleButton(Context context, Drawable content) {
        super(context);
        setContentDrawable(content);
    }

    public void setButtonColor(int buttonColor) {
        if(contentDrawable == null) setContentDrawable(new ColorDrawable(buttonColor));
        else if(contentDrawable instanceof ColorDrawable)   ((ColorDrawable)contentDrawable).setColor(buttonColor);
        else if(contentDrawable instanceof ShapeDrawable)   ((ShapeDrawable)contentDrawable).getPaint().setColor(buttonColor);
        else if(contentDrawable instanceof GradientDrawable)    ((GradientDrawable)contentDrawable).setColor(buttonColor);
    }

    public void setButtonImage(Bitmap buttonImage) {
        setContentDrawable(new BitmapDrawable(getResources(), buttonImage));
    }

    public void setContentDrawable(Drawable content) {
        contentDrawable = content;
        setBackground(createRippleDrawable());
    }

    private Drawable createRippleDrawable() {
        RippleDrawable rippleDrawable = new RippleDrawable(PRESSED_COLOR_SELECTOR, contentDrawable, null);
        return rippleDrawable;
    }

    private ColorStateList createPressedColorSelector(int pressedColor) {
        return new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_activated},
                new int[]{}
        }, new int[] {
                pressedColor,
                pressedColor,
                pressedColor,
                pressedColor
        });
    }
}

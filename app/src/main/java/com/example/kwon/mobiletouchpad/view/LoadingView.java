package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Kwon on 2018-04-15.
 */

public class LoadingView extends View {
    private final int MAX_STICKS = 12;
    private final float BIG_RAD_RATE = 1/8f;
    private final float SMALL_RAD_RATE = BIG_RAD_RATE / 2f;
    private final float STICK_WIDTH_RATE = SMALL_RAD_RATE / 3f;
    private final double START_POSI = Math.PI / 2;

    private int defaultStickColor = Color.LTGRAY;
    private int[] gradColors =
            new int[]{Color.rgb(232, 201, 201), Color.rgb(255, 175, 175), Color.rgb(255, 134, 136)};
    private int bg = Color.WHITE;

    public LoadingView(Context context) {
        super(context);
        super.setBackgroundColor(bg);
    }

    @Override
    public void setBackgroundColor(int bg) {
        this.bg = bg;
        super.setBackgroundColor(bg);
    }

    public void setAnimationColors(int defaultStickColor, int[] gradationColors) {
        this.defaultStickColor = defaultStickColor;
        this.gradColors = gradationColors;
    }

    private int currentPosi;
    public void updateLoadingView() {
        currentPosi = currentPosi+1 > MAX_STICKS ? currentPosi+1 - MAX_STICKS : currentPosi+1;
        invalidate();
    }

    private final DrawMaterial MATERIAL = new DrawMaterial();

    private void drawInitialSticks(Canvas canvas) {
        MATERIAL.p.setColor(defaultStickColor);
        MATERIAL.p.setStrokeWidth(MATERIAL.stickWidth);
        for(int i=0; i<MAX_STICKS; i++) {
            canvas.drawLine(MATERIAL.cx, MATERIAL.cy,
                    (float)(MATERIAL.cx+MATERIAL.bigRad*Math.cos(START_POSI-2*Math.PI*i/MAX_STICKS)),
                    (float)(MATERIAL.cy+MATERIAL.bigRad*Math.sin(START_POSI-2*Math.PI*i/MAX_STICKS)),
                    MATERIAL.p);
        }
    }

    private void drawOrderedSticks(Canvas canvas) {
        int[] order = new int[gradColors.length];
        MATERIAL.p.setStrokeWidth(MATERIAL.stickWidth);
        for(int i=0; i<order.length; i++) {
            order[i] = currentPosi+i>MAX_STICKS ? currentPosi+i-MAX_STICKS : currentPosi+i;
            MATERIAL.p.setColor(gradColors[i]);
            canvas.drawLine(MATERIAL.cx, MATERIAL.cy,
                    (float)(MATERIAL.cx+MATERIAL.bigRad*Math.cos(START_POSI-2*Math.PI*order[i]/MAX_STICKS)),
                    (float)(MATERIAL.cy-MATERIAL.bigRad*Math.sin(START_POSI-2*Math.PI*order[i]/MAX_STICKS)),
                    MATERIAL.p);
        }
    }

    private void drawInnerCircle(Canvas canvas) {
        MATERIAL.p.setStrokeWidth(1f);
        MATERIAL.p.setColor(bg);
        canvas.drawCircle(MATERIAL.cx, MATERIAL.cy, MATERIAL.smallRad, MATERIAL.p);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        MATERIAL.updateMaterial();
        drawInitialSticks(canvas);
        drawOrderedSticks(canvas);
        drawInnerCircle(canvas);
    }

    private class DrawMaterial {
        private float width, height;
        private float cx, cy;
        private float bigRad, smallRad;
        private float stickWidth;
        private Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        private DrawMaterial() {
            p.setStyle(Paint.Style.FILL);
        }

        private boolean isSizeChanged() {
            return width != (float)getWidth() || height != (float)getHeight();
        }

        private void updateMaterial() {
            if(!isSizeChanged())    return;
            width = (float)getWidth();
            height = (float)getHeight();
            cx = width / 2;
            cy  = height / 2;
            bigRad = height * BIG_RAD_RATE;
            smallRad = height * SMALL_RAD_RATE;
            stickWidth = height * STICK_WIDTH_RATE;
        }
    }
}

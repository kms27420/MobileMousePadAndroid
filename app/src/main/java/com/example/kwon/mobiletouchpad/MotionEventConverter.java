package com.example.kwon.mobiletouchpad;

import android.view.MotionEvent;

/**
 * Created by Kwon on 2018-03-27.
 */

public class MotionEventConverter {
    public static final String LEFT_DOWN_PREFIX = "l";
    public static final String LEFT_UP_PREFIX = "L";
    public static final String CLICK_PREFIX = "c";
    public static final String RIGHT_CLICK_PREFIX = "r";
    public static final String MOVE_PREFIX = "m";
    public static final String SCROLL_PREFIX = "s";
    public static final String KEYBOARD_PREFIX = "k";

    private int previousX, previousY;
    private long firstTouchedTime;

    public MotionEventConverter() {}

    public String convertToString(MotionEvent motionEvent) {
        String data = null;
        switch(motionEvent.getPointerCount()) {
            case 2 :
                data = convertTwoPointerMotionToString(motionEvent);
                break;
            case 1:
                data =  convertOnePointerMotionToString(motionEvent);
                break;
        }
        return data;
    }

    private String convertOnePointerMotionToString(MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();
        String data = null;

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                setFirstTouchedTime();
                setPreviousPoint(x, y);
                break;
            case MotionEvent.ACTION_MOVE :
                data = MOVE_PREFIX + String.format("%05d", x - previousX) + String.format("%05d", y - previousY);
                setPreviousPoint(x, y);
                break;
            case MotionEvent.ACTION_UP :
                if(System.currentTimeMillis() - firstTouchedTime <= 150)    data = CLICK_PREFIX;
        }

        return data;
    }

    private String convertTwoPointerMotionToString(MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();
        String data = null;

        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                setPreviousPoint(x, y);
                break;
            case MotionEvent.ACTION_MOVE :
                data = SCROLL_PREFIX + String.format("%05d", y - previousY);
                setPreviousPoint(x, y);
                break;
        }
        return data;
    }

    private void setPreviousPoint(int x, int y) {
        this.previousX = x;
        this.previousY = y;
    }

    private void setFirstTouchedTime() {
        firstTouchedTime = System.currentTimeMillis();
    }
}
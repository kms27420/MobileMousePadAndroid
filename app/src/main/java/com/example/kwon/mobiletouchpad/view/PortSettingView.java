package com.example.kwon.mobiletouchpad.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kwon.mobiletouchpad.custom_view.PaddingLinearLayout;
import com.example.kwon.mobiletouchpad.custom_view.RippleButton;

/**
 * Created by Kwon on 2018-04-15.
 */

public class PortSettingView extends LinearLayout {
    public PortSettingView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setWeightSum(5);
        addView(createTextView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));
        addView(createEditText(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        addView(createButtonView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));
    }

    public String getEnteredPort() {
        return ((EditText)((PaddingLinearLayout)getChildAt(1)).MAIN_VIEW_GROUP.getChildAt(0)).getText().toString();
    }

    public void setConfirmButtonListener(final OnClickListener confirmButtonListener) {
        ((EditText)((PaddingLinearLayout)getChildAt(1)).MAIN_VIEW_GROUP.getChildAt(0)).setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindowToken(), 0);
                    confirmButtonListener.onClick(null);
                    return true;
                }
                return false;
            }
        });
         ((LinearLayout)((PaddingLinearLayout)getChildAt(2)).MAIN_VIEW_GROUP.getChildAt(0)).getChildAt(0).setOnClickListener(confirmButtonListener);
    }

    public void setCancelButtonListener(OnClickListener cancelButtonListener) {
        ((LinearLayout)((PaddingLinearLayout)getChildAt(2)).MAIN_VIEW_GROUP.getChildAt(0)).getChildAt(1).setOnClickListener(cancelButtonListener);
    }

    private View createButtonView() {
        LinearLayout buttonView = new LinearLayout(getContext());
        buttonView.setOrientation(HORIZONTAL);
        buttonView.setWeightSum(2);

        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(50,10,50,10);

        buttonView.addView(createCommonButton("CONFIRM"), params);
        buttonView.addView(createCommonButton("CANCEL"), params);

        return new PaddingLinearLayout(buttonView,0.15f, 0.15f, 0.15f, 0.55f);
    }

    private Button createCommonButton(String name) {
        RippleButton commonButton = new RippleButton(getContext(),
                new ShapeDrawable(new RoundRectShape(new float[]{100f,100f,100f,100f,100f,100f,100f,100f},null,null)));
        commonButton.setText(name);
        commonButton.setTextColor(Color.WHITE);
        commonButton.setButtonColor(Color.LTGRAY);

        return commonButton;
    }

    private View createTextView() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setText("Enter the port number");
        return new PaddingLinearLayout(textView, 0.25f, 0.7f, 0.25f, 0);
    }

    private View createEditText() {
        EditText editText = new EditText(getContext());
        editText.setSingleLine();
        editText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        editText.setGravity(Gravity.CENTER);
        return new PaddingLinearLayout(editText, 0.25f, 0.2f, 0.25f, 0.2f);
    }
}

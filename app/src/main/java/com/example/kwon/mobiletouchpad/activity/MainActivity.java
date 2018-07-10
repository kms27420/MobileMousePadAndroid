package com.example.kwon.mobiletouchpad.activity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kwon.mobiletouchpad.binder.IntroViewBinder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContentView contentView;
    private final ViewStack VIEW_STACK = new ViewStack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(contentView==null)   contentView = new ContentView(new IntroViewBinder(this).bindedView());
        setContentView(contentView);
    }

    @Override
    public void onBackPressed() {
        View previousView = VIEW_STACK.pop();
        if(previousView==null)  showProgramExitAlert();
        else    contentView.changeView(previousView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alertDialog = null;
    }

    public void changeView(View view, boolean addToStack) {
        if (addToStack) VIEW_STACK.addToStack(contentView.getChildAt(0));
        contentView.changeView(view);
    }

    private AlertDialog alertDialog;
    private void showProgramExitAlert() {
        if(alertDialog==null) {
            alertDialog = new AlertDialog.Builder(this).setTitle("알림")
                    .setMessage("프로그램을 종료하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {System.exit(0);}
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                    }).create();
        }
        alertDialog.show();
    }

    private class ContentView extends LinearLayout {
        private final LayoutParams PARAMS = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

        private ContentView(View initedView) {
            super(MainActivity.this);
            setOrientation(VERTICAL);
            setWeightSum(1);
            changeView(initedView);
        }

        private void changeView(View view) {
            removeAllViews();
            addView(view, PARAMS);
        }
    }

    private class ViewStack {
        private final List<View> LIST = new ArrayList<>();

        private View pop() {
            if(LIST.size()==0)  return null;
            View popped = LIST.get(LIST.size()-1);
            LIST.remove(LIST.get(LIST.size()-1));
            return popped;
        }

        private void addToStack(View view) {
            LIST.add(view);
        }
    }
}
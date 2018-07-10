package com.example.kwon.mobiletouchpad.binder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.kwon.mobiletouchpad.activity.MainActivity;
import com.example.kwon.mobiletouchpad.connect.Connector;
import com.example.kwon.mobiletouchpad.connect.WifiConnector;
import com.example.kwon.mobiletouchpad.view.PortSettingView;

/**
 * Created by Kwon on 2018-05-31.
 */

public class PortSettingViewBinder {
    private static final int MIN_PORT = 49152;
    private static final int MAX_PORT = 65534;

    private View portSettingView;
    private final ConnectingViewBinder CONN_VIEW_BINDER;
    private final Connector CONNECTOR = new WifiConnector();
    private final MainActivity MAIN_ACTIVITY;

    public PortSettingViewBinder(MainActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        CONN_VIEW_BINDER = new ConnectingViewBinder(mainActivity).setConnector(CONNECTOR);
        bind();
    }

    public View bindedView() {
        return portSettingView;
    }

    private void bind() {
        portSettingView = new PortSettingView(MAIN_ACTIVITY);
        ((PortSettingView)portSettingView).setConfirmButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setPort(((PortSettingView)portSettingView).getEnteredPort());}
        });
        ((PortSettingView)portSettingView).setCancelButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {MAIN_ACTIVITY.onBackPressed();}
        });
    }

    private void setPort(String enteredPort) {
        if(PortChecker.isNumeric(enteredPort)) {
            int enteredPortInt = Integer.parseInt(enteredPort);
            if(PortChecker.isValid(enteredPortInt)) {
                ((WifiConnector) CONNECTOR).setPort(enteredPortInt);
                MAIN_ACTIVITY.changeView(CONN_VIEW_BINDER.bindedView(), true);
            } else    showEnteredNumAlert(buildAlertMsg(false));
        } else  showEnteredNumAlert(buildAlertMsg(true));
    }

    private String buildAlertMsg(boolean isNumericProblem) {
        return isNumericProblem ? "숫자로만 입력해주세요. (범위 : " + MIN_PORT + "~" + MAX_PORT + ")"
                : "범위를 확인해주세요. (범위 : " + MIN_PORT + "~" + MAX_PORT + ")";
    }

    private AlertDialog alertDialog;
    private void showEnteredNumAlert(String msg) {
        if(alertDialog==null) {
            alertDialog = new AlertDialog.Builder(MAIN_ACTIVITY).setTitle("Port 재입력 요청")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {alertDialog.dismiss();}
                    }).create();
        }
        alertDialog.setMessage(msg);
        alertDialog.show();
    }

    private static class PortChecker {
        private static boolean isNumeric(String port) {
            try {
                Integer.parseInt(port);
                return true;
            } catch(NumberFormatException e) {return false;}
        }

        private static boolean isValid(int port) {
            if(port>=MIN_PORT && port<=MAX_PORT)   return true;
            return false;
        }
    }
}

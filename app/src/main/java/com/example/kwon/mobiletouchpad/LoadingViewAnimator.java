package com.example.kwon.mobiletouchpad;

import com.example.kwon.mobiletouchpad.view.LoadingView;

/**
 * Created by Kwon on 2018-05-10.
 */

public class LoadingViewAnimator {
    private final LoadingView loadingView;
    private final Runnable loadingViewUpdater = new Runnable() {
        @Override
        public void run() {loadingView.updateLoadingView();}
    };

    public LoadingViewAnimator(LoadingView loadingView) {
        this.loadingView = loadingView;
    }

    private Thread animator;
    public void start() {
        animator = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        loadingView.post(loadingViewUpdater);
                        Thread.sleep(80);
                    }
                } catch(InterruptedException e) {}
            }
        };
        animator.start();
    }

    public void finish() {
        animator.interrupt();
        animator = null;
    }
}

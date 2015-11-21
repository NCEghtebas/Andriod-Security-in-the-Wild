package com.example.markassad.secproj2;

import android.os.Handler;
import android.os.Message;
import android.os.AsyncTask;

public class Timer extends AsyncTask<Void, Void, Void> {

    private Handler backgroundThread;

    public Timer(Handler threadHandler) {
        super();
        this.backgroundThread =threadHandler;
    }

    @Override
    protected Void doInBackground(Void...params) {

        try {
            Thread.sleep(MainActivity.WAIT);
        } catch (InterruptedException e) {}
        Message.obtain(backgroundThread, MainActivity.COMPLETE, "").sendToTarget();

        return null;
    }
}
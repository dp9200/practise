package com.example.lunchver2.model;

import com.example.lunchver2.myInterface.IThreadAction;

public class ThreadMgr {
    private IThreadAction threadAction = null;
    private Thread thread = null;
    private Runnable runnable = null;

    public ThreadMgr(IThreadAction threadAction)
    {
        this.threadAction = threadAction;
        runnable = new Runnable() {
            @Override
            public void run() {
                threadAction.excutedAction();
                thread = null;
            }
        };
    }

    public void start()
    {
        if (thread != null)
        {
            return;
        }

        thread = new Thread(runnable);
        thread.start();
    }
}

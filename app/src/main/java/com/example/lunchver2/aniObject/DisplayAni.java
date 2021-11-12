package com.example.lunchver2.aniObject;

import android.os.Handler;
import android.widget.TextView;

public class DisplayAni implements Runnable{
    private Handler handler = null;
    private String[] displayItems = null;
    private int currentIndex = 0;
    private int excuteFrame = 8;
    private int currentFrame = 0;
    private boolean isFinish = false;
    private TextView textView = null;

    public static final int frameSpacing = 33;

    public DisplayAni(String[] displayItems,Handler handler,TextView textView) {
        this.displayItems = displayItems;
        this.handler = handler;
        this.textView = textView;
    }

    public boolean checkIsFinish()
    {
        return isFinish;
    }

    @Override
    public void run() {
        currentFrame++;
        if (currentFrame % excuteFrame == 0) {
            update();
        }
        if (!isFinish)
            handler.postDelayed(this,frameSpacing);
    }

    private void update() {
        textView.setText(displayItems[currentIndex]);
        if (currentIndex == (displayItems.length - 4)) {
            currentFrame = 0;
            excuteFrame = 12;
        } else if (currentIndex == (displayItems.length - 1)) {
            isFinish = true;
            handler.removeCallbacks(this);
            handler = null;
            displayItems = null;
        }
        currentIndex++;
    }
}

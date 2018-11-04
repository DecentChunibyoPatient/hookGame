package com.wqg.gamecmd;

import android.view.MotionEvent;
import android.view.View;

public interface Touch {
    void open();
    void close();
    void move(View v, MotionEvent event);
}

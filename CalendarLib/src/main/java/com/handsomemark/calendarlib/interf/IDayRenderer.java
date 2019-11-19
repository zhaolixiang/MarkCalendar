package com.handsomemark.calendarlib.interf;

import android.graphics.Canvas;

import com.handsomemark.calendarlib.view.Day;


/**
 * Created by ldf on 17/6/26.
 */

public interface IDayRenderer {

    void refreshContent();

    void drawDay(Canvas canvas, Day day);

    IDayRenderer copy();

}

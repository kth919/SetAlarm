package com.noname.setalarm.view;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.noname.setalarm.model.ClockModel;

public class ClockObserver extends BaseObservable {

    private int hour;
    private int minute;

    public ClockObserver(ClockModel clockModel) {
        this.hour = clockModel.getHour();
        this.minute = clockModel.getMinute();
    }

    @Bindable
    public String getHour() {
        return String.valueOf(hour);
    }

    @Bindable
    public String getMinute() {
        return String.valueOf(minute);
    }

}

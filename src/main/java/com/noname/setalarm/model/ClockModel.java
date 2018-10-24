package com.noname.setalarm.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity()
public class ClockModel {

    @PrimaryKey
    @NonNull
    private int id;

    private int hour;
    private int minute;
    private boolean am_pm;

    public ClockModel(@NonNull int id, int hour, int minute, boolean am_pm) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
//        am_pm = hour >= 12;
        this.am_pm = am_pm;
    }

    @NonNull
    public int getId(){
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isAm_pm() {
        return am_pm;
    }
}

package com.noname.setalarm.repository;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.noname.setalarm.model.ClockModel;

import java.time.Clock;
import java.util.List;

@Entity
public class AlarmRoom {

    @PrimaryKey
    @NonNull
    private String alarmId;

    @TypeConverters(ClockTypeConverter.class)
    public final  List<ClockModel> timeList;

    public AlarmRoom(@NonNull String alarmId, List<ClockModel> timeList) {
        this.alarmId = alarmId;
        this.timeList = timeList;
    }

    @NonNull
    public String getAlarmId() {
        return alarmId;
    }

    public List<ClockModel> getTimeList() {
        return timeList;
    }
}

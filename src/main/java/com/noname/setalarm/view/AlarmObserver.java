package com.noname.setalarm.view;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.repository.AlarmRoom;

import java.util.ArrayList;
import java.util.List;

public class AlarmObserver extends BaseObservable {

    private List<ClockModel> tmpList;

    public AlarmObserver(AlarmRoom alarmRoom) {
        tmpList = alarmRoom.getTimeList();
    }

    @Bindable
    public String getTimeTxt() {

        StringBuilder result = new StringBuilder();
        for (ClockModel model : tmpList){
            result.append(model.getHour() + ":" + model.getMinute());
            result.append(model.isAm_pm()? "PM":"AM");
            result.append(" ");
        }

        return result.toString();
    }
}

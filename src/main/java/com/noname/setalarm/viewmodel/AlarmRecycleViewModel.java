package com.noname.setalarm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.noname.setalarm.model.AlarmModel;

import java.util.ArrayList;

public class AlarmRecycleViewModel extends BaseObservable {

    private ArrayList<AlarmModel> timeList;

    public AlarmRecycleViewModel(ArrayList<AlarmModel> timeList) {
        this.timeList = timeList;
    }

    public ArrayList<AlarmModel> getTimeList() {
        return timeList;
    }

    @Bindable
    public String getStringTime(){

        StringBuilder sb = new StringBuilder();
        for (AlarmModel source: timeList){
            sb.append(source.getTimeList());
            sb.append(" ");
        }

        return sb.toString();
    }
}

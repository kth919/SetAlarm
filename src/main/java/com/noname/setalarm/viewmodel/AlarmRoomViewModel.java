package com.noname.setalarm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.repository.AlarmRoom;
import com.noname.setalarm.repository.AlarmRoomRepo;

import java.util.List;

public class AlarmRoomViewModel extends AndroidViewModel {

    private AlarmRoomRepo alarmRoomRepo;
    private LiveData<List<AlarmRoom>> listLiveData;

    public AlarmRoomViewModel(@NonNull Application application) {
        super(application);
        this.alarmRoomRepo = new AlarmRoomRepo(application);
    }

    public LiveData<List<AlarmRoom>> getListLiveData() {
        if (listLiveData == null){
            listLiveData = alarmRoomRepo.getAllAlarm();
        }
        return listLiveData;
    }

    public void insert(AlarmRoom alarmRoom) { alarmRoomRepo.insert(alarmRoom); }

    public void delete(AlarmRoom alarmRoom){alarmRoomRepo.delete(alarmRoom);}

}

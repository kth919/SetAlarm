package com.noname.setalarm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.noname.setalarm.model.ClockModel;
import com.noname.setalarm.repository.AlarmRoom;
import com.noname.setalarm.repository.AlarmRoomRepo;
import com.noname.setalarm.repository.ClockDao;

import java.util.List;

public class ClockViewModel extends AndroidViewModel {

    private AlarmRoomRepo alarmRoomRepo;
    private LiveData<List<ClockModel>> listLiveData;

    public ClockViewModel(@NonNull Application application) {
        super(application);
        this.alarmRoomRepo = new AlarmRoomRepo(application);
    }

    public LiveData<List<ClockModel>> getListLiveData() {
        if (listLiveData == null){
            listLiveData = alarmRoomRepo.getAllClock();
        }
        return listLiveData;
    }

    public void insert(ClockModel clockModel) { alarmRoomRepo.insertClockModel(clockModel); }

    public void insertAlarm(AlarmRoom alarmRoom){alarmRoomRepo.insert(alarmRoom);}

    public void delete(ClockModel clockModel){alarmRoomRepo.deleteClockModel(clockModel);}

    public void deleteAll(){alarmRoomRepo.deleteAllClock();}

    public void updateHour(int hour, int minute){alarmRoomRepo.updateClockHour(hour, minute);}

    public void updateMinute(int hour, int minute){alarmRoomRepo.updateClockMinute(hour, minute);}

}

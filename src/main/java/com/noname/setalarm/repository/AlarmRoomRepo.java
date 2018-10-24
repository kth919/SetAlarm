package com.noname.setalarm.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.noname.setalarm.model.ClockModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmRoomRepo {

    private AlarmDao alarmDao;
    private ClockDao clockDao;
    private LiveData<List<AlarmRoom>> alarmLiveData;
    private LiveData<List<ClockModel>> clockLiveData;
    private ExecutorService executorService;

    public AlarmRoomRepo(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        alarmDao = db.alarmDao();
        clockDao = db.clockDao();

        alarmLiveData = alarmDao.getAllAlarm();
        clockLiveData = clockDao.getAllClock();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<AlarmRoom>> getAllAlarm(){
        return alarmLiveData;
    }

    public LiveData<List<ClockModel>> getAllClock(){
        return clockLiveData;
    }

//    public LiveData<AlarmRoom> getAlarm(String id){
//        return alarmDao.getAlarm(id);
//    }

    public void insert(AlarmRoom alarmRoom) {
        executorService.execute(() -> alarmDao.insertAlarm(alarmRoom));
    }

    public void delete(AlarmRoom alarmRoom){
        executorService.execute(() -> alarmDao.deleteAlarm(alarmRoom));
    }

    public void insertClockModel(ClockModel clockModel) {
        executorService.execute(() -> clockDao.insertClock(clockModel));
    }

    public void deleteClockModel(ClockModel clockModel){
        executorService.execute(() -> clockDao.deleteClock(clockModel));
    }

    public void deleteAllClock(){
        executorService.execute(() -> clockDao.deleteAll());
    }

    public void updateClockHour(int hour, int minute){
        executorService.execute(() -> clockDao.updateClockHour(hour, minute));
    }
    public void updateClockMinute(int hour, int minute){
        executorService.execute(() -> clockDao.updateClockMinute(hour, minute));
    }
}


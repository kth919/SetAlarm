package com.noname.setalarm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.noname.setalarm.model.ClockModel;

import java.util.List;

@Dao
public interface ClockDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertClock(ClockModel... clockModels);

    @Delete
    void deleteClock(ClockModel clockModel);

//    @Query("SELECT * from AlarmRoom where id = :id")
//    LiveData<AlarmRoom> getAlarm(String id);

    @Query("SELECT * from ClockModel")
    LiveData<List<ClockModel>> getAllClock();

    @Query("UPDATE ClockModel SET hour= :hour WHERE minute = :minute")
    void updateClockHour(int hour, int minute);

    @Query("UPDATE ClockModel SET minute= :minute WHERE hour = :hour")
    void updateClockMinute(int hour, int minute);

    @Query("DELETE FROM ClockModel")
    void deleteAll();
}

package com.noname.setalarm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlarm(AlarmRoom... alarmRoom);

    @Delete
    void deleteAlarm(AlarmRoom alarmRoom);

//    @Query("SELECT * from AlarmRoom where id = :id")
//    LiveData<AlarmRoom> getAlarm(String id);

    @Query("SELECT * from AlarmRoom")
    LiveData<List<AlarmRoom>> getAllAlarm();
}

package com.noname.setalarm.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.noname.setalarm.model.ClockModel;

@Database(entities = {AlarmRoom.class, ClockModel.class}, version = 1, exportSchema = false)
@TypeConverters({ClockTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
    public abstract ClockDao clockDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "alarm_database")
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}

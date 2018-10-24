package com.noname.setalarm.repository;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noname.setalarm.model.ClockModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClockTypeConverter {

    @TypeConverter
    public static List<ClockModel> stringToClockModelList(String data) {

        Type listType = new TypeToken<List<ClockModel>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String clockModelListToString(List<ClockModel> someObjects) {

        Gson gson = new Gson();
        String json = gson.toJson(someObjects);

        return json;
    }

}

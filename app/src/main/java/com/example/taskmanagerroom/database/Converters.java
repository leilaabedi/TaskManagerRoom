package com.example.taskmanagerroom.database;

import androidx.room.TypeConverter;

import com.example.taskmanagerroom.model.State;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Converters {

    @TypeConverter
    public static Date LongToDate(Long timeStamp) {
        return new Date(timeStamp);
    }

    @TypeConverter
    public static long DateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static UUID stringToUUID(String value) {
        return UUID.fromString(value);
    }

    @TypeConverter
    public static String UUIDToString(UUID uuid) {
        return uuid.toString();
    }


    @TypeConverter
    public static State StringToState(String statestr) {

        State temp = State.TODO;

        if (statestr.equals("TODO"))
            temp = State.TODO;

        if (statestr.equals("DOING"))
            temp = State.DOING;

        if (statestr.equals("DONE"))
            temp = State.DONE;

        return temp;

    }

    @TypeConverter
    public static String StateToString(State tempstate) {
        String result = "";

        if (tempstate == State.DOING)
            result = "DOING";

        if (tempstate == State.DONE)
            result = "DONE";

        if (tempstate == State.TODO)
            result = "TODO";

        return result;

    }
/*
    @TypeConverter
    public static String SimpleDateToString(SimpleDateFormat temp) {
        return temp.toString();
    }

    @TypeConverter
    public static SimpleDateFormat StringToSimpledate(String str) {
        SimpleDateFormat temp = new SimpleDateFormat(str);
        return temp;
    }
*/
}

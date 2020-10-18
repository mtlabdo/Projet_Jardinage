package com.abdessamad.jardinage.Fragments;

import com.abdessamad.jardinage.Converters.PlantGenre;

import androidx.room.TypeConverter;

public class GenrePlants {

    @TypeConverter
    public static PlantGenre toType (String text) {
        return text == null ? null : PlantGenre.valueOf(text);
    }

    @TypeConverter
    public static String fromType (PlantGenre type) {
        return type == null ? null : type.toString();

    }
}

package com.abdessamad.jardinage.Converters;

import com.abdessamad.jardinage.enumuration.Semer_mois;

import java.util.List;
import androidx.room.TypeConverter;
public class tempsPlantationConvert {

    @TypeConverter
    public static List<Semer_mois> toType (String text) {
        return null;
    }

    @TypeConverter
    public static String fromType (Semer_mois temps) {
        return null;
    }
}

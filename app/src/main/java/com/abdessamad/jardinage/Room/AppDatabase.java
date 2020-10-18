package com.abdessamad.jardinage.Room;

import android.content.Context;

import com.abdessamad.jardinage.Dao.parcelle;
import com.abdessamad.jardinage.Dao.plant;
import com.abdessamad.jardinage.Dao.potager;
import com.abdessamad.jardinage.Fragments.GenrePlants;
import com.abdessamad.jardinage.ParcelModel;
import com.abdessamad.jardinage.PlantModel;
import com.abdessamad.jardinage.PotageModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities= {PotageModel.class, ParcelModel.class, PlantModel.class}, version = 1, exportSchema = false)
@TypeConverters({GenrePlants.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;
    public static AppDatabase getDataBase(){
        return instance;
    }
    public abstract potager potagerDao();
    public abstract parcelle parcelleDao();
    public abstract plant plantDao ();
    public AppDatabase(){ }
    public static void createDatabase(Context context){
        if(instance==null)
            instance = Room.databaseBuilder(context,AppDatabase.class,"Jardinage.db").build();

    }
}

package com.abdessamad.jardinage.Dao;

import com.abdessamad.jardinage.PlantModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface plant {


    @Query("SELECT * FROM plants")
    LiveData<List<PlantModel>> getAllPlants();

    @Query("SELECT * FROM plants WHERE id= :id")
    LiveData<PlantModel> getPlant(int id);

    @Query("SELECT * FROM plants WHERE type =:type")
    LiveData<List<PlantModel>> getPlantsType(String type);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(PlantModel plant);

    @Delete
    void delete(PlantModel plant);
}

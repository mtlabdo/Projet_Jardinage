package com.abdessamad.jardinage.Dao;

import com.abdessamad.jardinage.ParcelModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface parcelle {

    @Query("SELECT  * FROM parcelles")
     LiveData<List<ParcelModel>> getParcelles();

    @Query("SELECT  * FROM parcelles WHERE idPotager = :Potager")
     LiveData<List<ParcelModel>> getParcellesByIdPotager(int Potager);


    @Query("UPDATE parcelles SET idPlant = :newId,date = :datePlanter WHERE id = :parcelle")
    void updateParcellePlant(int parcelle, int newId, String datePlanter);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ParcelModel parcelle);

    @Query("SELECT  * FROM parcelles WHERE idPlant >= 3")
    LiveData<List<ParcelModel>> getParcellesPlanted();


}

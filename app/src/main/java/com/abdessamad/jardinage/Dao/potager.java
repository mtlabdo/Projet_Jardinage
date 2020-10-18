package com.abdessamad.jardinage.Dao;


import com.abdessamad.jardinage.PotageModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface potager {

    @Query("SELECT * FROM potagers")
    LiveData<List<PotageModel>> findAll();


    @Query("SELECT * FROM potagers WHERE id = :id")
    LiveData<PotageModel> findbyId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PotageModel potager);

    @Delete
    void delete(PotageModel potager);


}

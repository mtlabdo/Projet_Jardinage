package com.abdessamad.jardinage.ViewModels;

import android.app.Application;

import com.abdessamad.jardinage.Dao.plant;
import com.abdessamad.jardinage.PlantModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class PlantsViewModel extends AndroidViewModel {

    MediatorLiveData<List<PlantModel>> plants;
    plant dao;
    public PlantsViewModel(@Nullable Application application){
        super(application);
        plants = new MediatorLiveData<>();
        plants.setValue(null);
    }

    public void setDao(plant dao) {
        this.dao = dao;
        plants.addSource(dao.getAllPlants(),plants::setValue);
    }

    public LiveData<List<PlantModel>> getPlants(){
        return plants;
    }

    public LiveData<List<PlantModel>> getPlantsByType(String type){
        return dao.getPlantsType(type);
    }


    public void createPlants(ArrayList<PlantModel> plants){
        new Thread(){
            @Override
            public void run() {
                for(PlantModel pl : plants) dao.insert(pl);
                }
        }.start();
        }


    public void  deletePlant(PlantModel plant){
        new Thread(){
            @Override
            public void run() {
                dao.delete(plant);
            }
        }.start();
    }
}

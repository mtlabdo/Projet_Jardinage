package com.abdessamad.jardinage.ViewModels;

import android.app.Application;

import com.abdessamad.jardinage.PlantModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PlantViewModel extends AndroidViewModel {

    com.abdessamad.jardinage.Dao.plant dao;
    LiveData<PlantModel> plant;
    MutableLiveData<Integer> id = new MutableLiveData<>();

    public PlantViewModel(@Nullable Application application){
        super(application);
        this.plant = Transformations.switchMap(id ,v->dao.getPlant(v));
    }

    public void setDao(com.abdessamad.jardinage.Dao.plant dao){
        this.dao = dao;
    }

    public void setId(int id){
        this.id.postValue(id);
    }

    public LiveData<PlantModel> getPlant(){
        return this.plant;
    }
    public LiveData<PlantModel> getPlant(int id){return dao.getPlant(id);}
    public void savePlant(PlantModel plant){
        new Thread(){
            @Override
            public void run() {
                dao.insert(plant);
            }
        }.start();
    }

}

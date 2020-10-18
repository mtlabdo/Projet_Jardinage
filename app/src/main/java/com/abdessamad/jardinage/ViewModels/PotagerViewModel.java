package com.abdessamad.jardinage.ViewModels;


import android.app.Application;

import com.abdessamad.jardinage.PotageModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PotagerViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> id = new MutableLiveData<>();
    private com.abdessamad.jardinage.Dao.potager dao;
    private LiveData<PotageModel> potager;

    public PotagerViewModel(@Nullable Application application){
        super(application);
    }

    public void setDao(com.abdessamad.jardinage.Dao.potager dao){
        this.dao = dao;
        this.potager = Transformations.switchMap(id, v -> dao.findbyId(v));
    }

    public void setId(int id) {
        this.id.postValue(id);
    }

    public LiveData<PotageModel> getPotager(){
        return potager;
    }


    public LiveData<PotageModel> getPotager(int id){
        return dao.findbyId(id);
    }

    public void savePotager(PotageModel p){
        new Thread(){
            @Override
            public void run() {
                dao.insert(p);
            }
        }.start();
    }

    public void getPotagers(){
        new Thread(){
            @Override
            public void run() {
                dao.findAll();
            }
        }.start();
    }
}

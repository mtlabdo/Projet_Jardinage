package com.abdessamad.jardinage.ViewModels;

import android.app.Application;

import com.abdessamad.jardinage.Dao.potager;
import com.abdessamad.jardinage.PotageModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

public class PotagersViewModel extends AndroidViewModel {

    private potager dao;
    private MediatorLiveData<List<PotageModel>> potagers;

    public PotagersViewModel(@Nullable Application application){
        super(application);
        potagers = new MediatorLiveData<>();
        potagers.setValue(null);
    }

    public void setDao(potager dao){
        this.dao = dao;
        potagers.addSource (dao.findAll(),potagers::setValue);
    }

    public void delete(PotageModel potager){
        new Thread(){
            @Override
            public void run() {
                dao.delete(potager);
            }
        }.start();
      ;
    }

    public MediatorLiveData<List<PotageModel>> getPotagers(){
        return potagers;
    }
}

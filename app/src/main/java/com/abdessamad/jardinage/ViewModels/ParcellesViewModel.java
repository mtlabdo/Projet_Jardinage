package com.abdessamad.jardinage.ViewModels;

import android.app.Application;

import com.abdessamad.jardinage.ParcelModel;
import com.abdessamad.jardinage.Dao.parcelle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ParcellesViewModel extends AndroidViewModel {

    private parcelle dao;
    private LiveData<List<ParcelModel>> parcelles;
    private MutableLiveData<Integer> idPotager = new MutableLiveData<>();

    public ParcellesViewModel(@Nullable Application application){
        super(application);
        parcelles = new MediatorLiveData<>();
    }

    public void setIdPotager(int idPotager) {
        this.idPotager.postValue(idPotager);
    }

    public void setDao(parcelle dao){
        this.dao = dao;
        this.parcelles  = Transformations.switchMap(idPotager , v->dao.getParcellesByIdPotager(v));


    }

    public LiveData<List<ParcelModel>> getParcelles() {
        return parcelles;
    }

    public void saveParcelle(ParcelModel parcelle){
     new Thread(){
         @Override
         public void run() {
            dao.insert(parcelle);
         }
     }.start();
    }

    public void update_ParcellePlant(int idParcelle,int new_idPlant,String date){
        new Thread(){
            @Override
            public void run() {
                dao.updateParcellePlant(idParcelle,new_idPlant,date);
            }
        }.start();
    }
}

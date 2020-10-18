package com.abdessamad.jardinage.ViewModels;

import android.app.Application;

import com.abdessamad.jardinage.CalendarModel;
import com.abdessamad.jardinage.ParcelModel;
import com.abdessamad.jardinage.PlantModel;
import com.abdessamad.jardinage.PotageModel;
import com.abdessamad.jardinage.Dao.parcelle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class AgendaViewModel extends AndroidViewModel {

    com.abdessamad.jardinage.Dao.potager potagerDao;
    parcelle parcelleDao;
    com.abdessamad.jardinage.Dao.plant plantDao;
    private MediatorLiveData<List<CalendarModel>> agenda;
    private MediatorLiveData<List<ParcelModel>> parcelles;
    private LiveData<PlantModel> plant;
    private LiveData<PotageModel> potager;

    private MutableLiveData<Integer> idPlant = new MutableLiveData<>();
    private MutableLiveData<Integer> idPotager = new MutableLiveData<>();


    public AgendaViewModel(@Nullable Application application){
        super(application);
        agenda = new MediatorLiveData<>();
        parcelles = new MediatorLiveData<>();
        plant = new MediatorLiveData<>();
        potager = new MediatorLiveData<>();
    }

    public void setDao(parcelle parcelleDao, com.abdessamad.jardinage.Dao.potager potagerDao, com.abdessamad.jardinage.Dao.plant plantDao) {
        this.parcelleDao = parcelleDao;
        this.plantDao = plantDao;
        this.potagerDao = potagerDao;
        parcelles.addSource(parcelleDao.getParcellesPlanted(),parcelles::setValue);
        this.plant = Transformations.switchMap(idPlant , v -> plantDao.getPlant(v));
        this.potager = Transformations.switchMap(idPotager , v -> potagerDao.findbyId(v));

    }

    public void setIdPlant(int idPlant) {
        this.idPlant.postValue(idPlant);
    }

    public void setIdPotager(int idPotager) {
        this.idPotager.postValue(idPotager);
    }

    public LiveData<List<ParcelModel>> getParcellesPlanted() {
        return parcelles;
    }

    public LiveData<PlantModel> getPlant() {
        return plant;
    }

    public LiveData<PotageModel> getPotager() {
        return potager;
    }


    public void CreateAgenda(){
        List<CalendarModel> agendaList = new ArrayList<>();
       for(int i = 0 ; i<parcelles.getValue().size();i++){
           ParcelModel parcelle = parcelles.getValue().get(i);
           CalendarModel agenda = new CalendarModel();
           agenda.setParcelle(parcelle.getName());
         /*  plantDao.getPlant(parcelle.getIdPlant()).observe(this.application.getApplicationContext(), new Observer<PlantModel>() {
               @Override
               public void onChanged(PlantModel plant) {
                   if(plant!=null){


                   agenda.setPlant(plant.getName());
                   agenda.setPlant(plant.getName());
                   agenda.setImagePlant(plant.getImage());
                   }
               }
           });
           */
//           agenda.setPotager(potagerDao.findbyId(parcelle.getIdPotager()).getValue().getName());
           agenda.setPotager("sq");

           agenda.setRecolteJours(parcelle.getDate());
           agendaList.add(agenda);
       }

       agenda.postValue(agendaList);
    }


    public MediatorLiveData<List<CalendarModel>> getAgenda() {
       // CreateAgenda();
        return agenda;
    }
}

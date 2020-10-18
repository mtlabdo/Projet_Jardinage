package com.abdessamad.jardinage.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdessamad.jardinage.CalendarModel;
import com.abdessamad.jardinage.PlantModel;
import com.abdessamad.jardinage.PotageModel;
import com.abdessamad.jardinage.ViewModels.PlantViewModel;
import com.abdessamad.jardinage.ParcelModel;
import com.abdessamad.jardinage.ViewModels.AgendaViewModel;
import com.abdessamad.jardinage.Room.AppDatabase;
import com.abdessamad.jardinage.ViewModels.PotagerViewModel;
import com.autoai.plantation.R;
import com.autoai.plantation.databinding.CustomCalendarBinding;
import com.autoai.plantation.databinding.FragmentAgendaBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Calendrier extends Fragment {

    AgendaViewModel viewModel;
    AgendaAdapter adapter;
    PlantViewModel plantViewModel;
    PotagerViewModel potagersViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AgendaViewModel.class);
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        potagersViewModel = ViewModelProviders.of(this).get(PotagerViewModel.class);
        potagersViewModel.setDao(AppDatabase.getDataBase().potagerDao());
        plantViewModel.setDao(AppDatabase.getDataBase().plantDao());
        viewModel.setDao(AppDatabase.getDataBase().parcelleDao(),AppDatabase.getDataBase().potagerDao(),AppDatabase.getDataBase().plantDao());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.titleAgenda);
        FragmentAgendaBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_agenda,container,false);

        RecyclerView recyclerView = binding.agendaList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new AgendaAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getParcellesPlanted().observe(this, new Observer<List<ParcelModel>>() {
            @Override
            public void onChanged(List<ParcelModel> parcelles) {
                    if(parcelles !=null){
                        adapter.setParcelles(parcelles);
                    }
            }
        });
        return binding.getRoot();
    }


    private class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {

        private List<CalendarModel> agendaList;
        private List<ParcelModel> parcelles;

        public void setAgenda(List<CalendarModel> agendaList){
            this.agendaList = agendaList;
            notifyDataSetChanged();

        }
        public void setParcelles(List<ParcelModel> parcelles){
            this.parcelles = parcelles;
            notifyDataSetChanged();

        }

        private class AgendaViewHolder extends RecyclerView.ViewHolder {

            CustomCalendarBinding binding;

            public AgendaViewHolder(@NonNull  CustomCalendarBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

        }
        public String getDateToday(){
            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            Date todayDate = new Date();
            return  currentDate.format(todayDate);

        }

        @NonNull
        @Override
        public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CustomCalendarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.custom_calendar,parent,false);
            return new AgendaViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
            ParcelModel parcelle = parcelles.get(position);
            if(parcelle.getIdPlant()>2){
            potagersViewModel.setId(parcelle.getIdPotager());
            potagersViewModel.getPotager(parcelle.getIdPotager()).observe(getActivity(), new Observer<PotageModel>() {
                @Override
                public void onChanged(PotageModel potager) {
                    if (potager!=null){
                        holder.binding.setNamePotager(potager.getName());
                    }
                }
            });
            plantViewModel.setId(parcelle.getIdPlant());

            plantViewModel.getPlant(parcelle.getIdPlant()).observe(getActivity(), new Observer<PlantModel>() {
                @Override
                public void onChanged(PlantModel plant) {
                    if (plant!=null){
                        holder.binding.setImagePlant(plant.getImage());
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateBeforeString = getDateToday();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        try{
                            c.setTime(sdf.parse(parcelle.getDate()));
                        }catch(ParseException e){
                            e.printStackTrace();
                        }
                        c.add(java.util.Calendar.DAY_OF_MONTH, plant.getDuree());
                        String dateAfterString = sdf.format(c.getTime());

                        try {
                            Date dateBefore = myFormat.parse(dateBeforeString);
                            Date dateAfter = myFormat.parse(dateAfterString);
                            long difference = dateAfter.getTime() - dateBefore.getTime();
                            float daysBetween = (difference / (1000*60*60*24));
                            holder.binding.setResteAvantRecole("reste\n"+((int)daysBetween)+" jrs");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        holder.binding.setNameP(plant.getName());

                    }

                }
            });
            holder.binding.setNameParcelle(parcelle.getName());
            }
        }


        @Override
        public int getItemCount() {
            return parcelles == null ? 0 : parcelles.size();
        }


    }
}

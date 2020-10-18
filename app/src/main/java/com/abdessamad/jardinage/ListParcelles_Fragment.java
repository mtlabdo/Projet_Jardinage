package com.abdessamad.jardinage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdessamad.jardinage.ViewModels.ParcellesViewModel;
import com.abdessamad.jardinage.ViewModels.PlantViewModel;
import com.autoai.plantation.R;
import com.abdessamad.jardinage.Room.AppDatabase;
import com.autoai.plantation.databinding.CustomParcelleBinding;
import com.autoai.plantation.databinding.FrgListParcelleBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListParcelles_Fragment extends Fragment {
    ParcelleAdapter adapter;
    private ParcellesViewModel viewModel;
    private PlantViewModel plantViewModel;
    public int id;
    public  static int idPotager = 0;
    String formPotager;

    public interface OnFragmentInteractionListener {
        List<ParcelModel> getParcelles();
        List<PlantModel> getPlants();
    }

    ArrayList<ParcelModel> parcellesList = new ArrayList<>();

    void parcelles(int idPotager,int columns,int rows) {
        for (int i = 0 ;i<columns*rows;i++){
            parcellesList.add(new ParcelModel("P"+(i+1), 0, idPotager,""));

        }
    }

    private OnFragmentInteractionListener mListener;

    public ListParcelles_Fragment() {
    }


    public interface ItemClick {
        public void onItemClick(View v, Object o);
    }

    private ItemClick itemClick = new ItemClick() {
        @Override
        public void onItemClick(View v, Object o) {
            ParcelModel p = (ParcelModel) o;
            ListParcelles_FragmentDirections.ActionParcellesFragmentToPlantsFragments2 action = ListParcelles_FragmentDirections.actionParcellesFragmentToPlantsFragments2();
            action.setIdParcelle(p.getId());
            Navigation.findNavController(v).navigate(action);
        }
    };

    public static ListParcelles_Fragment newInstance(String param1, String param2) {
        ListParcelles_Fragment fragment = new ListParcelles_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().setTitle(R.string.titleParcelles);
        viewModel = ViewModelProviders.of(this).get(ParcellesViewModel.class);
        plantViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        plantViewModel.setDao(AppDatabase.getDataBase().plantDao());
        viewModel.setDao(AppDatabase.getDataBase().parcelleDao());


        // viewModel.setIdPotager(idPotager);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FrgListParcelleBinding binding = DataBindingUtil.inflate(inflater, R.layout.frg_list_parcelle, container, false);
        RecyclerView recyclerView = binding.parcelles;

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ParcelleAdapter();
        recyclerView.setAdapter(adapter);

        if (getArguments() != null) {
            int idPlant = ListParcelles_FragmentArgs.fromBundle(getArguments()).getIdPlant();
            int idParcelle = ListParcelles_FragmentArgs.fromBundle(getArguments()).getIdParcelle();
            if (idPlant != 0 && idParcelle != 0) {
                planter(idPlant,idParcelle );
            }
        }
        if (ListParcelles_FragmentArgs.fromBundle(getArguments()).getIdPotager()!=0) {
            idPotager = ListParcelles_FragmentArgs.fromBundle(getArguments()).getIdPotager();
            formPotager = ListParcelles_FragmentArgs.fromBundle(getArguments()).getForme();
            Log.d("idPotager",""+idPotager);
            viewModel.setIdPotager(idPotager);
        }
        else  {
            if(idPotager!=0){
                Log.d("idPotager",""+idPotager);
                viewModel.setIdPotager(idPotager);}
        }

        if(viewModel.getParcelles().getValue()==null){

        }



        viewModel.getParcelles().observe(this, new Observer<List<ParcelModel>>() {
            @Override
            public void onChanged(List<ParcelModel> parcelles) {
                if (parcelles.size() == 0) {
                    parcelles(idPotager,Integer.parseInt(formPotager.split("X")[0]),Integer.parseInt(formPotager.split("X")[1]));
                    for (int i = 0; i < parcellesList.size(); i++) {
                        viewModel.saveParcelle(parcellesList.get(i));
                    }
                } else {
                    adapter.setParcelles(parcelles);

                }
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private class ParcelleAdapter extends RecyclerView.Adapter<ParcelleAdapter.ParcelleViewHolder> {

        private List<ParcelModel> parcelles;

        public void setParcelles(List<ParcelModel> parcelles) {
            this.parcelles = parcelles;
            notifyDataSetChanged();

        }

        private class ParcelleViewHolder extends RecyclerView.ViewHolder {

            CustomParcelleBinding binding;

            public ParcelleViewHolder(@NonNull CustomParcelleBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

        }

        @NonNull
        @Override
        public ParcelleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            CustomParcelleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custom_parcelle, parent, false);
            binding.setClick(itemClick);
            return new ParcelleViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ParcelleViewHolder holder, int position) {
            ParcelModel parcelle = parcelles.get(position);
            holder.binding.setParcelle(parcelle);
            //LiveData<PlantModel> plant =  plantViewModel.getPlant(parcelle.getIdPlant());
           // holder.binding.setPlant((PlantModel);
            plantViewModel.getPlant(parcelle.getIdPlant()).observe(getActivity(), new Observer<PlantModel>() {
                @Override
                public void onChanged(PlantModel plant) {

                    if(plant != null){
                        holder.binding.setPlant(plant);
                    }
                    else {
                        PlantModel p = new PlantModel();
                        p.setImage(R.drawable.chemin);
                        p.setName("Chemin");
                        holder.binding.setPlant(p);
                    }
                }
            });

        }


        @Override
        public int getItemCount() {
            return parcelles == null ? 0 : parcelles.size();
        }


    }

    public String getDateToday(){
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
       return  currentDate.format(todayDate);

    }
    void planter(int idPlant, int idParcelle) {
        viewModel.update_ParcellePlant(idParcelle, idPlant,getDateToday());
    }

}

package com.abdessamad.jardinage;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autoai.plantation.R;
import com.abdessamad.jardinage.Room.AppDatabase;
import com.abdessamad.jardinage.ViewModels.PlantsViewModel;
import com.autoai.plantation.databinding.CustomPlantBinding;
import com.autoai.plantation.databinding.FrgListPlantsBinding;
import com.autoai.plantation.databinding.PlanterAlertBinding;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Plants_frgmnt_viewPager extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static final String ARG_Parcelle = "parcelle";

    int idParcelle = 0;
    PlantAdapter adapter;
    PlantsViewModel viewModel;
    public String typePlant;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            Bundle args = getArguments();
        idParcelle =  Integer.parseInt(args.get(ARG_Parcelle).toString());
        typePlant = args.get(ARG_OBJECT).toString();
        }
        viewModel = ViewModelProviders.of(this).get(PlantsViewModel.class);
        viewModel.setDao(AppDatabase.getDataBase().plantDao());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.titlePlants);
        FrgListPlantsBinding binding = DataBindingUtil.inflate(inflater,R.layout.frg_list_plants,container,false);
        RecyclerView recyclerView = binding.listRv;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new PlantAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getPlantsByType(typePlant).observe(this, new Observer<List<PlantModel>>() {
            @Override
            public void onChanged(List<PlantModel> plants) {
                if(plants != null){
                    adapter.setPlants(plants);
                }

            }
        });
        return binding.getRoot();
    }

    public interface ItemClick {
        public void onItemClick(View v, Object o);
    }

    private ItemClick itemClick = new ItemClick() {
        @Override
        public void onItemClick(View v, Object o) {
            PlantModel p = (PlantModel) o;
            showAlertDialogButtonClicked(v,p);

        }
    };
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

        private List<PlantModel> plants;

        public void setPlants(List<PlantModel> plants){
            this.plants = plants;
            notifyDataSetChanged();

        }

        private class PlantViewHolder extends RecyclerView.ViewHolder {

            CustomPlantBinding binding;

            public PlantViewHolder(@NonNull  CustomPlantBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

        }

        @NonNull
        @Override
        public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            CustomPlantBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.custom_plant,parent,false);
            binding.setClick(itemClick);
            return new PlantViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
            PlantModel plant = plants.get(position);
            holder.binding.setPlant(plant);
        }


        @Override
        public int getItemCount() {
            return plants == null ? 0 : plants.size();
        }


    }

    public void showAlertDialogButtonClicked(View view, PlantModel plant) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Planter " + plant.getName() + " dans ParcelModel"+idParcelle+" ?");
        // set the custom layout
        PlanterAlertBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.planter_alert,null,false);

        binding.setPlant(plant);

        builder.setView(binding.getRoot());
        AlertDialog dialog = builder.create();
        binding.setClick(new ItemClick() {
            @Override
            public void onItemClick(View v, Object o) {
                PlantsTabListDirections.ActionPlantsFragmentToParcellesFragment action = PlantsTabListDirections.actionPlantsFragmentToParcellesFragment();
                action.setIdPlant(plant.getId());
                action.setIdParcelle(idParcelle);
                Navigation.findNavController(view).navigate(action);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

}
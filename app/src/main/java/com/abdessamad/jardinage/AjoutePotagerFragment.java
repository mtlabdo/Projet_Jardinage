package com.abdessamad.jardinage;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.autoai.plantation.R;
import com.abdessamad.jardinage.Room.AppDatabase;
import com.abdessamad.jardinage.ViewModels.PotagerViewModel;
import com.autoai.plantation.databinding.FrgPotagerAjouteBinding;

public class AjoutePotagerFragment extends Fragment {

    private int id;
    private PotageModel potager;
    private PotagerViewModel viewModel;
    public int getPosition(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }

    private FrgPotagerAjouteBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ajouter potager");
        super.setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this).get(PotagerViewModel.class);
        viewModel.setDao(AppDatabase.getDataBase().potagerDao());

        if (getArguments() !=null){
            id = AjoutePotagerFragmentArgs.fromBundle(getArguments()).getId();
            if (id != 0){
                viewModel.setId(id);
                viewModel.getPotager().observe(this, new Observer<PotageModel>() {
                    @Override
                    public void onChanged(PotageModel potager) {
                        if(potager != null){
                            binding.setPotager(potager);
                        }
                    }
                });
            }
            else {
                potager = new PotageModel();
            }
        }
        else {
            potager = new PotageModel();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.titleAddPotager);
        binding = DataBindingUtil.inflate(inflater,R.layout.frg_potager_ajoute,container,false);
        binding.setPotager(potager);
        binding.setOnSave(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forms[] = getResources().getStringArray(R.array.forme);

                String name = binding.name.getText().toString();
                int forme = getPosition(binding.spinner);
                PotageModel potager
                        = new PotageModel(0,name,forms[forme]);
                potager.setId(id);
                viewModel.savePotager(potager);
                Navigation.findNavController(getView()).popBackStack();
            }
        });
        return binding.getRoot();
    }





}

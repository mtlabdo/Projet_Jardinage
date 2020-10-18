package com.abdessamad.jardinage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autoai.plantation.R;
import com.abdessamad.jardinage.Room.AppDatabase;
import com.abdessamad.jardinage.ViewModels.PotagersViewModel;
import com.autoai.plantation.databinding.CustumPotagerBinding;
import com.autoai.plantation.databinding.FrgListPotagerBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PotagersFragment extends Fragment {

    private PotagersViewModel viewModel;

    public interface OnPotagersFragmentListener {
        List<PotageModel> getPotagers();

        void deletePotager(int position);
    }

    private OnPotagersFragmentListener mListener;
    private PotagerAdapter adapter;

    public PotagersFragment() {

    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PotagersViewModel.class);
        viewModel.setDao(AppDatabase.getDataBase().potagerDao());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.titlePotagers);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        FrgListPotagerBinding binding = DataBindingUtil.inflate(inflater, R.layout.frg_list_potager, container, false);
        binding.setAddPotager(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_potagersFragment_to_AjoutePotagerFragment);
            }
        });




        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new PotagerAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getPotagers().observe(this, new Observer<List<PotageModel>>() {
            @Override
            public void onChanged(List<PotageModel> potagers) {
                if (potagers == null) {

                } else {
                    adapter.setPotagers(potagers);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPotagersFragmentListener) {
            mListener = (OnPotagersFragmentListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ItemClick {
        public void onItemClick(View v, Object o);
    }

    public interface ItemLongClick {
        public boolean onItemLongClick(View v, Object o);
    }

    private ItemClick itemClick = new ItemClick() {
        @Override
        public void onItemClick(View v, Object o) {
            PotageModel potager = (PotageModel) o;
            PotagersFragmentDirections.ActionPotagersFragmentToParcellesFragment action = PotagersFragmentDirections.actionPotagersFragmentToParcellesFragment();
            action.setIdPotager(potager.getId());
            action.setForme(potager.getForm());
            Navigation.findNavController(v).navigate(action);


        }
    };
    private ItemLongClick itemLongClick = new ItemLongClick() {
        @Override
        public boolean onItemLongClick(View v, Object o) {
            PotageModel potager = (PotageModel)o;
            viewModel.delete(potager);
            return false;
        }
    };

    private class PotagerAdapter extends RecyclerView.Adapter<PotagerAdapter.PotagerViewHolder> {

        private List<PotageModel> potagers;

        private class PotagerViewHolder extends RecyclerView.ViewHolder {
            public CustumPotagerBinding binding;

            public PotagerViewHolder(@NonNull CustumPotagerBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

        }

        @NonNull
        @Override
        public PotagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CustumPotagerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custum_potager, parent, false);
            binding.setClick(itemClick);
            binding.setLongClick(itemLongClick);
            return new PotagerViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PotagerViewHolder holder, int position) {
            PotageModel potager = potagers.get(position);
            holder.binding.setPotager(potager);
        }

        public void setPotagers(List<PotageModel> potagers) {
            this.potagers = potagers;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return potagers == null ? 0 : potagers.size();
        }


    }

}

package com.abdessamad.jardinage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdessamad.jardinage.Converters.PlantGenre;
import com.abdessamad.jardinage.ViewModels.PlantsViewModel;
import com.autoai.plantation.R;
import com.abdessamad.jardinage.Room.AppDatabase;
import com.abdessamad.jardinage.Room.CreateData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class PlantsTabList extends Fragment {
    DemoCollectionPagerAdapter demoCollectionPagerAdapter;
    ViewPager viewPager;
    ArrayList<PlantModel> arraylist = new ArrayList<>();
    PlantsViewModel viewModel;
    int idParcelle = 0;
    List<PlantGenre> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         list = Arrays.asList(PlantGenre.values());


        viewModel = ViewModelProviders.of(this).get(PlantsViewModel.class);
        viewModel.setDao(AppDatabase.getDataBase().plantDao());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel.getPlants().observe(this, new Observer<List<PlantModel>>() {
            @Override
            public void onChanged(List<PlantModel> plants) {
                if(plants != null){
                    if(plants.size()<=0){
                        CreateData.createPlant(viewModel);
                    }

                }

            }
        });
        return inflater.inflate(R.layout.layout_pl, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
              idParcelle = PlantsTabListArgs.fromBundle(getArguments()).getIdParcelle();

        }
        demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(demoCollectionPagerAdapter);
    }



public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new Plants_frgmnt_viewPager();
        Bundle args = new Bundle();
        args.putString(Plants_frgmnt_viewPager.ARG_OBJECT, list.get(i).toString());
        args.putInt(Plants_frgmnt_viewPager.ARG_Parcelle, idParcelle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

       // return "OBJECT " + (position + 1);

        return list.get(position).toString();
    }
}


public static class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_list_plants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        TextView textView = (TextView) view.findViewById(R.id.text1);
        textView.setText("sdfsqd");
    }
}
}
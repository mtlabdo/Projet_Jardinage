package com.abdessamad.jardinage.Room;

import com.abdessamad.jardinage.Converters.PlantGenre;
import com.abdessamad.jardinage.PlantModel;
import com.abdessamad.jardinage.ViewModels.PlantsViewModel;
import com.abdessamad.jardinage.enumuration.Semer_mois;
import com.autoai.plantation.R;

import java.util.ArrayList;

public class CreateData {
    public static void createPlant(PlantsViewModel viewModel){


        String mois1 = Semer_mois.Jan.toString();
        String mois2 = Semer_mois.fév.toString();
        String mois3 = Semer_mois.mars.toString();
        String mois4 = Semer_mois.avr.toString();
        String mois5 = Semer_mois.mai.toString();
        String mois6 = Semer_mois.juin.toString();
        String mois7 = Semer_mois.juil.toString();
        String mois8 = Semer_mois.août.toString();
        String mois9 = Semer_mois.sept.toString();
        String mois10 = Semer_mois.oct.toString();
        String mois11 = Semer_mois.nov.toString();
        String mois12 = Semer_mois.déc.toString();

        ArrayList<PlantModel> plants = new ArrayList<>();


        plants.add(new PlantModel("Kokedama", PlantGenre.Feuille,"2000",mois1+"-"+mois2,6,R.drawable.fe1));
        plants.add(new PlantModel("Bonsai", PlantGenre.Feuille,"2000",mois4+"-"+mois5,7,R.drawable.fe2));


        plants.add(new PlantModel("Yummi", PlantGenre.Fruit,"1977",mois8+"-"+mois9,20,R.drawable.fr1));
        plants.add(new PlantModel("Lycium", PlantGenre.Fruit,"1977",mois9+"-"+mois10,11,R.drawable.fr2));
        plants.add(new PlantModel("Framboisier", PlantGenre.Fruit,"1977",mois2+"-"+mois11,11,R.drawable.fr3));

        plants.add(new PlantModel("Yucca", PlantGenre.Racine,"2000",mois1+"-"+mois2,60,R.drawable.r1));
        plants.add(new PlantModel("Asplenium", PlantGenre.Racine,"2000",mois7+"-"+mois1,90,R.drawable.r2));
        plants.add(new PlantModel("Alocasia", PlantGenre.Racine,"2000",mois5+"-"+mois6+"-"+mois10,60,R.drawable.r3));
        plants.add(new PlantModel("Croton", PlantGenre.Racine,"2000",mois1+"-"+mois2,10,R.drawable.r4));
        plants.add(new PlantModel("Ficus", PlantGenre.Racine,"2000",mois7+"-"+mois1,90,R.drawable.r5));


        plants.add(new PlantModel("Grimpantes",PlantGenre.Fleur,"1900",mois1+"-"+mois2,100,R.drawable.f1));
        plants.add(new PlantModel("Argumes", PlantGenre.Fleur,"2000",mois4+"-"+mois2,90,R.drawable.f2));
        plants.add(new PlantModel("Rosiers", PlantGenre.Fleur,"1999",mois9+"-"+mois10,80,R.drawable.f3));
        plants.add(new PlantModel("Bambous", PlantGenre.Fleur,"1978",mois2+"-"+mois11,50,R.drawable.f4));
        plants.add(new PlantModel("Bassin", PlantGenre.Fleur,"2018",mois9+"-"+mois10,60,R.drawable.f5));


        plants.add(new PlantModel("Chemin", PlantGenre.Chem,"1020", null,150, R.drawable.chemin));
        plants.add(new PlantModel("Jardin", PlantGenre.Jard,"1020",null,30,R.drawable.gaz));
        viewModel.createPlants(plants);

    }
}

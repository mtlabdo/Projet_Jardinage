package com.abdessamad.jardinage.ViewModels;

import android.widget.ImageView;
import android.widget.TextView;

import com.abdessamad.jardinage.Converters.PlantGenre;
import com.abdessamad.jardinage.enumuration.Semer_mois;

import androidx.databinding.BindingAdapter;

public class PlantationBindingAdapter {
    @BindingAdapter("momentPlantation")
    public static void setMomentPlantation (TextView view, String moment) {
        String momentsText = "";

        if (moment != null) {
            Semer_mois momentPlantation = null;
            String[] moments = moment.split("-");
            for (int i = 0;i<moments.length;i++){

                momentsText+=momentPlantation.valueOf(moments[i])+" | ";

            }
        }
        view.setText(momentsText);
    }


    @BindingAdapter("typeResource")
    public static void setTypeView (TextView view, PlantGenre typePlant) {
        String type;
        switch (typePlant)
        {

            case Fruit: type =  "légume fruit" ;break;
            case Racine: type =  "légume racine" ;break;
            case Feuille: type =  "légume feuille" ;break;
            case Chem: type =  "Chemin" ;break;
            case Jard: type =  "Jardin" ;break;
            case Fleur: type =  "légume fleur" ;break;
            default:   type =  "Chemin" ;break;

        }
        view.setText(type);
    }


    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }



}

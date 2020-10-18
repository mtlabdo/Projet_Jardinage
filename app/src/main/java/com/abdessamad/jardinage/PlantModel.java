package com.abdessamad.jardinage;


import com.abdessamad.jardinage.Converters.PlantGenre;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "plants")
public class PlantModel implements  Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private PlantGenre type;
    private String annee;
    private String moment;
    private int duree;
    private int image;

    public PlantModel(){
        this.id = 0;

    }
    @Ignore
    public PlantModel(String name, PlantGenre type, String annee, String moment, int duree, int image) {
        this.name = name;
        this.type = type;
        this.annee = annee;
        this.moment = moment;
        this.duree = duree;
        this.image  = image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getAnnee() {
        return annee;
    }

    public String getMoment() {
        return moment;
    }

    public int getDuree() {
        return duree;
    }

    public String getName() {
        return name;
    }

    public PlantGenre getType() {
        return type;
    }

    public String getannee() {
        return annee;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PlantGenre type) {
        this.type = type;
    }

    public void setannee(String annee) {
        this.annee = annee;
    }
}

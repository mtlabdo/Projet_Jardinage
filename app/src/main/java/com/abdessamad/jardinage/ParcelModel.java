package com.abdessamad.jardinage;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "parcelles")
public class ParcelModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int idPlant;
    private int idPotager;
    private String date;


    public ParcelModel(String name, int idPlant, int idPotager, String date) {
        this.name = name;
        this.idPlant = idPlant;
        this.idPotager = idPotager;
        this.date = date;
    }

    @Ignore
    public ParcelModel() {
        this.id = -1;
        this.name = "name";
        this.idPlant = -1;
        this.idPotager = -1;
        this.date = "";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setIdPotager(int idPotager) {
        this.idPotager = idPotager;
    }

    public int getIdPotager() {
        return idPotager;
    }


    public int getIdPlant() {
        return idPlant;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }
}

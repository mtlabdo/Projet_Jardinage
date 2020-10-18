package com.abdessamad.jardinage;

public class CalendarModel {

    private String plant;
    private String potager;
    private String parcelle;
    private String recolteJours;
    private int imagePlant;

    public CalendarModel(String plant, String potager, String parcelle, String recolteJours, int imagePlant) {
        this.plant = plant;
        this.potager = potager;
        this.parcelle = parcelle;
        this.recolteJours = recolteJours;
        this.imagePlant = imagePlant;
    }

    public CalendarModel() {
    }

    public String getPlant() {
        return plant;
    }

    public String getPotager() {
        return potager;
    }

    public String getParcelle() {
        return parcelle;
    }

    public String getRecolteJours() {
        return recolteJours;
    }


    public int getImagePlant() {
        return imagePlant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public void setPotager(String potager) {
        this.potager = potager;
    }

    public void setParcelle(String parcelle) {
        this.parcelle = parcelle;
    }

    public void setRecolteJours(String recolteJours) {
        this.recolteJours = recolteJours;
    }

    public void setImagePlant(int imagePlant) {
        this.imagePlant = imagePlant;
    }
}

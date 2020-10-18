package com.abdessamad.jardinage;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "potagers")
public class PotageModel extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String form;

    @Ignore
    public PotageModel(int id, String name, String form) {
        this.id = id;
        this.name = name;
        this.form = form;
    }

    public PotageModel(){
        this.id = -1;
        this.name="Default";
        this.form="3x3";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getForm() {
        return form;
    }

}

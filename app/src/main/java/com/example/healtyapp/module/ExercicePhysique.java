package com.example.healtyapp.module;

import java.util.ArrayList;

public class ExercicePhysique {

    private String title;
    private String level;
    private ArrayList<String> etapes;
    private double MET;
    private String type;
    private String objectif;

    public ExercicePhysique(){
        this.type="Fitness";
    }

    ExercicePhysique(String title, String level, ArrayList<String> etapes, double met, String objectif){
        this.title=title;

        this.level = level;
        this.etapes = etapes;
        this.MET = met;
        this.type="Fitness";
        this.objectif = objectif;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<String> getEtapes() {
        return etapes;
    }

    public void setEtapes(ArrayList<String> etapes) {
        this.etapes = etapes;
    }



    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMET() {
        return MET;
    }

    public void setMET(double MET) {
        this.MET = MET;
    }
}

package com.example.healtyapp.database_item;

import com.example.healtyapp.module.ExercicePhysique;

public class ActivityPhUser {
    private String id_user;
    private ExercicePhysique exercicePhysique;
    private double poids;
    private  String sexe;
    private int duree;
    private int calories_burned;

    public ActivityPhUser(){

    }

    public ExercicePhysique getExercicePhysique() {
        return exercicePhysique;
    }

    public void setExercicePhysique(ExercicePhysique exercicePhysique) {
        this.exercicePhysique = exercicePhysique;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(int calories_burned) {
        this.calories_burned = calories_burned;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}

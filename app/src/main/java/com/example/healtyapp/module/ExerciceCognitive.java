package com.example.healtyapp.module;

import java.util.ArrayList;

public class ExerciceCognitive {
    private String type,title;
    private ArrayList<String> etapes;
    private int duree;

    public ExerciceCognitive(){

    }

    public ExerciceCognitive( String title,String type, ArrayList<String> etapes) {
        this.type = type;
        this.title = title;
        this.etapes = etapes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getEtapes() {
        return etapes;
    }

    public void setEtapes(ArrayList<String> etapes) {
        this.etapes = etapes;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public static int convertDuree (long m) {
        int minutes = (int) (m / 1000) / 60;
        int seconds = (int) (m / 1000) % 60;

        return minutes + (int) (seconds / 60);
    }
}

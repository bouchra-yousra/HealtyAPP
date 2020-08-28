package com.example.healtyapp.database_item;

public class CognitieActivite {
    String title;
    int duree;

    public CognitieActivite(String title, int duree) {
        this.title = title;
        this.duree = duree;
    }

    //GETTERS
    public String getTitle() {
        return title;
    }

    public int getDuree() {
        return duree;
    }

    //SETTERS
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}

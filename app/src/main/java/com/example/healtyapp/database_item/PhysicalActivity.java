package com.example.healtyapp.database_item;

public class PhysicalActivity {
    String title;
    int duree;
    private int calories_burned;

    public PhysicalActivity (String title, int duree,int calories_burned) {
        this.title = title;
        this.duree = duree;
        this.calories_burned = calories_burned;
    }

    //GETTERS
    public String getTitle() {
        return title;
    }

    public int getDuree() {
        return duree;
    }

    public int getCalories_burned() {
        return calories_burned;
    }

    //SETTERS
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}

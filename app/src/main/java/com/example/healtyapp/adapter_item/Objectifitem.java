package com.example.healtyapp.adapter_item;

public class Objectifitem {
    static int nb = 0;
    public int id;
    public String name;
    int progress_physique;
    int progress_cognitive;
    int progress_nutrition;

    public int getId() {
        return id;
    }

    public Objectifitem(String name) {
        this.name = name;
    }

    public Objectifitem(String name, int progress_physique, int progress_cognitive, int progress_nutrition) {
        if (nb == 3)
            nb = 0;
        this.id = nb;
        nb++;
        this.name = name;
        this.progress_physique = progress_physique;
        this.progress_cognitive = progress_cognitive;
        this.progress_nutrition = progress_nutrition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgress_physique(int progress_physique) {
        this.progress_physique = progress_physique;
    }

    public void setProgress_cognitive(int progress_cognitive) {
        this.progress_cognitive = progress_cognitive;
    }

    public void setProgress_nutrition(int progress_nutrition) {
        this.progress_nutrition = progress_nutrition;
    }

    public String getName() {
        return name;
    }

    public int getProgress_physique() {
        return progress_physique;
    }

    public int getProgress_cognitive() {
        return progress_cognitive;
    }

    public int getProgress_nutrition() {
        return progress_nutrition;
    }
}

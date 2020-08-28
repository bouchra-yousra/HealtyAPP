package com.example.healtyapp.database_item;

import com.example.healtyapp.module.Calorie;

public class Aliment {
    private  String nom;

    private String categorie;
    private Calorie calorie;

    private int pourcentage_eau;

    public Aliment(){

    }

    public  Aliment(String nom,String categorie,Calorie calorie){
        this.nom= nom;

        this.categorie = categorie;
        this.calorie = calorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Calorie getCalorie() {
        return calorie;
    }

    public void setCalorie(Calorie calorie) {
        this.calorie = calorie;
    }

    public int getPourcentage_eau() {
        return pourcentage_eau;
    }

    public void setPourcentage_eau(int pourcentage_eau) {
        this.pourcentage_eau = pourcentage_eau;
    }
}

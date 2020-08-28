package com.example.healtyapp.database_item;

import com.example.healtyapp.vue.center_activities.MainMyMenu;

import java.util.ArrayList;

public class AlimentData {

    String nom;
    int quantite;
    public static int nb = 0;

    public AlimentData(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
        nb++;
    }

    //GETTERS
    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }
}

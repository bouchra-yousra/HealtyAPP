package com.example.healtyapp.database_item;

public class Aliment_historique {

    AlimentData alimentData;
    Aliment aliment;

    public Aliment_historique(AlimentData alimentData, Aliment aliment) {
        this.alimentData = alimentData;
        this.aliment = aliment;
    }

    //GETTERS
    public AlimentData getAlimentData() {
        return alimentData;
    }

    public Aliment getAliment() {
        return aliment;
    }
}

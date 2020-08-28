package com.example.healtyapp.module;

import java.util.Calendar;

public class ObjectiveUser {
    boolean active;
    String date_debut;
    String date_fin;
    String id;
    int taux_progress;

    public ObjectiveUser(String id) {
        Calendar calSelected = Calendar.getInstance();
        String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                + (calSelected.get(Calendar.MONTH) + 1)
                + calSelected.get(Calendar.YEAR);

        this.active = true;
        this.date_debut = today;
        this.id = id;
        this.taux_progress = 0;
    }

    static int calcul_progression(int activite_physique, int activivte_cognitive, int activite_nutrition) {
        return 0;
    }

    //METHODES
    public void quiter_objective () {
        //today
        Calendar calSelected = Calendar.getInstance();
        String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                + (calSelected.get(Calendar.MONTH) + 1)
                + calSelected.get(Calendar.YEAR);

        this.active = false;
        this.date_fin = today;
    }

    public int addToTauxProgress (int taux_progress) {
        this.taux_progress += taux_progress;
        return this.taux_progress;
    }

    //GETTERS
    public boolean isActive() {
        return active;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public String getId() {
        return id;
    }

    public int getTaux_progress() {
        return taux_progress;
    }

}

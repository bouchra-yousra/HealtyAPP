package com.example.healtyapp.module;

public class PreventionBurnOut extends ObjectiveUser {
    public static final String titre = "Prevent Burnout";

    public PreventionBurnOut() {
        super(titre);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition) {
        int a = 3;
        int b = 4;
        int c = 2;

        return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c) / (a+b+c);
    }
}
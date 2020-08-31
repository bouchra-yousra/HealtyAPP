package com.example.healtyapp.module;

public class PreventionBurnOut extends ObjectiveUser {
    public static final String titre = "Prevent Burnout";
    static final int a = 3;
    static final int b = 4;
    static final int c = 2;

    public PreventionBurnOut() {
        super(titre);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition) {
      return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c) / (a+b+c);
    }
}
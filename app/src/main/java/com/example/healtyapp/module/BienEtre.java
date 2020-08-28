package com.example.healtyapp.module;

public class BienEtre extends ObjectiveUser {
    public static final String titre = "Stay Healthy";

    public BienEtre() {
        super(titre);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition) {
        int a = 3;
        int b = 3;
        int c = 3;

        return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c) / (a+b+c);
    }
}

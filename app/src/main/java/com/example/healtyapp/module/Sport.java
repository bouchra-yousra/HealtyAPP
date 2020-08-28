package com.example.healtyapp.module;

public class Sport extends ObjectiveUser {
    public static final String titre = "Be More Sporty";

    public Sport() {
        super(titre);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition) {
        int a = 4;
        int b = 3;
        int c = 2;

        return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c) / (a+b+c);
    }
}

package com.example.healtyapp.module;

public class Sport extends ObjectiveUser {
    public static final String titre = "Be More Sporty";
    static final int a = 34;
    static final int b = 3;
    static final int c = 2;

    public Sport() {
        super(titre);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition) {
        return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c) / (a+b+c);
    }
}

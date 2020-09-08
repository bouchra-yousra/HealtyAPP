package com.example.healtyapp.module;

public class BienEtre extends ObjectiveUser {
    public static final String titre = "Stay Healthy";
    static final int a = 3;
    static final int b = 3;
    static final int c = 3;
    static final int d = 3;

    public BienEtre() {
        super(titre);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition) {
        return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c) / (a+b+c);
    }

    public static int calcul_progression (int activite_physique,int activivte_cognitive,int activite_nutrition,int water_activity) {
        return (activite_physique * a + activivte_cognitive * b + activite_nutrition * c+ water_activity * d) / (a+b+c+d);
    }
}

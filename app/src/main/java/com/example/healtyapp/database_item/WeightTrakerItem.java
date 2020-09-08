package com.example.healtyapp.database_item;

import java.util.Calendar;

public class WeightTrakerItem {
    private static int ideal_weight;
    private int weight; //en kg
    private String date;

    public WeightTrakerItem(int weight) {
        this.weight = weight;
        this.date = initDate();
    }

    private String initDate() {
        //today
        Calendar calSelected = Calendar.getInstance();
        String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                + (calSelected.get(Calendar.MONTH) + 1)
                + calSelected.get(Calendar.YEAR);
        return today;
    }

    //METHODES
    public int calculate_weight (int weight) {
        return ideal_weight - weight;
    }

    //GETTERS
    public static int getIdeal_weight() {
        return ideal_weight;
    }

    public String getDate() {
        return date;
    }

    public int getWeight() {
        return weight;
    }

    //SETTERS

    public static void setIdeal_weight(int ideal_weight) {
        WeightTrakerItem.ideal_weight = ideal_weight;
    }
}

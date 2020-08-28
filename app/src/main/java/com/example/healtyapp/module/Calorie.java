package com.example.healtyapp.module;

public class Calorie {
    private double lipide;
    private  double protine;
    private double glicide;
    private  double total;

    public Calorie(){

    }

    public Calorie(double lipide,double protine,double glicide){
        this.lipide = lipide;
        this.protine = protine;
        this.glicide = glicide;
        this.total = (lipide*9)+(protine*4) + (glicide*4);
    }



    public double getLipide() {
        return lipide;
    }

    public void setLipide(int lipide) {
        this.lipide = lipide;
    }

    public double getProtine() {
        return protine;
    }

    public void setProtine(int protine) {
        this.protine = protine;
    }

    public double getGlicide() {
        return glicide;
    }

    public void setGlicide(int glicide) {
        this.glicide = glicide;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

package com.example.healtyapp.module;

import com.example.healtyapp.Enumeration.gender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User {

    private String idUser;
    private String first_name;
    private String last_name;
    private String age;
    private String username;
    private String email;
    private String password;
    private ArrayList<String> objectifs;
    private String taille;
    private String poids;
    private String sexe;
    private String maladie;
    private String level;
    private double metabolisme;
    private double besoin_energy;
    private double besoin_eau;

    private Date date_naiss;

    public User(){
    }

    public Date getDate_naiss() {
        return date_naiss;
    }

    public void setMetabolisme(double metabolisme) {
        this.metabolisme = metabolisme;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public ArrayList<String> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(ArrayList<String> objectifs) {
        this.objectifs = objectifs;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getMetabolisme() {
        return metabolisme;
    }

    public void setMetabolisme() {

        this.metabolisme = Calculermetabolisme();
    }

    private double Calculermetabolisme(){

        if(this.sexe.equals(gender.HOMME)){


            return (13.7516*Double.parseDouble(this.poids))+
                    (500.33*convertToMeter(Double.parseDouble(this.taille)))-//c un moin
                    (6.7550*Integer.parseInt(this.age))+66.473;
        }else{
            return (9.5634*Double.parseDouble(this.poids))+
                    (184.96*convertToMeter(Double.parseDouble(this.taille)))-
                    (4.6756*Integer.parseInt(this.age))+655.0955;
        }


    }

    private double convertToMeter(double taille_cm){
        return  taille_cm/100.;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public double getBesoin_energy() {
        return besoin_energy;
    }

    public void setBesoin_energy() {
        switch (Integer.parseInt(this.level)){
            case 0 :
                this.besoin_energy = this.metabolisme * 1.375;
                break;
            case 1 :
                this.besoin_energy = this.metabolisme * 1.56;
                break;
            case 2 :
                this.besoin_energy = this.metabolisme * 1.64;
                break;
            case 3 :
                this.besoin_energy = this.metabolisme * 1.82;
                break;
        }
    }

    public double getBesoin_eau() {
        return besoin_eau;
    }

    public void setBesoin_eau() {
        this.besoin_eau = (double)(((Integer.parseInt(this.poids)-20)*15)+1500);
    }
}

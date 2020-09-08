package com.example.healtyapp.module;

import android.widget.Toast;

import androidx.annotation.IntegerRes;

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
    //private String birthday_;

/* private ArrayList<String> birthday ;

    public ArrayList<Integer> getBirthday() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(Integer.parseInt(birthday.get (0)));
        a.add(Integer.parseInt(birthday.get (1)));
        a.add(Integer.parseInt(birthday.get (2)));
        return a;
    }

    public void setBirthday(Birthday birthday) {
//        this.birthday.clear();
        this.birthday = new ArrayList<> ();
        this.birthday.add(String.valueOf(birthday.day));
        this.birthday.add(String.valueOf(birthday.month));
        this.birthday.add(String.valueOf(birthday.year));
    }*/
    public User(){
    }


    //BESOIN MACRON
    public double calcul_besoin_protine () {
        //30% de besoin energitique , 4 puisqu'on a 4 cal dans 1g
        return  ((this.besoin_energy * 30) / 100) / 4;
    }

    public double calcul_besoin_glucide () {
        //40% de besoin energitique , 4 puisqu'on a 4 cal dans 1g
        return  ((this.besoin_energy * 40) / 100) / 4;
    }

    public double calcul_besoin_lipide () {
        //30% de besoin energitique , 9 puisqu'on a 4 cal dans 1g
        return  ((this.besoin_energy * 30) / 100) / 9;
    }

    private double Calculermetabolisme(){

        if(this.sexe.equals(gender.HOMME)){


            return (13.7516*Double.parseDouble(this.poids))+
                    (500.33*convertToMeter(Double.parseDouble(this.taille)))-
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

    private ArrayList<Integer> divise_Birthday (String word){
        ArrayList arrayList = new ArrayList();
        String a="";

        for(int i=0;i<word.length();i++){
            if(String.valueOf(word.charAt(i)).equals(";")){
                arrayList.add(Integer.parseInt(a));
                a="";
            }else{
                a=a+word.charAt(i);
            }
        }
        return  arrayList;
    }

    private Birthday getBirthdayBirthday (String word){
        ArrayList<Integer> arrayList = new ArrayList();
        String a="";

        for(int i=0;i<word.length();i++){
            if(String.valueOf(word.charAt(i)).equals(";")){
                arrayList.add(Integer.parseInt(a));
                a="";
            }else{
                a=a+word.charAt(i);
            }
        }
        return  new Birthday(arrayList.get(0),arrayList.get(1),arrayList.get(3));
    }

        /*public String getBirthday_() {
        return birthday_;
    }*/

    public void setBirthday_(String birthday_) {
        this.age = birthday_;
    }

    public void setBirthday_(Calendar c) {
        Birthday b = new Birthday(c);
        this.setBirthday_(b.getDay()+";"+b.getMonth()+";"+b.getYear()+";");
    }

    public void setBirthday_(Birthday b) {
        this.setBirthday_(b.getDay()+";"+b.getMonth()+";"+b.getYear()+";");
    }

    //GETTERS
    public String getIdUser() {
        return idUser;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTaille() {
        return taille;
    }

    public String getPoids() {
        return poids;
    }

    public String getSexe() {
        return sexe;
    }

    public double getBesoin_energy() {
        return besoin_energy;
    }

    public double getBesoin_eau() {
        return besoin_eau;
    }


    public String getLevel() {
        return level;
    }

    public Double getMetabolisme() {
        return metabolisme;
    }

    public ArrayList<String> getObjectifs() {
        return objectifs;
    }

    public String getMaladie() {
        return maladie;
    }


    public int getAgeFromBirthday (Birthday b) {
        return b.calculate_age();
    }

    public String getAge_() {
        if (age.length() == 2)
            return age;
        else
            return String.valueOf(getAgeFromBirthday(getBirthdayBirthday(age)));
    }

    public String getAge() {
        return age;
    }



    //SETTERS
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

     public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setMetabolisme(double metabolisme) {
        this.metabolisme = metabolisme;
    }

    public void setMetabolisme() {

        this.metabolisme = Calculermetabolisme();
    }

    public void setObjectifs(ArrayList<String> objectifs) {
        this.objectifs = objectifs;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setAge(String age) {
        this.age = age;
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

    public void setBesoin_eau() {
        this.besoin_eau = (double)(((Integer.parseInt(this.poids)-20)*15)+1500);
    }

}

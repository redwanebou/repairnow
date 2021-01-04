package com.example.repairnow;

public class Autos {
    String merk,model,motor, omschrijving,brandstof,naam,telefoon,adres;


    public Autos(String merk, String model, String motor, String brandstof, String omschrijving,String naam, String telefoon,String adres) {
        this.merk = merk;
        this.model = model;
        this.motor = motor;
        this.brandstof = brandstof;
        this.omschrijving = omschrijving;
        this.naam = naam;
        this.telefoon = telefoon;
        this.adres = adres;
    }

    public String getAdres(){return adres;}
    public void setAdres(String adres){this.adres = adres;}

    public String getNaam(){return naam;}
    public void setNaam(String naam){this.naam = naam;}

    public String getTelefoon(){return telefoon;}
    public void setTelefoon(String telefoon){this.telefoon = telefoon;}


    public String getMerk() {
        return merk;
    }
    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getBrandstof() {
        return brandstof;
    }
    public void setBrandstof(String brandstof) {
        this.brandstof = brandstof;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}

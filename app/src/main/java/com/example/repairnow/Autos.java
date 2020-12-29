package com.example.repairnow;

public class Autos {
    String merk,model,motor, omschrijving,brandstof;


    public Autos(String merk, String model, String motor, String brandstof, String omschrijving) {
        this.merk = merk;
        this.model = model;
        this.motor = motor;
        this.brandstof = brandstof;
        this.omschrijving = omschrijving;
    }

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

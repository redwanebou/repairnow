package com.example.repairnow;

public class Opslag {
    String naam,email,wachtwoord,keuze;


    public Opslag(String naam, String email, String wachtwoord, String keuze) {
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
        this.keuze = keuze;
    }

    public String getNaam() {
        return naam;
    }
    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getKeuze() {
        return keuze;
    }

    public void setKeuze(String keuze) {
        this.keuze = keuze;
    }
}

package com.example.repairnow;

public class Opslag {
    String naam,email,wachtwoord, keuze,telefoon;


    public Opslag(String naam, String email, String wachtwoord, String telefoon, String keuze) {
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
        this.telefoon = telefoon;
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

    public String getTelefoon() {
        return telefoon;
    }
    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }

    public String getKeuze() {
        return keuze;
    }

    public void setKeuze(String keuze) {
        this.keuze = keuze;
    }
}

package com.example.lp.rest;

/**
 * Created by lp on 14/01/2016.
 */
public class Ville {

    protected String nom;
    protected String maj;
    protected int codePostal;
    protected float longitude;
    protected float latitude;

    public Ville(String nom, String maj, int codePostal, float longitude, float latitude) {
        this.nom = nom;
        this.maj = maj;
        this.codePostal = codePostal;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Ville() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMaj() {
        return maj;
    }

    public void setMaj(String maj) {
        this.maj = maj;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}

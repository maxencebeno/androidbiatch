package com.example.lp.rest;

/**
 * Created by lp on 14/01/2016.
 */
public class Ville {

    protected String nom;
    protected String maj;
    protected String codePostal;
    protected String longitude;
    protected String latitude;
    protected String codeinsee;
    protected String coderegion;
    protected String eloignement;

    public Ville(String nom, String maj, String codePostal, String longitude, String latitude, String codeinsee, String coderegion, String eloignement) {
        this.nom = nom;
        this.maj = maj;
        this.codePostal = codePostal;
        this.longitude = longitude;
        this.latitude = latitude;
        this.codeinsee = codeinsee;
        this.coderegion = coderegion;
        this.eloignement = eloignement;
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

    public String getCodePostal() {

        return codePostal;
    }

    public void setCodePostal(String codePostal) {

        this.codePostal = codePostal;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCodeinsee() {
        return codeinsee;
    }

    public void setCodeinsee(String codeinsee) {
        this.codeinsee = codeinsee;
    }

    public String getCoderegion() {
        return coderegion;
    }

    public void setCoderegion(String coderegion) {
        this.coderegion = coderegion;
    }

    public String getEloignement() {
        return eloignement;
    }

    public void setEloignement(String eloignement) {
        this.eloignement = eloignement;
    }

    @Override
    public String toString() {
        return this.getNom() + ", " + this.getCodePostal() + ", " + this.getLatitude() + ", " + this.getLongitude();
    }
}

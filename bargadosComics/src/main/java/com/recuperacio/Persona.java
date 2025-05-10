package com.recuperacio;

public class Persona {

    private String nom;

    public Persona(String nom, String cognom) {
        this.nom = nom;
        this.cognom = cognom;
    }
    private String cognom;

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return this.cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }
    
}

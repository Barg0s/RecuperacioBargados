package com.recuperacio;

public class Manga {
    private int id;
    private String titol;
    private float preu;
    private String nom;
    private String cognom;
    private String sinopsi;
    private String isbn;
    private String DataPublicacio;
    private int pags;
    private String portada;
    public Manga(int id,String titol, float preu, String nom, String cognom, String sinopsi, String isbn, String DataPublicacio, int pags, String portada) {
       this.id = id;
        this.titol = titol;
        this.preu = preu;
        this.nom = nom;
        this.cognom = cognom;
        this.sinopsi = sinopsi;
        this.isbn = isbn;
        this.DataPublicacio = DataPublicacio;
        this.pags = pags;
        this.portada = portada;
    }

    public Manga(String titol, float preu, String nom, String cognom, String sinopsi, String isbn, String DataPublicacio, int pags, String portada) {
        this.titol = titol;
        this.preu = preu;
        this.nom = nom;
        this.cognom = cognom;
        this.sinopsi = sinopsi;
        this.isbn = isbn;
        this.DataPublicacio = DataPublicacio;
        this.pags = pags;
        this.portada = portada;
    }



    
    public String getTitol() {
        return this.titol;
    }

    public int getId(){
        return this.id;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }

    public float getPreu() {
        return this.preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

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

    public String getSinopsi() {
        return this.sinopsi;
    }

    public void setSinopsi(String sinopsi) {
        this.sinopsi = sinopsi;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDataPublicacio() {
        return this.DataPublicacio;
    }

    public void setDataPublicacio(String DataPublicacio) {
        this.DataPublicacio = DataPublicacio;
    }

    public int getPags() {
        return this.pags;
    }

    public void setPags(int pags) {
        this.pags = pags;
    }

    public String getPortada() {
        return this.portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
    

}


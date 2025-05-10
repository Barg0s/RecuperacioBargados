package com.recuperacio;

public class Autor extends Persona{
    private String pais;
    private int any_naixement;
    private String foto;

    public Autor(String nom,String cognom,String pais, int any_naixement, String foto) {
        super(nom, cognom);
        this.pais = pais;
        this.any_naixement = any_naixement;
        this.foto = foto;
    }
    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getAny_naixement() {
        return this.any_naixement;
    }

    public void setAny_naixement(int any_naixement) {
        this.any_naixement = any_naixement;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}



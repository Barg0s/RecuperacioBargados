package com.recuperacio;

public class Stock {
    private int idManga;         
    private int quantitat;
    private String estat;

    public Stock(int idManga, int quantitat) {
        this.idManga = idManga;
        setQuantitat(quantitat);     }

    public int getIdManga() {
        return idManga;
    }

    public void setIdManga(int idManga) {
        this.idManga = idManga;
    }


    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
        determinarEstat(quantitat); 
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public void determinarEstat(int n) {
        if (n <= 0) {
            setEstat("Esgotat");
        } else {
            setEstat("En Stock");
        }
    }
}

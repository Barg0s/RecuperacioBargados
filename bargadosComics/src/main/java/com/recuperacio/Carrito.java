package com.recuperacio;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private static Carrito instancia;  
    private final List<Manga> items;
    private float preu;

    private Carrito() {
        this.items = new ArrayList<>();
        this.preu = 0.0f;  
    }

    public static Carrito getInstance() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    public void agregar(Manga manga) {
        items.add(manga);
        preu += manga.getPreu(); 
    }

    public void eliminar(Manga manga) {
        items.remove(manga);
        preu -= manga.getPreu();  
    }

    public void vaciar() {
        items.clear(); 
        preu = 0.0f; 
    }

    public float getPreu() {
        return this.preu;  
    }

    public void setPreu(float preu) {
        this.preu = preu; 
    }

    public List<Manga> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (Manga manga : items) {
            total += manga.getPreu();
        }
        return total;
    }

}

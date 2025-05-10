package com.recuperacio;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private static Carrito instancia;  
    private final List<Manga> items;
    private float preu;

    // Constructor privado
    private Carrito() {
        this.items = new ArrayList<>();
    }

    public static Carrito getInstance() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    public void agregar(Manga manga) {
        items.add(manga);
    }

    public void eliminar(Manga manga) {
        items.remove(manga);
    }

    public void vaciar() {
        items.clear();
        preu = 0;
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
        return items.stream()
                    .mapToDouble(Manga::getPreu)
                    .sum();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }
}

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
        this.preu = 0.0f;  // Inicializamos el precio a 0
    }

    public static Carrito getInstance() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    // Método para agregar un manga al carrito
    public void agregar(Manga manga) {
        items.add(manga);
        preu += manga.getPreu();  // Aumentamos el precio con el precio del manga añadido
    }

    // Método para eliminar un manga del carrito
    public void eliminar(Manga manga) {
        items.remove(manga);
        preu -= manga.getPreu();  // Restamos el precio del manga eliminado
    }

    // Método para vaciar el carrito
    public void vaciar() {
        items.clear();  // Limpiamos la lista de mangas
        preu = 0.0f;  // Restablecemos el precio a 0
    }

    // Método para obtener el precio total del carrito
    public float getPreu() {
        return this.preu;  // Devuelve el precio total
    }

    // Método para establecer el precio total (aunque no parece necesario con getTotal)
    public void setPreu(float preu) {
        this.preu = preu;  // Establece el precio manualmente, pero no lo usamos normalmente
    }

    // Método para obtener los items del carrito
    public List<Manga> getItems() {
        return items;
    }

    // Método para calcular el total de los items en el carrito
    public double getTotal() {
        return items.stream()
                    .mapToDouble(Manga::getPreu)
                    .sum();  // Esto devuelve el total de la suma de los precios de los mangas en el carrito
    }
}

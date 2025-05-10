package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Alert;

public class autorDao implements Dao<Autor> {

    public int getAutorId(String nom, String cognom) {
        AppData db = AppData.getInstance();
        String sql = "SELECT id FROM autor WHERE nom = '" + nom + "' AND cognom = '" + cognom + "'";
        ArrayList<HashMap<String, Object>> resultat = db.query(sql);
        if (!resultat.isEmpty()) {
            return (int) resultat.get(0).get("id");
        } else {
            System.out.println("Autor no trobat.");
            return -1;
        }
    }

    public void add(Autor autor) {
        AppData db = AppData.getInstance();

        String sql = "INSERT INTO autor (nom, cognom, pais, any_naixement, foto) VALUES ('" 
                    + autor.getNom() + "', '" 
                    + autor.getCognom() + "', '" 
                    + autor.getPais() + "', " 
                    + autor.getAny_naixement() + ", '" 
                    + autor.getFoto() + "')";
        
        try {
            db.update(sql);
            System.out.println("Autor afegit correctament.");
        } catch (Exception e) {
            System.out.println("Error en afegir l'autor: " + e.getMessage());
        }
    }

    public void update(int id, Autor autor) {
        AppData db = AppData.getInstance();

        String sql = "UPDATE autor SET "
                    + "nom = '" + autor.getNom() + "', "
                    + "cognom = '" + autor.getCognom() + "', "
                    + "pais = '" + autor.getPais() + "', "
                    + "any_naixement = " + autor.getAny_naixement() + ", "
                    + "foto = '" + autor.getFoto() + "' "
                    + "WHERE id = " + id;

        try {
            db.update(sql);
            System.out.println("Autor actualitzat correctament.");
        } catch (Exception e) {
            System.out.println("Error en actualitzar l'autor: " + e.getMessage());
        }
    }

    public void delete(int id) {
        AppData db = AppData.getInstance();

        try {
            // Primero borra los mangas del autor (si aplica)
            String sqlManga = "DELETE FROM manga WHERE id_autor = " + id;
            db.update(sqlManga);

            // Luego borra el autor
            String sqlAutor = "DELETE FROM autor WHERE id = " + id;
            db.update(sqlAutor);

            System.out.println("Autor i els seus mangas eliminats.");
        } catch (Exception e) {
            System.out.println("Error en eliminar l'autor: " + e.getMessage());
        }
    }

}

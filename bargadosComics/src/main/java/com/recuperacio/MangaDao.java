package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;

public class MangaDao implements Dao<Manga> {

    public int getAutorId(String nom, String cognom) {
        AppData db = AppData.getInstance();
    
        String sql = "SELECT id FROM autor WHERE nom = '" + nom + "' AND cognom = '" + cognom + "'";
        ArrayList<HashMap<String, Object>> resultat = db.query(sql);
    
        if (resultat.isEmpty()) {
            System.err.println("⚠️ Autor no encontrado: " + nom + " " + cognom);
            return -1; 
        }
    
        return (int) resultat.get(0).get("id");
    }
    

    public void add(Manga manga) {
        AppData db = AppData.getInstance();
    
        int idAutor = getAutorId(manga.getNom(), manga.getCognom());
    
        String sql = "INSERT INTO manga (titol, preu, id_autor, sinopsi, ISBN, data_publicacio, pags, portada) VALUES ('" 
                    + manga.getTitol() + "', " 
                    + manga.getPreu() + ", " 
                    + idAutor + ", '" 
                    + manga.getSinopsi() + "', " 
                    + manga.getIsbn() + ", '" 
                    + manga.getDataPublicacio() + "', " 
                    + manga.getPags() + ", '" 
                    + manga.getPortada() + "')";
        
        db.update(sql);
        stockDao stockDao = new stockDao();
        ArrayList<HashMap<String, Object>> newIdResult = db.query("SELECT last_insert_rowid();");
        int newId = ((Number) newIdResult.get(0).get("last_insert_rowid()")).intValue();
        

        Stock stockManga = new Stock(newId, 1);
        stockDao.add(stockManga);
    
    }
    
    public void update(int id, Manga manga) {
        AppData db = AppData.getInstance();
        
        int idAutor = getAutorId(manga.getNom(), manga.getCognom());
    
        if (manga.getTitol() == null || manga.getTitol().isEmpty()) {
            System.out.println("El título no puede estar vacío");
            return;
        }
        

        String titol = (manga.getTitol().replace("'", "''"));
        String sinopsi = (manga.getSinopsi().replace("'", "''"));

        String sql = "UPDATE manga SET "
                    + "titol = '" + titol + "', "
                    + "preu = " + manga.getPreu() + ", "
                    + "id_autor = " + idAutor + ", "
                    + "sinopsi = '" + sinopsi + "', "
                    + "ISBN = " + manga.getIsbn() + ", "
                    + "data_publicacio = '" + manga.getDataPublicacio() + "', "
                    + "pags = " + manga.getPags() + ", "
                    + "portada = '" + manga.getPortada() + "' "
                    + "WHERE id_manga = " + id;
    
        try {
            db.update(sql);
            System.out.println("Manga actualizado correctamente");
        } catch (Exception e) {
            System.out.println("Error al actualizar el manga: " + e.getMessage());
        }
    }
    

    public void delete(int id){
        AppData db = AppData.getInstance();

        String sql = "DELETE FROM manga WHERE id_manga = " + id;
        db.update(sql);

    }
}

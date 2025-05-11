package com.recuperacio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UsuariDao {

    public String getUserDNI(String nom, String cognom) {
        AppData db = AppData.getInstance();

        String sql = "SELECT dni FROM usuaris WHERE nom = '" + nom + "' AND cognom = '" + cognom + "'";
        ArrayList<HashMap<String, Object>> resultat = db.query(sql);
        String dni = (String) resultat.get(0).get("dni");
        return dni;
    }

    public void add(Usuari usuari) {
        AppData db = AppData.getInstance();
    
        // Obtener el DNI del usuari
    
        // Construir la consulta SQL con la ID del usuari
        String sql = "INSERT INTO usuaris (nom, cognom, password, rol, email, dni) VALUES ('" 
                    + usuari.getNom() + "', '" 
                    + usuari.getCognom() + "', '" 
                    + usuari.getPassword() + "', '" 
                    + usuari.getRol() + "', '" 
                    + usuari.getEmail() + "', '" 
                    + usuari.getDni() + "')";
        
        db.update(sql);
    }
    
    public void update(String dni, Usuari usuari) {
        AppData db = AppData.getInstance();
        
        // Obtener el DNI del usuari
        //String dniUsuario = getUserDNI(usuari.getNom(), usuari.getCognom());
    
        // Verificar que los datos del usuari no sean nulos o vacíos
        if (usuari.getNom() == null || usuari.getNom().isEmpty()) {
            System.out.println("El nombre no puede estar vacío");
            return;
        }
        
        // Crear la consulta SQL para actualizar el usuari
        String sql = "UPDATE usuaris SET "
        + "nom = '" + usuari.getNom() + "', "
        + "cognom = '" + usuari.getCognom() + "', "
        + "password = '" + usuari.getPassword() + "', "
        + "rol = '" + usuari.getRol() + "', "
        + "email = '" + usuari.getEmail() + "' "   // Aquí falta el cierre de comillas en email
        + "WHERE dni = '" + dni + "'";  // También el DNI debe ir entre comillas, porque es un string

    
        try {
            // Ejecutar la consulta de actualización
            db.update(sql);
            System.out.println("usuari actualizado correctamente");
        } catch (Exception e) {
            // Manejar cualquier excepción de base de datos
            System.out.println("Error al actualizar el usuari: " + e.getMessage());
        }
    }
    

    public void delete(String dni){
        AppData db = AppData.getInstance();
    
        // 1. Obtener el id_user asociado al dni
        String sql3 = "SELECT id_user FROM usuaris WHERE dni = '" + dni + "'";
        ArrayList<HashMap<String, Object>> result = db.query(sql3);
    
        if (!result.isEmpty()) {
            int idUser = (int) result.get(0).get("id_user");
    
            // 2. Eliminar registros relacionados en otras tablas
            String sql2 = "DELETE FROM registre_vendes WHERE id_user = " + idUser;
            db.update(sql2);
        }
    
        // 3. Eliminar el usuario
        String sql = "DELETE FROM usuaris WHERE dni = '" + dni + "'";
        db.update(sql);
    }


public boolean existeixDni(String dni) {
    AppData db = AppData.getInstance();
    String sql = "SELECT COUNT(*) AS total FROM usuaris WHERE dni = '" + dni + "'";
    ArrayList<HashMap<String, Object>> result = db.query(sql);

    if (!result.isEmpty()) {
        Object totalObj = result.get(0).get("total");
        int count = 0;

        if (totalObj instanceof Number) {
            count = ((Number) totalObj).intValue();
        } else if (totalObj instanceof String) {
            try {
                count = Integer.parseInt((String) totalObj);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return count > 0;
    }

    return false;
}


public boolean existeixCorreu(String correu) {
    AppData db = AppData.getInstance();
    String sql = "SELECT COUNT(*) AS total FROM usuaris WHERE email = '" + correu + "'";
    ArrayList<HashMap<String, Object>> result = db.query(sql);

    if (!result.isEmpty()) {
        Object totalObj = result.get(0).get("total");
        int count = 0;

        if (totalObj instanceof Number) {
            count = ((Number) totalObj).intValue();
        } else if (totalObj instanceof String) {
            try {
                count = Integer.parseInt((String) totalObj);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return count > 0;
    }

    return false;
}



}
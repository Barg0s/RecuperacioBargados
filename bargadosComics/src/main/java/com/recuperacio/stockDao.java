package com.recuperacio;


public class stockDao implements Dao<Stock> {


    public void add(Stock stock) {
        AppData db = AppData.getInstance();
    
        // Obtener el DNI del usuari
    
        // Construir la consulta SQL con la ID del usuari
        String sql = "INSERT INTO STOCK (id_manga,quantitat,estat) VALUES ('" 
                    + stock.getIdManga() + "', '" 
                    + stock.getQuantitat() + "', '" 
                    + stock.getEstat() + "')";
        
        db.update(sql);
    }
    
    public void update(int id, Stock stock) {
        AppData db = AppData.getInstance();
        
        
        // Crear la consulta SQL para actualizar el usuari
        String sql = "UPDATE stock SET "
                    + "id_manga = '" + stock.getIdManga() + "', "
                    + "quantitat = '" + stock.getQuantitat() + "', "
                    + "estat = '" + stock.getEstat() + "' "
                    + "WHERE id = " + id;
    
        try {
            // Ejecutar la consulta de actualización
            db.update(sql);
            System.out.println("usuari actualizado correctamente");
        } catch (Exception e) {
            // Manejar cualquier excepción de base de datos
            System.out.println("Error al actualizar el usuari: " + e.getMessage());
        }
    }
    

    public void delete(int id){
        AppData db = AppData.getInstance();

        String sql = "DELETE FROM stock WHERE id = " + id;
        db.update(sql);
    }
}

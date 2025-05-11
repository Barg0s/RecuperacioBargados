package com.recuperacio;


public class stockDao implements Dao<Stock> {


    public void add(Stock stock) {
        AppData db = AppData.getInstance();
    
    
        String sql = "INSERT INTO STOCK (id_manga,quantitat,estat) VALUES ('" 
                    + stock.getIdManga() + "', '" 
                    + stock.getQuantitat() + "', '" 
                    + stock.getEstat() + "')";
        
        db.update(sql);
    }
    
public void update(int idManga, Stock stock) {
    AppData db = AppData.getInstance();

    String sql = "UPDATE stock SET "
            + "quantitat = " + stock.getQuantitat() + ", "
            + "estat = '" + stock.getEstat() + "' "
            + "WHERE id_manga = " + stock.getIdManga();  

    try {
        db.update(sql);
        System.out.println("Stock actualizado correctamente");
    } catch (Exception e) {
        System.out.println("Error al actualizar el stock: " + e.getMessage());
    }
}

    public void delete(int id){
        AppData db = AppData.getInstance();

        String sql = "DELETE FROM stock WHERE id = " + id;
        db.update(sql);
    }
}

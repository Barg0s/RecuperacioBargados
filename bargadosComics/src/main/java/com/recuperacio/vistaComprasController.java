package com.recuperacio;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

public class vistaComprasController {

    @FXML
    private GridPane shop;
    @FXML
    private TextField total;

    @FXML
    private Button carrito,logout;

    @FXML
    private VBox carro;

    @FXML
    private ScrollPane scrollCarrito;

    @FXML
    private VBox carritoContenido;

    private float preuTotal = 0;
    private Carrito carritoGlobal = Carrito.getInstance();

    public void initialize() {
        Mangas();
        rellenarCarro();
    }
    

    @FXML
    public void obrirPagament(){
        PagamamentController crtl = (PagamamentController) UtilsViews.getController("ViewPagament");

        UtilsViews.setViewAnimating("ViewPagament");
        crtl.MostrarCarrito();
    }
    public void rellenarCarro() {
        if (carritoContenido == null) {
            return;
        }

        carritoContenido.getChildren().clear();

        if (carritoGlobal.getItems().isEmpty()) {
        }

        for (Manga manga : carritoGlobal.getItems()) {
            Label label = new Label(manga.getTitol());
            carritoContenido.getChildren().add(label);
        }
    }

    @FXML
    public void AbrirCarrito() {
        carro.setVisible(true);
        carrito.setVisible(false);
        logout.setVisible(false);
        rellenarCarro();
        carro.requestLayout();
    }

    @FXML
    public void ocultarCarrito() {
        carro.setVisible(false);
        carrito.setVisible(true);
        logout.setVisible(true);
    }
    @FXML
    public void Desconexio() {
        UtilsViews.setView("ViewLogin");
        
    }
    private void ComprarManga(String titol) {
        MangaDao mangaDao = new MangaDao();
        AppData db = AppData.getInstance();
    
        String sql = "SELECT * FROM manga WHERE titol = '" + titol + "'";
        ArrayList<HashMap<String, Object>> mangas = db.query(sql);
    
        for (HashMap<String, Object> manga : mangas) {
            String titolManga = manga.get("titol") != null ? manga.get("titol").toString() : "Desconocido";
            String preuManga = manga.get("preu") != null ? manga.get("preu").toString() : "0.0";
            String nomManga = manga.get("nom") != null ? manga.get("nom").toString() : "Desconocido";
            String cognomManga = manga.get("cognom") != null ? manga.get("cognom").toString() : "Desconocido";
            String sinopsiManga = manga.get("sinopsi") != null ? manga.get("sinopsi").toString() : "No disponible";
            String isbnManga = manga.get("isbn") != null ? manga.get("isbn").toString() : "Desconocido";
            String dataPublicacioManga = manga.get("DataPublicacio") != null ? manga.get("DataPublicacio").toString() : "Desconocida";
            int pagsManga = manga.get("pags") != null ? Integer.parseInt(manga.get("pags").toString()) : 0;
            String portadaManga = manga.get("portada") != null ? manga.get("portada").toString() : "";
            int id = manga.get("id_manga") != null ? Integer.parseInt(manga.get("id_manga").toString()) : -1;


            Manga manga1 = new Manga(
                id,
                titolManga,
                Float.parseFloat(preuManga),
                nomManga,
                cognomManga,
                sinopsiManga,
                isbnManga,
                dataPublicacioManga,
                pagsManga,
                portadaManga
            );
            String updateSql = "UPDATE stock SET quantitat = quantitat - 1 WHERE id_manga = '" + id + "'";
            db.update(updateSql);
            int updatedQuantity = mangaDao.ObtenirQuantitatManga(id);
            preuTotal +=Float.parseFloat(preuManga);
    
    
            carritoGlobal.agregar(manga1);
            carritoGlobal.setPreu(preuTotal);
            total.setText(String.format("%.2f", preuTotal));
    
        }
    

    }
    

    public void ObrirFitxa(String titol){
        String sql = "SELECT id_manga FROM manga WHERE titol = '" + titol + "'";
        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> mangas = db.query(sql);
        int id = (Integer) mangas.get(0).get("id_manga");
        ControllerTenda crtl = (ControllerTenda) UtilsViews.getController("ViewDetallsCompra");
        UtilsViews.setView("ViewDetallsCompra");
        try {
            crtl.carregarView(id);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void Tornar() {
        total.setText("0,00");

        carritoGlobal.vaciar();

        carritoContenido.getChildren().clear();

        carro.setVisible(false);
        carrito.setVisible(true);
        logout.setVisible(true);
        
        preuTotal = 0.0f; 
        total.setText(String.format("%.2f", preuTotal));  
    }

public void Mangas() {
    MangaDao mangaDao = new MangaDao();
    AppData db = AppData.getInstance();
    String sql = "SELECT id_manga, titol, preu, portada FROM manga";
    ArrayList<HashMap<String, Object>> mangas = db.query(sql);

    if (mangas.isEmpty()) {
        return;
    }

    shop.getChildren().clear();

    int row = 0;
    int col = 0;

    for (HashMap<String, Object> manga : mangas) {
        String titulo = manga.containsKey("titol") ? manga.get("titol").toString() : "Desconocido";
        String preuString = manga.containsKey("preu") ? manga.get("preu").toString() : "0.0";
        String path = manga.containsKey("portada") ? manga.get("portada").toString() : "";
        Integer id = manga.containsKey("id_manga") && manga.get("id_manga") != null ? (Integer) manga.get("id_manga") : -1;
        
        int quantitat = mangaDao.ObtenirQuantitatManga(id);

        Label labelManga = new Label(titulo);
        Label preu = new Label();
        
        if (quantitat == 0) {
            preu.setText("Producte esgotat");
        } else {
            preu.setText(String.format("%.2f", Float.parseFloat(preuString)));
        }

        ImageView view = new ImageView();
        view.setImage(new Image(path));
        view.setFitWidth(150);
        view.setFitHeight(150);
        view.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ObrirFitxa(titulo);
            }
        });

        view.setPreserveRatio(true);

        Button comprarBtn = new Button("Afegir");
        
        if (quantitat == 0) {
            comprarBtn.setDisable(true);
        } else {
            comprarBtn.setDisable(false);
        }

        comprarBtn.setOnAction(event -> {
            ComprarManga(titulo);
            rellenarCarro();

            int updatedQuantity = mangaDao.ObtenirQuantitatManga(id);

            if (updatedQuantity == 0) {
                comprarBtn.setDisable(true);
            }
        });

        VBox container = new VBox();
        container.getChildren().addAll(view, labelManga, preu, comprarBtn);

        HBox mangaItem = new HBox();
        mangaItem.getChildren().add(container);

        shop.add(mangaItem, col, row);

        col++;
        if (col > 4) {  
            col = 0;
            row++;
        }
    }
}



}

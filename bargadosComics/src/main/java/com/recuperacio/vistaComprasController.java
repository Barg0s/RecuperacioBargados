package com.recuperacio;

import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;

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
            System.out.println("carritoContenido no está enlazado!");
            return;
        }

        carritoContenido.getChildren().clear();

        if (carritoGlobal.getItems().isEmpty()) {
            System.out.println("El carrito está vacío.");
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
            preuTotal +=Float.parseFloat(preuManga)
            ;
    
    
            carritoGlobal.agregar(manga1);
            carritoGlobal.setPreu(preuTotal);
            total.setText(String.format("%.2f", preuTotal));
    
        }
    
        System.out.println("Mangas en el carrito:");
        for (Manga m : carritoGlobal.getItems()) {
            System.out.println("- " + m.getTitol());
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
        // Reiniciar el total
        total.setText("0,00");

        // Vaciar el carrito
        carritoGlobal.vaciar();

        // Limpiar la vista del carrito (si es necesario)
        carritoContenido.getChildren().clear();

        // Actualizar cualquier otro estado visual si es necesario
        // Por ejemplo, si el carrito está visible, puedes ocultarlo
        carro.setVisible(false);
        carrito.setVisible(true);
        logout.setVisible(true);
        
        // Recalcular el precio total y actualizar la vista
        preuTotal = 0.0f;  // Resetear la variable preuTotal
        total.setText(String.format("%.2f", preuTotal));  // Asegurarse de mostrarlo como "0,00"
    }

    public void Mangas() {
        AppData db = AppData.getInstance();
        String sql = "SELECT id_manga, titol, preu, portada FROM manga";
        ArrayList<HashMap<String, Object>> mangas = db.query(sql);

        shop.getChildren().clear();

        int row = 0;
        int col = 0;

        for (HashMap<String, Object> manga : mangas) {
            String titulo = manga.get("titol").toString();
            String preuString = manga.get("preu").toString();
            String path = manga.get("portada").toString();

            Label labelManga = new Label(titulo);
            Label preu = new Label(preuString);

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
            comprarBtn.setOnAction(event -> {
                ComprarManga(titulo); 
                rellenarCarro();      
                System.out.println(titulo + " añadido al carrito");
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

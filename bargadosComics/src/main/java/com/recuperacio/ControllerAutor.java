package com.recuperacio;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControllerAutor {

    private int autorId;

    @FXML
    private Button buttonAdd;
    @FXML
    public VBox autorContainer,obresContainer;
    @FXML
    private Text textCounter;
    @FXML
    private Button backButton;
    @FXML
    private TextField nom;
    @FXML
    private TextField cognoms;
    @FXML
    private TextField pais;
    @FXML
    private TextField any;
    @FXML
    private ImageView img;
    @FXML
    private Button editarButton;



    @FXML
    private void Enrere(ActionEvent event) throws Exception {
        UtilsViews.setView("ViewTaula");
    }

    @FXML
    private void abrirEditor(ActionEvent event) {
        ControllerEditarAutor crtl = (ControllerEditarAutor) UtilsViews.getController("ViewEditarAutor");
        crtl.setAutorId(autorId);
        UtilsViews.setViewAnimating("ViewEditarAutor");
    }


    @FXML
    public void eliminar(ActionEvent e) throws Exception {
        autorDao autorDao = new autorDao();
        autorDao.delete(autorId);
        UtilsViews.setView("ViewTaula");
    }

    @FXML
    public void mostrarObres(int id) {
        AppData db = AppData.getInstance();
        String sql = "SELECT titol FROM manga WHERE id_autor = " + id;
        ArrayList<HashMap<String, Object>> mangas = db.query(sql);
        
        obresContainer.getChildren().clear(); 
    
        for (HashMap<String, Object> manga : mangas) {
            System.out.println(manga.get("titol"));
            String titulo = manga.get("titol").toString();
            Label labelManga = new Label(titulo);
            obresContainer.getChildren().add(labelManga); 
        }
    }

    public void carregarView(int id) throws Exception {
        cargarAutor(id);
        UtilsViews.setView("ViewAutors");
    }

    public void initialize() {
        System.out.println("Autor cargado: " + nom + " " + cognoms);

    }

    public void cargarAutor(int idAutor) {
        this.autorId = idAutor;

        String sql = "SELECT * FROM autor WHERE id = " + idAutor;

        ArrayList<HashMap<String, Object>> autorData = ObtenirAutor(sql);
        if (!autorData.isEmpty()) {
            PoblarCamps(autorData);
            mostrarObres(autorId);

        } else {
            System.out.println("No se encontraron datos para el autor con ID: " + idAutor);
        }
    }

    public ArrayList<HashMap<String, Object>> ObtenirAutor(String sql) {
        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> result = db.query(sql);
        return result;
    }

    public void PoblarCamps(ArrayList<HashMap<String, Object>> query) {
        if (query != null && !query.isEmpty()) {
            HashMap<String, Object> map = query.get(0);

            Object nomValue = map.get("nom");
            Object cognomValue = map.get("cognom");
            Object paisValue = map.get("pais");
            Object anyValue = map.get("any_naixement");
            String imagePath = map.get("foto").toString();

            nom.setText(nomValue.toString());
            cognoms.setText(cognomValue.toString());
            pais.setText(paisValue.toString());
            any.setText(anyValue.toString());

            URL imageUrl = getClass().getResource("/" + imagePath);
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                img.setImage(image);
            } else {
                System.out.println("No se encontró la imagen: " + imagePath);
            }
        } else {
            System.out.println("La consulta no devolvió resultados.");
        }
    }
}

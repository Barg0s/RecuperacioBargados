package com.recuperacio;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {


    private int mangaId;

    @FXML
    private Button buttonAdd;
    @FXML
    public VBox mangacontainer;
    @FXML
    private Text textCounter;
    @FXML
    private Button backButton;
    @FXML
    private TextField titol;
    @FXML
    private TextField preu;
    @FXML
    private TextField autor;
    @FXML
    private TextArea sinopsi;
    @FXML
    private TextField isbn;
    @FXML
    private TextField data;
    @FXML
    private TextField pags;
    @FXML
    private ImageView img;
    @FXML
    private Button editarButton;


    
    @FXML

    //TODO
    
    //ARREGLAR(o hablar con el albert pq no entiendo)
    private void Enrere(ActionEvent event) throws Exception {
        // Cargar la vista anterior usando FXMLLoader
        UtilsViews.setView("ViewMain");
    }
    
    @FXML
    private void abrirEditor(ActionEvent event) {
        UtilsViews.setViewAnimating("ViewEditar");
    }
    


    public void carregarView(int id) throws Exception {
        cargarManga(id);
        UtilsViews.setView("ViewDetalls");

    }

    public void initialize(){
        System.out.println("titol " + titol);
    }

    public void cargarManga(int idManga) {
        this.mangaId = idManga;

        String sql = "SELECT m.*, CONCAT(a.nom, ' ', a.cognom) AS nom_complet " +
                     "FROM manga m JOIN autor a ON m.id_autor = a.id " +
                     "WHERE id_manga = " + idManga;
    
        ArrayList<HashMap<String, Object>> mangaData = ObtenirManga(sql);
        System.out.println("datos del manga" + mangaData);
    
        if (!mangaData.isEmpty()) {
            PoblarCamps(mangaData);
        } else {
            System.out.println("No se encontraron datos para el manga con ID: " + idManga);
        }
    }
    
    public ArrayList<HashMap<String, Object>> ObtenirManga(String sql){
        AppData db = AppData.getInstance();

        ArrayList<HashMap<String, Object>> result = db.query(sql);
        
        // Verificar los resultados de la consulta
        System.out.println("Resultado de la consulta: " + result);
        return result;
    }

    public void PoblarCamps(ArrayList<HashMap<String, Object>> query) {
        System.out.println("PoblarCamps ejecutado");
        if (query != null && !query.isEmpty()) {
            HashMap<String, Object> map = query.get(0);
            
            
            for (String key : map.keySet()) {
                System.out.println("Clave encontrada: " + key);
            }
    
            Object titolValue = map.get("titol");
            Object autorComplet = map.get("nom_complet");
            Object preuValue = map.get("preu");
            Object sinopsiValue = map.get("sinopsi");
            Object isbnValue = map.get("ISBN");
            Object pagsValue = map.get("pags");
            Object dataValue = map.get("data_publicacio");
            String imagePath = map.get("portada").toString();
                System.out.println(imagePath);
                titol.setText(titolValue.toString());
                autor.setText(autorComplet.toString());
                preu.setText(preuValue.toString());
                sinopsi.setText(sinopsiValue.toString());
                isbn.setText(isbnValue.toString());
                pags.setText(pagsValue.toString());
                data.setText(dataValue.toString());

            //String imagePath = "assets/images/db01.png";  // Ruta relativa a los recursos
            URL imageUrl = getClass().getResource("/" + imagePath);
            System.out.println("foto; "+imageUrl);
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                img.setImage(image);
            } else {
                System.out.println("Error: No se encontró la imagen en la ruta especificada.");
            }
            

        } else {
            System.out.println("La consulta no devolvió resultados.");
        }
    }
    


}

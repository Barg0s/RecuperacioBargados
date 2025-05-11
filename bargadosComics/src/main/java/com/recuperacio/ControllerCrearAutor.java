package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;
import com.utils.UtilsViews;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ControllerCrearAutor {


    private String imagePath;
    @FXML
    private Button buttonLoadImage;
    @FXML
    private Button buttonAdd;
    @FXML
    public VBox mangacontainer;
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

    public void initialize() {
     }


    
    @FXML
    private void Enrere(ActionEvent event) throws Exception {
        UtilsViews.setViewAnimating("ViewTaula");
    }


    public Autor ComprobarValors() {
        String nomText = nom.getText();
        String cognomsText = cognoms.getText();
        String paisText = pais.getText();
        String anyText = any.getText();
        if (nomText.isEmpty() || cognomsText.isEmpty() || paisText.isEmpty() || anyText.isEmpty()) {
            mostrarAlerta("Cap camp pot estar buit.");
            return null;
        }
    


        if  (nomText.isEmpty() || cognomsText.isEmpty()) {
            mostrarAlerta("L'autor ha de tenir nom i cognom.");
            return null;
        }
    
    
        if (!anyText.matches("\\d{4}")) {
            mostrarAlerta("L'any ha de tenir exactament 4 dígits numèrics.");
            return null;
        }
    
        int anycomprobar;
        try {
            anycomprobar = Integer.parseInt(any.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Ha de ser un un valor numeric");
            return null;
        }
    

    
        if (imagePath == null || imagePath.isEmpty()) {
            mostrarAlerta("Has de seleccionar una imatge per al manga.");
            return null;
        }
        return new Autor(nomText, cognomsText, paisText, anycomprobar, imagePath);
    }
    
    
    private static void mostrarAlerta(String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.show();
    }
    




    @FXML
    private void crearManga(){

        Autor autorNou = ComprobarValors();


        autorDao autorDao = new autorDao();
        autorDao.add(autorNou);
    }
@FXML
private void actionLoadImage() {
    AppData db = AppData.getInstance();
    Stage stage = (Stage) buttonLoadImage.getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imatges", "*.png", "*.jpg", "*.jpeg", "*.gif"));
    File selectedFile = fileChooser.showOpenDialog(stage);
    
    if (selectedFile != null) {
        try {
            File targetDir = new File(System.getProperty("user.dir") + "/src/main/resources/assets/images");
            
            if (!targetDir.exists()) {
                targetDir.mkdirs(); 
            }

            String fileName = selectedFile.getName(); 
            Path targetPath = Path.of(targetDir.getAbsolutePath(), fileName); 

            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING); 

            // Now, load the image
            String imageURI = targetPath.toUri().toString();  
            System.out.println("Image URI: " + imageURI);
            Image image = new Image(imageURI);
            System.out.println(fileName);
            img.setImage(image);
            this.imagePath = "assets/images/" + fileName;  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    public ArrayList<HashMap<String, Object>> ObtenirManga(String sql){
        AppData db = AppData.getInstance();

        ArrayList<HashMap<String, Object>> result = db.query(sql);
        
        System.out.println("Resultado de la consulta: " + result);
        return result;
    }

    

   
}

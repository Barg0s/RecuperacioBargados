package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ControllerCrearManga {

    @FXML
    private ChoiceBox<String> choiceBox;

    private String imagePath;
    @FXML
    private Button buttonLoadImage;
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
    private DatePicker picker;
    
    @FXML
    private TextField pags;
    @FXML
    private ImageView img;

    public void initialize() {
        loadAutors();
     }

    public void loadAutors(){
        AppData db = AppData.getInstance();
        String sql = "SELECT nom || ' ' ||  cognom AS nom_complet FROM autor";
        try {
            ArrayList<HashMap<String, Object>> rows = db.query(sql);
            ArrayList<String> tableNames = new ArrayList<>();
            
            for (HashMap<String, Object> row : rows) {
                String fullName = (String) row.get("nom_complet");
                if (fullName != null) {
                    tableNames.add(fullName);
                }
            }
    
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(tableNames);
    
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    @FXML
    private void Enrere(ActionEvent event) throws Exception {

        UtilsViews.setViewAnimating("ViewTaula");

    }



    




    @FXML
    private void crearManga() {
        Manga mangaNou = ComprobarValors();
        if (mangaNou != null) {
            MangaDao mangaDao = new MangaDao();
            mangaDao.add(mangaNou);
            mostrarMisstage("Manga creat correctament!");
            UtilsViews.setView("ViewTaula");
        }
    }
      
@FXML
private void actionLoadImage() {
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

            String imageURI = targetPath.toUri().toString();  
            Image image = new Image(imageURI);
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
        
        return result;
    }

    public Manga ComprobarValors() {
        String tit = titol.getText();
        String sinopsiNou = sinopsi.getText();
        String isbnNou = isbn.getText().replace("-", "");
        String dataPartit = picker.getValue().toString();
        if (tit.isEmpty() || sinopsiNou.isEmpty() || isbnNou.isEmpty() || dataPartit.isEmpty()) {
            mostrarAlerta("Cap camp pot estar buit.");
            return null;
        }
    
        String selectedItem = choiceBox.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            mostrarAlerta("L'autor no pot estar buit.");
            return null;
        }
    
        String[] selectedAutor = selectedItem.split(" ");
        if (selectedAutor.length < 2) {
            mostrarAlerta("L'autor ha de tenir nom i cognom.");
            return null;
        }
    
        String nomChoiceBox = selectedAutor[0];
        String cognomChoice = selectedAutor[1];
    
        if (!isbnNou.matches("\\d{13}")) {
            mostrarAlerta("L'ISBN ha de tenir exactament 13 dígits numèrics.");
            return null;
        }
    
        float preuNou;
        int pagsNou;
        try {
            preuNou = Float.parseFloat(preu.getText());
            pagsNou = Integer.parseInt(pags.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("El preu o les pàgines han de ser valors numèrics vàlids.");
            return null;
        }
    
        try {
            LocalDate.parse(dataPartit);
        } catch (DateTimeParseException e) {
            mostrarAlerta("La data de publicació no és vàlida.");
            return null;
        }
    
        if (imagePath == null || imagePath.isEmpty()) {
            mostrarAlerta("Has de seleccionar una imatge per al manga.");
            return null;
        }
    
        return new Manga(tit, preuNou, nomChoiceBox, cognomChoice, sinopsiNou, isbnNou, dataPartit, pagsNou, imagePath);
    }
    
    
    private static void mostrarAlerta(String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.show();
    }
    
    private static void mostrarMisstage(String missatge) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION); 
    alert.setTitle("Información");
    alert.setHeaderText(null);
    alert.setContentText(missatge);
    alert.show();
}

}

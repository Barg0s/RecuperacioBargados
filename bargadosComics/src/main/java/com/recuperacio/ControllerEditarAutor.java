package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.nio.file.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import java.net.URL;


public class ControllerEditarAutor {
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
    private TextField nom; 
    @FXML
    private TextField cognom; 
    @FXML
    private TextField pais; 
    @FXML
    private TextField anyNaixement; 
    
    private int autorId; 
    @FXML
    private ImageView img;



    public void setAutorId(int id) {
        this.autorId = id;
        cargarAutor(); 
    }



    @FXML
    private void Enrere(ActionEvent event) throws Exception {
        UtilsViews.setView("ViewTaula");
    }

    @FXML
    private void actualizarAutor() {

        
        
        Autor autorNou = ComprobarValors();
        autorDao autorDao = new autorDao();
        autorDao.update(autorId, autorNou);
        mostrarMisstage("Autor Actualitzat correctament");
        UtilsViews.setView("ViewTaula");
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

    @FXML
    public ArrayList<HashMap<String, Object>> ObtenirAutor(String sql) {
        AppData db = AppData.getInstance();
        ArrayList<HashMap<String, Object>> result = db.query(sql);
        System.out.println("Query: " + result);
        return result;
    }

    public void PoblarCamps(ArrayList<HashMap<String, Object>> query) {
        if (query != null && !query.isEmpty()) {
            HashMap<String, Object> map = query.get(0);
            
            for (String key : map.keySet()) {
                System.out.println("Claus:: " + key);
            }
    
            Object nomValue = map.get("nom");
            Object cognomValue = map.get("cognom");
            Object paisValue = map.get("pais");
            Object anyNaixementValue = map.get("any_naixement");
            String imagePath = (String) map.get("foto");
            
            nom.setText(nomValue.toString());
            cognom.setText(cognomValue.toString());
            pais.setText(paisValue.toString());
            anyNaixement.setText(anyNaixementValue.toString());

            // Cargar la foto
            URL imageUrl = getClass().getResource("/" + imagePath);
            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());
                img.setImage(image);
            } else {
                System.out.println("No s'ha trobat l'imatge.");
            }
        } else {
            System.out.println("No resultats");
        }
    }

    private void cargarAutor() {
        String sql = "SELECT * FROM autor WHERE id = " + autorId;
        ArrayList<HashMap<String, Object>> autorData = ObtenirAutor(sql);
        
        if (!autorData.isEmpty()) {
            PoblarCamps(autorData);
        }
    }

        public Autor ComprobarValors() {
        String nomText = nom.getText();
        String cognomsText = cognom.getText();
        String paisText = pais.getText();
        String anyText = anyNaixement.getText();
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
            anycomprobar = Integer.parseInt(anyNaixement.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Ha de ser un un valor numeric");
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
        private static void mostrarMisstage(String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); 
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.show();
    }

}

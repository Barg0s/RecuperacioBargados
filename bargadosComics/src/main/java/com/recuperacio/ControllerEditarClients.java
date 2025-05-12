package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControllerEditarClients {
    private String dniUsuari;

    @FXML
    private VBox autorContainer, compresContainer;
    private static final char[] LETRAS_DNI = {
        'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
        'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
    };
    @FXML
    private Text textCounter;

    @FXML
    private Button backButton, editarButton, buttonAdd;

    @FXML
    private TextField nom, cognoms, mail, passwd, dni;

    @FXML
    private ChoiceBox<String> rol;

    public void initialize() {
        loadRols();
    }

    public void setUsuariDni(String dni) {
        this.dniUsuari = dni;
        cargarAutor();
    }
    
    private void loadRols() {
        rol.getItems().clear();
        rol.getItems().addAll("admin", "client");
    }

    @FXML
    private void Enrere(ActionEvent event) throws Exception {
        UtilsViews.setView("ViewTaula");
    }

    @FXML
    private void actualitzarClient() {

        Usuari autorNou = ComprobarValors();
        UsuariDao usuariDao = new UsuariDao();
        usuariDao.update(dniUsuari, autorNou);
        mostrarMisstage("Usuari actualitztat correctament");
        UtilsViews.setView("ViewTaula");
    }

    private ArrayList<HashMap<String, Object>> obtenirAutor(String sql) {
        AppData db = AppData.getInstance();
        return db.query(sql);
    }

    private void poblarCamps(ArrayList<HashMap<String, Object>> query) {
        if (query != null && !query.isEmpty()) {
            HashMap<String, Object> map = query.get(0);

            nom.setText((String) map.get("nom"));
            cognoms.setText((String) map.get("cognom"));
            mail.setText((String) map.get("email"));
            passwd.setText((String) map.get("password"));
            dni.setText((String) map.get("dni"));
            rol.setValue((String) map.get("rol"));
        } else {
            System.out.println("No se encontraron resultados.");
        }
    }

    private void cargarAutor() {
        String sql = "SELECT * FROM usuaris WHERE dni = '" + dniUsuari + "'";
        ArrayList<HashMap<String, Object>> autorData = obtenirAutor(sql);
        poblarCamps(autorData);
    }
    public static boolean comprobarDNI(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Z]")) {
            return false;
        }

        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraIntroducida = dni.charAt(8);
        char letraCorrecta = LETRAS_DNI[numero % 23];

        return letraIntroducida == letraCorrecta;
    }
    public Usuari ComprobarValors() {
        String nomText = nom.getText().trim();
        String cognomsText = cognoms.getText().trim();
        String passswordText = passwd.getText();
        String correuText = mail.getText().trim();
        String dniText = dni.getText().trim();
    
        if (nomText.isEmpty() || cognomsText.isEmpty() || passswordText.isEmpty() || correuText.isEmpty()) {
            mostrarAlerta("Cap camp pot estar buit.");
            return null;
        }


    
        if (correuText.startsWith(".")|| correuText.endsWith(".") || !correuText.contains("@"))  {
            mostrarAlerta("El mail no es correcte");
            return null;
        } 
    
        return new Usuari(nomText, cognomsText, passswordText, "client", correuText, dniText);
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

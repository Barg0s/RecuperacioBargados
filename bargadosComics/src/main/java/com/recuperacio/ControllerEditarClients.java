package com.recuperacio;

import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControllerEditarClients {
    private String dniUsuari;

    @FXML
    private VBox autorContainer, compresContainer;

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
        String nomText = nom.getText();
        String cognomText = cognoms.getText();
        String mailText = mail.getText();
        String passwdText = passwd.getText();
        String dniText = dni.getText();
        String rolText = rol.getSelectionModel().getSelectedItem();

        Usuari autorNou = new Usuari(nomText, cognomText, passwdText, rolText,mailText, dniText);
        UsuariDao usuariDao = new UsuariDao();
        usuariDao.update(dniUsuari, autorNou);
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
    
}

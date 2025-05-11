package com.recuperacio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControllerClients {

    private String autorId;

    @FXML
    private Button buttonAdd;
    @FXML
    public VBox autorContainer, compresContainer;
    @FXML
    private Text textCounter;
    @FXML
    private Button backButton;
    @FXML
    private TextField nom;
    @FXML
    private TextField cognoms;
    @FXML
    private TextField mail;
    @FXML
    private TextField passwd;

    @FXML
    private Button editarButton;

    @FXML
    private void Enrere(ActionEvent event) throws Exception {
        UtilsViews.setView("ViewTaula");
    }

    @FXML
    private void abrirEditor(ActionEvent event) {
        String dniSeleccionado = autorId;
        ControllerEditarClients crtl = (ControllerEditarClients) UtilsViews.getController("ViewEditarClients");
        crtl.setUsuariDni(dniSeleccionado);

        UtilsViews.setViewAnimating("ViewEditarClients");
    }

    @FXML
    public void eliminar(ActionEvent e) throws Exception {
        UsuariDao UsuariDao = new UsuariDao();
        String dniSeleccionado = autorId;

        UsuariDao.delete(dniSeleccionado);
        
        UtilsViews.setView("ViewTaula");
    }

    @FXML
    public void mostrarObres(String id) {
        AppData db = AppData.getInstance();
        String sql = "SELECT " +
        "    rv.id AS id_compra, " +
        "    u.nom || ' ' || u.cognom AS nombre_usuario, " +
        "    m.titol AS titol_manga, " +
        "    rv.data_compra " +
        "FROM " +
        "    registre_vendes rv " +
        "JOIN " +
        "    usuaris u ON rv.id_user = u.id_user " +
        "JOIN " +
        "    manga m ON rv.id_manga = m.id_manga " +
        "WHERE " +
        "    u.dni = '" + id + "' " +
        "ORDER BY " +
        "    rv.data_compra DESC";
        ArrayList<HashMap<String, Object>> mangas = db.query(sql);

        compresContainer.getChildren().clear();

        StringBuilder contenido = new StringBuilder();

        for (HashMap<String, Object> manga : mangas) {
            String titulo = manga.get("titol_manga").toString();
            String dataCompra = manga.get("data_compra").toString();
            contenido.append("Títol: ").append(titulo)
                     .append(" | Data de compra: ").append(dataCompra)
                     .append("\n");
        }

        Label labelManga = new Label(contenido.toString());
        compresContainer.getChildren().add(labelManga);
    }

    public void carregarView(String id) throws Exception {
        cargarAutor(id);
        UtilsViews.setView("ViewClients");
    }

    public void initialize() {
        System.out.println("Autor cargado: " + nom + " " + cognoms);
    }

    public void cargarAutor(String idAutor) {
        this.autorId = idAutor;

        String sql = "SELECT * FROM usuaris WHERE dni = '" + idAutor + "'"; 

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
        return db.query(sql);
    }

    public void PoblarCamps(ArrayList<HashMap<String, Object>> query) {
        if (query != null && !query.isEmpty()) {
            HashMap<String, Object> map = query.get(0);

            Object nomValue = map.get("nom");
            Object cognomValue = map.get("cognom");
            Object paisValue = map.get("email");
            Object anyValue = map.get("password");

            nom.setText(nomValue.toString());
            cognoms.setText(cognomValue.toString());
            mail.setText(paisValue.toString());
            passwd.setText(anyValue.toString());

        } else {
            System.out.println("La consulta no devolvió resultados.");
        }
    }
}

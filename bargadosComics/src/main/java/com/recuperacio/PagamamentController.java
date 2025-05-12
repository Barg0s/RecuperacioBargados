package com.recuperacio;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.utils.UtilsViews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PagamamentController implements Initializable {

    @FXML
    private VBox compraContainer;
    @FXML
    private TextField nameField, cardNumberField, expiryDateField, cvvField, emailField;

    private final Carrito carritoGlobal = Carrito.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MostrarCarrito();
    }

    public boolean ComprobarValors() {
        String nombre = nameField.getText().trim();
        String numeroTarjeta = cardNumberField.getText().replaceAll("\\s+", "");
        String fechaExpiracion = expiryDateField.getText().trim();
        String cvv = cvvField.getText().trim();
        String email = emailField.getText().trim();
        String any = fechaExpiracion.substring(3, 5);
        String mes = fechaExpiracion.substring(0, 2);
        int anyInt = Integer.parseInt(any);
        int mesInt = Integer.parseInt(mes);

        if (nombre.isEmpty()) {
            mostrarAlerta("❌ El nom no pot estar buit");
            return false;
        }

        if (!numeroTarjeta.matches("\\d{16}")) {
            mostrarAlerta("❌ Ha de tenir 16 numeros.");
            return false;
        }

        if (!fechaExpiracion.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            mostrarAlerta("❌ Data d'expiració no valida.");
            return false;
        }
        if (anyInt < 25 || mesInt < 0 || mesInt > 12) {
            mostrarAlerta("❌ Data d'expiració no valida.");
            return false;
        }
        if (!cvv.matches("\\d{3,4}")) {
            mostrarAlerta("❌ CVV no valid, ha de tenir 3 o 4 digits");
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            mostrarAlerta("Correu no valid");
            return false;
        }

        return true;
    }

    public int getIdUser(String username) {
        String correuEscapat = username.replace("'", "''"); 
        String sql = "SELECT id_user FROM usuaris WHERE email = '" + correuEscapat + "'";

        ArrayList<HashMap<String, Object>> result = AppData.getInstance().query(sql);

        if (!result.isEmpty()) {
            int idUser = (int) result.get(0).get("id_user");
            return idUser;
        } else {
            return -1;
        }
    }



public void FerCompra() {
    List<Manga> compra = carritoGlobal.getItems();
    AppData db = AppData.getInstance();
    int idUserActual = getIdUser(Sessio.getInstance().getUsername());

    if (ComprobarValors()) {
        for (Manga m : compra) {
            int idManga = m.getId(); 
            String sql = "INSERT INTO registre_vendes (id_user, id_manga, data_compra) " +
                         "VALUES (" + idUserActual + ", " + idManga + ", CURRENT_TIMESTAMP);";
            db.update(sql);
        }

        // MOSTRAR LA VISTA ANTES DE VACIAR
        UtilsViews.setView("ViewCompras");

        // LLAMAR AL CONTROLADOR Y REINICIAR LA VISTA
        vistaComprasController crtlVistaCompras = (vistaComprasController) UtilsViews.getController("ViewCompras");
        crtlVistaCompras.Tornar();  

        carritoGlobal.vaciar();

        mostrarAlerta("Compra realiztada!");
    } else {
        System.out.println("ERROR");    }
}




private static void mostrarAlerta(String missatge) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION); 
    alert.setTitle("Información");
    alert.setHeaderText(null);
    alert.setContentText(missatge);
    alert.show();
}

    public void MostrarCarrito() {
        compraContainer.getChildren().clear();

        List<Manga> compra = carritoGlobal.getItems();
        double preu = carritoGlobal.getTotal();

        for (Manga m : compra) {
            Label label = new Label(m.getTitol() + " - " + String.format("%.2f €", m.getPreu()));
            compraContainer.getChildren().add(label);
        }

        Label labelPreu = new Label("Total: " + String.format("%.2f €", preu));
        compraContainer.getChildren().add(labelPreu);
    }
}

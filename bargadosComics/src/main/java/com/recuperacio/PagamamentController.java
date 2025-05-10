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

    private TextField nameField,cardNumberField,expiryDateField,cvvField,emailField;

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

    if (nombre.isEmpty()) {
        mostrarAlerta(" El nom no pot estar buit.");
        return false;
    }

    if (!numeroTarjeta.matches("\\d{16}")) {
        mostrarAlerta(" El número de targeta ha de contenir 16 dígits.");
        return false;
    }

    if (!fechaExpiracion.matches("(0[1-9]|1[0-2])/\\d{2}")) {
        mostrarAlerta(" Data de caducitat no vàlida. Utilitza el format MM/AA.");
        return false;
    }

    if (!cvv.matches("\\d{3,4}")) {
        mostrarAlerta(" El CVV no és vàlid. Ha de tenir 3 o 4 dígits.");
        return false;
    }

    if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
        mostrarAlerta("El correu electrònic no és vàlid.");
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
        mostrarAlerta("No s'ha trobat cap usuari amb aquest correu.");
        return -1;
    }
}
    
    private static void mostrarAlerta(String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.show();
    }
    



public void FerCompra() {
    List<Manga> compra = carritoGlobal.getItems();
    AppData db = AppData.getInstance();
    int idUserActual = getIdUser(Sessio.getInstance().getUsername());

    if (!ComprobarValors()) {
        mostrarAlerta("❌ No s'ha pogut realitzar la compra.");
        return;
    }

    for (Manga m : compra) {
        int idManga = m.getId(); 
        String sql = "INSERT INTO registre_vendes (id_user, id_manga, data_compra) " +
                     "VALUES (" + idUserActual + ", " + idManga + ", CURRENT_TIMESTAMP);";
        db.update(sql);
    }


    compraContainer.getChildren().clear(); 


    UtilsViews.setView("ViewCompras");
    carritoGlobal.vaciar();
    carritoGlobal.setPreu(0.0f);
    compraContainer.setVisible(false);
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


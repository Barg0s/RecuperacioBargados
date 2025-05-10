package com.recuperacio;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Action;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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


public class ControllerLogin {

    
     @FXML
     private Button LoginButton,veure;
         
     @FXML
     private Button crearUsuari;
     @FXML
     private TextField mail;
     @FXML
     private PasswordField Password;
     @FXML
     private TextField pass_text;
     
     @FXML
     public void togglevisiblePassword(ActionEvent event) { //https://stackoverflow.com/questions/17014012/how-to-unmask-a-javafx-passwordfield-or-properly-mask-a-textfield
        if (Password.isVisible()) {
            pass_text.setText(Password.getText());
            pass_text.setVisible(true);
            Password.setVisible(false);
        } else {
            Password.setText(pass_text.getText());
            Password.setVisible(true);
            pass_text.setVisible(false);
        }
    }
    

     @FXML
     private void ObrirLogin(ActionEvent event) {
         UtilsViews.setView("ViewCrear");
     }
     
     @FXML
     private void IniciarSessio(ActionEvent event) {
         String user = mail.getText();
         String passwd = Password.getText();
     
         AppData db = AppData.getInstance();
     
         String sql = "SELECT password, rol FROM usuaris WHERE email = '" + user + "'";
     
         ArrayList<HashMap<String, Object>> result = db.query(sql);
     
         if (result.isEmpty()) {
             System.out.println("User not found!");
             return;
         }
     
         String contraCorrecta = (String) result.get(0).get("password");
         String rol = (String) result.get(0).get("rol");
     
         if (passwd.equals(contraCorrecta) && rol.equals("admin")) {
            System.out.println(user);
            Sessio.getInstance().setUsername(user);
            System.out.println("Usuari loguejat: " +  Sessio.getInstance().getUsername());


            UtilsViews.setView("ViewTaula");
         } else if (rol.equals("client")) {
            Sessio.getInstance().setUsername(user);

            UtilsViews.setView("ViewCompras");

            
             //TODO
         } else {
             System.out.println("Contraseña incorrecta");
         }
     }
}
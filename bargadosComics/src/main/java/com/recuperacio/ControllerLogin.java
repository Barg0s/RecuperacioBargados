package com.recuperacio;


import java.util.ArrayList;
import java.util.HashMap;


import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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

            
         } else {
             System.out.println("Contraseña incorrecta");
         }
     }
}
package com.recuperacio;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class ControllerCrearUser {

    
    @FXML
    private Button crearButton;

    @FXML
    private Button backButton;
    @FXML
    private TextField nom,pass_text,pass_text2;
    @FXML
    private TextField cognom;
    @FXML
    private TextField correu;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField ConfirmarPassword;
    private static final char[] LETRAS_DNI = {
        'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
        'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
    };
    @FXML
    private TextField dni;
    

    @FXML
    public void veurePassword(ActionEvent event) { //https://stackoverflow.com/questions/17014012/how-to-unmask-a-javafx-passwordfield-or-properly-mask-a-textfield
       if (password.isVisible()) {
           pass_text.setText(password.getText());
           pass_text.setVisible(true);
           password.setVisible(false);
       } else {
           password.setText(pass_text.getText());
           password.setVisible(true);
           pass_text.setVisible(false);
       }
   }
   @FXML
   public void veureComprobar(ActionEvent event) { //https://stackoverflow.com/questions/17014012/how-to-unmask-a-javafx-passwordfield-or-properly-mask-a-textfield
      if (ConfirmarPassword.isVisible()) {
          pass_text2.setText(ConfirmarPassword.getText());
          pass_text2.setVisible(true);
          ConfirmarPassword.setVisible(false);
      } else {
        ConfirmarPassword.setText(pass_text.getText());
          ConfirmarPassword.setVisible(true);
          pass_text2.setVisible(false);
      }
  }
    
    
    @FXML
    private void Enrere(ActionEvent event) throws Exception {

        UtilsViews.setViewAnimating("ViewMain");

    }






    @FXML
    private void crearUsuari(){

        String nomChoiceBox = nom.getText();
        String cognomChoice = cognom.getText();

        System.out.println(nomChoiceBox);
        System.out.println(cognomChoice);

        if (password.getText().equals(ConfirmarPassword.getText())){

        }
        Usuari usuariNou = ComprobarValors();
        UsuariDao usuariDao = new UsuariDao();
        usuariDao.add(usuariNou);
        System.out.println("usuari creat");
        UtilsViews.setViewAnimating("ViewLogin");


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
        UsuariDao usuari1 = new UsuariDao();
        String nomText = nom.getText().trim();
        String cognomsText = cognom.getText().trim();
        String passswordText = password.getText();
        String passwordConfirmar = ConfirmarPassword.getText();
        String correuText = correu.getText().trim();
        String dniText = dni.getText().trim();
    
        if (nomText.isEmpty() || cognomsText.isEmpty() || passswordText.isEmpty() || correuText.isEmpty() || passwordConfirmar.isEmpty()) {
            mostrarAlerta("Cap camp pot estar buit.");
            return null;
        }

        if (usuari1.existeixDni(dniText)) {
            mostrarAlerta("El DNI ja està registrat.");

            return null;
        }
        if (usuari1.existeixCorreu(correuText)) {
            mostrarAlerta("El correu ja està registrat.");
            return null;
        }
        if (!comprobarDNI(dniText)) {
            mostrarAlerta("El DNI no és vàlid.");
            return null;
        }
    
        if (!passwordConfirmar.equals(passswordText)) {
            mostrarAlerta("Les contrasenyes no són iguals.");
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
    

   
}

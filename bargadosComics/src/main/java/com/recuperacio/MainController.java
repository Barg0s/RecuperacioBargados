package com.recuperacio;

import com.utils.UtilsViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private Button loginButton,userButton;
    @FXML
    private AnchorPane panel;



    @FXML
    private void ObrirCrear(ActionEvent event){
        UtilsViews.setViewAnimating("ViewLogin");
    }
    @FXML
    public void initialize()
    {
        /*Image fondo = new Image("/assets/images/2721187.png");
        BackgroundImage fondoderecha = new BackgroundImage(fondo,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(
                100, 100, true, true, true, false
            )
        );
        panel.setBackground(new Background(fondoderecha));/* */
    };

}

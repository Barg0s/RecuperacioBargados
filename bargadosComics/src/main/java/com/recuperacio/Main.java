package com.recuperacio;

import com.utils.UtilsViews;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// Fes anar l'exemple amb:
// ./run.sh com.exemple1602.Main

public class Main extends Application {

    final int WINDOW_WIDTH = 1000;
    final int WINDOW_HEIGHT = 600;
    final int MIN_WIDTH = 600;
    final int MIN_HEIGHT = 400;
    public static void main(String[] args) {





        Gestiodb.crearTaulaAutor();
        Gestiodb.crearTaulaManga();
        Gestiodb.crearTaulaClients();
        Gestiodb.crearTaulaVendes();
        Gestiodb.crearAutor();
        Gestiodb.crearManga();
        Gestiodb.insertarUsers();
        Gestiodb.crearTaulaStock();
        Gestiodb.insertarStock();
        Gestiodb.InsertarCompras();
        MangaDao mangaDao = new MangaDao();
        Manga manga = new Manga("Dragon ball 22", 12, "Akira", "Toriyama", "prov", "123455", "1984-12-12", 10, "assets/images/db22.png");
        mangaDao.add(manga);


        Manga mangaNuevo = new Manga("Dragon ball 22", 12, "Tite", "Kubo", "prov", "123455", "1984-12-12", 10, "assets/images/db22.png");

        mangaDao.update(3, mangaNuevo);

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        UtilsViews.parentContainer.setStyle("-fx-font: 14 arial;");
        UtilsViews.addView(getClass(), "ViewMain", "/assets/vistaPrincipal.fxml");

        UtilsViews.addView(getClass(), "ViewTaula", "/assets/vistaResultats.fxml");



        UtilsViews.addView(getClass(), "ViewLogin", "/assets/vistaLogin.fxml");


        UtilsViews.addView(getClass(), "ViewCrear", "/assets/vistaCrearUser.fxml");
        UtilsViews.addView(getClass(), "ViewDetalls", "/assets/vistaMangas.fxml");
        UtilsViews.addView(getClass(), "ViewDetallsCompra", "/assets/vistaMangasResum.fxml");
        UtilsViews.addView(getClass(), "ViewAutors", "/assets/vistaAutor.fxml");
        UtilsViews.addView(getClass(), "ViewCrearAutors", "/assets/vistaCrearAutor.fxml");
        UtilsViews.addView(getClass(), "ViewEditarClients", "/assets/vistaEditarClient.fxml");
        UtilsViews.addView(getClass(), "ViewClientsAdmin", "/assets/vistaCrearUserAdmin.fxml");
        UtilsViews.addView(getClass(), "ViewCompras", "/assets/vistaCompras.fxml");


        UtilsViews.addView(getClass(), "ViewEditarAutor", "/assets/vistaEditarAutor.fxml");
        UtilsViews.addView(getClass(), "ViewEditar", "/assets/vistaEditar.fxml");
        UtilsViews.addView(getClass(), "ViewCrearMangas", "/assets/vistaCrearMangas.fxml");
        UtilsViews.addView(getClass(), "ViewClients", "/assets/vistaClient.fxml");
        UtilsViews.addView(getClass(), "ViewPagament", "/assets/vistaPago.fxml");


        Scene scene = new Scene(UtilsViews.parentContainer);

        stage.setScene(scene);
        stage.setTitle("JavaFX App");
        stage.setMinWidth(MIN_WIDTH);
        stage.setWidth(WINDOW_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setHeight(WINDOW_HEIGHT);
        stage.show();

        // Afegeix una icona només si no és un Mac
        if (!System.getProperty("os.name").contains("Mac")) {
            Image icon = new Image("file:icons/icon.png");
            stage.getIcons().add(icon);
        }
    }
    // Aquesta funció es crida quan es tanca l'aplicació
    @Override
    public void stop() throws Exception {
        AppData db = AppData.getInstance();
        db.close();
        super.stop();
    }


}
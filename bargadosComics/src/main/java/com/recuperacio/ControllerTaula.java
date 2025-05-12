package com.recuperacio;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.utils.UtilsViews;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class ControllerTaula implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableView<HashMap<String, Object>> table;

    @FXML
    private Label label,name;

    // Called when the FXML file is loaded
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Usuario: " + Sessio.getInstance().getUsername());
        name.setText(Sessio.getInstance().getUsername());

        mostraDetalls();
        reload();
        // Acció que s'executa quan el 'coiceBox' canvia de valor
        choiceBox.setOnAction((event) -> {
            String selectedTable = choiceBox.getSelectionModel().getSelectedItem();
            // La selecció pot ser 'null' quan es reconstrueix el 'choiceBox'
            if (selectedTable != null) {
                setTable(selectedTable);
            }
        });
    }

    @FXML
    public void TancarSessio(){
        Sessio.getInstance().setUsername("");
        UtilsViews.setView("ViewLogin");
    }

    @FXML
    public void ObrirTenda(){
        UtilsViews.setViewAnimating("ViewCompras");
    }
    /**
     * Llista les taules de la base de dades al 'choiceBox'
     * @param selectedTable taula que s'ha d'escollir (si existeix), o "" per la primera taula
     * @param selectedRow fila que s'ha de marcar (si existeix), o -1 si no se'n selecciona cap
     */
    public void loadTables(String selectedTable, int selectedRow) {
        String username = Sessio.getInstance().getUsername();
        System.out.println("Usuario desde sessio: " + username); 
    

        name.setText(username);  
        // Obtenir el nom de les taules de la base de dades
        AppData db = AppData.getInstance();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name <> 'sqlite_sequence'";
        ArrayList<HashMap<String, Object>> rows = db.query(sql);
        
        // Posar els noms de les taules a 'tableNames'
        ArrayList<String> tableNames = new ArrayList<>();
        for (HashMap<String, Object> row : rows) {
            tableNames.add((String) row.get("name"));
        }

        // Posar els noms de les taules a la 'choiceBox'
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(tableNames);

        if (selectedTable != null && selectedTable.equalsIgnoreCase("") == false && tableNames.indexOf(selectedTable) != -1) {
            // Escollir la taula 'selectedTable'
            choiceBox.getSelectionModel().select(selectedTable);
            setTable(selectedTable);

            // Escollir la fila 'selectedRow'
            if (selectedRow >= 0 && selectedRow < table.getItems().size()) {
                table.getSelectionModel().select(selectedRow);
            }
        } else {
            // Escollir la primera taula
            choiceBox.getSelectionModel().selectFirst();
            setTable(tableNames.get(0));
        }
    }
        


    

    @FXML
    public void crear() {
        try {
            String tabla = choiceBox.getValue();
            System.out.println("tabla seleccionada" + tabla);
            switch (tabla) {
                case "manga":
                    UtilsViews.setViewAnimating("ViewCrearMangas");
                    
                    break;
                case "usuaris":
                    UtilsViews.setViewAnimating("ViewClientsAdmin");
                    
                    break;
                case "autor":
                    UtilsViews.setViewAnimating("ViewCrearAutors");
                     
                    break;
                default:
                    break;
            }

        } catch (Exception e4) {
            System.out.println("❌ Se produjo un error inesperado: " + e4.getMessage());
            e4.printStackTrace();
        }
    }
    public boolean actualizarCantidadStockPorTitulo(String tituloManga, int nuevaCantidad) {
        if (tituloManga == null || tituloManga.isEmpty()) return false;
    
        String tituloEscapado = tituloManga.replace("'", "''");
        System.out.println(tituloEscapado);
        String sqlSelect = "SELECT id_manga FROM manga WHERE titol = '" + tituloManga + "'";
        ArrayList<HashMap<String, Object>> resultados = AppData.getInstance().query(sqlSelect);
    
        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontró el manga con título: " + tituloManga);
            return false;
        }
    
        int idManga = (int) resultados.get(0).get("id_manga");
        System.out.println(idManga);
        Stock stock = new Stock(idManga, nuevaCantidad);  
        stockDao stockDao = new stockDao();
        stockDao.update(idManga, stock);
        vistaComprasController crtl = (vistaComprasController) UtilsViews.getController("ViewCompras");
        crtl.Mangas();

        reload();
        
        return true;
    }
    
    /**
     * Mostra una taula a la TableView 'table'
     * @param tableName nom de la taula a mostrar
     */
    @FXML
    private void setTable(String tableName) {

        // Vigilar que hi ha un 'tableName'
        if (tableName == null || tableName.trim().isEmpty()) {
            System.out.println("La taula seleccionada és null o buida.");
            return;
        }



        // Obtenir els continguts de la taula
AppData db = AppData.getInstance();
String sql = "";

switch (tableName) {
    case "manga":
        sql = "SELECT id_manga, titol FROM manga";
        break;

    case "autor":
        sql = "SELECT id, nom || ' ' || cognom AS nom_complet FROM autor";
        break;

    case "usuaris":
        sql = "SELECT dni, nom || ' ' || cognom AS nom_complet, rol FROM usuaris";
        break;

    case "registre_vendes":
        sql = "SELECT " +
              "    rv.id AS id_compra, " +  
              "    u.nom || ' ' || u.cognom AS nombre_usuario, " +  
              "    m.titol AS titol_manga, " + 
              "    rv.data_compra " +  
              "FROM registre_vendes rv " +
              "JOIN usuaris u ON rv.id_user = u.id_user " +  
              "JOIN manga m ON rv.id_manga = m.id_manga " +  
              "ORDER BY rv.data_compra DESC";  
        break;

        case "stock":
        sql = """
            SELECT 
                manga.titol, 
                stock.quantitat, 
                stock.estat
            FROM 
                stock
            JOIN 
                manga ON stock.id_manga = manga.id_manga
            """;
        break;
    

    default:
        return; 
}



        ArrayList<HashMap<String, Object>> rows = db.query(sql);

        // Esborrar les columnes i files actuals de la taula
        table.getColumns().clear();
        table.getItems().clear();

        if (!rows.isEmpty()) {

            // Determinar els noms de les columnes
            HashMap<String, Object> firstRow = rows.get(0);
            for (String key : firstRow.keySet()) {
                TableColumn<HashMap<String, Object>, Object> column = new TableColumn<>(key);
                column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get(key)));
                table.getColumns().add(column);
            }

            // Ajustar l'amplada de cada columna de manera equitativa
            table.getColumns().forEach(column ->
                column.prefWidthProperty().bind(table.widthProperty().divide(table.getColumns().size()))
            );
        }

        // Assignar les dades a la taula
        ObservableList<HashMap<String, Object>> data = FXCollections.observableArrayList(rows);
        table.setItems(data);

        // Afegir listener per detectar la selecció d'una fila
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            setLabelInfo(newSelection);
        });

        // Fer la taula editable
        makeTableEditable(table, row -> {
            String selectedTable = choiceBox.getSelectionModel().getSelectedItem();

            // Definir el nom de la clau primària, segons la taula
            String keyName = "id";
            if (tableName.equals("manga")) {
                keyName = "id_manga";
            }
            if (tableName.equals("stock")) {
                keyName = "titol";
                String titulo = (String) row.get("titol");

                Object quantitat = row.get("quantitat");
                int cantidad = Integer.parseInt(quantitat.toString());
                actualizarCantidadStockPorTitulo(titulo, cantidad);
            }
            boolean modified = setModifiedRow(selectedTable, keyName, row);
            if (modified) {
                setLabelInfo(row);
            }
        });
    }
    
    /**
     * Carrega de nou les dades de la base de dades
     * si hi ha una taula o fila sel·leccionades, intenta mantenir-les
     * @param event
     */
    @FXML
    public void reload() {
        String selectedTable = choiceBox.getSelectionModel().getSelectedItem();
        int selectedRow = table.getSelectionModel().getSelectedIndex();

        loadTables(selectedTable, selectedRow);
    }

    /**
     * Mostra la informació de la fila sel·lecionada al labe inferior
     * Si no hi ha cap fila escollida mostra "Cap fila escollida"
     * @param rowData
     */
    private void setLabelInfo(HashMap<String, Object> rowData) {
        if (rowData == null) {
            label.setText("Cap fila escollida");
        } else {
            StringBuilder info = new StringBuilder();
            rowData.forEach((key, value) -> info.append(key).append(": ").append(value).append("  "));
            label.setText(info.toString());
        }
    }

    /** 
     * Actualitza la base de dades quan es modifica una fila
     * @param tableName nom de la taula
     * @param rowData dades de la fila a actualitzar
     * @return true si s'ha fet el canvi
     */ 
    private boolean setModifiedRow(String tableName, String keyName, HashMap<String, Object> rowData) {

        if (rowData == null || tableName == null) return false;

        Object idValue = rowData.get(keyName);

        StringBuilder setClause = new StringBuilder();
        for (String key : rowData.keySet()) {
            if (key.equals(keyName)) continue;
    
            Object value = rowData.get(key);
            if (setClause.length() > 0) setClause.append(", ");
    
            // Adaptar la query a "sqlite"
            if (value == null) {
                setClause.append(String.format("%s = NULL", key));
            } else if (value instanceof Number) {
                setClause.append(String.format("%s = %s", key, value));
            } else {
                String escaped = value.toString().replace("'", "''");
                setClause.append(String.format("%s = '%s'", key, escaped));
            }
        }
    
        String sql = String.format("UPDATE %s SET %s WHERE %s = %s", tableName, setClause, keyName, idValue);
        AppData.getInstance().update(sql);
        System.out.println("Actualitzat: " + sql);
    
        return true;
    }



    public void mostraDetalls() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                HashMap<String, Object> selectedRow = table.getSelectionModel().getSelectedItem();
                if (selectedRow == null) return;
    
                String selectedTable = choiceBox.getSelectionModel().getSelectedItem();
                try {
                    switch (selectedTable) {
                        case "manga": {
                            Object value = selectedRow.get("id_manga");
                            int id = Integer.parseInt(value.toString());
    
                            Controller crtl = (Controller) UtilsViews.getController("ViewDetalls");
                            UtilsViews.setView("ViewDetalls");
                            crtl.carregarView(id);
                            break;
                        }
                        case "autor": {
                            Object value = selectedRow.get("id");
                            int id = Integer.parseInt(value.toString());
    
                            ControllerAutor controllerAutor = (ControllerAutor) UtilsViews.getController("ViewAutors");
                            UtilsViews.setView("ViewAutors");
                            controllerAutor.carregarView(id);
                            break;
                        }
                        case "usuaris": {
                            Object dniValue = selectedRow.get("dni"); 
                            String dni = dniValue.toString();
    
                            ControllerClients controllerClients = (ControllerClients) UtilsViews.getController("ViewClients");
                            UtilsViews.setView("ViewClients");
                            controllerClients.carregarView(dni);
                            break;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("❌ Error al cargar detalles: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
    

    /*DONE
     * hacer que cuando se haga clic en un campo de la tabla llame a obtenir manga y luego abrir la vista de detalles.
     * 
     * 
     * 
    */
        
    /** 
     * Transforma una taula en editable
     * @param table, taula que ha de ser editable
     * @param onEdit, mètode a executar quan s'ha editat una fila
     */ 
    public static void makeTableEditable(TableView<HashMap<String, Object>> table, Consumer<HashMap<String, Object>> onEdit) {
        table.setEditable(true);

        if (table.getItems().isEmpty()) return;

        for (TableColumn<HashMap<String, Object>, ?> tc : table.getColumns()) {
            @SuppressWarnings("unchecked")
            TableColumn<HashMap<String, Object>, Object> col = (TableColumn<HashMap<String, Object>, Object>) tc;
            String key = col.getText();
            Object sampleValue = table.getItems().get(0).get(key);

            StringConverter<Object> converter;

            if (sampleValue instanceof Integer) {
                converter = new StringConverter<>() {
                    public String toString(Object o) { return o == null ? "" : o.toString(); }
                    public Object fromString(String s) {
                        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
                    }
                };
            } else if (sampleValue instanceof Double) {
                converter = new StringConverter<>() {
                    public String toString(Object o) { return o == null ? "" : o.toString(); }
                    public Object fromString(String s) {
                        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
                    }
                };
            } else {
                converter = new StringConverter<>() {
                    public String toString(Object o) { return o == null ? "" : o.toString(); }
                    public Object fromString(String s) { return s; }
                };
            }

            col.setCellFactory(TextFieldTableCell.forTableColumn(converter));
            col.setOnEditCommit(e -> {
                e.getRowValue().put(key, e.getNewValue());
                onEdit.accept(e.getRowValue());
            });
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.Part;
import InventoryManagementSystem.Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Heather Smith
 */
public class MainScreenController implements Initializable {

    //Setting the buttons, tableview and columns for the part tableview
    @FXML
    private Button addPartButton;
    @FXML
    private Button searchPart;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private TextField searchPartTextField;
    @FXML
    public TableView partTableView;
    @FXML
    public TableColumn partIDColumn;
    @FXML
    public TableColumn partNameColumn;
    @FXML
    public TableColumn partInvColumn;
    @FXML
    public TableColumn partPriceColumn;
    @FXML
    public TableColumn partMaxColumn;
    @FXML
    public TableColumn partMinColumn;

    //Setting the buttons, tableview and columns for the product tableview
    @FXML
    private Button addProductButton;
    @FXML
    private Button searchProduct;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private TextField searchProductTextField;
    @FXML
    public TableView productTableView;
    @FXML
    public TableColumn productIDColumn;
    @FXML
    public TableColumn productNameColumn;
    @FXML
    public TableColumn productInvColumn;
    @FXML
    public TableColumn productPriceColumn;
    @FXML
    public TableColumn productMaxColumn;
    @FXML
    public TableColumn productMinColumn;

    /**
     * When this method is called the scene will switch to the Add scene
     *
     */
    public void AddPartButtonPushed(ActionEvent event) throws IOException {}

    //Switches to the modify part screen
    public void ModifyPartButtonPushed(ActionEvent event) throws IOException {}

    //Switches to the add product screen
    public void AddProductButtonPushed(ActionEvent event) throws IOException {}

    //Switches to the modify product screen
    public void ModifyProductButtonPushed(ActionEvent event) throws IOException {}

    public void DeletePartButtonPushed(ActionEvent event) throws IOException {}

    public void DeleteProductButtonPushed(ActionEvent event) throws IOException {}

    public void ExitButtonPushed(ActionEvent event) throws IOException {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            Platform.exit();
            System.exit(0);
        }
    }

    public void setPartTableSource(ObservableList partList) {
        Property<ObservableList<Part>> partsProperty = new SimpleObjectProperty<>(partList);
        partTableView.itemsProperty().bind(partsProperty);
    }

    public void setProductTableSource(ObservableList productList) {
        Property<ObservableList<Product>> productsProperty = new SimpleObjectProperty<>(productList);
        productTableView.itemsProperty().bind(productsProperty);
    }

    public Button getAddPartButton() {
        return addPartButton;
    }

    public Button getModifyPartButton() {
        return modifyPartButton;
    }

    public Button getDeletePartButton() {
        return deletePartButton;
    }

    public Button getDeleteProductButton() {
        return deleteProductButton;
    }

    public Button getAddProductButton() {
        return addProductButton;
    }

    public Button getModifyProductButton() {
        return modifyProductButton;
    }

    public Button getSearchPartButton() {
        return searchPart;
    }

    public Button getSearchProductButton() {
        return searchProduct;
    }

    public TextField getSearchPartText() {
        return searchPartTextField;
    }

    public TextField getSearchProductText() {
        return searchProductTextField;
    }

    public TableView getPartsTable() {
        return partTableView;
    }

    public TableView getProductsTable() {
        return productTableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

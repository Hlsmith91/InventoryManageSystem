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
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author HeatherSmith
 */
public class AddProductScreenController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private Button delete;
    @FXML
    private Button search;
    @FXML
    private TableView allPartsTable;
    @FXML
    private TableColumn allPartIdColumn;
    @FXML
    private TableColumn allPartNameColumn;
    @FXML
    private TableColumn allPartInvColumn;
    @FXML
    private TableColumn allPartPriceColumn;
    @FXML
    private TableView assocPartTable;
    @FXML
    private TableColumn assocIdColumn;
    @FXML
    private TableColumn assocNameColumn;
    @FXML
    private TableColumn assocInvColumn;
    @FXML
    private TableColumn assocPriceColumn;
    @FXML
    private TextField searchTextField;
    @FXML
    public TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField minTextField;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product product;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        Property<ObservableList<Part>> assocPartsProperty = new SimpleObjectProperty<>(associatedParts);
        assocPartTable.itemsProperty().bind(assocPartsProperty);
    }

    public void cancelButtonClicked(ActionEvent event) throws IOException {

    }

    public Button getAddButton() {
        return add;
    }

    public Button getSaveButton() {
        return save;
    }

    public Button getCancelButton() {
        return cancel;
    }

    public Product createProduct() {
        
        if(! isValid()) return null;
        Product product = null;
            int id = Integer.parseInt(idTextField.getText());
            int inv = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            
            product = new Product(id, nameTextField.getText(), price, inv, min, max);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
          return product;  
    }

    public void addButtonClicked(ActionEvent e) {
        Part part = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        assocPartTable.refresh();
    }

    public void deleteButtonClicked(ActionEvent e) {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            Part part = (Part) assocPartTable.getSelectionModel().getSelectedItem();
            associatedParts.remove(part);
            assocPartTable.refresh();
        }
    }
    
    public void searchButtonClicked() {

    }

    public void setAllPartTableSource(ObservableList partList) {
        allPartsTable.setItems(partList);
    }

    public TableView getallPartsTable() {
        return allPartsTable;
    }

    public TableView getAssocPartTable() {
        return assocPartTable;
    }

    public Button getSearchPartButton() {
        return search;
    }

    public TextField getSearchPartText() {
        return searchTextField;
    }

    private boolean isValid() {
        Alert a = new Alert(AlertType.INFORMATION);
        
        if(assocPartTable.getItems().isEmpty())
        {
            a.setContentText("Product must have an associated part.");
            a.showAndWait();
            return false;
        }

            if(Integer.parseInt(minTextField.getText()) > Integer.parseInt(maxTextField.getText()))
            {
                a.setContentText("Min must be less than max.");
                a.showAndWait();
                return false;
            }
            if(Integer.parseInt(invTextField.getText()) > Integer.parseInt(maxTextField.getText()) 
                    || Integer.parseInt(invTextField.getText()) < Integer.parseInt(minTextField.getText()))
            {
                a.setContentText("Inventory must be between the min and max.");
                a.showAndWait();
                return false;
            }
            double pricesum = 0.00;
            for(Part part : associatedParts)
                pricesum += part.getPrice();
            
                if(Double.parseDouble(priceTextField.getText()) < pricesum)
                {
                    a.setContentText("Price must be equal to or higher than the sum of the associated part prices.");
                    a.showAndWait();
                    return false;
                }
            
            
        return true;
    }
    
     public void reset()
    {
        idTextField.clear();
        nameTextField.clear();
        invTextField.clear();
        priceTextField.clear();
        maxTextField.clear();
        minTextField.clear();
        searchTextField.clear();
        
       associatedParts.clear();
    }
     
     

}

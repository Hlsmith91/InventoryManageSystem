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
 * @author Heather Smith
 */
public class ModifyProductController implements Initializable {
    @FXML
    private Button delete;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private Button search;
    @FXML
    private Button add;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField idTextField;
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
    Product product;
    boolean modifySuccess;
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
       
    }

    public void setProduct(Product selectedProduct) {
        product = selectedProduct;
        assocPartTable.setItems(product.getAllAssociatedParts());
        idTextField.setText(Integer.toString(product.getId()));
        nameTextField.setText(product.getName());
        invTextField.setText(Integer.toString(product.getStock()));
        priceTextField.setText(Double.toString(product.getPrice()));
        maxTextField.setText(Integer.toString(product.getMax()));
        minTextField.setText(Integer.toString(product.getMin()));
        
        
    }

    public void addButtonClicked(ActionEvent e) {
        Part part = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(part);
        assocPartTable.refresh();

    }
    
     public void cancelButtonClicked(ActionEvent event) throws IOException {

    }
    
     public void deleteAssocPart()
     {
         Alert a = new Alert(AlertType.CONFIRMATION);
         a.setContentText("Are you sure you want to delete this part?");
         Optional<ButtonType> result = a.showAndWait();
         if(result.get() == ButtonType.OK)
         {
            Part part = (Part)assocPartTable.getSelectionModel().getSelectedItem();
            product.deleteAssociatedPart(part);
            assocPartTable.refresh();
         }
     }
     
    public void reset() {

    }

    public void setAllPartTableSource(ObservableList partList) {
        allPartsTable.setItems(partList);
    }

    public Button getSaveButton() {
        return save;
    }

    public Button getCancelButton() {
        return cancel;
    }
    
    public Button getDeleteButton()
    {
        return delete;
    }
    public void modifyProduct() {
        modifySuccess = false;
        
        if(isValid()) 
        {
            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            int inv = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setStock(inv);
            product.setMin(min);
            product.setMax(max);
            modifySuccess = true;
        }
        
    }

    public Product getProduct() {
        return product;
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
        Alert a = new Alert(Alert.AlertType.INFORMATION);

        if (assocPartTable.getItems().isEmpty()) {
            a.setContentText("Product must have an associated part.");
            a.show();
            return false;
        }

        if (Integer.parseInt(minTextField.getText()) > Integer.parseInt(maxTextField.getText())) {
            a.setContentText("Min must be less than max.");
            a.show();
            return false;
        }
        if (Integer.parseInt(invTextField.getText()) > Integer.parseInt(maxTextField.getText())
                || Integer.parseInt(invTextField.getText()) < Integer.parseInt(minTextField.getText())) {
            a.setContentText("Inventory must be between the min and max.");
            a.show();
            return false;
        }
        double pricesum = 0;
        for (Part part : product.getAllAssociatedParts()) {
            pricesum += part.getPrice();
        }

        if (Double.parseDouble(priceTextField.getText()) < pricesum) {
            a.setContentText("Price must be equal to or higher than the sum of the associated part prices.");
            a.show();
            return false;
        }

        return true;
    }

    public boolean getModifySuccess() {
        
        return modifySuccess;
    }
    
    public void clearTextFields()
    {
        searchTextField.clear();
        nameTextField.clear();
        invTextField.clear();
        priceTextField.clear();
        maxTextField.clear();
        minTextField.clear();
    }
    
    public void resetAssociatedParts()
    {
        Part part = (Part)allPartsTable.getSelectionModel().getSelectedItem();
        product.deleteAssociatedPart(part);
        assocPartTable.refresh();
        
    }
}

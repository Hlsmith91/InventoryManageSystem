/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagementSystem;

import InventoryManagementSystem.Model.InHouse;
import InventoryManagementSystem.Model.Inventory;
import InventoryManagementSystem.Model.Outsourced;
import InventoryManagementSystem.Model.Part;
import InventoryManagementSystem.Model.Product;
import InventoryManagementSystem.View_Controller.AddPartController;
import InventoryManagementSystem.View_Controller.AddProductScreenController;
import InventoryManagementSystem.View_Controller.MainScreenController;
import InventoryManagementSystem.View_Controller.ModifyPartController;
import InventoryManagementSystem.View_Controller.ModifyProductController;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Heather Smith
 */
public class InventoryManagementSystem extends Application {

    private Scene mainScreenScene;
    private Scene addPartScene;
    private Scene modifyPartScene;
    private Scene addProductScene;
    private Scene modifyProductScene;
    Part part;
    Product product;
    Product selectedProduct;
    Part selectedPart;
    Alert confirm = new Alert(AlertType.CONFIRMATION);
    ObservableList <Part> searchList = FXCollections.observableArrayList();
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory newInventory = new Inventory();
        addTestData(newInventory);
        FXMLLoader mainFXML = new FXMLLoader(getClass().getResource("View_Controller/MainScreen.fxml"));
        FXMLLoader addPartFXML = new FXMLLoader(getClass().getResource("View_Controller/AddPart.fxml"));
        FXMLLoader modifyPartFXML = new FXMLLoader(getClass().getResource("View_Controller/ModifyPart.fxml"));
        FXMLLoader addProductFXML = new FXMLLoader(getClass().getResource("View_Controller/AddProductScreen.fxml"));
        FXMLLoader modifyProductFXML = new FXMLLoader(getClass().getResource("View_Controller/ModifyProduct.fxml"));

        mainScreenScene = new Scene(mainFXML.load());
        addPartScene = new Scene(addPartFXML.load());
        modifyPartScene = new Scene(modifyPartFXML.load());
        addProductScene = new Scene(addProductFXML.load());
        modifyProductScene = new Scene(modifyProductFXML.load());

        MainScreenController mainController = mainFXML.getController();
        AddPartController inHouseController = addPartFXML.getController();
        AddProductScreenController addProductCtrl = addProductFXML.getController();
        ModifyPartController modifyPartCtrl = modifyPartFXML.getController();
        ModifyProductController modifyProductCtrl = modifyProductFXML.getController();
        
        //Set tables on the main screen
        mainController.setPartTableSource(newInventory.getAllParts());
        mainController.setProductTableSource(newInventory.getAllProducts());
        
        //Searches for the main screen
        //Search Part table
        mainController.setPartTableSource(newInventory.getAllParts());
        Button searchPart = mainController.getSearchPartButton();
        searchPart.setOnAction(e -> {
        String searchText = mainController.getSearchPartText().getText();
             if(searchText.isEmpty())
             {
                mainController.setPartTableSource(newInventory.getAllParts());  
             }
             else
             {
                 int id = 0;
                 try
                 {
                    id = Integer.parseInt(searchText);
                 }
                 catch(NumberFormatException ex)     
                 {}
                if (id > 0) {
                     ObservableList <Part> searchList = FXCollections.observableArrayList();
                     Part part = newInventory.lookupPart(id);
                     if(part != null)
                     {
                     searchList.add(part);
                     }
                     mainController.setPartTableSource(searchList);                  
                } else { 
                    mainController.setPartTableSource(newInventory.lookupPart(searchText));
                }
             }
        });
        
        //Search Product table
        mainController.setProductTableSource(newInventory.getAllProducts());
        Button searchProductMain = mainController.getSearchProductButton();
        searchProductMain.setOnAction(e -> {
        String searchText = mainController.getSearchProductText().getText();
             if(searchText.isEmpty())
             {
                mainController.setProductTableSource(newInventory.getAllProducts());  
             }
             else
             {
                 int id = 0;
                 try
                 {
                    id = Integer.parseInt(searchText);
                 }
                 catch(NumberFormatException ex)     
                 {}
                if (id > 0) {
                     ObservableList <Product> searchList = FXCollections.observableArrayList();
                     Product product = newInventory.lookupProduct(id);
                     if(product != null)
                     {
                     searchList.add(product);
                     }
                     mainController.setProductTableSource(searchList);                  
                } else { 
                    mainController.setProductTableSource(newInventory.lookupProduct(searchText));
                }
             }
        });
        
        //Delete selected part in part table on main screen
        Button deletePartButton = mainController.getDeletePartButton();
        deletePartButton.setOnAction(e -> {
            Button btn = mainController.getDeletePartButton();
            try {
                mainController.DeletePartButtonPushed(e);
                part = (Part) mainController.partTableView.getSelectionModel().getSelectedItem();
                if(part != null)
                {
                    confirm.setContentText("Are you sure you want to delete the part?");
                    Optional<ButtonType> result = confirm.showAndWait();
                
                    if(result.get() == ButtonType.OK)
                    {
                        mainController.partTableView.getItems().remove(part);
                        newInventory.DeletePart(part);
                    }
                }
                else
                {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setHeaderText("Select Part");
                    a.setContentText("Please select a part to delete.");
                    a.showAndWait();
                }
            } catch (IOException ex) {
                Logger.getLogger(InventoryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Delete selected Product if it has no associated Part
        Button deleteProductButton = mainController.getDeleteProductButton();
        deleteProductButton.setOnAction(e -> {
            Button btn = mainController.getDeleteProductButton();
            try {
                mainController.DeleteProductButtonPushed(e);
                product = (Product) mainController.productTableView.getSelectionModel().getSelectedItem();
                if (product != null) {
                    if (product.getAllAssociatedParts().isEmpty()) {
                        confirm.setContentText("Are you sure you want to delete the product?");
                        Optional<ButtonType> result = confirm.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            mainController.productTableView.getItems().remove(product);
                            newInventory.DeleteProduct(product);
                        }
                    } else {
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setHeaderText("Product has an associated part");
                        a.setContentText("Product has an associated part. To be able to delete the product remove the associated part.");
                        a.showAndWait();
                    }
                }
                else
                {
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setHeaderText("Select Product");
                    a.setContentText("Please select a product to delete.");
                    a.showAndWait();
                }
            } catch (IOException ex) {
                Logger.getLogger(InventoryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // Enable the add part button and shows the add part screen and sets the part id
        Button showAddPart = mainController.getAddPartButton();
        showAddPart.setOnAction(e -> {
            inHouseController.idTextField.setText(Integer.toString(newInventory.getCurrentPartId()));
            stage.setScene(addPartScene);
        });
        
        //Enable the add product button and shows the add product screen and sets the product id
        Button addProduct = mainController.getAddProductButton();
        addProduct.setOnAction(e -> {
            addProductCtrl.idTextField.setText(Integer.toString(newInventory.getCurrentProductId()));
            stage.setScene(addProductScene);
        });
      
        //Enable the Modify product button and shows screen with the selected part
        modifyProductCtrl.setAllPartTableSource(newInventory.getAllParts());
        Button modifyProduct = mainController.getModifyProductButton();
        modifyProduct.setOnAction(e -> {
            TableView productTable = mainController.getProductsTable();
            selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
            if(selectedProduct != null)
            {
                modifyProductCtrl.setProduct(selectedProduct);
                stage.setScene(modifyProductScene);
            }
            else
            {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText("Select Product");
                a.setContentText("Please select a product.");
                a.showAndWait();
            }
                    
        });
        
        //Enable the Modify product button and sends the selected product
        Button modifyPart = mainController.getModifyPartButton();
        modifyPart.setOnAction(e -> {
            selectedPart = (Part) mainController.getPartsTable().getSelectionModel().getSelectedItem();
            if(selectedPart != null)
            {
                modifyPartCtrl.setPart(selectedPart);
                stage.setScene(modifyPartScene);
            }
            else
            {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Please select a part.");
                a.showAndWait();
            }

        });
        
        //Fuctionalities for the Add Part Screen
        Button saveNewPartButton = inHouseController.getSaveButton();
        saveNewPartButton.setOnAction(e -> {
            Button btn = inHouseController.getSaveButton();
            try {
                inHouseController.saveButtonClicked(e);
                Part part = inHouseController.getPart();
                
                if (part != null) {
                    confirm.setHeaderText("Save Confirmation");
                    confirm.setContentText("Are you sure you want to save?");
                    Optional<ButtonType> result = confirm.showAndWait();
                    
                    if(result.get() == ButtonType.OK)
                    {
                        newInventory.addPart(part);
                        stage.setScene(mainScreenScene);
                        inHouseController.clearTextFields();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
 
        //Cancels the add part screen and sets the scene to the main screen
        Button cancelAddPart = inHouseController.getCancelPartButton();
        cancelAddPart.setOnAction(e -> {
            confirm.setContentText("Are you sure you want to cancel?");
            Optional<ButtonType> result = confirm.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                inHouseController.clearTextFields();
                stage.setScene(mainScreenScene);
            }
        });
        
        //Fuctionalities for Modify Part Screen
        Button saveModPartButton = modifyPartCtrl.getSaveButton();
        saveModPartButton.setOnAction(e -> {
            Button btn = modifyPartCtrl.getSaveButton();

            modifyPartCtrl.saveButtonClicked();
            Part modPart = modifyPartCtrl.getModPart();
            Part part = modifyPartCtrl.getPart();
            if (modPart != null) {
                int index = newInventory.getAllParts().indexOf(part);
                confirm.setHeaderText("Save Confirmation");
                confirm.setContentText("Are you sure you want to save?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == ButtonType.OK) {
                    newInventory.updatePart(index, modPart);
                    mainController.partTableView.refresh();
                    stage.setScene(mainScreenScene);
                }
            }
        });
        
        //Cancels the modify part screen and sets the scene to the main screen
        Button cancelModifyPart = modifyPartCtrl.getCancelModPartButton();
        cancelModifyPart.setOnAction(e -> {
            confirm.setContentText("Are you sure you want to cancel?");
            Optional<ButtonType> result = confirm.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                stage.setScene(mainScreenScene);
            }
        });

//Functionalities for Add Product Screen
        Button saveNewProductButton = addProductCtrl.getSaveButton();
        saveNewProductButton.setOnAction(e -> {
            Button btn = addProductCtrl.getSaveButton();
            Product product = addProductCtrl.createProduct();
   
            if (product != null) 
            {
                confirm.setHeaderText("Save Confirmation");
                confirm.setContentText("Are you sure you want to save?");
                Optional<ButtonType> result = confirm.showAndWait();
                if(result.get() == ButtonType.OK)
                {
                    newInventory.addProduct(product); 
                    addProductCtrl.reset();
                    stage.setScene(mainScreenScene);
                    
                }
            }
        });
        
 //Search the allParts table in add Product Screen
        addProductCtrl.setAllPartTableSource(newInventory.getAllParts());
        Button searchProduct = addProductCtrl.getSearchPartButton();
        searchProduct.setOnAction(e -> {
        String searchText = addProductCtrl.getSearchPartText().getText();
             if(searchText.isEmpty())
             {
                addProductCtrl.setAllPartTableSource(newInventory.getAllParts());  
             }
             else
             {
                 int id = 0;
                 try
                 {
                    id = Integer.parseInt(searchText);
                 }
                 catch(NumberFormatException ex)     
                 {}
                if (id > 0) {
                     ObservableList <Part> searchList = FXCollections.observableArrayList();
                     Part part = newInventory.lookupPart(id);
                     if(part != null)
                     {
                     searchList.add(part);
                     }
                     addProductCtrl.setAllPartTableSource(searchList);  
                } else { 
                    addProductCtrl.setAllPartTableSource(newInventory.lookupPart(searchText));
                }
                
             }
        });
        
        Button cancelAddProduct = addProductCtrl.getCancelButton();
        cancelAddProduct.setOnAction(e -> {
            addProductCtrl.reset();
            addProductCtrl.setAllPartTableSource(newInventory.getAllParts());
            stage.setScene(mainScreenScene);
        });
        
//Fuctionalities for Modify Product Screen
        Button searchModPart = modifyProductCtrl.getSearchPartButton();
        searchModPart.setOnAction(e -> {
        String searchText = modifyProductCtrl.getSearchPartText().getText();
             if(searchText.isEmpty())
             {
                modifyProductCtrl.setAllPartTableSource(newInventory.getAllParts());  
             }
             else
             {
                 int id = 0;
                 try
                 {
                    id = Integer.parseInt(searchText);
                 }
                 catch(NumberFormatException ex)     
                 {}
                if (id > 0) {
                     
                     Part part = newInventory.lookupPart(id);
                     if(part != null)
                     {
                     searchList.add(part);
                     }
                     modifyProductCtrl.setAllPartTableSource(searchList);                  
                } else { 
                    modifyProductCtrl.setAllPartTableSource(newInventory.lookupPart(searchText));
                }
             }
        });

        Button saveModProductButton = modifyProductCtrl.getSaveButton();
        saveModProductButton.setOnAction(e -> {
            Button btn = modifyProductCtrl.getSaveButton();
            modifyProductCtrl.modifyProduct();
            if (modifyProductCtrl.getModifySuccess()) {
                mainController.getProductsTable().refresh();
                stage.setScene(mainScreenScene);
            } 
        });
        
        Button cancelModProduct = modifyProductCtrl.getCancelButton();
        cancelModProduct.setOnAction(e -> {
            modifyProductCtrl.resetAssociatedParts();
            modifyProductCtrl.clearTextFields();
            modifyProductCtrl.setAllPartTableSource(newInventory.getAllParts());
            stage.setScene(mainScreenScene);
        });
        stage.setTitle("Inventory Management System");
        stage.setScene(mainScreenScene);
        stage.show();
    }

    void addTestData(Inventory inv) {
        //InHouse test data
        Part p1 = new InHouse(1, "PartPart", 8.99, 34, 5, 10, 1345);
        Part p2 = new InHouse(2, "PartPart", 4.99, 105, 3, 28, 234);
        inv.addPart(p1);
        inv.addPart(p2);
        //Outsourced test data
        inv.addPart(new Outsourced(3, "Part 3", 27.65, 32, 2, 3, "Outsource Data"));
        inv.addPart(new Outsourced(4, "Part 4", 150.24, 437, 2, 500, "Outsource Data 2"));

        //associated Part  and Product test data
        Product prd = new Product(1, "Product 4", 456.54, 23, 25, 9);
        inv.addProduct(prd);
        inv.addProduct(new Product(2, "Product 1", 243.45, 23, 2, 6));
        inv.addProduct(new Product(3, "Product 3", 546.00, 3, 2, 4));
        prd.addAssociatedPart(p1);
        prd.addAssociatedPart(p2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.InHouse;
import InventoryManagementSystem.Model.Outsourced;
import InventoryManagementSystem.Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Heather Smith
 */
public class AddPartController implements Initializable {

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
    @FXML
    private TextField switchTextField;
    @FXML
    private RadioButton inHouseRb;
    @FXML
    private RadioButton outSourcedRb;
    @FXML
    private Label switchLabel;
    @FXML
    private ToggleGroup optionToggleGroup;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    String textId;
    Part part;
    Stage stage;
    Parent root;
    Alert a = new Alert(AlertType.NONE);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionToggleGroup = new ToggleGroup();
        this.inHouseRb.setToggleGroup(optionToggleGroup);
        this.outSourcedRb.setToggleGroup(optionToggleGroup);
    }

    public Button getSaveButton() {
        return save;
    }

    public Part getPart() {
        return part;
    }

    public Button getCancelPartButton() {
        return cancel;
    }

    public void radioButtonSelected(ActionEvent event) throws IOException {
        if (inHouseRb.isSelected() == true) {
            switchLabel.setText("Machine Id");
            switchTextField.setPromptText("Mach ID");
        } else if (outSourcedRb.isSelected() == true) {
            switchLabel.setText("Company Name");
            switchTextField.setPromptText("Comp Nm");
        }
    }

    public void cancelButtonClicked(ActionEvent event) throws IOException {
    }

    public Part saveButtonClicked(ActionEvent event) throws IOException {
        if(!isValid()) return null; 
                   
        int id = Integer.parseInt(idTextField.getText());
        int inv = Integer.parseInt(invTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());

        if (inHouseRb.isSelected() == true) {
            int machId = Integer.parseInt(switchTextField.getText());
            part = new InHouse(id, nameTextField.getText(), price, inv, min, max, machId);
        } else if (outSourcedRb.isSelected() == true) {
            part = new Outsourced(id, nameTextField.getText(), price, inv, min, max, switchTextField.getText());
        }       
       
        return part;
    }
    
    public boolean isValid()
    {   
        Alert a = new Alert(AlertType.INFORMATION);
        if(Integer.parseInt(invTextField.getText()) > Integer.parseInt(maxTextField.getText()) 
                || Integer.parseInt(invTextField.getText()) < Integer.parseInt(minTextField.getText()))
        {
            a.setContentText("Inventory must be between the max and the min.");
            a.showAndWait();
            return false;
        }
        if(Integer.parseInt(maxTextField.getText()) < Integer.parseInt(minTextField.getText()))
        {
            a.setContentText("Max must be greater than min");
            a.showAndWait();
            return false;
        }
        if(nameTextField.getText().trim().isEmpty())
        {
            a.setContentText("Please enter a part name.");
            a.showAndWait();
            return false;
        }
        if(invTextField.getText().trim().isEmpty())
        {
            invTextField.setText("0");
            return true;
        }
        if(priceTextField.getText().trim().isEmpty())
        {
            a.setContentText("Please enter a price.");
            a.showAndWait();
            return false;
        }
        if(inHouseRb.isSelected())
        {
            try
            {
                Integer.parseInt(switchTextField.getText());
            }
            catch(NumberFormatException ex)
            {
                a.setContentText("Please enter a numeric machine id.");
                a.showAndWait();
                return false;
            }
        }
        else if(outSourcedRb.isSelected() && switchTextField.getText().trim().isEmpty())
        {
            a.setContentText("Please enter a company name.");
            a.showAndWait();
            return false;
        }

        return true;
    }
    
    public void clearTextFields()
    {
        idTextField.clear();
        nameTextField.clear();
        invTextField.clear();
        priceTextField.clear();
        maxTextField.clear();
        minTextField.clear();
        switchTextField.clear();
    }
}

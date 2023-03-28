/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagementSystem.View_Controller;

import InventoryManagementSystem.Model.InHouse;
import InventoryManagementSystem.Model.Outsourced;
import InventoryManagementSystem.Model.Part;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Heather Smith
 */
public class ModifyPartController implements Initializable {

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
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label inv;
    @FXML
    private Label price;
    @FXML
    private Label max;
    @FXML
    private Label min;

    Part part;
    Part modPart;

    public Button getSaveModPartButton() {
        return save;
    }

    public Button getCancelModPartButton() {
        return cancel;
    }

    public void searchButtonClick() {
    }

    public Part saveButtonClicked() {
        if (isValid()) {
            int id = Integer.parseInt(idTextField.getText());
            int inv = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());

            if (inHouseRb.isSelected() == true) {
                int machId = Integer.parseInt(switchTextField.getText());
                modPart = new InHouse(id, nameTextField.getText(), price, inv, min, max, machId);

            } else if (outSourcedRb.isSelected() == true) {
                modPart = new Outsourced(id, nameTextField.getText(), price, inv, min, max, switchTextField.getText());
            }
        } else {
            return null;
        }
        return modPart;

    }

    public void setPart(Part part) {
        this.part = part;
        idTextField.setText(Integer.toString(part.getId()));
        nameTextField.setText(part.getName());
        invTextField.setText(Integer.toString(part.getStock()));
        priceTextField.setText(Double.toString(part.getPrice()));
        maxTextField.setText(Integer.toString(part.getMax()));
        minTextField.setText(Integer.toString(part.getMin()));
        if (part instanceof InHouse) {
            InHouse inPart = (InHouse) part;
            inHouseRb.setSelected(true);
            switchTextField.setText(Integer.toString(inPart.getMachineId()));
        } else {
            Outsourced outPart = (Outsourced) part;
            outSourcedRb.setSelected(true);
            inHouseRb.setSelected(false);
            switchLabel.setText("Company Name");
            switchTextField.setText(outPart.getCompanyName());
        }
    }

    public Button getSaveButton() {
        return save;
    }

    public Part getModPart() {
        return modPart;
    }

    public Part getPart() {
        return part;
    }

    public boolean isValid() {
        Alert a = new Alert(AlertType.INFORMATION);
        if(Integer.parseInt(invTextField.getText()) > Integer.parseInt(maxTextField.getText())
                || Integer.parseInt(invTextField.getText()) < Integer.parseInt(minTextField.getText()))
        {
            a.setContentText("Inventory must be between the max and the min.");
            a.showAndWait();
            return false;
        }
        if (Integer.parseInt(maxTextField.getText()) < Integer.parseInt(minTextField.getText())) {
            a.setContentText("Max must be higher than the min");
            a.showAndWait();
            return false;
        }
        if (invTextField.getText().trim().isEmpty()) {
            invTextField.setText("0");
            return true;
        }
        if (nameTextField.getText().trim().isEmpty()) {
            a.setContentText("Please enter a part name.");
            a.showAndWait();
            return false;
        }
        if(inHouseRb.isSelected() && switchTextField.getText().trim().isEmpty())
        {
            a.setContentText("Please enter a machine id.");
            a.showAndWait();
            return false;
        }
        else if(outSourcedRb.isSelected() && switchTextField.getText().trim().isEmpty())
        {
            a.setContentText("Please enter a company name.");
            a.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}

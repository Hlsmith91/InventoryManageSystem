/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryManagementSystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import InventoryManagementSystem.Model.Outsourced;
/**
 *
 * @author Heather Smith
 */
public class Inventory {

    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> lookupParts = FXCollections.observableArrayList();
    int id;
    int productCount = 0;
    int partCount = 0;
    
    public void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
            partCount++;
        }
    }

    public void addProduct(Product newProduct) {
        if (newProduct != null)
        {
            allProducts.add(newProduct);
            productCount ++;
        }
    }

    public void updatePart(int index, Part selectedPart) {
        
        Part part = allParts.get(index);
        if(part == null) return;
        if(selectedPart instanceof Outsourced)
        {
            part = new Outsourced(selectedPart.getId(), selectedPart.getName(), selectedPart.getPrice(), selectedPart.getStock(),
                    selectedPart.getMin(), selectedPart.getMax(), ((Outsourced) selectedPart).getCompanyName());
        }
        else
        {
            part = new InHouse(selectedPart.getId(), selectedPart.getName(), selectedPart.getPrice(),selectedPart.getStock(),
                    selectedPart.getMin(), selectedPart.getMax(), ((InHouse)selectedPart).getMachineId());
        }
        
        allParts.set(index, part);

    }

    public void updateProduct(int index, Product newProduct) {
        Product product = allProducts.get(index);
        product.setName(newProduct.getName());
        product.setStock(newProduct.getStock());
        product.setPrice(newProduct.getPrice());
        product.setMax(newProduct.getMax());
        product.setMin(newProduct.getMin());

    }

    public boolean DeletePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i) == selectedPart) {
                allParts.remove(i);
                break;
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean DeleteProduct(Product selectedProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i) == selectedProduct) {
                allProducts.remove(i);
                break;
            } else {
                return false;
            }
        }

        return true;
    }

    public Part lookupPart(int partId) {

        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    public Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public ObservableList<Part> getAllParts() {

        return allParts;
    }

    public ObservableList<Product> getAllProducts() {

        return allProducts;
    }
    
    public int getCurrentPartId()
    {
        return partCount +1;
    }
    
    public int getCurrentProductId()
    {
        return productCount +1;
    }
}

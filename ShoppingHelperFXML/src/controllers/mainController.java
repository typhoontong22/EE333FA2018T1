/*
 * File: mainController.java
 * Author: Tyler Pierce tpierce7@uab.edu
 * Assignment:  ShoppingHelperFXML - EE333 Fall 2018
 * Vers: 1.0.0 12/05/2018 ATP - initial coding
 *
 * Credits:  (if any for sections of code)
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
/**
 *
 * @author Tyler Pierce tpierce7@uab.edu
 */
public class mainController {
    
    @FXML
    private ComboBox selectStoreComboBox;
    @FXML
    private Button viewInventoryButton;
    @FXML
    private Button importListButton;
    @FXML
    private Button createListButton;
    @FXML
    private Button goButton;
    
    // Event listener on ComboBox[#selectStoreComboBox].onAction
    @FXML
    public void selectStore(ActionEvent event){
        //Code for storing the selected choice and enabling buttons
    }
    
    // Event listener on Button[#viewInventoryButton].onAction
    @FXML
    public void viewInventory(ActionEvent event){
        Main.setPane(1);
    }
    
    // Event listener on Button[#importListButton].onAction
    @FXML
    public void importList(ActionEvent event){
        //Code that pulls up file explorer
    }
    
    // Event listener on Button[#createListButton].onAction
    @FXML
    public void createList(ActionEvent event){
        //Just move to shoppingListPage
        Main.setPane(1);
    }
    
    // Event listener on Button[#goButton].onAction
    @FXML
    public void go(ActionEvent event){
        // ??? Not really sure what this is supposed to do...
        Main.setPane(2);
    }
    
    public void initialize(){
        // All the junk like disabling and enabling buttons and stuff I guess
    }
    
}

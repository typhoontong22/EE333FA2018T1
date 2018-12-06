/*
 * File: shoppingListController.java
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
public class shoppingListController {

@FXML
private Button removeButton;
@FXML
private Button backButton;
@FXML
private Button viewButton;
@FXML
private Button helpButton;
@FXML
private TextField searchTextField;

// Event listener on Button[#removeButton].onAction
@FXML
public void remove(ActionEvent event){
    // Code that removes all selected rows
}

// Event listener on Button[#viewButton].onAction
@FXML
public void view(ActionEvent event){
    // Code that sets up which of the rows should be shown
    Main.setPane(2);
}

// Event listener on Button[#backButton].onAction
@FXML
public void back(ActionEvent event){
    Main.setPane(0);
}

// Event listener on Button[#helpButton].onAction
@FXML
public void help(ActionEvent event){
    // Code that pops up a help window
}


// Event listener on Button[#searchButton].onAction
@FXML
public void search(ActionEvent event){
    // code that searches for items against the stores list
}

public void initialize(){
        // All the junk like disabling and enabling buttons and stuff I guess
    }

    
}

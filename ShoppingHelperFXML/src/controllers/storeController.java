/*
 * File: storeController.java
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
public class storeController {

    @FXML
    private Button closeButton;
    @FXML
    private Button helpButton;
    
    
    // Event listener on Button[#closeButton].onAction
    @FXML
    public void close(ActionEvent event){
        // Code that closes out the program I guess...
        Main.setPane(0);
    }
    
    // Event listener on Button[#helpButton].onAction
    @FXML
    public void help(ActionEvent event){
        // Code that pops up a help window
    }
    
    public void initialize(){
        // All the junk like disabling and enabling buttons and stuff I guess
    }
    
}

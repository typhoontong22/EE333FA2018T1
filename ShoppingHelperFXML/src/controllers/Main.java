/*
 * File: Main.java
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author Tyler Pierce tpierce7@uab.edu
 */
public class Main extends Application {
    static AnchorPane root;
    
    static List<AnchorPane> anchor = new ArrayList<AnchorPane>();
    
    private static int idx_cur = 0;
    
    @Override
    public void start(Stage primaryStage) {
        try{
            root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/anchor.fxml"));
            
            anchor.add((AnchorPane)FXMLLoader.load(getClass().getResource("/view/mainPage.fxml")));
            anchor.add((AnchorPane)FXMLLoader.load(getClass().getResource("/view/shoppingListPage.fxml")));
            anchor.add((AnchorPane)FXMLLoader.load(getClass().getResource("/view/storePage.fxml")));
            
            root.getChildren().add(anchor.get(0));

            Scene scene = new Scene(root, 550, 440);
            
            primaryStage.setTitle("Shopping Helper");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        } 
    }

    public static AnchorPane getPane(int idx){
        return anchor.get(idx);
    }
    
    public static void setPane(int idx){
        root.getChildren().remove(anchor.get(idx_cur));
        root.getChildren().add(anchor.get(idx));
        idx_cur = idx;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

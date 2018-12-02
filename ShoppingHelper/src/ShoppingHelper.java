/*
 * File: ScheduleBuilder.java
 * Author: David Green DGreen@uab.edu
 * Assignment:  ScheduleBuilder - EE333 Fall 2018
 * Vers: 1.1.0 11/26/2018 dgg - prevent resizing, add button enabling
 * Vers: 1.0.0 11/19/2018 dgg - initial coding
 */

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.ReadOnlyListPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author David Green DGreen@uab.edu
 */
public class ShoppingHelper extends Application {

    private final static double BUTTON_WIDTH = 120;

    private Dispatch dispatch;

    //private TextField pathField;
    //private TextField courseField;
    //private TextField semesterField;
    private ComboBox storeBox;
    
    
    Button viewInventoryButton;
    Button importListButton;
    Button createListButton;
    Button goButton;

    private String path;
    private String course;
    private String semester;
    
    @Override
    public void start(Stage primaryStage) {
        
        dispatch = new Dispatch(); // call to create backend object

        primaryStage.setTitle("Shopping Helper"); 

        // Configuration Section

        Label titleLabel     = new Label("Shopping Helper");
        //Label courseLabel   = new Label("Course");
        //Label semesterLabel = new Label("Semester");
        
        // fill in values for early test
        storeBox = new ComboBox();
        storeBox.setPromptText("Please Select Store...  ");
        //Populate
        ObservableList<String> store = FXCollections.observableArrayList("Target");
        
        storeBox.setItems(store);
        GridPane configGrid = new GridPane();
        configGrid.add(titleLabel,     1, 0);
        configGrid.add(storeBox,        1, 2);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(configGrid, HPos.CENTER);
        //configGrid.add(pathLabel,     0, 0);
        //configGrid.add(pathField,     1, 0);
        //configGrid.add(courseLabel,   0, 1);
        //configGrid.add(courseField,   1, 2);
        //configGrid.add(semesterLabel, 0, 2);
        //configGrid.add(semesterField, 1, 3);
        //GridPane.setHalignment(pathLabel,     HPos.RIGHT);
        //GridPane.setHalignment(courseLabel,   HPos.RIGHT);
        //GridPane.setHalignment(semesterLabel, HPos.RIGHT);
        configGrid.setPadding(new Insets(25, 25, 25, 25));
        configGrid.setHgap(50);
        configGrid.setVgap(10);
       
        // Work Flow Section
        
        viewInventoryButton           = new Button("View Inventory");
        importListButton         = new Button("Import List");
        createListButton                = new Button("Create List");
        goButton              = new Button("Go!");
        
        
        //Label createEditLabel      = new Label("Create or modify schedule in \r\n" +
        //                                       "in Spreadsheet.  Export CSV");
        //Label editTemplateLabel    = new Label("Edit Markdown template that\r\n" +
        //                                       "will hold schedule");
        //Label mergeLabel           = new Label("Merge CSV infomration into\r\n" +
        //                                       "Template replacing old information");
        //Label touchUpLabel         = new Label("Optionally edit results if\r\n" +
        //                                       "needed to apply any touch ups");
        //Label viewLabel            = new Label("View results in final form \r\n" +
        //                                       "after formatting");
        //Label exportHTMLLabel      = new Label("Create HTML version of schedule");

        viewInventoryButton.setMinWidth(BUTTON_WIDTH);
        importListButton.setMinWidth(BUTTON_WIDTH);
        createListButton.setMinWidth(BUTTON_WIDTH);
        goButton.setMinWidth(BUTTON_WIDTH);
        //viewButton.setMinWidth(BUTTON_WIDTH);
        //exportHTMLButton.setMinWidth(BUTTON_WIDTH);
 
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);
        //grid.add(new Label("1."),    0, 0);
        grid.add(viewInventoryButton,   1, 0);
        //grid.add(createEditLabel,    2, 0);
        //grid.add(new Label("2."),    0, 1);
        grid.add(importListButton, 1, 1);
        //grid.add(editTemplateLabel,  2, 1);
        //grid.add(new Label("3."),    0, 2);
        grid.add(createListButton,        1, 2);
        //grid.add(mergeLabel,         2, 2);
        //grid.add(new Label("4."),    0, 3);
        grid.add(goButton,      1, 3);
        //grid.add(touchUpLabel,       2, 3);
        //grid.add(new Label("5."),    0, 4);
        //grid.add(viewButton,         1, 4);
        //grid.add(viewLabel,          2, 4);
        //grid.add(new Label("6."),    0, 5);
        //grid.add(exportHTMLButton,   1, 5);
        //grid.add(exportHTMLLabel,    2, 5);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);

        // Map button actions to GUI methods (which generally dispatch to
        // Dispatch behaviors
        storeBox.setOnAction(event -> storeBoxSelected());
        viewInventoryButton.setOnAction(event -> viewInventoryClicked());
        importListButton.setOnAction(event -> importListClicked());
        createListButton.setOnAction(event -> createListClicked());
        goButton.setOnAction(event -> goClicked());
        //viewButton.setOnAction(event -> viewClicked());
        //exportHTMLButton.setOnAction(event -> exportHTMLClicked());

        enableReadyButtons();
        
        BorderPane borderPane = new BorderPane();
        
        borderPane.setTop(configGrid);
        borderPane.setCenter(grid);
        borderPane.setBottom(new Label("  Version 1.0 (20181202)                            Team 1"));
        
        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);       // don't allow resize
        primaryStage.focusedProperty().addListener(
                (observable, oldValue, newValue) -> { enableReadyButtons(); } );
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Update which buttons are enabled based on the state of the system
     */
    public void enableReadyButtons() {
        copyConfigInfo();
//        viewInventoryButton.setDisable(  ! dispatch.readyForCreateEdit());
//        importListButton.setDisable(! dispatch.readyForEditTemplate());
//        createListButton.setDisable(       ! dispatch.readyForMerge());
//        goButton.setDisable(     ! dispatch.readyForTouchUp());
        viewInventoryButton.setDisable(true);
        importListButton.setDisable(true);
        createListButton.setDisable(true);
        goButton.setDisable(true);
    }
    
    /**
     * GUI side of action associated with Create/Edit button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void viewInventoryClicked() {
//        copyConfigInfo();                           // ensure it is current
//        dispatch.createEditSchedule();
//        enableReadyButtons();
    }

    /**
     * GUI side of action associated with Edit Template button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void importListClicked() {
//        copyConfigInfo();                           // ensure it is current
//        dispatch.editTemplate();
//        enableReadyButtons();
        goButton.setDisable(false);
    }

    /**
     * GUI side of action associated with Merge button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void createListClicked() {
//        copyConfigInfo();                           // ensure it is current
//        dispatch.mergeSchedule();
//        enableReadyButtons();
        goButton.setDisable(false);
    }
    
    /**
     * GUI side of action associated with Touch Up button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
    public void goClicked() {
//        copyConfigInfo();                           // ensure it is current
//        dispatch.touchUpResult();        
//        enableReadyButtons();
    }
    
    /**
     * GUI side of action associated with View button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
//    public void viewClicked() {
//        copyConfigInfo();                           // ensure it is current
//        dispatch.viewResult();        
//        enableReadyButtons();
//    }
//    
    /**
     * GUI side of action associated with Export HTML button click
     * Set up config info and call code in dispatch.
     * TBD: update GUI State
     */
//    public void exportHTMLClicked() {
//        copyConfigInfo();                           // ensure it is current
//        dispatch.exportResult();
//        enableReadyButtons();
//    }

    // Copy the config out of text fields into private String variables
    // and convey this info to the dispatch object
    private void copyConfigInfo() {
        //path     = pathField.getText();
        //course   = courseField.getText();
        //semester = semesterField.getText();
        dispatch.setFullPath(path, course, semester);
    }

    private void storeBoxSelected() {
        viewInventoryButton.setDisable(false);
        importListButton.setDisable(false);
        createListButton.setDisable(false);
    }
    
}

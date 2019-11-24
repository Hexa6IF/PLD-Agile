package view;

import java.io.File;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Window {
    
    private final Controller controller;
    
    public Window(Controller controller) {
	this.controller = controller;
    }
    
    public void launchWindow() {
	Stage stage = new Stage();
	buildAndShowStage(stage);	
    }
    
    private void buildAndShowStage(Stage stage) {
	stage.setTitle("Del'IFeroo");
	
	stage.setResizable(false);
	
	stage.setScene(getScene(stage));
	
	stage.show();
    }
    
    private Scene getScene(Stage stage) {
	BorderPane border = new BorderPane();
	
	VBox menu = createMenu(stage);
	
	border.setTop(menu);
	
	return new Scene(border);
    }
    
    private VBox createMenu(Stage stage) {
	FileChooser fileChooser = new FileChooser();
	
	Menu menuFile = new Menu("File");
	Menu menuHelp = new Menu("Help");
	
	MenuItem loadMap = new MenuItem("Load Map...");
	MenuItem loadDeliveries = new MenuItem("Load Deliveries...");
	
	MenuItem about = new MenuItem("About");
	
	MenuBar menuBar = new MenuBar();
	
	menuFile.getItems().add(loadMap);
	menuFile.getItems().add(loadDeliveries);
	
	menuHelp.getItems().add(about);
	
	loadMap.setOnAction(e -> {
	    File mapFile = fileChooser.showOpenDialog(stage);
	});
	loadDeliveries.setOnAction(e -> {
	    File deliveriesFile = fileChooser.showOpenDialog(stage);
	});
	
	menuBar.getMenus().addAll(menuFile, menuHelp);
	
	return new VBox(menuBar);
    }
    
    
}

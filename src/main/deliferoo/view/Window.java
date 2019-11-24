package view;

import controller.Controller;
import javafx.stage.Stage;

public class Window {
    
    private final Controller controller;
    
    public Window(Controller controller) {
	this.controller = controller;
    }
    
    public void launchWindow() {
	Stage stage = new Stage();
	buildAndShowScene(stage);	
    }
    
    private void buildAndShowScene(Stage stage) {
	stage.setTitle("Del'IFeroo");
	
	stage.setFullScreen(true);
	stage.setResizable(false);
	
	stage.show();
    }
}

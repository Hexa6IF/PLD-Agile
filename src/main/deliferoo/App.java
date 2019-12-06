import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;
import java.util.Map;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Delivery;
import model.Edge;
import model.FullMap;
import model.Node;
import view.Window;
import xml.XMLParser;

/**
 * Main Application class for Del'IFeroo
 * 
 * @author sadsitha
 */
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
	new Controller();
    }

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {
	//launch(args);
    }
    
 
}

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.FullGraph;
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
	new Window(new Controller()).launchWindow();
    }

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {
	//launch(args);
    }

    /**
     * 
     * @return sum
     */
    public Integer onePlusOne() {
	return 1 + 1;
    }

    

}

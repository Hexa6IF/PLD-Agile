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
	XMLParser parser = new XMLParser();
	try {
	    System.out.println("before reading");
	    File fXmlFile = new File("resources/petitPlan.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    System.out.println("after reading");
	    FullGraph graph = parser.mapParser(doc);
	    System.out.println("aie");
	    graph.printFullGraph();
	    
	} catch (Exception e) {
	    System.out.println("erroree");
	}
    }

    /**
     * 
     * @return sum
     */
    public Integer onePlusOne() {
	return 1 + 1;
    }

    

}

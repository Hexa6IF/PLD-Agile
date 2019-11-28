package view;

import java.io.File;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.FullMap;
import view.NodeTextView.NodeType;
import xml.XMLParser;

public class Window {

    private Controller controller;
    private Rectangle2D bounds;
    private MapView mapView;
    private ObservableList<NodeTextView> nodeTextViews;
    private SpecialNodeView tableBox;

    private FullMap map;

    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();

	XMLParser parser = XMLParser.getInstance();
	try {
	    File fXmlFile = new File("src/main/resources/grandPlan.xml");
	    this.map = parser.parseMap(fXmlFile);
	} catch(Exception e) {
	    System.err.println(e);
	}

	this.mapView = new MapView(this.map, this.bounds.getHeight(), this.bounds.getWidth());

    }

    public void launchWindow() {
	Stage stage = new Stage();
	buildAndShowStage(stage);
    }

    private void buildAndShowStage(Stage stage) {
	stage.setTitle("Del'IFeroo");

	stage.setX(this.bounds.getMinX());
	stage.setY(this.bounds.getMinY());
	stage.setWidth(this.bounds.getWidth());
	stage.setHeight(this.bounds.getHeight());

	stage.setResizable(false);

	stage.setScene(getScene(stage));

	stage.show();
    }

    private Scene getScene(Stage stage) {
	BorderPane border = new BorderPane();

	/* Initialise Node text view */
	this.initialiseTable();

	border.setTop(new TopMenuBar(stage));
	border.setRight(createSideBar());
	border.setCenter(this.mapView.getMapView());

	return new Scene(border);
    }
    
    private VBox createSideBar() {
	VBox sideBar = new VBox();

	Rectangle rect1 = new Rectangle();
	rect1.setHeight(bounds.getHeight() / 4);
	rect1.setWidth(bounds.getWidth() / 3);
	
	Rectangle rect2 = new Rectangle();
	rect2.setHeight(bounds.getHeight() / 4);
	rect2.setWidth(bounds.getWidth() / 3);

	sideBar.getChildren().addAll(rect1, this.tableBox.getTable(), rect2);	

	return sideBar;
    }
    
    private GridPane createDetailView() {
	GridPane grid = new GridPane();	
	return grid;
    }

    /*
     * Creates the text view of special nodes
     * 
     */
    private void initialiseTable() {
	//example data to remove
	this.nodeTextViews = FXCollections.observableArrayList(
		new NodeTextView(1,Color.BLACK,NodeType.PICKUP,7f,"14h34"),
		new NodeTextView(2,Color.BLACK,NodeType.PICKUP,10f,"14h34"),
		new NodeTextView(1,Color.PURPLE,NodeType.DROPOFF,5f,"14h34"),
		new NodeTextView(2,Color.PURPLE,NodeType.PICKUP,4f,"14h34")
		);
	this.tableBox = new SpecialNodeView(this.nodeTextViews, this.bounds.getWidth()/3, this.bounds.getHeight()/2);
    }
}

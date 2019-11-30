package view;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import algorithm.Dijkstra;
import algorithm.TSP1;
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
import model.BestPath;
import model.Delivery;
import model.Edge;
import model.FullMap;
import xml.XMLParser;

public class Window {

    private Controller controller;
    private Rectangle2D bounds;
    
    FullMap map;
    
    private MapView mapView;
    private TableBoxView tableBoxView;

    private List<Delivery> deliveries;

    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();
	this.mapView = new MapView(this.bounds.getHeight(), 2 * this.bounds.getWidth() / 3);
	this.tableBoxView = new TableBoxView(this.bounds.getHeight() / 2, this.bounds.getWidth() / 3);
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

	border.setTop(createMenu(stage));
	border.setRight(createSideBar());
	border.setCenter(this.mapView.getMapView());
	
	return new Scene(border);
    }

    private VBox createMenu(Stage stage) {
	FileChooser fileChooser = new FileChooser();
	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

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
	    this.map = XMLParser.getInstance().parseMap(mapFile);
	    this.mapView.setMap(this.map);
	    this.mapView.drawMap(Color.BLACK, 2);
	});
	loadDeliveries.setOnAction(e -> {
	    File deliveriesFile = fileChooser.showOpenDialog(stage);
	    this.deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, this.map);
	    
	    List<SpecialNodeTextView> specialNodeTextViews = new ArrayList<SpecialNodeTextView>();
	    	    
	    for(Delivery delivery : this.deliveries) {
		Color color = generateRandomColor();
		specialNodeTextViews.add(new SpecialNodeTextView(delivery.getDeliveryIndex(), color, delivery.getPickupNode()));
		specialNodeTextViews.add(new SpecialNodeTextView(delivery.getDeliveryIndex(), color, delivery.getDeliveryNode()));
		this.mapView.drawMarker(delivery, color, 20);
	    }
	    
	    this.tableBoxView.setItems(FXCollections.observableArrayList(specialNodeTextViews));
	    
	    Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
	    TSP1 tsp = new TSP1();
	    tsp.searchSolution(3000, bestPaths);
	    List<BestPath> round = tsp.getBestPathSolution();
	    this.mapView.drawRound(round, Color.HOTPINK, 5);
	});

	menuBar.getMenus().addAll(menuFile, menuHelp);

	return new VBox(menuBar);
    }

    private VBox createSideBar() {
	VBox sideBar = new VBox();

	Rectangle rect1 = new Rectangle();
	rect1.setHeight(bounds.getHeight() / 4);
	rect1.setWidth(bounds.getWidth() / 3);

	Rectangle rect2 = new Rectangle();
	rect2.setHeight(bounds.getHeight() / 4);
	rect2.setWidth(bounds.getWidth() / 3);

	sideBar.getChildren().addAll(rect1, this.tableBoxView, rect2);	

	return sideBar;
    }

    private Color generateRandomColor() {
	Random rand = new Random();

	Double r = rand.nextDouble();
	Double g = rand.nextDouble();
	Double b = rand.nextDouble();
	return Color.color(r, g, b);
    }
}

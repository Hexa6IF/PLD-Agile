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
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import xml.XMLParser;

public class Window {

    private Controller controller;
    private Rectangle2D bounds;
    private MapView mapView;
    private ObservableList<SpecialNodeView> specialNodeViews;
    private TableBoxView tableBoxView;

    private FullMap map;
    private List<Delivery> deliveries;

    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();

	XMLParser parser = XMLParser.getInstance();
	try {
	    File fXmlFile = new File("src/main/resources/moyenPlan.xml");
	    this.map = parser.parseMap(fXmlFile);
	} catch (Exception e) {
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
	try {
	    this.initialiseTable();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    System.err.println("init failed");
	    e.printStackTrace();
	}

	border.setTop(new TopMenuBar(stage));
	border.setRight(createSideBar());
	
	/** calculate best paths **/
	
	Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
	
	/** calculate round **/
	TSP1 tsp = new TSP1();
	tsp.searchSolution(3000, bestPaths);
	List<BestPath> round = tsp.getBestPathSolution();
	/** print map**/
	border.setCenter(this.mapView.getMapView());
	/** draw parcours **/
	this.mapView.drawRound(round);
	/** draw delivery markers **/
	this.mapView.drawMarkers(this.deliveries);
	
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
	
	TableView<SpecialNodeView> table = this.tableBoxView.getTable();
	table.setPrefHeight(bounds.getHeight() / 2);
	table.setPrefWidth(bounds.getWidth() / 3);

	sideBar.getChildren().addAll(rect1, table, rect2);	

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
    private void initialiseTable() throws Exception {
	XMLParser parser = XMLParser.getInstance();
	
	try {
	    File deliveryFile = new File("src/main/resources/demandeMoyen3.xml");
	    this.deliveries = parser.parseDeliveries(deliveryFile, this.map);
	} catch (Exception e) {
	    System.err.println(e);
	    e.printStackTrace();
	}
	this.specialNodeViews = FXCollections.observableArrayList(createSpecialNodeViewList());
	this.tableBoxView = new TableBoxView(this.specialNodeViews);
    }
    
    private ArrayList<SpecialNodeView> createSpecialNodeViewList() throws Exception{
	Random rand = new Random();
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	LocalTime now = this.deliveries.get(0).getPickupNode().getPassageTime();
	ArrayList<SpecialNodeView> specialNodeViewTmpList = new ArrayList<SpecialNodeView>();
	
	Double r = rand.nextDouble();
	Double g = rand.nextDouble();
	Double b = rand.nextDouble();
	Color firstDeliveryColor = Color.color(r, g, b);
	specialNodeViewTmpList.add(new SpecialNodeView(0, firstDeliveryColor, deliveries.get(0).getPickupNode()));
	for (int i = 1; i < this.deliveries.size(); i++) {
	    r = rand.nextDouble();
	    g = rand.nextDouble();
	    b = rand.nextDouble();
	    Color randomColor = Color.color(r, g, b);
	    
	    now = now.plusMinutes((int) Math.round(deliveries.get(i).getPickupNode().getDuration()));
	    deliveries.get(i).getPickupNode().setPassageTime(now);
	    now = now.plusMinutes((int) Math.round(deliveries.get(i).getDeliveryNode().getDuration()));
	    deliveries.get(i).getDeliveryNode().setPassageTime(now);
	    specialNodeViewTmpList.add(new SpecialNodeView(i, randomColor, deliveries.get(i).getPickupNode()));
	    specialNodeViewTmpList.add(new SpecialNodeView(i, randomColor, deliveries.get(i).getDeliveryNode()));
	}
	deliveries.get(0).getDeliveryNode().setPassageTime(now);
	specialNodeViewTmpList.add(new SpecialNodeView(0, firstDeliveryColor, deliveries.get(0).getDeliveryNode()));

	return specialNodeViewTmpList;
    }
}

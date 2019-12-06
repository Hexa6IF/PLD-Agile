package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import controller.Controller;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.Round;

/**
 * Main window class
 * 
 * @author teck
 * @author sadsitha
 *
 */
public class Window {

    private Controller controller;
    private Rectangle2D bounds;
    private MapView mapView;
    private MessageView messageView;
    private DeliveryDetailView deliveryDetailView;
    private TableBoxView tableBoxView;

    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();
	this.mapView = new MapView(this.bounds.getHeight(), 2 * this.bounds.getWidth() / 3);
	this.messageView = new MessageView(this.bounds.getHeight() / 4, this.bounds.getWidth() / 3);
	this.deliveryDetailView = new DeliveryDetailView(this.bounds.getHeight() / 4, this.bounds.getWidth() / 3);
	this.tableBoxView = new TableBoxView(this.bounds.getHeight() / 2, this.bounds.getWidth() / 3);
	this.tableBoxView.setItems(FXCollections.observableList(new ArrayList<SpecialNodeTextView>()));
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
	    this.controller.loadMap(mapFile);
	});
	loadDeliveries.setOnAction(e -> {
	    File deliveriesFile = fileChooser.showOpenDialog(stage);
	    this.controller.loadDeliveries(deliveriesFile);
	});

	menuBar.getMenus().addAll(menuFile, menuHelp);
	return new VBox(menuBar);
    }

    private VBox createSideBar() {
	VBox sideBar = new VBox();
	sideBar.getChildren().addAll(this.messageView, this.deliveryDetailView, this.tableBoxView);
	return sideBar;
    }

    public Color generateRandomColor() {
	Random rand = new Random();
	Double r = rand.nextDouble();
	Double g = rand.nextDouble();
	Double b = rand.nextDouble();
	return Color.color(r, g, b);
    }

    /**rect1
     * Update the map graphical view
     * 
     * @param map
     */
    public void updateMap(FullMap map) {
	this.mapView.updateMap(map);
    }

    /**
     * Update the list of deliveries to display
     * 
     * @param deliveries
     */
    public void updateDeliveries(List<Delivery> deliveries) {
	ObservableList<SpecialNodeTextView> specialNodeTextViews = tableBoxView.getItems();
	specialNodeTextViews.clear();
	for (Delivery delivery : deliveries) {
	    Color color = generateRandomColor();
	    specialNodeTextViews
		    .add(new SpecialNodeTextView(delivery.getDeliveryIndex(), color, delivery.getPickupNode()));
	    specialNodeTextViews
		    .add(new SpecialNodeTextView(delivery.getDeliveryIndex(), color, delivery.getDeliveryNode()));
	    this.mapView.drawMarker(delivery, color, 20);
	}
    }

    /**
     * Update the displayed message
     * 
     * @param message
     */
    public void updateMessage(String message) {
	this.messageView.setCurrentMessage(message);
    }

    /**
     * Updates the round
     * 
     * @param round
     */
    public void updateRound(Round round) {
	this.mapView.updateRound(round);
    }
}

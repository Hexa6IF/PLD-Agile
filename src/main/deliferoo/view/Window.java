package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import controller.Controller;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;

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
    private OverviewPanel overviewPanel;
    private DeliveryDetailView deliveryDetailView;
    private ControlPanel controlPanel;
    private TableBoxView tableBoxView;
    private Map<Integer, Color> deliveryColorMap;

    /**
     * Constructor
     * 
     * @param controller
     */
    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();
	this.mapView = new MapView(this.bounds.getHeight(), 5 * this.bounds.getWidth() / 9);
	this.overviewPanel = new OverviewPanel(this.bounds.getHeight() / 6, 4 * this.bounds.getWidth() / 9);
	this.deliveryDetailView = new DeliveryDetailView(this.bounds.getHeight() / 6, 4 * this.bounds.getWidth() / 9);
	this.controlPanel = new ControlPanel(this.bounds.getHeight() / 6, 4 * this.bounds.getWidth() / 9);
	this.tableBoxView = new TableBoxView(this.bounds.getHeight() / 2, 4 * this.bounds.getWidth() / 9);
	this.tableBoxView.setItems(FXCollections.observableList(new ArrayList<SpecialNodeTextView>()));
	this.deliveryColorMap = new HashMap<>();

	this.addRowListeners();
	this.addButtonListeners();
    }

    /**
     * Build window content and show
     */
    public void launchWindow() {
	Stage stage = new Stage();
	buildAndShowStage(stage);
    }

    /**
     * Update the map graphical view
     * 
     * @param map
     */
    public void updateMap(FullMap map) {
	this.mapView.drawMap(map, Color.BLACK, 2);
    }

    /**
     * Update the list of deliveries to display
     * 
     * @param deliveries
     */
    public void updateDeliveries(List<Delivery> deliveries) {
	this.mapView.clearMarkers();

	List<SpecialNode> nodesToInsert = new ArrayList<>();
	for(Delivery delivery : deliveries) {
	    Color color;	    
	    if(deliveryColorMap.containsKey(delivery.getDeliveryIndex())) {
		color = this.deliveryColorMap.get(delivery.getDeliveryIndex());
	    } else {
		color = generateRandomColor();
		deliveryColorMap.put(delivery.getDeliveryIndex(), color);
	    }
	    nodesToInsert.add(delivery.getPickupNode());
	    nodesToInsert.add(delivery.getDeliveryNode());
	}
	this.tableBoxView.updateTableBox(nodesToInsert, this.deliveryColorMap);
	this.drawMarkers(deliveries, 20);
    }

    public void drawMarkers(List<Delivery> deliveries, Integer markerSize) {
	this.mapView.clearMarkers();
	for(Delivery delivery : deliveries) {
	    this.mapView.drawMarker(delivery, this.deliveryColorMap.get(delivery.getDeliveryIndex()), markerSize);
	}
	this.addMarkerSelectListeners();
    }

    /**
     * Update the view for selected delivery
     * 
     * @param delivery
     */
    public void updateSelectedDelivery(Delivery delivery) {
	this.mapView.highlightMarkers(delivery, Color.DODGERBLUE);
	this.deliveryDetailView.updateDeliveryDetail(delivery, this.deliveryColorMap);
    }

    /**
     * Update the view for the calculated round and orders the nodes in the table
     * 
     * @param round
     */
    public void updateRound(List<SpecialNode> circuit, Map<String, Map<String, BestPath>> bestPaths) {
	this.mapView.clearRound();
	List<SpecialNode> nodesToInsert = new ArrayList<>();

	for(int i = 0; i < circuit.size() - 1; i++) {
	    SpecialNode start = circuit.get(i);
	    SpecialNode end = circuit.get(i + 1);
	    BestPath bestPath = bestPaths.get(start.getNode().getNodeId()).get(end.getNode().getNodeId());

	    if(i == 0) {
		nodesToInsert.add(start);
	    }
	    nodesToInsert.add(end);

	    this.mapView.drawBestPath(bestPath, Color.HOTPINK, 8);
	}
	this.tableBoxView.updateTableBox(nodesToInsert, this.deliveryColorMap);
    }

    /**
     * Update the displayed message
     * 
     * @param message
     */
    public void updateMessage(String message) {
	this.controlPanel.setCurrentMessage(message);
    }

    public void disableButtons(boolean modify, boolean add, boolean remove, boolean confirm, 
	    boolean calculate, boolean undo, boolean redo, boolean cancel) {
	this.controlPanel.disableButtons(modify, add, remove, confirm, calculate, undo, redo, cancel);
    }

    /**
     * Generates a random color
     * 
     * @return	a random color
     */
    public Color generateRandomColor() {
	Random rand = new Random();
	Double r = rand.nextDouble();
	Double g = rand.nextDouble();
	Double b = rand.nextDouble();
	return Color.color(r, g, b);
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
	border.setCenter(this.mapView);
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
	    if(mapFile != null) {
		this.controller.loadMap(mapFile);
	    }
	});
	loadDeliveries.setOnAction(e -> {
	    File deliveriesFile = fileChooser.showOpenDialog(stage);
	    if(deliveriesFile != null) {
		this.controller.loadDeliveries(deliveriesFile);
	    }
	});

	menuBar.getMenus().addAll(menuFile, menuHelp);
	return new VBox(menuBar);
    }

    private VBox createSideBar() {
	VBox sideBar = new VBox();
	sideBar.getChildren().addAll(this.overviewPanel, this.deliveryDetailView, this.controlPanel, this.tableBoxView);
	return sideBar;
    }

    public void enableDeliveryModification(Delivery delivery) {
	Map<SpecialNode, Shape> deliveryMarkers =  this.mapView.getMarkers();
	SpecialNode pickup = delivery.getPickupNode();
	SpecialNode dropoff = delivery.getDeliveryNode();

	this.mapView.highlightMarkers(delivery, Color.DARKVIOLET);

	this.addMarkerDragListeners(pickup, deliveryMarkers.get(pickup));
	this.addMarkerDragListeners(dropoff, deliveryMarkers.get(dropoff));
    }

    public void addMarkerDragListeners(SpecialNode sn, Shape marker) {
	marker.setOnMousePressed(new EventHandler<MouseEvent>() {
	    @Override 
	    public void handle(MouseEvent mouseEvent) {
	    }
	});

	marker.setOnMouseReleased(e -> {
	    marker.getScene().setCursor(Cursor.HAND);
	});

	marker.setOnMouseDragged(e -> {
	    String nearestNodeId = this.mapView.moveMarkerToNearestNode(marker, e.getSceneX(), e.getSceneY());
	    this.controller.moveSpecialNode(sn, nearestNodeId);
	});

	marker.setOnMouseEntered(new EventHandler<MouseEvent>() {
	    @Override public void handle(MouseEvent mouseEvent) {
		if (!mouseEvent.isPrimaryButtonDown()) {
		    marker.getScene().setCursor(Cursor.HAND);
		}
	    }
	});

	marker.setOnMouseExited(new EventHandler<MouseEvent>() {
	    @Override public void handle(MouseEvent mouseEvent) {
		if (!mouseEvent.isPrimaryButtonDown()) {
		    marker.getScene().setCursor(Cursor.DEFAULT);
		}
	    }
	});
    }

    private void addRowListeners() {
	this.tableBoxView.getSelectionModel().selectedItemProperty().addListener((observer, oldSelection, newSelection) -> {
	    if(newSelection == null) {
		this.tableBoxView.getSelectionModel().clearSelection();
	    } else {
		this.controller.selectDeliveryClick(newSelection.getDeliveryIndex());
	    }
	});
    }

    private void addMarkerSelectListeners() {
	for(SpecialNode sn : this.mapView.getMarkers().keySet()) {
	    this.mapView.getMarkers().get(sn).setOnMouseClicked(e -> {
		this.controller.selectDeliveryClick(sn.getDelivery().getDeliveryIndex());	
	    });
	}
    }

    private void addButtonListeners() {
	this.controlPanel.setModifyAction(e -> {
	    this.controller.modifyButtonClick();
	});

	this.controlPanel.setAddAction(e -> {
	    this.controller.addButtonClick();
	});

	this.controlPanel.setRemoveAction(e -> {
	    this.controller.removeButtonClick();
	});

	this.controlPanel.setConfirmAction(e -> {
	    this.controller.confirmButtonClick();
	});

	this.controlPanel.setCalculateAction(e -> {
	    this.controller.calculateButtonClick();
	});

	this.controlPanel.setUndoAction(e -> {
	    this.controller.undo();
	});

	this.controlPanel.setRedoAction(e -> {
	    this.controller.redo();
	});

	this.controlPanel.setCancelAction(e -> {
	    this.controller.cancelButtonClick();
	});
    }
}

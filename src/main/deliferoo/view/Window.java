package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import controller.Controller;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import model.SpecialNodeType;

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

	this.mapView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
	    Pair<String, Bounds> nearestNode = this.mapView.getNearestNodeBounds(e.getSceneX(), e.getSceneY());
	    this.controller.placeNode(nearestNode.getKey(), nearestNode.getValue());
	});

	this.addTableRowListeners();
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
     * Clear window content and show
     */
    public void clearMap() {
	this.mapView.getChildren().clear();
    }

    /**
     * Clear delivery markers from window content and show
     */
    public void clearDeliveriesMarkers() {
	this.mapView.clearMarkers();
    }

    /**
     * Clear delivery round from window content and show
     */
    public void clearDeliveriesRound() {
	this.mapView.clearMarkers();
    }

    public void clearTempMarkers() {
	this.mapView.clearTempMarker();
    }

    public void drawTempMarker(SpecialNode toDraw, Integer deliveryIndex) {
	Color color;
	if(deliveryColorMap.containsKey(deliveryIndex)) {
	    color = deliveryColorMap.get(deliveryIndex);
	} else {
	    color = generateRandomColor();
	    deliveryColorMap.put(deliveryIndex, color);
	}
	this.mapView.drawTempMarker(toDraw, color, 20);
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
	    if(delivery == null) {
		continue;
	    }

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

    /**
     * Update the view for the calculated round and orders the nodes in the table
     * 
     * @param round
     */
    public void updateRound(List<SpecialNode> round, Map<String, Map<String, BestPath>> bestPaths) {
	this.mapView.clearRoundLines();
	if(round.size() > 2) {
	    for(int i = 0; i < round.size() - 1; i++) {
		String startId = round.get(i).getNode().getNodeId();
		String endId = round.get(i + 1).getNode().getNodeId();
		BestPath bestPath = bestPaths.get(startId).get(endId);
		this.mapView.drawBestPath(bestPath, Color.HOTPINK, 8);
	    }
	}
	this.tableBoxView.updateTableBox(round, this.deliveryColorMap);
	this.overviewPanel.updateOverview(round);
    }

    /**
     * Confirm the round calculated and hide difference indicators.
     *
     */
    public void confirmRound() {
	this.overviewPanel.confirmRound();
    }

    public void drawMarkers(List<Delivery> deliveries, Integer markerSize) {
	this.mapView.clearMarkers();
	for(Delivery delivery : deliveries) {
	    if(delivery == null) {
		this.mapView.drawMarker(null, null, null);
	    } else {
		this.mapView.drawMarker(delivery, this.deliveryColorMap.get(delivery.getDeliveryIndex()), markerSize);
	    }
	}
	this.addMarkerSelectListeners();
    }

    /**
     * Update the view for selected delivery
     * 
     * @param delivery
     */
    public void updateSelectedDelivery(Delivery delivery) {
	this.mapView.highlightMarkers(delivery.getDeliveryIndex(), Color.DODGERBLUE);
	this.updateDeliveryDetail(delivery);
    }

    public void updateDeliveryDetail(Delivery delivery) {
	this.deliveryDetailView.updateDeliveryDetail(delivery, this.deliveryColorMap);
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

    public void enableDeliveryModification(Integer deliveryIndex) {
	if(deliveryIndex != 0) {
	    Pair<Shape, Shape> deliveryMarkers =  this.mapView.getMarkers().get(deliveryIndex);
	    this.mapView.highlightMarkers(deliveryIndex, Color.DARKVIOLET);
	    this.addMarkerDragListeners(deliveryIndex, SpecialNodeType.PICKUP, deliveryMarkers.getKey());
	    this.addMarkerDragListeners(deliveryIndex, SpecialNodeType.DROPOFF, deliveryMarkers.getValue());
	}
    }

    public void setDurationEdit(boolean pickup, boolean dropoff) {
	this.deliveryDetailView.setDurationEdit(pickup, dropoff);
    }

    public Double getPickupDuration() {
	return this.deliveryDetailView.getPickupDuration();
    }

    public Double getDropoffDuration() {
	return this.deliveryDetailView.getDropoffDuration();
    }

    public void setRoundOrdering(boolean enable) {
	this.tableBoxView.setDrag(enable);
    }

    public void addMarkerDragListeners(Integer id, SpecialNodeType type, Shape marker) {
	marker.setOnMouseReleased(e -> {
	    marker.getScene().setCursor(Cursor.HAND);
	});

	marker.setOnMouseDragged(e -> {
	    String nearestNodeId = this.mapView.moveMarkerToNearestNode(marker, e.getSceneX(), e.getSceneY());
	    this.controller.changeNodePosition(id, type, nearestNodeId);
	});

	marker.setOnMouseEntered(e -> {
	    if (!e.isPrimaryButtonDown()) {
		marker.getScene().setCursor(Cursor.HAND);
	    }
	});

	marker.setOnMouseExited(e -> {
	    if (!e.isPrimaryButtonDown()) {
		marker.getScene().setCursor(Cursor.DEFAULT);
	    }
	});
    }

    private void addTableRowListeners() {
	TableBoxView tbv = this.tableBoxView;

	/* selection listeners */
	tbv.getSelectionModel().selectedItemProperty().addListener((observer, oldSelection, newSelection) -> {
	    if(newSelection == null) {
		tbv.getSelectionModel().clearSelection();
	    } else {
		this.controller.selectDeliveryClick(newSelection.getDeliveryIndex(), newSelection.getType());
	    }
	});

	/* drag listeners */
	tbv.setRowFactory(tv -> {
	    TableRow<SpecialNodeTextView> row = new TableRow<>();
	    row.setOnDragDetected(e -> {
		Integer id = row.getIndex();
		if (tbv.getDrag() && !row.isEmpty() && !(id == 0 || id == tbv.getItems().size() - 1)) {
		    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
		    db.setDragView(row.snapshot(null, null));
		    ClipboardContent cc = new ClipboardContent();
		    cc.put(TableBoxView.SNTV_INDEX_DATA, id);
		    db.setContent(cc);
		    e.consume();
		}
	    });

	    row.setOnDragOver(e -> {
		Dragboard db = e.getDragboard();
		if (db.hasContent(TableBoxView.SNTV_INDEX_DATA)) {
		    if (row.getIndex() != ((Integer)db.getContent(TableBoxView.SNTV_INDEX_DATA)).intValue()) {
			e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			e.consume();
		    }
		}
	    });

	    row.setOnDragDropped(e -> {
		Dragboard db = e.getDragboard();
		if (db.hasContent(TableBoxView.SNTV_INDEX_DATA)) {
		    int dropIndex = row.getIndex();
		    if(!(dropIndex == 0 || dropIndex >= tbv.getItems().size() - 1)) {
			int draggedIndex = (Integer) db.getContent(TableBoxView.SNTV_INDEX_DATA);
			SpecialNodeTextView draggedNode = tbv.getItems().remove(draggedIndex);
			tbv.getItems().add(dropIndex, draggedNode);
			e.setDropCompleted(true);
			tbv.getSelectionModel().select(dropIndex);
			this.controller.changeRoundOrder(tbv.getItems());
			e.consume();
		    }
		}
	    });
	    return row ;
	});
    }

    private void addMarkerSelectListeners() {
	List<Pair<Shape, Shape>> markers = this.mapView.getMarkers();
	for(int i = 0; i < markers.size(); i++) {
	    if(markers.get(i) == null) {
		continue;
	    }

	    if(i == 0) {
		markers.get(i).getKey().setOnMouseClicked(e -> {
		    this.controller.selectDeliveryClick(0, SpecialNodeType.START);	
		});
		continue;
	    }

	    Integer deliveryIndex = i;
	    markers.get(i).getKey().setOnMouseClicked(e -> {
		this.controller.selectDeliveryClick(deliveryIndex, SpecialNodeType.PICKUP);	
	    });
	    markers.get(i).getValue().setOnMouseClicked(e -> {
		this.controller.selectDeliveryClick(deliveryIndex, SpecialNodeType.DROPOFF);
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
//TO DELETE
	public void drawSimulation(String selectedNode) {
		this.mapView.drawSimulation(selectedNode, Color.DARKBLUE,8);
		
	}
}

package view;

import java.io.File;

import controller.Controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Edge;
import model.FullGraph;
import model.Node;

public class Window {
    
    private Controller controller;
    private Rectangle2D bounds;
    private FullGraphMap fullGraphMap;
    
    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();
	
	Node n1 = new Node(25175791l, 45.75406f, 4.857418f);
	Node n2 = new Node(2129259178l, 45.750404f, 4.8744674f);
	Node n3 = new Node(26086130l, 45.75871f, 4.8784823f);
	
	Edge e1 = new Edge(n1, n2, 10f, "ouais");
	Edge e2 = new Edge(n2, n3, 20f, "yep");
	
	Edge[] e = {e1, e2};
	Node[] n = {n1, n2, n3};
	
	this.fullGraphMap = new FullGraphMap(new FullGraph(e, n, 45.750404f, 45.75871f, 4.857418f, 4.8784823f), this.bounds.getHeight(), this.bounds.getWidth());
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
	border.setCenter(this.fullGraphMap.getMap());
	
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
	});
	loadDeliveries.setOnAction(e -> {
	    File deliveriesFile = fileChooser.showOpenDialog(stage);
	});

	menuBar.getMenus().addAll(menuFile, menuHelp);

	return new VBox(menuBar);
    }
    
    private VBox createSideBar() {	
	VBox sideBar = new VBox();
	
	Rectangle rect = new Rectangle();
	rect.setHeight(bounds.getHeight()/2);
	rect.setWidth(bounds.getWidth()/3);
	TableView<String> table = new TableView<String>();
	table.setPrefSize(bounds.getWidth()/3, bounds.getHeight()/2);
	
	sideBar.setPrefWidth(bounds.getWidth()/3);
	sideBar.getChildren().addAll(rect, table);

	return sideBar;
    }
}

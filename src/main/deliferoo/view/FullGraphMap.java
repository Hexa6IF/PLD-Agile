package view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import model.Node;
import model.AbstractEdge;

/**
 * Class for displaying full map
 * 
 * @author sadsitha
 */
public class FullGraphMap {
    private final Float rectHeight = 400.0f;
    private final Float rectWidth = 400.0f;
    
    private BorderPane pane;
    private List<Node> nodeList;
    private Integer counter;
    private Rectangle map;

    /**
     * Constructor
     * 
     * @param nodeList List of nodes to display on graph
     */
    public FullGraphMap(List<Node> nodeList) {
	this.pane = new BorderPane();
	this.nodeList = nodeList;
	this.map = new Rectangle();
	this.counter = 0;
	
	this.setBaseMap();
    }
    
    /**
     * Constructor - with empty parameters
     * 
     */
    public FullGraphMap() {
	this.pane = new BorderPane();
	this.nodeList = new ArrayList<Node>();
	this.map = new Rectangle();
	this.counter = 0;
	
	this.setBaseMap();
    }

    /*
     * Returns a border pane containing the map
     * 
     * @return pane BorderPane containing current map
     */
    public BorderPane getMap() {
	this.pane.setCenter(map);
	return this.pane;
    }
    
    /*
     * Creates the map background
     * 
     * @return map Rectangle to represent map background
     */
    private void setBaseMap() {
	this.map.setWidth(rectWidth); 
	this.map.setHeight(rectHeight);
	this.map.setFill(Color.WHITE);
    }
    
    private void mapNodes(Rectangle map) {
	Double xOrigin = map.getX();
	Double yOrigin = map.getY();
	Double maxLatitude = Double.NEGATIVE_INFINITY;
	Double minLatitude = Double.POSITIVE_INFINITY;
	Double maxLongitude = Double.NEGATIVE_INFINITY;
	Double minLongitude = Double.POSITIVE_INFINITY;
	this.nodeList.forEach(node->{
	    
	});
    }

}

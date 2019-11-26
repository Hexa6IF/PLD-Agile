package view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.Edge;
import model.FullGraph;
import model.Node;

/**
 * Class for displaying full map
 * 
 * @author sadsitha
 */
public class FullGraphMap {

    private Pane map;
    
    private FullGraph mapGraph;
    private Float dimension;

    /**
     * Constructor
     * 
     * @param nodeList List of nodes to display on graph
     */
    public FullGraphMap(FullGraph mapGraph, Float dimension) {
	this.map = new Pane();
	this.map.setPrefSize(500, 500);
	this.mapGraph = mapGraph;
	this.dimension = dimension;
    }
    
    public Pane getMap() {
	draw();
	this.map.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, new Insets(5))));
	return this.map;
    }
    
    public void draw() {
	for(Edge e : (Edge[])mapGraph.getEdges()) {
	    float x1 = this.dimension*((e.getNodeOrigin().getLatitude() - this.mapGraph.getMinLatitude()) / (this.mapGraph.getMaxLatitude() - this.mapGraph.getMinLatitude()));
	    float y1 = this.dimension*((e.getNodeOrigin().getLongitude() - this.mapGraph.getMinLongitude()) / (this.mapGraph.getMaxLongitude() - this.mapGraph.getMinLongitude()));
	    float x2 = this.dimension*((e.getNodeDest().getLatitude() - this.mapGraph.getMinLatitude()) / (this.mapGraph.getMaxLatitude() - this.mapGraph.getMinLatitude()));
	    float y2 = this.dimension*((e.getNodeDest().getLongitude() - this.mapGraph.getMinLongitude()) / (this.mapGraph.getMaxLongitude() - this.mapGraph.getMinLongitude()));
	    
	    Line l = new Line(x1, y1, x2, y2);
	    this.map.getChildren().add(l);
	}
    }
}

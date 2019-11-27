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
    private Double height;
    private Double width;

    private Double offsetX;
    private Double offsetY;

    /**
     * Constructor
     * 
     * @param nodeList List of nodes to display on graph
     */
    public FullGraphMap(FullGraph mapGraph, Double screenHeight, Double screenWidth ) {
	this.map = new Pane();
	this.mapGraph = mapGraph;

	this.height = screenHeight;
	this.width = 2 * screenWidth / 3;

	this.offsetX = 0.05 * this.width;
	this.offsetY = 0.05 * this.height;
    }

    public Pane getMap() {
	draw();
	this.map.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), 
		BorderWidths.DEFAULT, new Insets(10))));
	return this.map;
    }

    private void draw() {
	Double dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);	
	System.out.println(dimension);
	for(Edge e : (Edge[])mapGraph.getEdges()) {
	    this.map.getChildren().add(createroad(e, dimension));
	}
    }

    private Line createroad(Edge edge, Double dimension) {
	Double x1 = this.offsetX + dimension * ((edge.getNodeOrigin().getLatitude() - this.mapGraph.getMinLatitude()) / 
		(this.mapGraph.getMaxLatitude() - this.mapGraph.getMinLatitude()));
	Double y1 = this.offsetY + dimension * ((edge.getNodeOrigin().getLongitude() - this.mapGraph.getMinLongitude()) / 
		(this.mapGraph.getMaxLongitude() - this.mapGraph.getMinLongitude()));
	Double x2 = this.offsetX + dimension * ((edge.getNodeDest().getLatitude() - this.mapGraph.getMinLatitude()) / 
		(this.mapGraph.getMaxLatitude() - this.mapGraph.getMinLatitude()));
	Double y2 = this.offsetY + dimension * ((edge.getNodeDest().getLongitude() - this.mapGraph.getMinLongitude()) / 
		(this.mapGraph.getMaxLongitude() - this.mapGraph.getMinLongitude()));
	return new Line(x1, y1, x2, y2);
    }
}

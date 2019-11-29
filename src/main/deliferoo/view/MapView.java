package view;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.BestPath;
import model.Edge;
import model.FullMap;

/**
 * Class for displaying full map
 * 
 * @author sadsitha
 */
public class MapView {

    private Pane mapView;

    private FullMap map;
    private Double height;
    private Double width;

    private Double offsetX;
    private Double offsetY;

    /**
     * Constructor
     * 
     * @param nodeList List of nodes to display on graph
     */
    public MapView(FullMap map, Double screenHeight, Double screenWidth ) {
	this.mapView = new Pane();
	this.map = map;

	this.height = screenHeight;
	this.width = 2 * screenWidth / 3;

	this.offsetX = 0.05 * this.width;
	this.offsetY = 0.05 * this.height;
    }

    public Pane getMapView(Map<String, BestPath> bestPaths) {
	this.mapView = new Pane();
	draw(map.getEdgeList(), Color.BLACK, 2);

	Random rand = new Random();
	
	for(String s : bestPaths.keySet()) {
	    System.out.println(s);
	}
	
	//342873658
	//208769039
	//25173820
	Double r = rand.nextDouble();
	Double g = rand.nextDouble();
	Double b = rand.nextDouble();
	Color color = Color.color(r, g, b);
	draw(bestPaths.get("34401989").getPath(), color, 5);

	this.mapView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), 
		BorderWidths.DEFAULT, new Insets(10))));
	return this.mapView;
    }

    private void draw(List<Edge> edges, Color color, Integer width) {
	Double dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);
	for(Edge edge : edges) {
	    this.mapView.getChildren().add(createRoad(edge, dimension, color, width));
	}
    }

    private Line createRoad(Edge edge, Double dimension, Color color, Integer width) {
	Double x1 = this.offsetX + dimension * (edge.getStart().getLongitude() - this.map.getMinLong()) / 
		this.map.getRangeLongitude();
	Double y1 = this.offsetY + dimension * (this.map.getMaxLat() - edge.getStart().getLatitude()) / 
		this.map.getRangeLatitude();

	Double x2 = this.offsetX + dimension * (edge.getEnd().getLongitude() - this.map.getMinLong()) / 
		this.map.getRangeLongitude();
	Double y2 = this.offsetY + dimension * (this.map.getMaxLat() - edge.getEnd().getLatitude()) / 
		this.map.getRangeLatitude();

	Line road = new Line(x1, y1, x2, y2);
	road.setStroke(color);
	road.setStrokeWidth(width);
	Tooltip.install(road, new Tooltip(edge.getStreetName() + " " + edge.getStart().getNodeId() + " | " + edge.getEnd().getNodeId() + " | " + edge.getDistance()));
	
	return road;
    }
}

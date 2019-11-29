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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.BestPath;
import model.Delivery;
import model.Edge;
import model.FullMap;
import model.SpecialNode;
import model.SpecialNodeType;

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
    public MapView(FullMap map, Double screenHeight, Double screenWidth) {
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

	for (String s : bestPaths.keySet()) {
	    System.out.println(s);
	}

	// 342873658
	// 208769039
	// 25173820
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
	for (Edge edge : edges) {
	    this.mapView.getChildren().add(createRoad(edge, dimension, color, width));
	}
    }

    private Line createRoad(Edge edge, Double dimension, Color color, Integer width) {
	Double x1 = this.offsetX
		+ dimension * (edge.getStart().getLongitude() - this.map.getMinLong()) / this.map.getRangeLongitude();
	Double y1 = this.offsetY
		+ dimension * (this.map.getMaxLat() - edge.getStart().getLatitude()) / this.map.getRangeLatitude();

	Double x2 = this.offsetX
		+ dimension * (edge.getEnd().getLongitude() - this.map.getMinLong()) / this.map.getRangeLongitude();
	Double y2 = this.offsetY
		+ dimension * (this.map.getMaxLat() - edge.getEnd().getLatitude()) / this.map.getRangeLatitude();

	Line road = new Line(x1, y1, x2, y2);
	road.setStroke(color);
	road.setStrokeWidth(width);
	Tooltip.install(road, new Tooltip(edge.getStreetName() + " " + edge.getStart().getNodeId() + " | "
		+ edge.getEnd().getNodeId() + " | " + edge.getDistance()));

	return road;
    }

    public void drawMarkers(List<Delivery> deliveries) {
	Double dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);

	for (Delivery delivery : deliveries) {
	    Random rand = new Random();
	    Double r = rand.nextDouble();
	    Double g = rand.nextDouble();
	    Double b = rand.nextDouble();
	    Color deliveryColor = Color.color(r, g, b);
	    SpecialNode start = delivery.getPickupNode();
	    SpecialNode end = delivery.getDeliveryNode();
	    Shape startMarker = this.createMarker(start, dimension, deliveryColor);
	    Shape endMarker = this.createMarker(end, dimension, deliveryColor);
	    this.mapView.getChildren().addAll(startMarker, endMarker);
	}
    }

    private Shape createMarker(SpecialNode node, Double dimension, Paint paint) {
	Shape marker = null;

	Double x = this.offsetX
		+ dimension * ((node.getNode().getLongitude() - this.map.getMinLong()) / this.map.getRangeLongitude());
	Double y = this.offsetY
		+ dimension * ((this.map.getMaxLat() - node.getNode().getLatitude()) / this.map.getRangeLatitude());

	String textToDisplay = node.getSpecialNodeType() + " : " + node.getDuration();

	if (node.getSpecialNodeType() == SpecialNodeType.PICKUP) {
	    marker = new Circle(x, y, 5.0, paint);
	} else if (node.getSpecialNodeType() == SpecialNodeType.DROPOFF) {
	    marker = new Rectangle(x - 5, y - 5, 10, 10);
	    marker.setFill(paint);
	} else {
	    marker = new Circle(x, y, 5.0, paint);
	}

	Tooltip.install(marker, new Tooltip(textToDisplay));
	return marker;
    }

    public Shape drawRound(List<BestPath> bestPaths) {
	Polyline round = new Polyline();
	Double dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);e
	for (BestPath bestPath : bestPaths) {
	    List<Edge> path = bestPath.getPath();
	    for (Edge edge : path) {
		
		Double x1 = this.offsetX
			+ dimension * (edge.getStart().getLongitude() - this.map.getMinLong()) / this.map.getRangeLongitude();
		Double y1 = this.offsetY
			+ dimension * (this.map.getMaxLat() - edge.getStart().getLatitude()) / this.map.getRangeLatitude();

		Double x2 = this.offsetX
			+ dimension * (edge.getEnd().getLongitude() - this.map.getMinLong()) / this.map.getRangeLongitude();
		Double y2 = this.offsetY
			+ dimension * (this.map.getMaxLat() - edge.getEnd().getLatitude()) / this.map.getRangeLatitude();
		round.getPoints().addAll(new Double[] { x1, y1, x2, y2 });
	    }

	}
    }

}

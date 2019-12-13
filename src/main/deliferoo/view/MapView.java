package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.util.Pair;
import model.BestPath;
import model.Delivery;
import model.Edge;
import model.FullMap;
import model.Node;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * Class for displaying a map
 *
 * @author sadsitha
 */
public class MapView extends Pane {

    private Double minLong;
    private Double maxLong;
    private Double minLat;
    private Double maxLat;

    private Double height;
    private Double width;
    private Double offsetX;
    private Double offsetY;
    private Double dimension;

    private List<Set<Line>> roundLines;
    private Map<String, Shape> nodeShapes;
    private List<Pair<Shape, Shape>> markers;

    private Shape tempPickup;
    private Shape tempDropoff;
    private Shape tempWarehouse;

    /**
     * Constructor
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public MapView(Double height, Double width) {
	super();
	this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5),
		BorderWidths.DEFAULT, new Insets(10))));
	this.height = height;
	this.width = width;
	this.offsetX = 0.05 * this.width;
	this.offsetY = 0.05 * this.height;
	this.dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);
	this.roundLines = new ArrayList<Set<Line>>();
	this.nodeShapes = new HashMap<String, Shape>();
	this.markers = new ArrayList<Pair<Shape, Shape>>();
    }

    /**
     * Gets all the lines corresponding to the round
     * @return	round lines
     */
    public List<Set<Line>> getRoundLine() {
	return this.roundLines;
    }

    /**
     * Gets the markers on the map
     * @return	all markers in the map
     */
    public List<Pair<Shape, Shape>> getMarkers() {
	return this.markers;
    }

    /**
     * Draws the map
     *
     * @param color       Color of the paths of this MapView
     * @param strokeWidth Width of the paths
     */
    public void drawMap(FullMap map, Color color, Integer strokeWidth) {
	this.minLong = map.getMinLong();
	this.maxLong = map.getMaxLong();
	this.minLat = map.getMinLat();
	this.maxLat = map.getMaxLat();
	
	Set<Node> nodes = new HashSet<Node>();
	this.getChildren().clear();
	
	for (Edge edge : map.getEdgeList()) {
	    drawEdge(edge, color, strokeWidth);
	    nodes.add(edge.getStart());
	    nodes.add(edge.getEnd());
	}

	for (Node node : nodes) {
	    Shape nodeShape = drawNode(node, Color.TRANSPARENT, 4);
	    nodeShapes.put(node.getNodeId(), nodeShape);
	}
    }

    /**
     * Draws a path
     *
     * @param edge        the corresponding edge to be drawn on the corresponding MapView
     * @param color       the color of the drawn path
     * @param strokeWidth the width of the path drawn
     */
    public Line drawEdge(Edge edge, Color color, Integer strokeWidth) {
	Pair<Double, Double> p1 = calculateRelativePosition(edge.getStart());
	Pair<Double, Double> p2 = calculateRelativePosition(edge.getEnd());

	Line path = new Line(p1.getKey(), p1.getValue(), p2.getKey(), p2.getValue());

	path.setStroke(color);
	path.setStrokeWidth(strokeWidth);

	this.getChildren().add(path);
	return path;
    }

    /**
     * Draws a square corresponding to a node
     * @param node	the corresponding node
     * @param color	the color of the square
     * @param size	the size of the square
     * @return		the square drawn
     */
    public Shape drawNode(Node node, Color color, Integer size) {
	Pair<Double, Double> p = calculateRelativePosition(node);

	Double x = p.getKey();
	Double y = p.getValue();

	Rectangle nodeShape = new Rectangle(x - 2, y - 2, 4, 4);
	nodeShape.setFill(color);

	Tooltip tip = new Tooltip(node.getNodeId());
	Tooltip.install(nodeShape, tip);
	nodeShape.toFront();
	this.getChildren().add(nodeShape);

	return nodeShape;
    }

    /**
     * Draws the best path from one node to another
     * @param bestPath		the best path from one node to another
     * @param color		the color of the line to draw
     * @param strokeWidth	the width of the line
     */
    public void drawBestPath(BestPath bestPath, Color color, Integer strokeWidth) {
	Set<Line> bestPathLines = new HashSet<>();
	for (Edge edge : bestPath.getPath()) {
	    Line road = drawEdge(edge, color, strokeWidth);
	    road.toBack();
	    bestPathLines.add(road);
	}
	this.roundLines.add(bestPathLines);
    }

    /**
     * Clears lines on map corresponding to the round
     */
    public void clearRoundLines() {
	for(Set<Line> lines : this.roundLines) {
	    this.getChildren().removeAll(lines);
	}
	this.roundLines = new ArrayList<>();
    }
    
    public void grayBestPaths(Integer index) {
	for(int i = 0; i < this.roundLines.size(); i++){
	    if(i >= index) {
		for(Line l : this.roundLines.get(i)) {
		    l.setStroke(Color.DARKGRAY);
		};
	    } else {
		for(Line l : this.roundLines.get(i)) {
		    l.setStroke(Color.HOTPINK);
		};
	    }
	}
    }

    /**
     * Draws markers corresponding to checkpoints of a delivery
     *
     * @param delivery   the corresponding delivery to be represented by the markers
     * @param color      the color code for the markers
     * @param markerSize the size of the markers
     */
    public void drawMarker(Delivery delivery, Color color, Integer markerSize) {
	if(delivery == null) {
	    this.markers.add(null);
	    return;
	}

	if (delivery.getDeliveryIndex() == 0) {
	    SpecialNode warehouse = delivery.getPickupNode();

	    Pair<Double, Double> p = calculateRelativePosition(warehouse.getNode());

	    Double x = p.getKey();
	    Double y = p.getValue();

	    Polyline marker = new Polyline();
	    marker.getPoints().addAll(x, y - 2 * markerSize / 3, x - markerSize / 2, y + markerSize / 3,
		    x + markerSize / 2, y + markerSize / 3, x, y - 2 * markerSize / 3);

	    marker.setFill(color);
	    marker.setStroke(Color.BLACK);
	    marker.setStrokeWidth(1);

	    this.markers.add(delivery.getDeliveryIndex(), new Pair<Shape, Shape>(marker, marker));
	    this.getChildren().add(marker);
	} else {
	    SpecialNode start = delivery.getPickupNode();
	    SpecialNode end = delivery.getDeliveryNode();

	    Pair<Double, Double> p1 = calculateRelativePosition(start.getNode());
	    Pair<Double, Double> p2 = calculateRelativePosition(end.getNode());

	    Double x1 = p1.getKey();
	    Double y1 = p1.getValue();

	    Double x2 = p2.getKey();
	    Double y2 = p2.getValue();

	    Shape startMarker = new Circle(x1, y1, markerSize / 2);
	    Shape endMarker = new Rectangle(x2 - markerSize / 2, y2 - markerSize / 2, markerSize, markerSize);

	    startMarker.setFill(color);
	    startMarker.setStroke(Color.BLACK);
	    startMarker.setStrokeWidth(1);

	    endMarker.setFill(color);
	    endMarker.setStroke(Color.BLACK);
	    endMarker.setStrokeWidth(1);

	    this.markers.add(delivery.getDeliveryIndex(), new Pair<Shape, Shape>(startMarker, endMarker));
	    this.getChildren().addAll(startMarker, endMarker);
	}
    }

    public void drawTempMarker(SpecialNode toDraw, Color color, Integer markerSize) {
	Pair<Double, Double> p = calculateRelativePosition(toDraw.getNode());
	Double x = p.getKey();
	Double y = p.getValue();
	if(toDraw.getSpecialNodeType() == SpecialNodeType.PICKUP) {
	    this.getChildren().remove(this.tempPickup);
	    this.tempPickup = new Circle(x, y, markerSize / 2);
	    this.tempPickup.setFill(color);
	    this.tempPickup.setStroke(Color.BLACK);
	    this.tempPickup.setStrokeWidth(1);
	    this.getChildren().add(tempPickup);
	} else if(toDraw.getSpecialNodeType() == SpecialNodeType.DROPOFF){
	    this.getChildren().remove(this.tempDropoff);
	    this.tempDropoff = new Rectangle(x - markerSize / 2, y - markerSize / 2, markerSize, markerSize);
	    this.tempDropoff.setFill(color);
	    this.tempDropoff.setStroke(Color.BLACK);
	    this.tempDropoff.setStrokeWidth(1);
	    this.getChildren().add(tempDropoff);
	} else {
	    this.getChildren().remove(this.tempWarehouse);
	    Polyline marker = new Polyline();
	    marker.getPoints().addAll(x, y - 2 * markerSize / 3, x - markerSize / 2, y + markerSize / 3,
		    x + markerSize / 2, y + markerSize / 3, x, y - 2 * markerSize / 3);
	    this.tempWarehouse = marker;
	    this.tempWarehouse.setFill(color);
	    this.tempWarehouse.setStroke(Color.BLACK);
	    this.tempWarehouse.setStrokeWidth(1);
	    this.getChildren().add(tempWarehouse);
	}
    }
    
    public void clearTempMarker() {
	this.getChildren().removeAll(this.tempPickup, this.tempDropoff, this.tempWarehouse);
    }

    /**
     * Changes border of markers corresponding to selected delivery
     * 
     * @param deliveryIndex
     * @param color
     */
    public void highlightMarkers(Integer deliveryIndex, Color color) {
	for(Pair<Shape, Shape> shapes : this.markers) {
	    if(shapes == null) {
		continue;
	    }
	    shapes.getKey().setStroke(Color.BLACK);
	    shapes.getKey().setStrokeWidth(1);
	    shapes.getValue().setStroke(Color.BLACK);
	    shapes.getValue().setStrokeWidth(1);
	}

	Shape startMarker = markers.get(deliveryIndex).getKey();
	startMarker.setStroke(color);
	startMarker.setStrokeType(StrokeType.OUTSIDE);
	startMarker.setStrokeWidth(5);

	Shape endMarker= markers.get(deliveryIndex).getValue();
	endMarker.setStroke(color);
	endMarker.setStrokeType(StrokeType.OUTSIDE);
	endMarker.setStrokeWidth(5);
    }

    /**
     * Clears all markers on map
     */
    public void clearMarkers() {
	for(Pair<Shape, Shape> shapes : this.markers) {
	    if(shapes == null) {
		continue;
	    }
	    this.getChildren().removeAll(shapes.getKey(), shapes.getValue());
	}
	this.markers = new ArrayList<>();
    }

    /**
     * Moves marker to the nearest node from coordinates (x, y)
     * @param marker	marker to move
     * @param x		x-value 
     * @param y		y-value 
     * @return		ID of the nearest node
     */
    public String moveMarkerToNearestNode(Shape marker, Double x, Double y) {
	Pair<String, Bounds> nearestNodeBounds = this.getNearestNodeBounds(x, y);
	Double newX = nearestNodeBounds.getValue().getCenterX();
	Double newY = nearestNodeBounds.getValue().getCenterY();
	marker.setLayoutX(newX - marker.getLayoutBounds().getCenterX());
	marker.setLayoutY(newY - marker.getLayoutBounds().getCenterY());
	return nearestNodeBounds.getKey();
    }

    /**
     * Gets the position of the nearest node from coordinates (x, y)
     * @param x		x-value
     * @param y		y-value
     * @return		the bounds of the nearest node as value and its ID as key
     */
    public Pair<String, Bounds> getNearestNodeBounds(Double x, Double y) {
	String nearestId = null;
	Double minDistance = Double.POSITIVE_INFINITY;

	for(String id : this.nodeShapes.keySet()) {
	    Shape s = this.nodeShapes.get(id);
	    Bounds coordinates = s.localToScene(s.getBoundsInLocal());

	    Double nodeX = coordinates.getCenterX();
	    Double nodeY = coordinates.getCenterY();

	    Double distance = Math.sqrt(Math.pow(x - nodeX, 2) + Math.pow(y - nodeY, 2));
	    if(minDistance > distance) {
		minDistance = distance;
		nearestId = id;
	    }
	}
	return new Pair<String, Bounds>(nearestId, this.nodeShapes.get(nearestId).getLayoutBounds());
    }

    /**
     * Calculates the relative position of a point from the MapView pane
     *
     * @param point point of interest
     * @return the coordinates as a pair with X as key and Y as value
     */
    private Pair<Double, Double> calculateRelativePosition(Node point) {
	Double x = this.offsetX + dimension * (point.getLongitude() - this.minLong) / (this.maxLong - this.minLong);
	Double y = this.offsetY + dimension * (this.maxLat - point.getLatitude()) / (this.maxLat - this.minLat);

	return new Pair<Double, Double>(x, y);
    }
}

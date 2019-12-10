package view;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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

    private Map<Pair<String, String>, Set<Line>> roundLine;
    private Map<String, Shape> nodeShapes;
    private Map<SpecialNode, Shape> markers;

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
	this.roundLine = new HashMap<Pair<String, String>, Set<Line>>();
	this.nodeShapes = new HashMap<String, Shape>();
	this.markers = new HashMap<SpecialNode, Shape>();
    }

    public Map<Pair<String, String>, Set<Line>> getRoundLine() {
	return this.roundLine;
    }

    public Map<SpecialNode, Shape> getMarkers() {
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
     * @param edge        the corresponding edge to be drawn on the corresponding
     *                    MapView
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

    public void drawBestPath(BestPath bestPath, Color color, Integer strokeWidth) {
	String startId = bestPath.getStart().getNode().getNodeId();
	String endId = bestPath.getEnd().getNode().getNodeId();

	Set<Line> bestPathLines = new HashSet<>();
	for (Edge edge : bestPath.getPath()) {
	    Line road = drawEdge(edge, color, strokeWidth);
	    road.toBack();
	    bestPathLines.add(road);
	}

	Pair<String, String> entry = new Pair<String, String>(startId, endId);
	this.roundLine.put(entry, bestPathLines);
    }

    /**
     * Draws markers corresponding to checkpoints of a delivery
     *
     * @param delivery   the corresponding delivery to be represented by the markers
     * @param color      the color code for the markers
     * @param markerSize the size of the markers
     */
    public void drawMarker(Delivery delivery, Color color, Integer markerSize) {
	SpecialNode start = delivery.getPickupNode();
	SpecialNode end = delivery.getDeliveryNode();

	for (SpecialNode sn : Arrays.asList(start, end)) {
	    Shape marker = null;

	    Pair<Double, Double> p = calculateRelativePosition(sn.getNode());
	    Double x = p.getKey();
	    Double y = p.getValue();

	    if (sn.getSpecialNodeType() == SpecialNodeType.PICKUP) {
		marker = new Circle(x, y, markerSize / 2);
	    } else if (sn.getSpecialNodeType() == SpecialNodeType.DROPOFF) {
		marker = new Rectangle(x - markerSize / 2, y - markerSize / 2, markerSize, markerSize);
	    } else {
		Polyline triangle = new Polyline();
		triangle.getPoints().addAll(x, y - 2 * markerSize / 3, x - markerSize / 2, y + markerSize / 3,
			x + markerSize / 2, y + markerSize / 3, x, y - 2 * markerSize / 3);
		marker = triangle;
	    }
	    marker.setFill(color);
	    marker.setStroke(Color.BLACK);
	    marker.setStrokeWidth(1);

	    this.getChildren().add(marker);
	    markers.put(sn, marker);
	}
    }

    /**
     * Changes border of markers corresponding to selected delivery
     * 
     * @param deliveryIndex
     */
    public void highlightMarkers(Delivery delivery, Color color) {
	for (SpecialNode s : markers.keySet()) {
	    markers.get(s).setStroke(Color.BLACK);
	    markers.get(s).setStrokeWidth(1);
	}

	SpecialNode pickup = delivery.getPickupNode();
	SpecialNode dropoff = delivery.getDeliveryNode();

	for (SpecialNode sn : Arrays.asList(pickup, dropoff)) {
	    markers.get(sn).setStroke(color);
	    markers.get(sn).setStrokeType(StrokeType.OUTSIDE);
	    markers.get(sn).setStrokeWidth(5);
	}
    }

    /**
     * Clears all markers on map
     */
    public void clearMarkers() {
	for (SpecialNode sn : this.markers.keySet()) {
	    this.getChildren().remove(this.markers.get(sn));
	}
	this.markers = new HashMap<>();
    }

    public void clearRound() {
	for (Pair<String, String> p : this.roundLine.keySet()) {
	    this.getChildren().removeAll(this.roundLine.get(p));
	}
    }

    public String moveMarkerToNearestNode(Shape marker, Double x, Double y) {
	Pair<String, Bounds> nearestNodeBounds = getNearestNodeBounds(x, y);
	Double newX = nearestNodeBounds.getValue().getCenterX();
	Double newY = nearestNodeBounds.getValue().getCenterY();
	marker.setLayoutX(newX - marker.getLayoutBounds().getCenterX());
	marker.setLayoutY(newY - marker.getLayoutBounds().getCenterY());
	return nearestNodeBounds.getKey();
    }

    public Pair<String, Bounds> getNearestNodeBounds(Double x, Double y) {
	String nearestId = null;
	Double minDistance = Double.POSITIVE_INFINITY;

	for (String id : this.nodeShapes.keySet()) {
	    Shape s = this.nodeShapes.get(id);
	    Bounds coordinates = s.localToScene(s.getBoundsInLocal());

	    Double nodeX = coordinates.getCenterX();
	    Double nodeY = coordinates.getCenterY();

	    Double distance = Math.sqrt(Math.pow(x - nodeX, 2) + Math.pow(y - nodeY, 2));
	    if (minDistance > distance) {
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

    public Parent createZoomPane(final Pane pane) {
	final double SCALE_DELTA = 1.1;
	final StackPane zoomPane = new StackPane();

	zoomPane.getChildren().add(this);

	final ScrollPane scroller = new ScrollPane();
	final Group scrollContent = new Group(zoomPane);
	scroller.setContent(scrollContent);

	scroller.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
	    @Override
	    public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
		zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
	    }
	});

	scroller.setPrefViewportWidth(256);
	scroller.setPrefViewportHeight(256);
	pane.setScaleX(1.05);
	pane.setScaleY(1.05);
	
	zoomPane.setOnScroll(new EventHandler<ScrollEvent>() {
	    @Override
	    public void handle(ScrollEvent event) {
		event.consume();

		if (event.getDeltaY() == 0) {
		    return;
		}

		double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
		
		if ( pane.getScaleX() < 1.1 && scaleFactor <1) {
		    return;
		}

		// amount of scrolling in each direction in scrollContent coordinate
		// units
		Point2D scrollOffset = figureScrollOffset(scrollContent, scroller);

		pane.setScaleX(pane.getScaleX() * scaleFactor);
		pane.setScaleY(pane.getScaleY() * scaleFactor);

		// move viewport so that old center remains in the center after the
		// scaling
		repositionScroller(scrollContent, scroller, scaleFactor, scrollOffset);

	    }
	});

	// Panning via drag....
	final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();
	scrollContent.setOnMousePressed(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
	    }
	});

	scrollContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		double deltaX = event.getX() - lastMouseCoordinates.get().getX();
		double extraWidth = scrollContent.getLayoutBounds().getWidth()
			- scroller.getViewportBounds().getWidth();
		double deltaH = deltaX * (scroller.getHmax() - scroller.getHmin()) / extraWidth;
		double desiredH = scroller.getHvalue() - deltaH;
		scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), desiredH)));

		double deltaY = event.getY() - lastMouseCoordinates.get().getY();
		double extraHeight = scrollContent.getLayoutBounds().getHeight()
			- scroller.getViewportBounds().getHeight();
		double deltaV = deltaY * (scroller.getHmax() - scroller.getHmin()) / extraHeight;
		double desiredV = scroller.getVvalue() - deltaV;
		scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), desiredV)));
	    }
	});

	return scroller;
    }

    private Point2D figureScrollOffset(javafx.scene.Node scrollContent, ScrollPane scroller) {
	double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
	double hScrollProportion = (scroller.getHvalue() - scroller.getHmin())
		/ (scroller.getHmax() - scroller.getHmin());
	double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
	double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
	double vScrollProportion = (scroller.getVvalue() - scroller.getVmin())
		/ (scroller.getVmax() - scroller.getVmin());
	double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
	return new Point2D(scrollXOffset, scrollYOffset);
    }

    private void repositionScroller(javafx.scene.Node scrollContent, ScrollPane scroller, double scaleFactor,
	    Point2D scrollOffset) {
	double scrollXOffset = scrollOffset.getX();
	double scrollYOffset = scrollOffset.getY();
	double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
	if (extraWidth > 0) {
	    double halfWidth = scroller.getViewportBounds().getWidth() / 2;
	    double newScrollXOffset = (scaleFactor - 1) * halfWidth + scaleFactor * scrollXOffset;
	    scroller.setHvalue(
		    scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
	} else {
	    scroller.setHvalue(scroller.getHmin());
	}
	double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
	if (extraHeight > 0) {
	    double halfHeight = scroller.getViewportBounds().getHeight() / 2;
	    double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
	    scroller.setVvalue(
		    scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
	} else {
	    scroller.setHvalue(scroller.getHmin());
	}
    }
}

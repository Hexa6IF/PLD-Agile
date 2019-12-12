package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.BestPath;
import model.Delivery;
import model.Edge;
import model.FullMap;
import model.Node;
import model.Round;
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
    private List<Line> roundLine;
    private Map<Integer, Set<Shape>> markers;
    private List<Rectangle> intersections;
    List<Delivery> deliveries;

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
	this.roundLine = new ArrayList<Line>();
	this.markers = new HashMap<Integer, Set<Shape>>();
	this.intersections = new ArrayList<Rectangle>();
	this.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    /**
     *
     * @return the Intersections
     */
    public List<Rectangle> getIntersections() {
	return intersections;
    }

    /**
     *
     * @return the Markers
     */
    public Map<Integer, Set<Shape>> getMarkers() {
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
	    drawPath(edge,color, strokeWidth);
	    nodes.add(edge.getStart());
	    nodes.add(edge.getEnd());}
	drawNodes(nodes, 4);
    }

    /**
     * Draws all nodes
     *
     * @param nodes       the set of all the nodes in
     *                    MapView
     * @param Width       the width of the node drawn
     */
    public void drawNodes(Collection<Node> nodes, Integer width) {
	for(Node node : nodes) {
	    Pair<Double, Double> p = calculateRelativePosition(node);
	    Rectangle nodeRect = new Rectangle(p.getKey(), p.getValue(), width, width);
	    nodeRect.setFill(Color.TRANSPARENT);
	    intersections.add(nodeRect);
	    this.getChildren().add(nodeRect);
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
    public Line drawPath(Edge edge, Color color, Integer strokeWidth) {
	Pair<Double, Double> p1 = calculateRelativePosition(edge.getStart());
	Pair<Double, Double> p2 = calculateRelativePosition(edge.getEnd());
        Line path = new Line(p1.getKey(), p1.getValue(), p2.getKey(), p2.getValue());
        path.setStroke(color);
	path.setStrokeWidth(strokeWidth);
	this.getChildren().add(path);
	return path;
    }

    /**
     * Draws markers corresponding to checkpoints of a delivery
     *
     * @param delivery   the corresponding delivery to be represented by the markers
     * @param color      the color code for the markers
     * @param markerSize the size of the markers
     */
    
    public void drawMarker(Delivery delivery, Color color, Integer markerSize) {
	Integer index = delivery.getDeliveryIndex();

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

	    if (markers.get(index) == null) {
		markers.put(index, new HashSet<Shape>());
	    }
	    markers.get(index).add(marker);
	}
    }

    /**
     * Draws the paths followed by a round of deliveries
     *
     * @param round a list of best paths to take to optimally complete the round
     */
    public void drawRound(Round round) {
	Color c = Color.web("#4a80f5");
	this.getChildren().removeAll(this.roundLine);
	this.roundLine = new ArrayList<Line>();
	for (BestPath bestPath : round.getResultPath()) {
	    List<Edge> path = bestPath.getPath();
	    Edge longestEdge=path.get(0);
	    for (Edge edge : path) {
		Line road = drawPath(edge,c, 8);
		road.toBack();
		this.roundLine.add(road); 
		if(edge.getDistance()>longestEdge.getDistance()) {
		    longestEdge=edge;
		}
		}
	     Node end=longestEdge.getEnd();
	    Node start=longestEdge.getStart();
	    Pair<Double, Double> end1=calculateRelativePosition(end);
	    Pair<Double, Double> start1=calculateRelativePosition(start);
	    System.out.println("start end coeff "+(end1.getKey()-start1.getKey()/end1.getValue()-start1.getValue());
            drawArrowHead(start1,end1);	
	    }
	}
    private void drawArrowHead(Pair<Double, Double> start,Pair<Double, Double> end)
    {
	Color c = Color.web("#4a80f5");
	double l=20;
	Line righthead;
	Line lefthead;
	double x=end.getKey();    
	double y=end.getValue();
	double x1=start.getKey();    
	double y1=start.getValue();
	
	double phi = Math.toRadians(10);
	double alpha = Math.atan2(y-y1,x-x1);
	/*if((x>x1&&y>y1)||(x<x1&&y>y1))
	{
	    alpha = alpha+Math.PI;
	    phi=phi+Math.PI;
	}*/
	System.out.println("alpha is "+Math.toDegrees(alpha));
	double gamma=alpha-phi; 
        double x2=x+l*Math.cos(gamma);
	double y2=y+l*Math.sin(gamma);
	double x3=x+l*Math.sin(gamma);
	double y3=y+l*Math.cos(gamma);
            /*x = x2 -(20*Math.cos(rho));
            y = y2- (20*Math.sin(rho));
            rho = theta - phi;*/
          righthead=new Line(x,y,x2,y2);
	  righthead.setStroke(c);
	  righthead.setStrokeWidth(3);
	  lefthead=new Line(x,y,x3,y3);
	  lefthead.setStroke(c);
	  lefthead.setStrokeWidth(3);
          this.getChildren().addAll(righthead,lefthead);
 }
    

    /** 
     * Changes border of markers corresponding to selected delivery
     * 
     * @param deliveryIndex
     */
    public void highlightSelectedMarkers(Integer deliveryIndex) {
	for (Integer id : markers.keySet()) {
	    for (Shape s : markers.get(id)) {
		if (id == deliveryIndex) {
		    s.setStroke(Color.DODGERBLUE);
		    s.setStrokeType(StrokeType.OUTSIDE);
		    s.setStrokeWidth(5);
		} else {
		    s.setStroke(Color.BLACK);
		    s.setStrokeWidth(1);
		}
	    }
	}
    }

    /**
     * Clears all markers on map
     */
    public void clearMarkers() {
	for (Integer id : this.markers.keySet()) {
	    this.getChildren().removeAll(this.markers.get(id));
	}
	this.markers = new HashMap<>();
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

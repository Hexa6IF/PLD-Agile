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
    private List<Line> arrows;
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
	this.arrows = new ArrayList<Line>();
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
	this.getChildren().removeAll(this.arrows);
	this.arrows.clear();
	this.roundLine = new ArrayList<Line>();
	System.out.println(round.getResultPath().size());
	for (BestPath bestPath : round.getResultPath()) {
	    List<Edge> path = bestPath.getPath();
//	    Edge longestEdge=path.get(0);
	    Edge edgeArrow = path.get(path.size()/2);
	    for (Edge edge : path) {
		Line road = drawPath(edge,c, 8);
		road.toBack();
		this.roundLine.add(road); 
//		if(edge.getDistance()>=longestEdge.getDistance()) {
//		    longestEdge=edge;
//		}
	    }
	    Node end=edgeArrow.getEnd();
	    Node start=edgeArrow.getStart();
	    Pair<Double, Double> end1=calculateRelativePosition(end);
	    Pair<Double, Double> start1=calculateRelativePosition(start);
//	    System.out.println("start end coeff "+(end1.getValue()-start1.getValue()/end1.getKey()-start1.getKey()));
	    System.out.println("start : "+start1.toString());
	    System.out.println("end : "+end1.toString());
	    System.out.println(path.toString());
	    System.out.println("");
	    Double middle_x = end1.getKey() - ((end1.getKey()-start1.getKey())/2);
	    Double middle_y = end1.getValue() - ((end1.getValue()-start1.getValue())/2);
	    Pair<Double, Double> middle = new Pair<Double, Double>(middle_x, middle_y);
            drawArrowHead(start1,middle);	
	    }
	}
    private void drawArrowHead(Pair<Double, Double> start,Pair<Double, Double> end)
    {
	Color c = Color.web("#4a80f5");
	double ARROW_SIZE=20;
	Line righthead;
	Line lefthead;
	double end_x=end.getKey();    
	double end_y=end.getValue();
	double start_x=start.getKey();    
	double start_y=start.getValue();
	
	final Double arrowAngle = 10.0;
//	double phi = Math.toRadians(arrowAngle);
//	double alpha = Math.atan2(end_y-start_y,end_x-start_x);
//	System.out.println("alpha is "+Math.toDegrees(alpha));
//	double gammaRight;
//	double gammaLeft;
//	if (alpha>=0) {
//	    gammaRight = alpha-phi;
//	    gammaLeft = alpha+phi;
//	}
//	else {
//	    gammaRight = alpha+phi;
//	    gammaLeft = alpha-phi;
//	}
//	
//	System.out.println("gammaRight is "+Math.toDegrees(gammaRight));
//	System.out.println("gammaLeft is "+Math.toDegrees(gammaLeft));
//
//	double right_x;
//	double right_y;
//	double left_x;
//	double left_y;
//	if (alpha >= 0 && alpha <= Math.PI/2) {
//	    right_x = end_x - l*Math.sin(gammaRight);
//	    right_y=end_y-l*Math.cos(gammaRight);
//	    left_x=end_x-l*Math.sin(gammaLeft);
//	    left_y=end_y-l*Math.cos(gammaLeft);
//	}
//	else if (alpha > Math.PI/2 && alpha <= Math.PI) {
//	    right_x = end_x + l*Math.sin(gammaRight);
//	    right_y=end_y+l*Math.cos(gammaRight);
//	    left_x=end_x+l*Math.sin(gammaLeft);
//	    left_y=end_y+l*Math.cos(gammaLeft);
//	}
//	else if (alpha > -1.0*Math.PI/2 && alpha < 0) {
//	    right_x = end_x - l*Math.sin(gammaRight);
//	    right_y=end_y+l*Math.cos(gammaRight);
//	    left_x=end_x+l*Math.sin(gammaLeft);
//	    left_y=end_y+l*Math.cos(gammaLeft);
//	}
//	else {
//	    right_x = end_x - l*Math.sin(gammaRight);
//	    right_y=end_y-l*Math.cos(gammaRight);
//	    left_x=end_x-l*Math.sin(gammaLeft);
//	    left_y=end_y-l*Math.cos(gammaLeft);
//	}
//	System.out.println("right x : "+right_x);
//	System.out.println("right y : "+right_y);
//	System.out.println("left x : "+left_x);
//	System.out.println("left y : "+left_y);
	//ArrowHead
        double angle = Math.atan2((end_y - start_y), (end_x - start_x)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROW_SIZE + end_x;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROW_SIZE + end_y;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * ARROW_SIZE + end_x;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * ARROW_SIZE + end_y;
        
          //righthead=new Line(right_x,right_y,end_x,end_y);
        righthead=new Line(x1,y1,end_x,end_y);
	  righthead.setStroke(c);
	  righthead.setStrokeWidth(3);
	  //lefthead=new Line(left_x,left_y,end_x,end_y);
	  lefthead=new Line(x2,y2,end_x,end_y);
	  lefthead.setStroke(c);
	  lefthead.setStrokeWidth(3);
	  this.arrows.add(righthead);
	  this.arrows.add(lefthead);
          this.getChildren().addAll(righthead, lefthead);
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

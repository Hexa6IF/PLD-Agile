package view;

import java.util.List;

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

	this.offsetX = 0.025 * this.width;
	this.offsetY = 0.025 * this.height;
    }

    public Pane getMapView() {
	draw();
	this.mapView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), 
		BorderWidths.DEFAULT, new Insets(10))));
	return this.mapView;
    }

    private void draw() {
	Double dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);
	for(Edge edge : map.getEdgeList()) {
	    this.mapView.getChildren().add(createRoad(edge, dimension));
	}
    }

    private Line createRoad(Edge edge, Double dimension) {
	Double x1 = this.offsetY + dimension * ((edge.getStart().getLongitude() - this.map.getMinLong()) / 
		this.map.getRangeLongitude());
	Double y1 = this.offsetX + dimension * ((edge.getStart().getLatitude() - this.map.getMinLat()) / 
		this.map.getRangeLatitude());
	
	Double x2 = this.offsetY + dimension * ((edge.getEnd().getLongitude() - this.map.getMinLong()) / 
		this.map.getRangeLongitude());
	Double y2 = this.offsetX + dimension * ((edge.getEnd().getLatitude() - this.map.getMinLat()) / 
		this.map.getRangeLatitude());
	
	Line road = new Line(x1, y1, x2, y2);
	road.setStrokeWidth(2);
	Tooltip.install(road, new Tooltip(edge.getStreetName()));
	
	return road;
    }
    
    private void drawMarkers(List <BestPath> bestPaths, List<Delivery> deliveries){
	Double dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);
	for(BestPath bestPath : bestPaths) {
	    this.mapView.getChildren().add(createMarker(bestPath, dimension));
	}
	
	for(Delivery delivery : deliveries ) {
    }
    
    private Circle createMarker(BestPath bestPath, Double dimension, Paint paint) {
	Double x = this.offsetX + dimension * ((bestPath.getStart().getNode().getLongitude() - this.map.getMinLong()) / 
		this.map.getRangeLongitude());
	Double y = this.offsetY + dimension * (( this.map.getMaxLat() - bestPath.getStart().getNode().getLatitude()) / 
		this.map.getRangeLatitude());
	Circle marker = new Circle(x, y, 5.0, paint);
	String textToDisplay = bestPath.getStart().toString() + " - " + bestPath.getEnd().toString() ;
	Tooltip.install(marker, new Tooltip(textToDisplay));
	return marker;
    }
    
    
}

package view;

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
import model.Edge;
import model.FullGraph;

/**
 * Class for displaying full map
 * 
 * @author sadsitha
 */
public class MapView {

    private Pane mapView;

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
    public MapView(FullGraph mapGraph, Double screenHeight, Double screenWidth ) {
	this.mapView = new Pane();
	this.mapGraph = mapGraph;

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
	for(Edge e : (Edge[])mapGraph.getEdges()) {
	    this.mapView.getChildren().add(createRoad(e, dimension));
	}
    }

    private Line createRoad(Edge edge, Double dimension) {
	Double x1 = this.offsetY + dimension * ((edge.getNodeOrigin().getLongitude() - this.mapGraph.getMinLongitude()) / 
		this.mapGraph.getRangeLongitude());
	Double y1 = this.offsetX + dimension * ((edge.getNodeOrigin().getLatitude() - this.mapGraph.getMinLatitude()) / 
		this.mapGraph.getRangeLatitude());
	
	Double x2 = this.offsetY + dimension * ((edge.getNodeDest().getLongitude() - this.mapGraph.getMinLongitude()) / 
		this.mapGraph.getRangeLongitude());
	Double y2 = this.offsetX + dimension * ((edge.getNodeDest().getLatitude() - this.mapGraph.getMinLatitude()) / 
		this.mapGraph.getRangeLatitude());
	
	Line road = new Line(x1, y1, x2, y2);
	road.setStrokeWidth(2);
	Tooltip.install(road, new Tooltip(edge.getStreetName()));
	
	return road;
    }
}

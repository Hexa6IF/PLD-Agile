package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.shape.Circle;
import model.Node;

/**
 * Class for displaying full map
 * 
 * @author sadsitha
 */
public class FullGraphMap {

    private XYChart<Number, Number> chart;
    private List<Node> nodeList;
    private List<Circle> markerList;
    private XYChart.Series series = new XYChart.Series();

    /**
     * Constructor
     * 
     * @param nodeList List of nodes to display on graph
     */
    public FullGraphMap(List<Node> nodeList) {
	this.nodeList = nodeList;
	this.markerList = new ArrayList<Circle>();
    }

    /**
     * Constructor - with empty parameters
     * 
     */
    public FullGraphMap() {
	this.nodeList = new ArrayList<Node>();
	this.markerList = new ArrayList<Circle>();
	
	/* Create example node list */
	this.nodeList.add(new Node(1l, 10.0f, 20.0f));
	this.nodeList.add(new Node(2l, 20.0f, 10.0f));
	this.nodeList.add(new Node(3l, 15.0f, 15.0f));
	this.nodeList.add(new Node(4l, 20.0f, 20.0f));
	this.nodeList.add(new Node(5l, 17.0f, 20.0f));
    }

    /*
     * Returns a chart representing the map
     * 
     * @return chart XYChart containing current map
     */
    public XYChart<Number, Number> getMap() {
	this.mapNodes();
	return this.chart;
    }

    private void mapNodes() {

	Float maxLatitude = Float.NEGATIVE_INFINITY;
	Float minLatitude = Float.POSITIVE_INFINITY;
	Float maxLongitude = Float.NEGATIVE_INFINITY;
	Float minLongitude = Float.POSITIVE_INFINITY;

	/* Get max/min longitude/latitude from node list */
	for (Node node : this.nodeList) {
	    if (node.getLatitude() > maxLatitude) {
		maxLatitude = node.getLatitude();
	    }

	    if (node.getLatitude() < minLatitude) {
		minLatitude = node.getLatitude();
	    }

	    if (node.getLongitude() > maxLongitude) {
		maxLongitude = node.getLongitude();
	    }

	    if (node.getLongitude() < minLongitude) {
		minLongitude = node.getLongitude();
	    }
	}
	
	/* get step */
	Float stepX = ((maxLongitude - minLongitude)/10);
	Float stepY = ((maxLatitude - minLatitude)/10);

	NumberAxis xAxis = new NumberAxis(minLongitude-stepX, maxLongitude+stepX, stepX);
	NumberAxis yAxis = new NumberAxis(minLatitude-stepY, maxLatitude+stepY, stepY);
	this.chart = new LineChart<Number, Number>(xAxis, yAxis);
	this.parametriseChart();


	for (Node node : this.nodeList) {

		Series<Number, Number> intersectionMarkers = new Series<Number, Number>();
		intersectionMarkers.setName("Intersection markers");
	    intersectionMarkers.getData()
		    .add(new LineChart.Data<Number, Number>(node.getLongitude(), node.getLatitude()));
	    this.chart.getData().add(intersectionMarkers);
	}


    }

    /*
     * Parametrises chart to desired look
     * 
     */
    private void parametriseChart() {
	this.chart.setLegendVisible(false);
	this.chart.setHorizontalGridLinesVisible(false);
	this.chart.setVerticalGridLinesVisible(false);
	this.chart.getYAxis().setTickLabelsVisible(false);
	this.chart.getYAxis().setOpacity(0);
	this.chart.getXAxis().setTickLabelsVisible(false);
	this.chart.getXAxis().setOpacity(0);
    }

}

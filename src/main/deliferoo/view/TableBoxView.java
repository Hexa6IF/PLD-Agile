package view;

import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.BestPath;
import model.Delivery;
import model.Round;
import model.SpecialNode;

/**
 * Class for displaying table of special nodes (pickup and delivery nodes)
 *
 * @author sadsitha
 */
public class TableBoxView extends TableView<SpecialNodeTextView> {

    /**
     * Constructor
     *
     * @param height preferred height of node
     * @param width  preferred width of node
     */
    public TableBoxView(Double height, Double width) {
	super();
	this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	this.setTableColumns();

	this.setPrefHeight(height);
	this.setPrefWidth(width);
    }

    /**
     * Updates the order of the table box view according to calculated round
     * 
     * @param round
     * @param deliveryColourMap
     */
    public void updateSpecialNodes(Round round, Map<Delivery, Color> deliveryColourMap) {
	this.getItems().clear();
	Integer counter = 0;
	ObservableList<SpecialNodeTextView> specialNodeTextViews = this.getItems();
	for (BestPath bestPath : round.getResultPath()) {
	    if (counter++ == 0) {
		SpecialNode startSpecialNode = bestPath.getStart();
		Color startDeliveryColor = deliveryColourMap.get(startSpecialNode.getDelivery());
		SpecialNodeTextView departureNode = new SpecialNodeTextView(startSpecialNode, startDeliveryColor);
		specialNodeTextViews.add(departureNode);

	    }
	    SpecialNode endSpecialNode = bestPath.getEnd();
	    Color endDeliveryColor = deliveryColourMap.get(endSpecialNode.getDelivery());
	    SpecialNodeTextView arrivalNode = new SpecialNodeTextView(endSpecialNode, endDeliveryColor);
	    specialNodeTextViews.add(arrivalNode);
	}
    }

    /**
     * Adds a SpecialNodeTextView to the table
     * 
     * @param specialNode
     * @param color
     */
    public void addSpecialNode(SpecialNode specialNode, Color color) {
	ObservableList<SpecialNodeTextView> specialNodeTextViews = this.getItems();
	SpecialNodeTextView specialNodeTextView = new SpecialNodeTextView(specialNode, color);
	specialNodeTextViews.add(specialNodeTextView);
    }

    /*
     * Sets the table columns
     * 
     */
    private void setTableColumns() {
	TableColumn<SpecialNodeTextView, String> indexColumn = new TableColumn<SpecialNodeTextView, String>(
		"Delivery index");
	indexColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeTextView, String>("deliveryIndex"));
	indexColumn.prefWidthProperty().bind(this.widthProperty().divide(5));

	TableColumn<SpecialNodeTextView, String> typeColumn = new TableColumn<SpecialNodeTextView, String>("Type");
	typeColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeTextView, String>("type"));
	typeColumn.prefWidthProperty().bind(this.widthProperty().divide(5));

	TableColumn<SpecialNodeTextView, Number> durationColumn = new TableColumn<SpecialNodeTextView, Number>(
		"Duration (min)");
	durationColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeTextView, Number>("duration"));
	durationColumn.prefWidthProperty().bind(this.widthProperty().divide(5));

	TableColumn<SpecialNodeTextView, String> timeColumn = new TableColumn<SpecialNodeTextView, String>("Time");
	timeColumn.setCellValueFactory(new PropertyValueFactory<SpecialNodeTextView, String>("time"));
	timeColumn.prefWidthProperty().bind(this.widthProperty().divide(5));

	TableColumn<SpecialNodeTextView, String> colorColumn = this.setColorColumn();
	colorColumn.prefWidthProperty().bind(this.widthProperty().divide(5));

	this.getColumns().setAll(indexColumn, typeColumn, durationColumn, timeColumn, colorColumn);
    }

    /*
     * Set color column - use color of NodeTextView to create custom css to set
     * background color of cells
     * 
     * @return colorColumn
     */
    private TableColumn<SpecialNodeTextView, String> setColorColumn() {
	TableColumn<SpecialNodeTextView, String> colorColumn = new TableColumn<SpecialNodeTextView, String>(
		"Color code");
	colorColumn.setCellFactory(
		new Callback<TableColumn<SpecialNodeTextView, String>, TableCell<SpecialNodeTextView, String>>() {
		    @Override
		    public TableCell<SpecialNodeTextView, String> call(TableColumn<SpecialNodeTextView, String> param) {
			return new TableCell<SpecialNodeTextView, String>() {
			    @Override
			    protected void updateItem(String item, boolean empty) {
				if (!empty) {
				    /* get index of item in collection */
				    int currentIndex = indexProperty().getValue() < 0 ? 0 : indexProperty().getValue();
				    /* get color of item */
				    Color color = param.getTableView().getItems().get(currentIndex).getColor();
				    /* set background color of cell */
				    Double r = color.getRed() * 255;
				    Double g = color.getGreen() * 255;
				    Double b = color.getBlue() * 255;
				    setStyle("-fx-background-color: rgb(" + r + "," + g + ", " + b + ");");
				}
			    }
			};
		    }

		});
	return colorColumn;
    }
}